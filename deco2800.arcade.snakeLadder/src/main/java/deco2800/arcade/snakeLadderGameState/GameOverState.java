package deco2800.arcade.snakeLadderGameState;

import com.badlogic.gdx.Gdx;

import deco2800.arcade.client.ArcadeSystem;
import deco2800.arcade.snakeLadder.SnakeLadder;

public class GameOverState extends GameState {

	@Override
	public void handleInput(SnakeLadder context) {
		int playerIndex=context.getturns();
		context.statusMessage = context.gamePlayers[playerIndex].getPlayerName() +" Win! ";
		//setting the scores
		context.player1.storeScore("Number", context.gamePlayers[0].getScore());
	}

}
