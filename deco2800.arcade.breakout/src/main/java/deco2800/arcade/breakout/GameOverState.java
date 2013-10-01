package deco2800.arcade.breakout;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

import deco2800.arcade.client.ArcadeSystem;

public class GameOverState extends GameState {

	@Override
	public void handleState(GameScreen context) {
		if (Gdx.input.isTouched() || Gdx.input.isButtonPressed(Keys.SPACE)) {
			// gameOver();
			// call dispose() method.
			context.dispose();
			// may need to change this if bumpCount becomes private
			context.bumpCount++;
			ArcadeSystem.goToGame(ArcadeSystem.UI);
		}

	}

}
