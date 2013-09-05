package deco2800.arcade.client.network;

import java.io.IOException;
import java.security.Key;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.SealedObject;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import deco2800.arcade.client.network.listener.ConnectionListener;
import deco2800.arcade.client.network.listener.NetworkListener;
import deco2800.arcade.protocol.NetworkObject;
import deco2800.arcade.protocol.Protocol;
import deco2800.arcade.protocol.SealedListenerProxy;
import deco2800.arcade.protocol.Sealer;

// FIXME extending Listener may /not/ be the best way to go
// since java supports only linear inheritance
public class NetworkClient {

	private Client client;
	private SealedListenerProxy sealedListener;
	private Sealer sealer;
	private SessionModel session;

	// Shared secret for this session
	private Key secret;

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
			client.connect(5000, serverAddress, tcpPort, udpPort);
			
			Protocol.register(client.getKryo());
		} catch (IOException e) {
			throw new NetworkException("Unable to connect to the server", e);
		}
		
		// Use sealed listener as proxy layer to encrypt/decrypt transmissions
		// FIXME need to support adding key later
		sealer = new Sealer(null);
		sealedListener = new SealedListenerProxy(sealer);

		// Create session
		// FIXME get keys
		session = new SessionModel(null, null);
		
		// Add this as listener
		this.client.addListener(new ConnectionListener(session));
		this.client.addListener(sealedListener);
	}

	/**
	 * Sends a NetworkObject over TCP
	 * 
	 * @param object
	 * @throws NotAuthenticatedException
	 */
	public void sendNetworkObject(NetworkObject object) {
		// Check whether we have an authenticated session yet
		if (secret == null) {
//			throw new NotAuthenticatedException();
		}

		// Encrypt the object
		SealedObject sealedObject = null;
		try {
			sealedObject = sealer.seal(object);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// FIXME first check object is registered
		this.client.sendTCP(sealedObject);
	}

	/**
	 * Adds a listener to the network client
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
}
