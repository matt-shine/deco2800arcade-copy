package deco2800.arcade.client.network.listener;

import com.esotericsoftware.kryonet.Connection;

import deco2800.arcade.protocol.multiplayerGame.GameStateUpdateRequest;
import deco2800.arcade.protocol.multiplayerGame.NewMultiGameRequest;
import deco2800.arcade.protocol.multiplayerGame.NewMultiResponse;
import deco2800.arcade.protocol.multiplayerGame.NewMultiSessionResponse;



public class MultiplayerListener extends NetworkListener {

	@Override
	public void connected(Connection connection) {
		super.connected(connection);
	}

	@Override
	public void disconnected(Connection connection) {
		super.disconnected(connection);
	}

	@Override
	public void idle(Connection connection) {
		super.idle(connection);
	}

	@Override
	public void received(Connection connection, Object object) {
		super.received(connection, object);

		if (object instanceof NewMultiGameRequest){ //TESTING
			System.out.println("Recieved message from server.");
		} else if (object instanceof NewMultiResponse) { //Connected to Server
			NewMultiResponse response = (NewMultiResponse) object;
			if (response.OK == NewMultiResponse.OK) {
				System.out.println("Connect OK");
			}
		}
		else if (object instanceof NewMultiSessionResponse) { //Game Found
			System.out.println("Game Found");
			//Testing
			GameStateUpdateRequest stateUpdate = new GameStateUpdateRequest();
			stateUpdate.gameId = ((NewMultiSessionResponse) object).gameId;
			stateUpdate.gameSession = ((NewMultiSessionResponse) object).sessionId;
			stateUpdate.stateChange = "Update Game State";
			connection.sendTCP(stateUpdate);
			System.out.println("Game state update request sent");
		} else if (object instanceof GameStateUpdateRequest) {
			System.out.println("Game State Updated");
		}
	}

}
