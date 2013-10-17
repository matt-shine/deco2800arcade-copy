package deco2800.arcade.client.network.listener;

import com.esotericsoftware.kryonet.Connection;

import deco2800.arcade.client.Arcade;
import deco2800.arcade.client.ArcadeSystem;
import deco2800.arcade.protocol.multiplayerGame.GameStateUpdateRequest;
import deco2800.arcade.protocol.multiplayerGame.NewMultiGameRequest;
import deco2800.arcade.protocol.multiplayerGame.NewMultiResponse;
import deco2800.arcade.protocol.multiplayerGame.NewMultiSessionResponse;



public class MultiplayerListener extends NetworkListener {
	
	Arcade arcade;
	
	public MultiplayerListener(Arcade arcade) {
		this.arcade = arcade;
	}

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
		
		if (object instanceof NewMultiSessionResponse) { //Game Found
			int sessionId = ((NewMultiSessionResponse) object).sessionId;
			arcade.getCurrentGame().setHost(((NewMultiSessionResponse) object).host);
			arcade.getCurrentGame().setMultiSession(sessionId);			
			ArcadeSystem.setGameWaiting(false);
		} else if (object instanceof GameStateUpdateRequest) {
			if (((GameStateUpdateRequest) object).initial == false) {
				arcade.getCurrentGame().updateGameState((GameStateUpdateRequest) object);
			} else {
				arcade.getCurrentGame().startMultiplayerGame();
				arcade.getCurrentGame().updateGameState((GameStateUpdateRequest) object);
			}
		} 
	}

}
