package deco2800.arcade.burningskies.entities;

import com.badlogic.gdx.graphics.Texture;

public abstract class PowerUp extends Entity {
	
	float stateTime;
	
	private float x;
	private float y;
		
	private float velocityX;
	private float velocityY;

	/**
	 * Initialise the power up and randomly set the velocity
	 * @param texture
	 * @param x
	 * @param y
	 */
	public PowerUp(Texture texture, float x, float y) {
		super(texture);//, 10, 7, 0.15f);
		//TODO: TEST CODE REMOVE
		this.x = x;
		this.y = y;
		this.velocityX = randVel() * randDirection();
		this.velocityY = randVel() * randDirection();
		System.out.println("Power up --> vx: " + velocityX + ", vy: " + velocityY);
		
		setX(x);
		setY(y);
	}

	public abstract void powerOn(PlayerShip player);

	/**
	 * Animates the movement of the power ups 
	 */
	@Override
	public void act(float delta) {
		x += velocityX * delta;
		y += velocityY * delta;
		
		setX(x);
		setY(y);		
	}
	
	/**
	 * Returns a float between 50 and 100
	 * @return 
	 */	
	private float randVel() {
		return (float) Math.floor(Math.random() * 50) + 50;
	}
	
	private int randDirection() {
		if(Math.floor(Math.random() * 2) == 1) {
			return 1;
		}
		else {
			return -1;
		}
	}

}
