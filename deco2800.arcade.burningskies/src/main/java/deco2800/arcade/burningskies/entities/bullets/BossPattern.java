package deco2800.arcade.burningskies.entities.bullets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import deco2800.arcade.burningskies.entities.PlayerShip;
import deco2800.arcade.burningskies.entities.Ship;
import deco2800.arcade.burningskies.entities.bullets.Bullet.Affinity;
import deco2800.arcade.burningskies.screen.PlayScreen;

public class BossPattern extends BulletPattern {
	
	private PlayerShip player;
	private int counter;
	private static Texture image = new Texture(Gdx.files.internal("images/bullets/bullet_2.png"));
	
	public BossPattern(Ship emitter, PlayerShip player, PlayScreen screen) {
		super(emitter, screen);
		interval = 0.4f;
		this.player = player;
	}
	
	public void fire(float lag, float x, float y) {
		float angle = new Vector2(player.getCenterX() - x, player.getCenterY() - y).angle();
		BossBullet bullet;
		bullet = new BossBullet(emitter, player, new Vector2(x,y), angle);
		screen.addBullet(bullet);
		counter++;
		if(counter % 10 == 0) {
			screen.spawnRandomEnemy();
			EnemyBullet bullet2;
			for(int i=0;i<360;i+=12) {
				bullet2 = new EnemyBullet(Affinity.ENEMY, 10, emitter, player, new Vector2(x,y), i, image);
				screen.addBullet(bullet2);
			}
			counter = 0;
		}
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
