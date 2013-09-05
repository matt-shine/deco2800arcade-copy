package deco2800.arcade.burningskies.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.graphics.Texture;

public abstract class Ship  extends Image {

	private int health;	
	float velocity;
	public Vector2 position;
	
	/**
	 * Basic constructor for a ship.
	 * @ensure health && velocity > 0
	 */
	public Ship(int health, Texture image, Vector2 pos) {
		super(image);
		this.health = health;
		setX(pos.x);
		setY(pos.y);
	}
	/**
	 * Checks if the current ship is alive.
	 */
	public boolean isAlive() {
		if (health == 0) {
			return false;
		}		
		return true;
	}
	
	/**
	 * Modifies the current health of the ship accordingly.
	 * May be negative or positive value.
	 */
	public void setHealth(int healthchange) {
		this.health += healthchange;
	}
	
	abstract void velocity(float speed);
	
	/**
	 * Handles if the ship is out of bounds. The subclasses will determine
	 * what to do if this is so.
	 */
	abstract void outBounds(float delta);
}
