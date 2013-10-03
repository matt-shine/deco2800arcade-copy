package deco2800.server.listener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import deco2800.arcade.protocol.game.GameRequestType;
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
			System.out.println("GameID from request: " + ((NewMultiGameRequest) object).gameId);
			NewMultiGameRequest multiRequest = (NewMultiGameRequest) object;
			matchmakerQueue.checkForGame(multiRequest, connection);
			MultiGameRequestType requestType = multiRequest.requestType;

			switch (requestType){
			case NEW:
				System.out.println("Connection Received");
				connection.sendTCP(NewMultiResponse.OK);
				NewMultiSessionResponse response = new NewMultiSessionResponse();
				response.gameId = ((NewMultiGameRequest) object).gameId;
				response.sessionId = 0;
				response.playerID = 0;
				connection.sendTCP(response);
				break;
			case JOIN:
				//TODO: 2+ player games
				handleJoinMultiRequest();
				break;
			default:
				break;
			}	
		} else if (object instanceof GameStateUpdateRequest) {
			//Sends update to server to broadcast
			GameStateUpdateRequest request = (GameStateUpdateRequest) object;
			Map<Integer, MultiplayerServer> activeServers = matchmakerQueue.getActiveServers();
			MultiplayerServer server = activeServers.get(request.gameSession);
			server.stateUpdate(request);
		}
	}


	private void handleJoinMultiRequest() {
		//TODO: this method. 
	}



}