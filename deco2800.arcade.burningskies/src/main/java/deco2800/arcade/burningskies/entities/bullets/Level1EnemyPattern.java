package deco2800.arcade.burningskies.entities.bullets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import deco2800.arcade.burningskies.entities.PlayerShip;
import deco2800.arcade.burningskies.entities.Ship;
import deco2800.arcade.burningskies.entities.bullets.Bullet.Affinity;
import deco2800.arcade.burningskies.screen.PlayScreen;

public class Level1EnemyPattern extends BulletPattern {
	
	private static Texture image = new Texture(Gdx.files.internal("images/bullets/bullet_2.png"));
	
	private PlayerShip player;
	
	public Level1EnemyPattern(Ship emitter, PlayerShip player, PlayScreen screen, int difficulty) {
		super(emitter, screen);
		interval = (float) (2f/(difficulty*0.5));
		this.player = player;
	}
	
	public void fire(float lag, float x, float y) {
		EnemyBullet bullet;
		for(int i=0;i<360;i+=36) {
			bullet = new EnemyBullet(Affinity.ENEMY, 10, emitter, player, new Vector2(x,y), i, image);
			screen.addBullet(bullet);
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
