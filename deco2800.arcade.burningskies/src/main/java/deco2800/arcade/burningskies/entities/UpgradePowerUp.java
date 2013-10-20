package deco2800.arcade.burningskies.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class UpgradePowerUp extends PowerUp {
	
	static final Texture texture = new Texture(Gdx.files.internal("images/items/bullet_upgrade.png"));
	
	//Either have all PowerUps inherit a screen, or only the BulletPattern ones.
	public UpgradePowerUp(float x, float y) {
			super(texture, x, y);		
	}
	
	@Override
	public void powerOn(PlayerShip player) {
		player.upgradeBullets();
	}

}
