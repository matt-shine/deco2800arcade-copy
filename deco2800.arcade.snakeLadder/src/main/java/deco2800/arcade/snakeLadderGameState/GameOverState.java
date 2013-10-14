package deco2800.arcade.snakeLadderGameState;

import com.badlogic.gdx.Gdx;

import deco2800.arcade.client.ArcadeSystem;
import deco2800.arcade.snakeLadder.SnakeLadder;

public class GameOverState extends GameState {

	@Override
	public void handleInput(SnakeLadder context) {
		if (Gdx.input.isTouched()) {
    		context.gameOver();
    		//auto go back to ArcadeSystem.UI game menu
    		ArcadeSystem.goToGame(ArcadeSystem.UI);
    	}
	}

}
