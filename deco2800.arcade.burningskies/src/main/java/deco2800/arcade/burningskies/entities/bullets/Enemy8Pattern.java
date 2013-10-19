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
}
