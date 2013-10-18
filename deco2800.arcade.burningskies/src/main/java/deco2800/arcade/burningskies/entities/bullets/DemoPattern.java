package deco2800.arcade.burningskies.entities.bullets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import deco2800.arcade.burningskies.entities.PlayerShip;
import deco2800.arcade.burningskies.entities.Ship;
import deco2800.arcade.burningskies.entities.bullets.Bullet.Affinity;
import deco2800.arcade.burningskies.screen.PlayScreen;

public class DemoPattern extends BulletPattern {
	
	private int angle = 0;

	private static Texture image = new Texture(Gdx.files.internal("images/bullets/energy_ball_1.png"));
	private static Texture image2 = new Texture(Gdx.files.internal("images/bullets/energy_ball_2.png"));
	
	public DemoPattern(Ship emitter, PlayScreen screen) {
		super(emitter, screen);
		interval = (float) 0.01;
	}
	
	public void fire(float lag, float x, float y) {
		SpiralBullet bullet = new SpiralBullet(Affinity.PLAYER, 10, emitter, (PlayerShip) emitter, new Vector2(x,y), angle, 1,  image);
		screen.addBullet(bullet);
		SpiralBullet bullet2 = new SpiralBullet(Affinity.PLAYER, 10, emitter, (PlayerShip) emitter, new Vector2(x,y), angle+180, -1, image2);
		screen.addBullet(bullet2);
		bullet.act(lag);
		bullet2.act(lag);
		angle = (angle+5);
	}
}
