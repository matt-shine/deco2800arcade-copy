package deco2800.server.listener;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import lobby.LobbyRequest;
import deco2800.arcade.protocol.lobby.NewLobbyRequest;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class LobbyListener extends Listener {

	private Map<String, Map<String, Set<Connection>>> lobbySessions;
	
	@Override
	public void received(Connection connection, Object object) {
		super.received(connection, object);
		
		if (object instanceof LobbyRequest) {
			
			NewLobbyRequest 
			
			
		}
			
	}
	

	private void handleJoinLobbyRequest(Connection connection, String username, String lobbySessionId){
		Map<String, Set<Connection>> userConnections;
		
		if (lobbySessions.containsKey(username)) {
			//User already has a lobby session
			userConnections = lobbySessions.get(lobbySessionId);
		} else {
			//No lobby session for user
			userConnections = new HashMap<String, Set<Connection>>();
		}
		
		Set<Connection> connections;
		if (userConnections.containsKey(username)) {
			//
		}
		
	}
	
}
