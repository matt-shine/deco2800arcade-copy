package deco2800.arcade.burningskies.entities;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.graphics.Texture;

public abstract class Ship  extends Image {

	private int health;	
	float velocity;
	public Rectangle bounds = new Rectangle();
	
	/**
	 * Basic constructor for a ship.
	 * @ensure health && velocity > 0
	 */
	public Ship(int health, Texture image, Vector2 pos, Vector2 size) {
		super(image);
		this.health = health;
		this.bounds.x = pos.x;
		this.bounds.y = pos.y;
		this.bounds.width = size.x;
		this.bounds.width = size.y;
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
