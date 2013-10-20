package deco2800.arcade.cyra.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class MovableEntity extends Entity {
 
	protected Vector2 velocity;
	protected float speed;
	protected float rotation;
	
	/**
	 * Constructor for new MoveableEntity, requires a speed, rotation, position, width and height.
	 * @param speed Speed of entity
	 * @param rotation Rotation of entity
	 * @param pos Position of entity
	 * @param width Width of entity
	 * @param height Height of entity
	 */
	public MovableEntity(float speed, float rotation, Vector2 pos, float width, float height) {
		super(pos,width,height);
		this.speed = speed;
		this.rotation = rotation;
		this.velocity = new Vector2(0,0);
	}
	
	//abstract void advance(float delta);
	
	/**
	 * Gets the speed of the moveableEntity
	 * @return float - speed of moveableEntity
	 */
	public float getSpeed() {
		return speed;
	}
	
	/**
	 * Gets the velocity of the moveableEntity
	 * @return Vector2 - velocity of moveableEntity
	 */
	public Vector2 getVelocity() {
		return velocity;
	}
	
	/**
	 * Set the velocity of moveable entity
	 * @param velocity
	 */
	public void setVelocity(Vector2 velocity) {
		this.velocity = velocity;
	}
	
	/**
	 * Get the rotation of the moveableEntity
	 * @return float - rotation of moveableEntity
	 */
	
	public float getRotation() {
		return rotation;
	}
	
	/**
	 * Set the rotation of the moveableEntity
	 * @param rotation
	 */
	
	public void setRotation(float rotation) {
		this.rotation = rotation; 
	}
	
	/**
	 * Get the projection rectangle
	 * @return Rectangle
	 */
	public Rectangle getProjectionRect() {
		Rectangle rect = new Rectangle (position.x, position.y, width, height);
		rect.x += velocity.x*Gdx.graphics.getDeltaTime();
		rect.y += velocity.y*Gdx.graphics.getDeltaTime();
		//System.out.println("projrect "+rect.x+","+rect.y);
		return rect;
	}
	
	/**
	 * Get the x Projection of the rectangle
	 * @return Rectangle - x Projection
	 */
	public Rectangle getXProjectionRect() {
		Rectangle rect = new Rectangle (position.x, position.y, width, height);
		rect.x += velocity.x*Gdx.graphics.getDeltaTime();
		//System.out.println("projrect "+rect.x+","+rect.y);
		return rect;
	}
	/**
	 * Get the y Projection of the rectangle
	 * @return Rectangle - y Projection
	 */
	public Rectangle getYProjectionRect() {
		Rectangle rect = new Rectangle (position.x, position.y, width, height);
		rect.y += velocity.y*Gdx.graphics.getDeltaTime();
		//System.out.println("projrect "+rect.x+","+rect.y);
		return rect;
	}
	
	public abstract boolean isSolid();
	public abstract void handleTopOfMovingPlatform(MovablePlatform movablePlatform);
	public abstract void handleXCollision(Rectangle tile);
	public abstract void handleYCollision(Rectangle tile, boolean onMovablePlatform, MovablePlatform movablePlatform);
	public abstract void handleNoTileUnderneath();
	
	
	//public abstract void handleX
	//public abstract boolean 
	
	/**
	 * Update the bounds of the moveableEntity
	 * @param ship
	 */
	public void update(Player ship) {
		bounds.x = position.x;
		bounds.y = position.y;
		
	}
}
