package deco2800.arcade.burningskies.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.graphics.Texture;

public abstract class Ship  extends Image {

	private int health;	
	float velocity;
	Rectangle bounds = new Rectangle();
	
	/**
	 * Basic constructor for a ship.
	 * @ensure health && velocity > 0
	 */
	public Ship(int health, Texture image, Vector2 hitbox1, Vector2 hitbox2) {
		super(image);
		this.health = health;
		this.bounds.x = hitbox1.x;
		this.bounds.y = hitbox1.y;
		this.bounds.width = hitbox2.x;
		this.bounds.width = hitbox2.y;
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
	abstract void outBounds();
}
