package deco2800.server.listener;

import java.util.Set;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import deco2800.arcade.protocol.connect.ConnectionRequest;
import deco2800.arcade.protocol.connect.ConnectionResponse;
import deco2800.server.database.DatabaseException;
import deco2800.server.database.HashStorage;
import deco2800.server.database.PlayerDatabaseManager;

public class ConnectionListener extends Listener {
	// list of all connected users
	private Set<String> connectedUsers;
	private boolean initialised = false;
	private HashStorage hashStorage = new HashStorage();

	public ConnectionListener(Set<String> connectedUsers) {
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
			ConnectionResponse response = new ConnectionResponse();
			response.register = false;

			if (initialised == false) {
				try {
					hashStorage.initialise();
				} catch (DatabaseException e) {
					response.playerID = -2;
					connection.sendTCP(response);
					e.printStackTrace();
				}
				initialised = true;
			}

			if (request.register) {
				try {
					response.playerID = hashStorage.registerUser(request.username, request.password);
					response.register = true;
					connection.sendTCP(response);
				} catch (DatabaseException e) {
					e.printStackTrace();
				}
			} else {
				try {
					if (hashStorage.checkPassword(request.username,
							request.password) == true) {
						response.playerID = hashStorage
								.getPlayerID(request.username);
						
						//For testing:
						if (request.username.equals("debug1")){
							response.playerID = 888;
						} else if (request.username.equals("debug")){
							response.playerID = 999;
						} else if (request.username.equals("debug2")){
							response.playerID = 777;
						}						
						
						connection.sendTCP(response);
						connectedUsers.add(request.username);
					} else {
						response.playerID = -1;
						connection.sendTCP(response);
					}
				} catch (DatabaseException e) {
					response.playerID = -2;
					connection.sendTCP(response);
					e.printStackTrace();
				}
			}
		}
	}

}
