package deco2800.arcade.hunter;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
/**
 * 
 * @author Nessex
 */
public abstract class Entity {	
	private Rectangle bounds = new Rectangle(); //Entity bounding box
	private EntityType type; //To allow event checks for all entities of a type
	
	/**
	 * Entity constructor
	 * @param boundingBox
	 */
	public Entity(Rectangle bounds, EntityType type) {
		this.bounds.x = bounds.x;
		this.bounds.y = bounds.y;
		this.bounds.width = bounds.width;
		this.bounds.height = bounds.height;

		this.type = type;
	}
	
	/**
	 * 
	 */
	public void move(Vector2 position) {
		this.bounds.x = position.x;
		this.bounds.y = position.y;
	}

	public Rectangle getBounds() {
		return bounds;
	}

	/**
	* Check for a collision/intersection with another entity
	*/
	public boolean checkForCollision(Entity e) {
		/* To implement - TODO*/
		return false;
	}

	/**
	 * Run each game tick
	 */
	public void update() {
		
	}	
}