package deco2800.arcade.breakout;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

import deco2800.arcade.breakout.screens.GameScreen;
import deco2800.arcade.client.ArcadeSystem;
/**
 * Handles when the game is over
 * @author Carlie Smits and Naveen Kumar
 *
 */
public class GameOverState extends GameState {
	
	/**
	 * Handles when the game is over i.e. return to the arcade 
	 */
	@Override
	public void handleState(GameScreen context) {
		if (Gdx.input.isTouched() || Gdx.input.isButtonPressed(Keys.SPACE)) {
			context.dispose();
			context.bumpCount++;
			ArcadeSystem.goToGame(ArcadeSystem.UI);
		}

	}

}
