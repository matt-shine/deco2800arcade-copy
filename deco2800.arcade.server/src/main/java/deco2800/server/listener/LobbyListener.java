package deco2800.server.listener;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import deco2800.arcade.protocol.lobby.*;
import deco2800.server.ArcadeServer;
import deco2800.server.Lobby;
import deco2800.server.LobbyMatch;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class LobbyListener extends Listener {
	
	/* Holds the users currently in the lobby */
	private Map<String, Map<String, Set<Connection>>> lobbyUsers;

	
	Lobby lobby = Lobby.instance();
	
	@Override
	public void received(Connection connection, Object object) {
		super.received(connection, object);
		
		ArcadeServer server = new ArcadeServer(); //get the server instance
		
		if (object instanceof LobbyRequest) {
			
			NewLobbyRequest newLobbyRequest = (NewLobbyRequest) object;
			String username = newLobbyRequest.username;
			LobbyRequestType requestType = newLobbyRequest.requestType;
			String gameId = newLobbyRequest.gameId;
			
			switch (requestType)
			{
			case JOINLOBBY:
				handleJoinLobbyRequest(username, connection);
				break;
			case CREATEMATCH:
				handleCreateMatchRequest(gameId, username, connection);
				break;
			case CANCELMATCH:
				handleCancelMatchRequest(username);
				break;
			}
		}
			
	}
	

	private void handleJoinLobbyRequest(String username, Connection connection){
		
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
	
	private void handleCreateMatchRequest(String gameId, String username, Connection connection) {
		if (lobby.userHasMatch(username)) {
			connection.sendTCP(JoinLobbyResponse.CREATE_MATCH_FAILED);
		} else {
			lobby.createMatch(gameId, username, connection);
			connection.sendTCP(JoinLobbyResponse.OK);
		}
	}
	
	private void handleCancelMatchRequest(String username) {
		lobby.destroyMatch(username);
	}
	
	private void handleJoinMatchRequest(String username, Connection connection, LobbyMatch match) {
		
	}
 	
	
}
