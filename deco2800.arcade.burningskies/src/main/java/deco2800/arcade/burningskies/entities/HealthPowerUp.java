package deco2800.arcade.burningskies.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import deco2800.arcade.burningskies.entities.bullets.DemoPattern;

public class HealthPowerUp extends PowerUp {
	
	private static final int equalizer = 3;
	static final Texture texture = new Texture(Gdx.files.internal("images/items/health.png"));

	public HealthPowerUp(float x, float y) {
		super(texture, x, y);
	}
	
	@Override
	public void powerOn(PlayerShip player) {
		player.heal(30);
	}

	public int getEquals() {
		return equalizer;
	}
	
	public boolean equals(Object o) {
		if (!(o instanceof DemoPattern)) {
			return false;
		}
		DemoPattern f = (DemoPattern) o;
		return equalizer == f.getEquals();
	}
}
