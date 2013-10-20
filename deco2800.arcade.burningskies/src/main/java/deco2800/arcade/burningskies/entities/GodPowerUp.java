package deco2800.arcade.burningskies.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class GodPowerUp extends PowerUp {

	static final Texture texture = new Texture(Gdx.files.internal("images/items/god_mode.png"));
	
	
	public GodPowerUp(float x, float y) {
		super(texture, x, y);
	}

	@Override
	public void powerOn(PlayerShip player) {
		player.setGodMode(true);
	}

}
