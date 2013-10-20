package deco2800.arcade.breakout;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;

import deco2800.arcade.breakout.screens.GameScreen;
/**
 * Handles when the game is paused
 * @author Carlie Smits
 *
 */
public class PauseState extends GameState {

	private Vector2 prevBallVelocity;
	private Vector2 prevPowerupBallVelocity;
	
	/**
	 * Sets the velocity of the ball to be 0 when the game is paused
	 * @param context - the current game screen
	 * @param ballVelocity - the regular ball's velocity
	 * @param pBallVelocity - the powerup ball's velocity
	 */
	public PauseState(GameScreen context, Vector2 ballVelocity, Vector2 
			pBallVelocity) {
		prevBallVelocity = new Vector2(ballVelocity);
		prevPowerupBallVelocity = new Vector2(pBallVelocity);
	}
	
	/**
	 * A method for handling the state of the game after the game has been
	 * paused i.e. either resumes the game or returns to the arcade
	 */
	@Override
	public void handleState(GameScreen context) {
		context.setStatus("Press Space to Resume, Tab for Overlay or E to" +
				" return to menu!");
		if (Gdx.input.isKeyPressed(Keys.SPACE)) {
			context.inGameUnpause(prevBallVelocity, prevPowerupBallVelocity);
		} else if (Gdx.input.isKeyPressed(Keys.E)) {
			context.dispose();
			context.setMenuScreen();
			//ArcadeSystem.goToGame(ArcadeSystem.UI);
		}
	}

}
