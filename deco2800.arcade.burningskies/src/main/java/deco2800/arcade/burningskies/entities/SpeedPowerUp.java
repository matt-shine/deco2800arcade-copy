package deco2800.arcade.burningskies.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import deco2800.arcade.burningskies.entities.bullets.DemoPattern;

public class SpeedPowerUp extends PowerUp {
	
	private static final int equalizer = 1;
	
	static final Texture texture = new Texture(Gdx.files.internal("images/items/speed_up.png"));

	public SpeedPowerUp(float x, float y) {
		super(texture, x, y);
	}

	@Override
	public void powerOn(PlayerShip player) {
		player.setMaxSpeed(1200f);

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
