package deco2800.arcade.burningskies.entities.bullets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import deco2800.arcade.burningskies.entities.PlayerShip;
import deco2800.arcade.burningskies.entities.Ship;

public class BossBullet extends Bullet {
	
	private float spin = 0;
	
	private static Texture tex = new Texture(Gdx.files.internal("images/bullets/boss_bullet.png"));

	public BossBullet(int damage, Ship parent, PlayerShip player, Vector2 initialPosition, float direction) {
		super(Affinity.ENEMY, damage, parent, player, initialPosition, direction, tex);
		velocity = new Vector2(0,-500);
	}
	
	@Override
	void moveBullet(float delta) {
		super.moveBullet(delta);
		setRotation(spin);
		spin += 360*delta;
	}
}
