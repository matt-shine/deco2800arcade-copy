package deco2800.arcade.burningskies.entities.bullets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import deco2800.arcade.burningskies.entities.PlayerShip;
import deco2800.arcade.burningskies.entities.Ship;
import deco2800.arcade.burningskies.entities.bullets.Bullet.Affinity;
import deco2800.arcade.burningskies.screen.PlayScreen;

public class PlayerPattern extends BulletPattern {
	
	private static Texture image = new Texture(Gdx.files.internal("images/bullets/energy_ball_1.png"));;
	private int upgrade = 0;
	
	public PlayerPattern(Ship emitter, PlayScreen screen) {
		super(emitter, screen);
		interval = (float) 0.03;
	}
	
	public void fire(float lag, float x, float y) {
		PlayerBullet bullet;
		PlayerBullet bullet2;
		float x1, x2, y1, r1, r2;
		bullet = new PlayerBullet(Affinity.PLAYER, 10, emitter, (PlayerShip) emitter, new Vector2(x,y), emitter.getRotation() + 90, image);
		screen.addBullet(bullet);
		bullet.act(lag);
		for(int i=1; i<=upgrade; i++) {
			x1 = x - 20*i;
			x2 = x + 20*i;
			y1 = y - 5*i;
			r1 = emitter.getRotation() + 90 + 3*i;
			r2 = emitter.getRotation() + 90 - 3*i;
			bullet = new PlayerBullet(Affinity.PLAYER, 10, emitter, (PlayerShip) emitter, new Vector2(x1,y1), r1, image);
			bullet2 = new PlayerBullet(Affinity.PLAYER, 10, emitter, (PlayerShip) emitter, new Vector2(x2,y1), r2, image);
			screen.addBullet(bullet);
			screen.addBullet(bullet2);
			bullet.act(lag);
			bullet2.act(lag);
		}
	}
	
	public void upgrade() {
		upgrade++;
		if(upgrade > 3) {
			upgrade = 2;
		}
	}
}
