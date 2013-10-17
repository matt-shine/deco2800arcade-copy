package deco2800.arcade.burningskies.entities.bullets;

import com.badlogic.gdx.math.Vector2;

import deco2800.arcade.burningskies.entities.PlayerShip;
import deco2800.arcade.burningskies.entities.Ship;
import deco2800.arcade.burningskies.screen.PlayScreen;

public class BossPattern extends BulletPattern {
	
	private PlayerShip player;
	
	public BossPattern(Ship emitter, PlayerShip player, PlayScreen screen) {
		super(emitter, screen);
		interval = 1f;
		this.player = player;
	}
	
	public void fire(float lag, float x, float y) {
		BossBullet bullet;
		bullet = new BossBullet(emitter, player, new Vector2(x,y), 0);
		screen.addBullet(bullet);
	}
	
	@Override
	public void onRender(float delta) {
		if(!firing) return;
		timer += delta;
		if(timer < interval) return;
		fire(0, emitter.getCenterX(), emitter.getCenterY());
		timer = timer % interval;
	}
}
