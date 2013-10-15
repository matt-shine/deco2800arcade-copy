package deco2800.arcade.breakout.powerup;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import deco2800.arcade.breakout.GameScreen;


public class LifePowerup extends Powerup{

	private final String img = "LIFE.png";
	private Sprite sprite = new Sprite(new Texture(Gdx.files.classpath("imgs/" + img)));
	private GameScreen context;
	/**
	 * Instantiate a new instance of the life powerup class
	 * @param gs - the current game screen
	 */
	public LifePowerup(GameScreen gs) {
		context = gs;
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
	
}
