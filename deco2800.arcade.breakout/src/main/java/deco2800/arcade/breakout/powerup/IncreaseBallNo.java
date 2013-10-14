package deco2800.arcade.breakout.powerup;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

import deco2800.arcade.breakout.GameScreen;

public class IncreaseBallNo extends Powerup{
	private final String img = "increaseballno.png";
	private Sprite sprite = new Sprite(new Texture(Gdx.files.classpath("imgs/" + img)));
	private GameScreen context;
	
	/**
	 * Instantiate a new instance of the increase ball class
	 * @param gs - the current game screen
	 */
	public IncreaseBallNo(GameScreen gs) {
		context = gs;
	}
	
	/**
	 * Increases the number of balls
	 * Adds to the score if the increase ball power up is already active.
	 */
	public void applyPowerup() {
		if (context.getNumBalls() > 1) {
			context.incrementScore(20 * context.getLevel());
			return;
		}
		Vector2 position = new Vector2(context.getPaddle().getPaddleX(), 
				context.getPaddle().getPaddleY());
		context.createNewBall(position);
	}
	
	/**
	 * @return the sprite for this power up
	 */
	public Sprite getSprite() {
		return this.sprite;
	}
}
