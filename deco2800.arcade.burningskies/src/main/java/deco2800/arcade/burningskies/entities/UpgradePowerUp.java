package deco2800.arcade.burningskies.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class UpgradePowerUp extends PowerUp {
	
	static final Texture texture = new Texture(Gdx.files.internal("images/items/health.png"));
	
	//Either have all PowerUps inherit a screen, or only the BulletPattern ones.
	public UpgradePowerUp(float x, float y) {
			super(texture, x, y);		
	}
	
	@Override
	public void powerOn(PlayerShip player) {
		//Demo of these changes. These are set though and not timed at the moment.
		player.upgradeBullets();
		player.setMaxSpeed(600f);//2000 is quite insane speed.
	}

}
