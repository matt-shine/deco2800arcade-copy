package deco2800.server.listener;


import java.util.UUID;

import deco2800.arcade.protocol.lobby.CreateMatchRequest;
import deco2800.arcade.protocol.lobby.JoinLobbyMatchRequest;
import deco2800.arcade.protocol.lobby.NewLobbyRequest;
import deco2800.server.Lobby;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class LobbyListener extends Listener {

	Lobby lobby = Lobby.instance();

	@Override
	public void received(Connection connection, Object object) {
		super.received(connection, object);

		if (object instanceof NewLobbyRequest) {
			
			
			
			NewLobbyRequest newLobbyRequest = (NewLobbyRequest) object;
			int playerId = newLobbyRequest.playerID;
			switch (newLobbyRequest.requestType) {
			case JOINLOBBY:
				Lobby.instance().addPlayerToLobby(playerId, connection);
				break;
			case LEAVELOBBY:
				Lobby.instance().removePlayerFromLobby(playerId);
				break;
			case POPULATE:
				Lobby.instance().sendGamesToLobbyUser(newLobbyRequest.playerID);
				break;
			default:
				break;
			
			}
			
			
		}
		else if (object instanceof CreateMatchRequest) {
			
			CreateMatchRequest request = (CreateMatchRequest) object;
			String gameId = request.gameId;
			int playerId = request.hostPlayerId;
			Connection hostConnection = connection;
			
			lobby.createMatch(gameId, playerId, hostConnection);
		}
		else if (object instanceof JoinLobbyMatchRequest) {
			
			JoinLobbyMatchRequest request = (JoinLobbyMatchRequest) object;
			int matchId = request.matchId;
			int playerId = request.playerID;
			lobby.joinMatch(matchId, playerId, connection);
		}

	}
	

}