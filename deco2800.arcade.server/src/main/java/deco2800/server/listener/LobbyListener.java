package deco2800.server.listener;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import deco2800.arcade.protocol.lobby.*;

import deco2800.server.ArcadeServer;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class LobbyListener extends Listener {

	private Map<String, Map<String, Set<Connection>>> lobbyUsers;
	
	@Override
	public void received(Connection connection, Object object) {
		super.received(connection, object);
		
		ArcadeServer server = new ArcadeServer(); //get the server instance
		
		if (object instanceof LobbyRequest) {
			
			NewLobbyRequest newLobbyRequest = (NewLobbyRequest) object;
			String username = newLobbyRequest.username;
			LobbyRequestType requestType = newLobbyRequest.requestType;
			
			switch (requestType)
			{
			case JOINLOBBY:
				handleJoinLobbyRequest(connection, username);
			case CREATEMATCH:
				
			case CANCELMATCH:
			case JOINGAME:
			
			}
		}
			
	}
	

	private void handleJoinLobbyRequest(Connection connection, String username){
		
		Map<String, Set<Connection>> userConnections;
		
		if (lobbyUsers.containsKey(username)) {
			//User already has a lobby session
			userConnections = lobbyUsers.get(username);
		} else {
			//No lobby session for user
			userConnections = new HashMap<String, Set<Connection>>();
		}
		
		if (userConnections.containsKey(username)) {
			//
			Set<Connection> connections = userConnections.get(username);
			connections.add(connection);
			connection.sendTCP(JoinLobbyResponse.OK);
		} else {
			//session not found
			connection.sendTCP(JoinLobbyResponse.UNAVAILABLE);
		}
		
	}
	
}
