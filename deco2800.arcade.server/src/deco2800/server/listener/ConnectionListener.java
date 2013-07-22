package deco2800.server.listener;

import java.util.Set;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import deco2800.arcade.protocol.connect.ConnectionRequest;
import deco2800.arcade.protocol.connect.ConnectionResponse;

public class ConnectionListener extends Listener {

	private Set<String> connectedUsers;
	
	public ConnectionListener(Set<String> connectedUsers){
		this.connectedUsers = connectedUsers;
	}
	
	@Override
	public void received(Connection connection, Object object) {
		super.received(connection, object);
		
		if (object instanceof ConnectionRequest) {
			ConnectionRequest request = (ConnectionRequest) object;
			System.out.println("Connection request for user: " + request.username);
			connectedUsers.add(request.username);

			connection.sendTCP(ConnectionResponse.OK);
			System.out.println("Connection granted");
		}
	}

	
}
