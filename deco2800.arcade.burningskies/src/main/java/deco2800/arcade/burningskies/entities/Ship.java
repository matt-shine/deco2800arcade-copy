package deco2800.arcade.burningskies.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.Texture;

public abstract class Ship extends Entity {

	protected int health;	
	protected Vector2 velocity;
	protected Vector2 position;
	protected float flash = 0f;
	
	/**
	 * Basic constructor for a ship.
	 * @ensure health && velocity > 0
	 */
	public Ship(int health, Texture image, Vector2 position) {
		super(image);
		this.health = health;
		this.position = position;
		velocity = new Vector2(0,0);
	}
	/**
	 * Checks if the current ship is alive.
	 */
	public boolean isAlive() {
		if (health <= 0) {
			return false;
		} else return true;
	}
	
	public int getHealth() {
		return health;
	}
	
	@Override
    public void act(float delta) {
		onRender(delta);
        super.act(delta);
	}
	
	@Override
	public boolean remove() {
		return super.remove();
	}
	
	/**
	 * Damages the ship
	 */
	public void damage(int healthchange) {
		this.health -= healthchange;
		flash = 1f;
	}

	public void heal(int healthchange) {
		this.health += healthchange;
	}
	
	public boolean inBounds() {
		float left = getX() + getWidth();
		float right = getY() + getHeight();
		if(left < 0 || right < 0 || getX() > getStage().getWidth() || getY() > getStage().getHeight() ) {
			return true;
		} else return false;
	}
	
	/**
	 * What to do every frame. Perhaps bounds checking etc.
	 * Make sure to super.onRender so you implement damage flashes
	 */
	void onRender(float delta) {
		if(flash > 0) {
			setColor(1, 1-flash, 1-flash, 1);
			flash -= delta*25;
			if(flash <= 0) {
				setColor(1, 1, 1, 1);
			}
		}
	}
}
