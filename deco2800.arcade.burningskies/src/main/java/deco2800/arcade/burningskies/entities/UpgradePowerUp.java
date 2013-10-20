package deco2800.arcade.burningskies.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class UpgradePowerUp extends PowerUp {
	
	static final Texture texture = new Texture(Gdx.files.internal("images/items/bullet_upgrade.png"));
	
	
	/**
	 * Passes the variable to the super class (PowerUp)
	 * @param x
	 * @param y
	 */
	public UpgradePowerUp(float x, float y) {
			super(texture, x, y);		
	}
	
	/**
	 * Calls the player.upgradeBullet() function
	 */
	@Override
	public void powerOn(PlayerShip player) {
		player.upgradeBullets();
	}

}
