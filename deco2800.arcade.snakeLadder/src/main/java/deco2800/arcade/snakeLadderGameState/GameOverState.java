package deco2800.arcade.snakeLadderGameState;

import com.badlogic.gdx.Gdx;

import deco2800.arcade.client.ArcadeSystem;
import deco2800.arcade.snakeLadder.SnakeLadder;

public class GameOverState extends GameState {

	@Override
	public void handleInput(SnakeLadder context) {
		int turn=context.getturns();
		int playerIndex = turn%context.gamePlayers.length;
		context.statusMessage = context.gamePlayers[playerIndex].getPlayerName() +" Win! ";
	}

}
