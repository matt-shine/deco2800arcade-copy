package deco2800.arcade.hunter;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
/**
 * 
 * @author Nessex
 */
public abstract class Character {	
	Rectangle bounds = new Rectangle(); // The bounding box for the character
	
	/**
	 * Basic constructor for character
	 * @param initial character position
	 */
	public Character(Vector2 position, int width, int height) {
		this.bounds.x = position.x;
		this.bounds.y = position.y;
		this.bounds.width = width;
		this.bounds.height = height;
	}
	
	/**
	 * 
	 */
	public void move() {
		
	}

	/**
	 * 
	 */
	public void update() {
		
	}
	
}
