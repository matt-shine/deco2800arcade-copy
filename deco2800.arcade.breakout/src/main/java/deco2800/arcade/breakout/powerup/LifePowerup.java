package deco2800.arcade.breakout.powerup;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import deco2800.arcade.breakout.screens.GameScreen;

/**
 * Increases the lives for the life powerup
 * @author Carlie Smits
 *
 */
public class LifePowerup extends Powerup{

	private final String img = "LIFE.png";
	private GameScreen context;
	private Sprite sprite;
	/**
	 * Instantiate a new instance of the life powerup class
	 * @param gs - the current game screen
	 */
	public LifePowerup(GameScreen gs) {
		context = gs;
		setSprite();
	}
	/**
	 * Increase the lives
	 */
	public void applyPowerup() {
		context.incrementLives(1);
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
	public void setSprite(){
		this.sprite = new Sprite(new Texture(Gdx.files.classpath("imgs/" + img)));
	}
	
}
