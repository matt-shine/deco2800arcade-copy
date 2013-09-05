package deco2800.server.listener;

import java.security.PublicKey;
import java.util.Set;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import deco2800.arcade.protocol.connect.ClientKeyExchange;
import deco2800.arcade.protocol.connect.ConnectionRequest;
import deco2800.arcade.protocol.connect.ConnectionResponse;

public class ConnectionListener extends Listener {
	//list of all connected users
	private Set<String> connectedUsers;
	
	public ConnectionListener(Set<String> connectedUsers){
		this.connectedUsers = connectedUsers;
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
			connectedUsers.add(request.username);

			connection.sendTCP(ConnectionResponse.OK);
			
			// TODO send ServerKeyExchange encrypted/signed with
			// our server certificate
		}
		
		if (object instanceof ClientKeyExchange) {
			ClientKeyExchange request = (ClientKeyExchange) object;
			
			PublicKey clientPublicKey = null;
			try {
				clientPublicKey = request.extractClientKey(null);
			} catch (Exception e) {
				e.printStackTrace();
				// TODO it failed so ask client to resend
			}
			
			// TODO add client public key to some sessionId/playerId/sessionInfo mapping
		}
	}

}
