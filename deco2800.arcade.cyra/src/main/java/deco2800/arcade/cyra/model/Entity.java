package deco2800.arcade.cyra.model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Abstract class for all objects in the world that have bounds and a position.
 * 
 * @author Game Over
 *
 */
public abstract class Entity {
	//Location of the entity
	protected Vector2 position;
	protected float width;
	protected float height;
	protected Rectangle bounds;
	//Whether or not the entity is on a moving platform
	protected boolean onMovPlatform;
	
	/**
	 * Constructor for new entity, requires a position, width and height.
	 * @param pos Position of entity
	 * @param width Width of entity
	 * @param height Height of entity
	 */
	public Entity (Vector2 pos, float width, float height) {
		this.position = pos;
		this.width = width;
		this.height = height;
		bounds = new Rectangle(pos.x, pos.y, width, height);
	}
	
	/**
	 * Gets the bounds of the entity
	 * @return Bounds of the entity as a Rectangle
	 */
	public Rectangle getBounds() {
		//return bounds;
		return new Rectangle(position.x, position.y, width, height);
	}
	
	/**
	 * Set bounds of entity
	 * @param bounds New bounds of the entity, given a Rectangle
	 */
	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
		position.x = bounds.x;
		position.y = bounds.y;
		width = bounds.width;
		height = bounds.height;
	}
	
	/**
	 * Set the position of the entity.
	 * @param pos New position of the entity.
	 */
	public void setPosition(Vector2 pos) {
		this.position = pos;
	}
	
	/**
	 * Set whether an entity is on a moving platform.
	 * @param isOnMovPlatform set true if entity is on a moving platform
	 */
	public void setOnMovPlatform(boolean isOnMovPlatform) {
		onMovPlatform = isOnMovPlatform;
		return;
	}
	
	/**
	 * Returns the position of the entity in the game world as a Vector2
	 * @return Vector2 - position of entity
	 */
	public Vector2 getPosition() {
		return position;
	}
	/**
	 * Gets the width of the entity
	 * @return float - width of entity
	 */
	public float getWidth() {
		return bounds.width;
	}
	
	/**
	 * Gets the height of the entity
	 * @return float - height of entity
	 */
	public float getHeight() {
		return bounds.height;
	}
	
	/**
	 * Checks if entity is on a moving platform.
	 * @return true if on moving platform, false if not
	 */
	public boolean getOnMovPlatform() {
		return onMovPlatform;
	}
}
