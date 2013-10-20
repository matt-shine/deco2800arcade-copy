package deco2800.arcade.breakout.powerup;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import deco2800.arcade.breakout.screens.GameScreen;

/**
 * Decreases the speed of the ball for the slow ball powerup
 * 
 * @author Carlie Smits
 * 
 */
public class SlowBall extends Powerup {
	private final String img = "slowerball.png";
	private GameScreen context;
	private Sprite sprite;

	/**
	 * Instantiate a new instance of the slow ball class
	 * 
	 * @param gs
	 *            - the current game screen
	 */
	public SlowBall(GameScreen gs) {
		context = gs;
		setSprite();
	}

	/**
	 * Slow the current ball Adds to the score if the slow ball power up is
	 * already active.
	 */
	public void applyPowerup() {
		if (context.getNumSlowBallsActivated() != 0) {
			context.incrementScore(20 * context.getLevel());
			return;
		}
		context.incrementNumSlowBallsActivated();
		if (context.getBall() != null) {
			context.getBall().slowBall();
		}
		if (context.getPowerupBall() != null) {
			context.getPowerupBall().slowBall();
		}
	}

	/**
	 * @return the sprite for this power up
	 */
	public Sprite getSprite() {
		return this.sprite;
	}

	/**
	 * Set the sprite for the powerup
	 */
	public void setSprite() {
		this.sprite = new Sprite(
				new Texture(Gdx.files.classpath("imgs/" + img)));
	}
}
