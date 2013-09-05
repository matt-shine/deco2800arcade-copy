package deco2800.server.listener;

import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.SecretKey;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import deco2800.arcade.protocol.connect.ClientKeyExchange;
import deco2800.arcade.protocol.connect.ConnectionRequest;
import deco2800.arcade.protocol.connect.ConnectionResponse;
import deco2800.arcade.protocol.connect.ServerKeyExchange;
import deco2800.arcade.protocol.connect.SessionKeyExchange;
import deco2800.server.SecretGenerator;
import deco2800.server.Session;
import deco2800.server.SessionManager;

public class ConnectionListener extends Listener {
	//list of all connected users
	private SessionManager connectedSessions;
	
	public ConnectionListener(SessionManager sessionManager){
		this.connectedSessions = sessionManager;
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

		if (object instanceof ConnectionRequest) {
			ConnectionRequest request = (ConnectionRequest) object;

			connection.sendTCP(ConnectionResponse.OK);
			
			// TODO send ServerKeyExchange encrypted/signed with
			// our server certificate
			ServerKeyExchange serverKeyExchange = new ServerKeyExchange();
			try {
				// FIXME add key values
				serverKeyExchange.setServerKey(null, null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if (object instanceof ClientKeyExchange) {
			ClientKeyExchange request = (ClientKeyExchange) object;
			
			PublicKey clientPublicKey = null;
			try {
				// FIXME get server private key
				PrivateKey serverPrivateKey = null;
				clientPublicKey = request.getClientKey(serverPrivateKey);
			} catch (Exception e) {
				e.printStackTrace();
				// TODO it failed so ask client to resend
			}
			
			if(clientPublicKey != null) {
				SecretKey sessionKey = SecretGenerator.generateSecret();
				
				// Store session details
				Session session = new Session(sessionKey, clientPublicKey);
				this.connectedSessions.add(session);
				
				// Send session key to client
				SessionKeyExchange sessionKeyExchange = new SessionKeyExchange();
				try {
					sessionKeyExchange.setSessionKey(sessionKey, clientPublicKey);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				connection.sendTCP(sessionKeyExchange);
			}
		}
	}

}
