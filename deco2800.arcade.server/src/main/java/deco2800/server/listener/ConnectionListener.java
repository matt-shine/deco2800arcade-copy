package deco2800.server.listener;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.SecretKey;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import deco2800.arcade.protocol.CertificateHandler;
import deco2800.arcade.protocol.connect.ClientKeyExchange;
import deco2800.arcade.protocol.connect.ConnectionRequest;
import deco2800.arcade.protocol.connect.ConnectionResponse;
import deco2800.arcade.protocol.connect.HandshakeRequest;
import deco2800.arcade.protocol.connect.ServerKeyExchange;
import deco2800.arcade.protocol.connect.SessionKeyExchange;
import deco2800.server.SecretGenerator;
import deco2800.server.Session;
import deco2800.server.SessionManager;

public class ConnectionListener extends Listener {
	// Maintain a collection of the sessions
	private SessionManager connectedSessions;
	private KeyPair serverKeyPair;

	public ConnectionListener(SessionManager sessionManager, KeyPair keyPair) {
		this.connectedSessions = sessionManager;
		this.serverKeyPair = keyPair;
	}

	@Override
	/**
	 * takes Connection object called connection and an Object called object
	 * checks if object is an instance of ConnectionRequest. If true prints out
	 * connection feedback and adds the user to the list of connected users.
	 * Then sends a response to the users machine and prints out Connection
	 * Granted. Else do nothing.
	 */
	public void received(Connection connection, Object object) {
		super.received(connection, object);

		if (object instanceof HandshakeRequest) {
			HandshakeRequest request = (HandshakeRequest) object;
			generateKeyPair();

			connection.sendTCP(ConnectionResponse.OK);

			// Send server public key certified with the server certificate
			PublicKey serverCert = CertificateHandler.getServerCertificate();
			ServerKeyExchange serverKeyExchange = new ServerKeyExchange();
			try {
				serverKeyExchange.setServerKey(serverKeyPair.getPublic(),
						serverCert);
			} catch (Exception e) {
				e.printStackTrace();
			}
			connection.sendTCP(serverKeyExchange);
		}

		if (object instanceof ClientKeyExchange) {
			ClientKeyExchange request = (ClientKeyExchange) object;

			PublicKey clientPublicKey = null;
			try {
				// FIXME get server private key
				PrivateKey serverPrivateKey = this.serverKeyPair.getPrivate();
				clientPublicKey = request.getClientKey(serverPrivateKey);
			} catch (Exception e) {
				e.printStackTrace();
				// TODO it failed so ask client to resend
			}

			if (clientPublicKey != null) {
				SecretKey sessionKey = SecretGenerator.generateSecret();

				// Store session details
				Session session = new Session(sessionKey, clientPublicKey);
				this.connectedSessions.add(session);

				// Send session key to client
				SessionKeyExchange sessionKeyExchange = new SessionKeyExchange();
				try {
					sessionKeyExchange.setSessionKey(sessionKey,
							clientPublicKey);
				} catch (Exception e) {
					e.printStackTrace();
				}

				connection.sendTCP(sessionKeyExchange);
			}
		}
	}

	private void generateKeyPair() {
		KeyPairGenerator kpg = null;
		try {
			kpg = KeyPairGenerator.getInstance("RSA");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		if (kpg != null) {
			kpg.initialize(2048);
			KeyPair keyPair = kpg.generateKeyPair();

			this.serverKeyPair = keyPair;
		} else {
			// Something went wrong
			this.serverKeyPair = null;
		}
	}

}
