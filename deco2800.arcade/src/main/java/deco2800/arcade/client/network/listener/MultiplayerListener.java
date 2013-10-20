package deco2800.arcade.client.network.listener;

import com.esotericsoftware.kryonet.Connection;

import deco2800.arcade.client.Arcade;
import deco2800.arcade.client.ArcadeSystem;
import deco2800.arcade.protocol.multiplayerGame.GameStateUpdateRequest;
import deco2800.arcade.protocol.multiplayerGame.NewMultiGameRequest;
import deco2800.arcade.protocol.multiplayerGame.NewMultiResponse;
import deco2800.arcade.protocol.multiplayerGame.NewMultiSessionResponse;


/**
 * A listener for requests related to the multiplayer matchmaking system
 * @author Joey
 *
 */
public class MultiplayerListener extends NetworkListener {
	
	private Arcade arcade;
	
	/**
	 * The constructor for the listener.
	 * Takes an arcade to allow methods to be called
	 * @param arcade The client arcade
	 */
	public MultiplayerListener(Arcade arcade) {
		this.arcade = arcade;
	}

	@Override
	public void received(Connection connection, Object object) {
		super.received(connection, object);
		
		//Game is found -- Start the game, set host, give the game the session number and pause it
		if (object instanceof NewMultiSessionResponse) {
			int sessionId = ((NewMultiSessionResponse) object).sessionId;
			arcade.getCurrentGame().setHost(((NewMultiSessionResponse) object).host);
			arcade.getCurrentGame().setMultiSession(sessionId);			
			ArcadeSystem.setGameWaiting(false);
		//State updates -- If it is the inital update only send it to the non-host player
		//Otherwise send it to both
		} else if (object instanceof GameStateUpdateRequest) {
			if (!(((GameStateUpdateRequest) object).initial)) {
				arcade.getCurrentGame().updateGameState((GameStateUpdateRequest) object);
			} else {
				arcade.getCurrentGame().startMultiplayerGame();
				arcade.getCurrentGame().updateGameState((GameStateUpdateRequest) object);
			}
		} 
	}

}
