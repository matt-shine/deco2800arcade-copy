package deco2800.arcade.breakout.powerup;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import deco2800.arcade.breakout.screens.GameScreen;

/**
 * Increases the paddle size for the increase paddle powerup
 * @author Carlie Smits
 *
 */
public class IncreasePaddle extends Powerup{

	private final String img = "increasepaddle.png";
	private GameScreen context;
	private Sprite sprite;
	//A variable for comparing floats
	private final float EPSILON = 0.0001f;
	
	/**
	 * Instantiate a new instance of the increase paddle class
	 * @param gs - the current game screen
	 */
	public IncreasePaddle(GameScreen gs) {
		context = gs;
		setSprite();
	}
	
	/**
	 * Increase the size of the paddle.
	 * Adds to the score if the increase power up is already active.
	 */
	public void applyPowerup() {
		if ((context.getPaddle().getPaddleShapeWidth() - context.getPaddle().getStandardWidth()) > EPSILON) {
			context.incrementScore(20 * context.getLevel());
			return;
		}
		context.getPaddle().increaseSize();
	}
	/**
	 * @return the sprite for this powerup 
	 */
	public Sprite getSprite() {
		return this.sprite;
	}
	/**
	 * Set the sprite for the powerup
	 */
	public void setSprite(){
		this.sprite = new Sprite(new Texture(Gdx.files.classpath("imgs/" + img)));
	}
}
