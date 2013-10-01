package deco2800.server.listener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;








import deco2800.arcade.protocol.lobby.ActiveMatchDetails;
import deco2800.arcade.protocol.lobby.CreateMatchRequest;
import deco2800.arcade.protocol.lobby.JoinLobbyResponse;
import deco2800.arcade.protocol.lobby.LobbyRequestType;
import deco2800.arcade.protocol.lobby.NewLobbyRequest;
import deco2800.server.ArcadeServer;
import deco2800.server.Lobby;
import deco2800.server.LobbyMatch;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class LobbyListener extends Listener {

	/* Holds the users currently in the lobby */
	private Map<String, Map<Integer, Set<Connection>>> lobbyUsers = new HashMap<String, Map<Integer, Set<Connection>>>();


	Lobby lobby = Lobby.instance();

	@Override
	public void received(Connection connection, Object object) {
		super.received(connection, object);

		if (object instanceof NewLobbyRequest) {
			
			NewLobbyRequest newLobbyRequest = (NewLobbyRequest) object;
			int playerId = newLobbyRequest.playerID;
			System.out.println(newLobbyRequest.requestType);
			LobbyRequestType requestType = newLobbyRequest.requestType;
			String gameId = null;
			if (newLobbyRequest.gameId != null) {
				gameId = newLobbyRequest.gameId;
			}
			

			switch (requestType)
			{
			case JOINLOBBY:
				handleJoinLobbyRequest(playerId, connection);
				break;
			case CREATEMATCH:
				handleCreateMatchRequest(gameId, playerId, connection);
				break;
			case CANCELMATCH:
				handleCancelMatchRequest(playerId);
				break;
			case GETMATCHES:
				handleGetMatchesRequest(connection);
				break;
			}
		} else if (object instanceof CreateMatchRequest) {
			
			CreateMatchRequest request = (CreateMatchRequest) object;
			String gameId = request.gameId;
			int playerId = request.hostPlayerId;
			System.out.println("[SERVER] Create Match Request received (gameId: " + gameId + ", playerId: " + playerId);
			Connection hostConnection = connection;
			
			lobby.createMatch(gameId, playerId, hostConnection);
			
		}

	}
	
	private void handleGetMatchesRequest(Connection connection) {
		lobby.sendMatchesToClient(connection);
	}

	private void handleJoinLobbyRequest(int playerId, Connection connection){

		Map<Integer, Set<Connection>> userConnections;

		if (lobbyUsers.containsKey(playerId)) {
			//User already has a lobby session
			
			userConnections = lobbyUsers.get(playerId);
			
		} else {
			//No lobby session for user
			
			userConnections = new HashMap<Integer, Set<Connection>>();
		}
		
		if (userConnections.containsKey(playerId)) {
			//
			Set<Connection> connections = userConnections.get(playerId);
			connections.add(connection);
			connection.sendTCP(JoinLobbyResponse.OK);
		} else {
			//session not found
			connection.sendTCP(JoinLobbyResponse.UNAVAILABLE);
		}

	}

	private void handleCreateMatchRequest(String gameId, int playerId, Connection connection) {
		System.out.println("HANDLE MATCH REQUEST");
		if (lobby.userHasMatch(playerId)) {
			connection.sendTCP(JoinLobbyResponse.CREATE_MATCH_FAILED);
		} else {
			lobby.createMatch(gameId, playerId, connection);
			connection.sendTCP(JoinLobbyResponse.OK);
		}
	}

	private void handleCancelMatchRequest(int playerId) {
		lobby.destroyMatch(playerId);
	}

	private void handleJoinMatchRequest(int playerId, Connection connection, LobbyMatch match) {

	}
 	

}