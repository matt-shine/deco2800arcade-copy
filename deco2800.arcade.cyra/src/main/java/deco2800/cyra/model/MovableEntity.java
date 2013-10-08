package deco2800.cyra.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class MovableEntity extends Entity {
 
	protected Vector2 velocity;
	protected float speed;
	protected float rotation;
	
	public MovableEntity(float speed, float rotation, Vector2 pos, float width, float height) {
		super(pos,width,height);
		this.speed = speed;
		this.rotation = rotation;
		this.velocity = new Vector2(0,0);
	}
	
	//abstract void advance(float delta);
	
	public float getSpeed() {
		return speed;
	}
	
	public Vector2 getVelocity() {
		return velocity;
	}
	
	public void setVelocity(Vector2 velocity) {
		this.velocity = velocity;
	}
	
	public float getRotation() {
		return rotation;
	}
	
	public void setRotation(float rotation) {
		this.rotation = rotation; 
	}
	
	public Rectangle getProjectionRect() {
		Rectangle rect = new Rectangle (position.x, position.y, width, height);
		rect.x += velocity.x*Gdx.graphics.getDeltaTime();
		rect.y += velocity.y*Gdx.graphics.getDeltaTime();
		//System.out.println("projrect "+rect.x+","+rect.y);
		return rect;
	}
	
	public Rectangle getXProjectionRect() {
		Rectangle rect = new Rectangle (position.x, position.y, width, height);
		rect.x += velocity.x*Gdx.graphics.getDeltaTime();
		//System.out.println("projrect "+rect.x+","+rect.y);
		return rect;
	}
	
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
	
	public void update(Player ship) {
		bounds.x = position.x;
		bounds.y = position.y;
		
	}
}
