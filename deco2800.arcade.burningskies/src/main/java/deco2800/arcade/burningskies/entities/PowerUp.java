package deco2800.arcade.burningskies.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public abstract class PowerUp extends Entity {
	
	float stateTime;
	
	private Vector2 position;
	private Vector2 velocity;

	/**
	 * Initialise the power up and randomly set the velocity
	 * @param texture
	 * @param x
	 * @param y
	 */
	public PowerUp(Texture texture, float x, float y) {
		super(texture);//, 10, 7, 0.15f);
		//TODO: TEST CODE REMOVE
		position = new Vector2(x, y);
		velocity = new Vector2(randVel() * randDirection(), randVel() * randDirection());
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
		return (float) (Math.random()*50 + 50);
	}
	
	private int randDirection() {
		if(Math.random() > 0.5) {
			return 1;
		}
		else {
			return -1;
		}
	}

}
