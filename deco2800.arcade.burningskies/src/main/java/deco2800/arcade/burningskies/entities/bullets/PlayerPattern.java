package deco2800.arcade.burningskies.entities.bullets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import deco2800.arcade.burningskies.entities.PlayerShip;
import deco2800.arcade.burningskies.entities.Ship;
import deco2800.arcade.burningskies.entities.bullets.Bullet.Affinity;
import deco2800.arcade.burningskies.screen.PlayScreen;

public class PlayerPattern extends BulletPattern {
	
	private Texture image;
	
	public PlayerPattern(Ship emitter, PlayScreen screen) {
		super(emitter, screen);
		image = new Texture(Gdx.files.internal("images/placeholder_bullet.png"));
		interval = (float) 0.03;
	}
	
	public void fire(float lag, float x, float y) {
		float x1 = x - 40;
		float x2 = x + 40;
		PlayerBullet bullet = new PlayerBullet(Affinity.PLAYER, -10, emitter, (PlayerShip) emitter, new Vector2(x1,y), 90, image);
		PlayerBullet bullet2 = new PlayerBullet(Affinity.PLAYER, -10, emitter, (PlayerShip) emitter, new Vector2(x2,y), 90, image);
		screen.addBullet(bullet);
		screen.addBullet(bullet2);
		bullet.act(lag);
		bullet2.act(lag);
	}
}
