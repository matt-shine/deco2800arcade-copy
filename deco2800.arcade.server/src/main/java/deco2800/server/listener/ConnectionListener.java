package deco2800.server.listener;

import java.util.Set;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import deco2800.arcade.protocol.connect.ConnectionRequest;
import deco2800.arcade.protocol.connect.ConnectionResponse;
import deco2800.server.database.DatabaseException;
import deco2800.server.database.HashStorage;


public class ConnectionListener extends Listener {
	//list of all connected users
	private Set<String> connectedUsers;
	private boolean initialised = false;
	private HashStorage hashStorage = new HashStorage();
	
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
			
			if (initialised == false) {
				try {
					hashStorage.initialise();
				} catch (DatabaseException e) {
					connection.sendTCP(ConnectionResponse.ERROR);
					e.printStackTrace();
				}
				initialised = true;
			}
			
			try {
				if (hashStorage.checkPassword(request.username, request.password) == true) {
					connection.sendTCP(ConnectionResponse.OK);
					connectedUsers.add(request.username);
				} else {
					connection.sendTCP(ConnectionResponse.REFUSED);
				}
			} catch (DatabaseException e) {
				connection.sendTCP(ConnectionResponse.ERROR);
				e.printStackTrace();
			}
		}
	}

	
}
