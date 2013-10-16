package deco2800.arcade.burningskies.entities.bullets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import deco2800.arcade.burningskies.entities.PlayerShip;
import deco2800.arcade.burningskies.entities.Ship;
import deco2800.arcade.burningskies.entities.bullets.Bullet.Affinity;
import deco2800.arcade.burningskies.screen.PlayScreen;

public class BombPattern extends BulletPattern {
	
	private Texture image;
	private Texture image2;
	private static final int equalizer = 3;
	
	public BombPattern(Ship emitter, PlayScreen screen) {
		super(emitter, screen);
		image = new Texture(Gdx.files.internal("images/bullets/energy_ball_1.png"));
		image2 = new Texture(Gdx.files.internal("images/bullets/energy_ball_2.png"));
		interval = 1000f;
	}
	
	public void fire(float lag, float x, float y) {
		BombBullet bullet;
		for(int i=0;i<360;i+=4) {
			bullet = new BombBullet(Affinity.PLAYER, 1000, emitter, (PlayerShip) emitter, new Vector2(x,y), i, image);
			screen.addBullet(bullet);
			//bullet.act(lag);
		}
		for(int i=2;i<360;i+=4) {
			bullet = new BombBullet(Affinity.PLAYER, 1000, emitter, (PlayerShip) emitter, new Vector2(x,y), i, image2);
			screen.addBullet(bullet);
			//bullet.act(lag);
		}
	}
	
	public int getEquals() {
		return equalizer;
	}
	
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof BombPattern)) {
			return false;
		}
		BombPattern f = (BombPattern) o;
		return equalizer == f.getEquals();
	}
}
