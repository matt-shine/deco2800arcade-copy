package deco2800.arcade.pong;

import com.badlogic.gdx.Gdx;

import deco2800.arcade.client.ArcadeSystem;

public class GameOverState extends GameState {

	@Override
	public void handleInput(Pong context) {
		if (Gdx.input.isTouched()) {
		    // the arcade system calls gameOver for us
    		ArcadeSystem.goToGame(ArcadeSystem.UI);
    	}
	}

}
