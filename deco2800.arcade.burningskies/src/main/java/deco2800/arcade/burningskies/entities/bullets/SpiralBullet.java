package deco2800.arcade.burningskies.entities.bullets;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import deco2800.arcade.burningskies.entities.PlayerShip;
import deco2800.arcade.burningskies.entities.Ship;

public class SpiralBullet extends Bullet {
	
	private float spiralMult;

	/**
	 * Set up the initial velocity and acceleration to default values for the bullets
	 * @param affinity
	 * @param damage
	 * @param parent
	 * @param player
	 * @param initialPosition
	 * @param direction
	 * @param spiralMult
	 * @param image
	 */
	public SpiralBullet(Affinity affinity, int damage, Ship parent,
			PlayerShip player, Vector2 initialPosition, float direction, float spiralMult, Texture image) {
		super(affinity, damage, parent, player, initialPosition, direction, image);
		velocity = new Vector2(150,150);
		acceleration = new Vector2(10,0);
		this.spiralMult = spiralMult;
	}
	
	/**
	 * Updates the position of the bullet relative the application's coordinates
	 */
	@Override
	void moveBullet(float delta) {
		velocity.add( acceleration.x * delta, acceleration.y * delta );
		velocity.setAngle(direction);
		position.add( velocity.x * delta, velocity.y * delta );
		setX(position.x);
		setY(position.y);
		setRotation(direction);
		direction += 30*delta *spiralMult;
	}

}
