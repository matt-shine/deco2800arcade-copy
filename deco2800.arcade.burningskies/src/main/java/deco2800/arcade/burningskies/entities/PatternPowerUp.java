package deco2800.arcade.burningskies.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import deco2800.arcade.burningskies.entities.bullets.DemoPattern;
import deco2800.arcade.burningskies.screen.PlayScreen;

public class PatternPowerUp extends PowerUp {
	
	static final Texture texture = new Texture(Gdx.files.internal("images/items/spiral_upgrade.png"));
	private PlayScreen screen;
	
	public PatternPowerUp(PlayScreen screen,float x, float y) {
		super(texture, x, y);
		this.screen = screen;
	}

	@Override
	public void powerOn(PlayerShip player) {
		player.setBulletPattern(new DemoPattern(player, screen), true);
	}
}
