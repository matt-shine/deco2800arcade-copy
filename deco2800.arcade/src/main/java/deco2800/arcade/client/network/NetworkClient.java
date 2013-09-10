package deco2800.arcade.client.network;

import java.io.IOException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.SealedObject;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import deco2800.arcade.client.network.listener.ConnectionListener;
import deco2800.arcade.client.network.listener.NetworkListener;
import deco2800.arcade.protocol.CertificateHandler;
import deco2800.arcade.protocol.NetworkObject;
import deco2800.arcade.protocol.Protocol;
import deco2800.arcade.protocol.SealedListenerProxy;
import deco2800.arcade.protocol.SymmetricSealer;

/**
 * Encapsulates the underlying network calls, providing a layer of security so
 * that messages can be transmitted securely. The class allows listeners to
 * register as handlers for incoming messages.
 * 
 */
public class NetworkClient {

	private Client client;
	private SealedListenerProxy sealedListener;
	private SymmetricSealer sealer;
	private SessionModel session;

	// Client public and private keys help handshake with server
	private KeyPair keyPair;
	private String algorithm = "RSA";

	// Shared secret for this session
	private Key secret;

	// Time before the connection is aborted
	private static final int TIMEOUT = 5000;
	
	/**
	 * Creates a new network client
	 * 
	 * @param serverAddress
	 * @param tcpPort
	 * @param udpPort
	 * @throws NetworkException
	 */
	public NetworkClient(String serverAddress, int tcpPort, int udpPort)
			throws NetworkException {
		this.client = new Client();
		this.client.start();

		try {
			client.connect(TIMEOUT, serverAddress, tcpPort, udpPort);
			Protocol.register(client.getKryo());
		} catch (IOException e) {
			throw new NetworkException("Unable to connect to the server", e);
		}

		// FIXME need to support adding key later. Currently we never actually
		// add the SecretKey to the sealer.

		// Use sealed listener as proxy layer to encrypt/decrypt transmissions
		sealer = new SymmetricSealer(null);
		sealedListener = new SealedListenerProxy(sealer);

		// Create session and initialise with keys
		generateKeyPair();
		session = new SessionModel(CertificateHandler.getClientCertificate(),
				this.keyPair);

		// Hand the session over to the connection listener. The session is used
		// to store state information as part of the handshaking process.
		this.client.addListener(new ConnectionListener(session));

		// Tell kryonet we want sealedListener to handle incoming messages. It
		// unseals secure messages and forwards the message to other listeners.
		this.client.addListener(sealedListener);
	}

	/**
	 * Sends a NetworkObject over TCP
	 * 
	 * The message is sealed using a symmetric session key before transmission
	 * and is sent securely to the server.
	 * 
	 * @param object
	 */
	public void sendNetworkObject(NetworkObject object) {
		// Check whether we have an authenticated session yet
		if (secret == null) {
			// TODO determine whether other teams want to check for failure.
			// throw new NotAuthenticatedException();
		}

		// Encrypt the object before transmission
		SealedObject sealedObject = null;
		try {
			sealedObject = sealer.seal(object);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Send the sealed object across the network
		// TODO may want to check that the object class has actually been
		// registered.
		this.client.sendTCP(sealedObject);
	}

	/**
	 * Adds a listener to the network client
	 * 
	 * Secure messages are unsealed before they get to the listener.
	 * 
	 * @param listener
	 */
	public void addListener(NetworkListener listener) {
		sealedListener.addListener(listener);
	}

	/**
	 * Removes a listener from the network client
	 * 
	 * @param listener
	 */
	public void removeListener(NetworkListener listener) {
		sealedListener.remove(listener);
	}

	/**
	 * Generate public and private key pair for the client. The keys are used as
	 * part of the handshake process that sets up a symmetric session.
	 */
	private void generateKeyPair() {
		KeyPairGenerator kpg = null;
		try {
			kpg = KeyPairGenerator.getInstance(algorithm);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		if (kpg != null) {
			kpg.initialize(2048);
			KeyPair keyPair = kpg.generateKeyPair();

			this.keyPair = keyPair;
		} else {
			// Something went wrong
			this.keyPair = null;
		}
	}
}
