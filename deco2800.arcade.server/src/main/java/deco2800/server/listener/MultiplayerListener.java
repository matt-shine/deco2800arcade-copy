package deco2800.server.listener;

import java.util.Map;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import deco2800.arcade.protocol.game.GameRequestType;
import deco2800.arcade.protocol.multiplayerGame.ActiveGameRequest;
import deco2800.arcade.protocol.multiplayerGame.GameStateUpdateRequest;
import deco2800.arcade.protocol.multiplayerGame.MultiGameRequestType;
import deco2800.arcade.protocol.multiplayerGame.NewMultiGameRequest;
import deco2800.arcade.protocol.multiplayerGame.NewMultiResponse;
import deco2800.arcade.protocol.multiplayerGame.NewMultiSessionResponse;
import deco2800.server.MultiplayerServer;
import deco2800.server.MatchmakerQueue;

public class MultiplayerListener extends Listener {
	private MatchmakerQueue matchmakerQueue;

	public MultiplayerListener(MatchmakerQueue matchmakerQueue) {
		this.matchmakerQueue = matchmakerQueue;
	}

	@Override
	/**
	 * received takes a connection input and an object input and stores the data
	 * in a queueSessions map.
	 * @require inputs are connection and object
	 */
	public void received(Connection connection, Object object) {
		super.received(connection, object);

		if (object instanceof NewMultiGameRequest) {
			NewMultiGameRequest multiRequest = (NewMultiGameRequest) object;

			MultiGameRequestType requestType = multiRequest.requestType;

			switch (requestType) {
			case NEW:
				connection.sendTCP(NewMultiResponse.OK);
				NewMultiSessionResponse response = new NewMultiSessionResponse();
				response.gameId = ((NewMultiGameRequest) object).gameId;
				response.sessionId = 0;
				response.playerID = ((NewMultiGameRequest) object).playerID;
				connection.sendTCP(response);
				break;
			case JOIN:
				// TODO: 2+ player games
				handleJoinMultiRequest();
				break;
			case MATCHMAKING:
				matchmakerQueue.checkForGame(multiRequest, connection);
			default:
				break;
			}
		} else if (object instanceof GameStateUpdateRequest) {
			// Sends update to server to broadcast
			GameStateUpdateRequest request = (GameStateUpdateRequest) object;
			Map<Integer, MultiplayerServer> activeServers = matchmakerQueue
					.getServerList();
			MultiplayerServer server = activeServers.get(request.gameSession);
			if (server != null) {
		        server.stateUpdate(request);
			}
		//Request from arcade to update active server list
		} else if (object instanceof ActiveGameRequest) {
			((ActiveGameRequest) object).serverList = matchmakerQueue.getServerListAsList();
			connection.sendTCP(object);
		}
	}

	private void handleJoinMultiRequest() {
		System.out.println("JOIN GAME REQUEST HERE");
	}

}