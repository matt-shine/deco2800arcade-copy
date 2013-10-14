package deco2800.arcade.breakout.powerup;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import deco2800.arcade.breakout.GameScreen;

public class DecreasePaddle extends Powerup{

	private final String img = "decreasepaddle.png";
	private Sprite sprite = new Sprite(new Texture(Gdx.files.classpath("imgs/" + img)));
	private GameScreen context;
	//A variable for comparing floats
	private final float EPSILON = 0.0001f;
	
	/**
	 * Instantiate a new instance of the decrease paddle class
	 * @param gs - The current game screen
	 */
	public DecreasePaddle(GameScreen gs) {
		context = gs;
	}

	/**
	 * Decreases the size of the paddle.
	 * Adds to the score if the decrease power up is already active. 
	 */
	public void applyPowerup() {
		if ((context.getPaddle().getPaddleShapeWidth() - context.getPaddle().getStandardWidth()) < -EPSILON) {
			context.incrementScore(20 * context.getLevel());
			return;
		}
		context.getPaddle().decreaseSize();
	}
	/**
	 * @return the sprite for this power up
	 */
	public Sprite getSprite() {
		return this.sprite;
	}
}
