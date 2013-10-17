package deco2800.arcade.breakout;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;

import deco2800.arcade.client.ArcadeSystem;

public class PauseState extends GameState {

	private Vector2 prevBallVelocity;
	private Vector2 prevPowerupBallVelocity;
	
	public PauseState(GameScreen context, Vector2 ballVelocity, Vector2 pBallVelocity) {
		prevBallVelocity = new Vector2(ballVelocity);
		prevPowerupBallVelocity = new Vector2(pBallVelocity);
	}
	
	@Override
	public void handleState(GameScreen context) {
		context.setStatus("Press Space to Resume, Tab for Overlay or Esc to exit!");
		if (Gdx.input.isKeyPressed(Keys.SPACE)) {
			context.inGameUnpause(prevBallVelocity, prevPowerupBallVelocity);
		} else if (Gdx.input.isKeyPressed(Keys.ESCAPE)) {
			context.dispose();
			ArcadeSystem.goToGame(ArcadeSystem.UI);
		}
	}

}
