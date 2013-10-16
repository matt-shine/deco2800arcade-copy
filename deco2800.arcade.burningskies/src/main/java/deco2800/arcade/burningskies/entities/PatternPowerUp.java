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

	private ArrayList<BulletPattern> patterns = new ArrayList<BulletPattern>();
	private static final int equalizer = 2;
	
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
		if (currentPattern == null) currentPattern = new PlayerPattern(player, screen);
		patterns.add(new DemoPattern(player, screen));
		patterns.add(new PlayerPattern(player, screen));
		patterns.add(new BombPattern(player, screen));
		for (int i=0; i<2; i++) {
			if (currentPattern.equals(patterns.get(i))) {
				patterns.remove(i);
				break;
			}
		}
		return patterns.get((int)Math.round(Math.random()));
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
