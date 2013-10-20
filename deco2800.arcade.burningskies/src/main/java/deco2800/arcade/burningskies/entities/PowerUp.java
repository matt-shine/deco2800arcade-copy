package deco2800.arcade.burningskies.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

/**
 * The basis for any powerup, creates the powerup objects position and velocity 
 * in the game.
 */
public abstract class PowerUp extends Entity {
	
	float stateTime;
	
	private Vector2 position = new Vector2();
	private Vector2 velocity = new Vector2();

	/**
	 * Initialise the power up and randomly set the velocity
	 * @ensure texture != null && x != null && y != null
	 */
	public PowerUp(Texture texture, float x, float y) {
		super(texture);
		position.set(x, y);
		velocity.set(randVel() * randDirection(), randVel() * randDirection());
		setPosition(x, y);
	}

	public abstract void powerOn(PlayerShip player);

	/**
	 * Animates the movement of the power ups 
	 */
	@Override
	public void act(float delta) {
		position.add(velocity.x*delta, velocity.y*delta);
		setPosition(position.x, position.y);
	}
	
	/**
	 * Returns a float between 50 and 100
	 * @return 
	 */	
	private float randVel() {
		return (float) (Math.random()*30 + 30);
	}
	
	/**
	 * Returns a positive or negative value.
	 * @return
	 */
	private int randDirection() {
		if(Math.random() > 0.5) {
			return 1;
		}
		else {
			return -1;
		}
	}

}
