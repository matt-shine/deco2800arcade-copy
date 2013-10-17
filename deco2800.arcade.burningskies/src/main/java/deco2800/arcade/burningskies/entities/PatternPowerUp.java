package deco2800.arcade.burningskies.entities;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import deco2800.arcade.burningskies.entities.bullets.BulletPattern;
import deco2800.arcade.burningskies.entities.bullets.DemoPattern;
import deco2800.arcade.burningskies.entities.bullets.PlayerPattern;
import deco2800.arcade.burningskies.entities.bullets.BombPattern;
import deco2800.arcade.burningskies.screen.PlayScreen;

public class PatternPowerUp extends PowerUp {
	
	static final Texture texture = new Texture(Gdx.files.internal("images/items/slow_down.png"));
	private PlayScreen screen;
	
	public PatternPowerUp(PlayScreen screen,float x, float y) {
		super(texture, x, y);
		this.screen = screen;
	}

	@Override
	public void powerOn(PlayerShip player) {
		player.setBulletPattern(randomPattern(player));
	}

	private BulletPattern randomPattern(PlayerShip player) {
		BulletPattern currentPattern = player.getPattern();
		ArrayList<BulletPattern> patterns = new ArrayList<BulletPattern>();
		patterns.add(new DemoPattern(player, screen));
		patterns.add(new PlayerPattern(player, screen));
		patterns.add(new BombPattern(player, screen));
		for (int i=0; i<patterns.size(); i++) {
			if (patterns.get(i).getClass() == currentPattern.getClass()) {
				patterns.remove(i);
				break;
			}
		}
		//Have to use rounding function, int casting always floors.
		return patterns.get((int)Math.round(Math.random() * (patterns.size() - 1)));
	}
}
