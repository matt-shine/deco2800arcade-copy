package deco2800.arcade.burningskies.entities.bullets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import deco2800.arcade.burningskies.entities.PlayerShip;
import deco2800.arcade.burningskies.entities.Ship;

public class BossBullet extends Bullet {
	
	private static Texture tex = new Texture(Gdx.files.internal("images/bullets/boss_bullet.png"));

	public BossBullet(Ship parent, PlayerShip player, Vector2 initialPosition, float direction) {
		super(Affinity.ENEMY, 100, parent, player, initialPosition, direction, tex);
		velocity = new Vector2(0,-500);
	}
	
	@Override
	void moveBullet(float delta) {
		position.add( velocity.x * delta, velocity.y * delta );
		setX(position.x);
		setY(position.y);
		setRotation(direction);
		direction += 360*delta;
	}

}
