package deco2800.arcade.burningskies.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class HealthPowerUp extends PowerUp {

	static final Texture texture = new Texture(Gdx.files.internal("images/items/health.png"));

	public HealthPowerUp(float x, float y) {
		super(texture, x, y);
	}
	
	@Override
	public void powerOn(PlayerShip player) {
		player.heal(30);
	}
}
