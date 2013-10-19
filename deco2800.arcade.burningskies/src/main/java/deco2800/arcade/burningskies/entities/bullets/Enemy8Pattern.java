package deco2800.arcade.burningskies.entities.bullets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import deco2800.arcade.burningskies.entities.PlayerShip;
import deco2800.arcade.burningskies.entities.Ship;
import deco2800.arcade.burningskies.entities.bullets.Bullet.Affinity;
import deco2800.arcade.burningskies.screen.PlayScreen;

public class Enemy8Pattern extends BulletPattern {
	
	private static Texture image = new Texture(Gdx.files.internal("images/bullets/bullet_2.png"));
	private Vector2 lastEmit; // lag compensation on movement of emitter
	private Vector2 thisEmit = new Vector2();

	private PlayerShip player;
	
	public Enemy8Pattern(Ship emitter, PlayerShip player, PlayScreen screen) {
		super(emitter, screen);
		interval = 0.03f;
		this.player = player;
	}

	@Override
	public void fire(float lag, float x, float y) {
		PlayerBullet bullet;
		bullet = new PlayerBullet(Affinity.ENEMY, 10, emitter, player, new Vector2(x,y), 
				emitter.getRotation() + 90, image);
		screen.addBullet(bullet);
		bullet.act(lag);
	}

//	@Override
//	public void onRender(float delta) {
//		if(!firing) return;
//		timer += delta;
//		if(timer < interval) return;
//		float x = 0, y = 0, timeDiff;
//		thisEmit.x = emitter.getCenterX();
//		thisEmit.y = emitter.getCenterY();
//		if(lastEmit == null) lastEmit = new Vector2(thisEmit);
//		// Compensate for frame drops - it happens always, bloody GC
//		int loop = (int) Math.floor(timer / interval) - 1;
//		for(int i=0;i<=loop;i++) {
//			timeDiff = timer-delta-i*interval;
//			x = thisEmit.x - timeDiff/timer*((thisEmit.x - lastEmit.x));
//			y = thisEmit.y - timeDiff/timer*((thisEmit.y - lastEmit.y));
//			fire(timeDiff, x, y);
//		}
//		timer = timer % interval;
//		lastEmit.set(thisEmit);
//	}
}
