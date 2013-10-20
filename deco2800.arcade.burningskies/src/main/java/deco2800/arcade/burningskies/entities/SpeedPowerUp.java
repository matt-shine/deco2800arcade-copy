package deco2800.arcade.burningskies.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class SpeedPowerUp extends PowerUp {

	static final Texture texture = new Texture(Gdx.files.internal("images/items/speed_up.png"));

	/**
	 * Passes the variable to the super class
	 * @param x
	 * @param y
	 */
	public SpeedPowerUp(float x, float y) {
		super(texture, x, y);
	}

	/**
	 * Set the player's max speed to static value (currently 600f)
	 */
	@Override
	public void powerOn(PlayerShip player) {
		player.setMaxSpeed(600f);

	}
}
