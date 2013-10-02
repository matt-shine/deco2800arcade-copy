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
			Lobby.instance().addPlayerToLobby(playerId, connection);
			
			}
		else if (object instanceof CreateMatchRequest) {
			
			CreateMatchRequest request = (CreateMatchRequest) object;
			String gameId = request.gameId;
			int playerId = request.hostPlayerId;
			System.out.println("[SERVER] Create Match Request received (gameId: " + gameId + ", playerId: " + playerId);
			Connection hostConnection = connection;
			
			lobby.createMatch(gameId, playerId, hostConnection);
		}

	}
	

}