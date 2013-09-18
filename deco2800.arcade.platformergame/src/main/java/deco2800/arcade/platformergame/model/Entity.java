package deco2800.arcade.platformergame.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class Entity {
	protected Rectangle bounds;
	protected EdgeCollider collider = new EdgeCollider();
	
	public Entity (Vector2 pos, float width, float height) {
		bounds = new Rectangle(pos.x, pos.y, width, height);
	}
	public Rectangle getBounds() {
		return bounds;
	}
	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}
	
	public void setPosition(Vector2 pos) {
		this.bounds.setX(pos.x);
		this.bounds.setY(pos.y);
	}
	
	public Vector2 getPosition() {
		return new Vector2(bounds.getX(), bounds.getY());
	}
	
	public float getWidth() {
		return bounds.width;
	}
	
	public float getHeight() {
		return bounds.height;
	}
	
	public float getX() {
		return bounds.x;
	}
	
	public float getY() {
		return bounds.y;
	}
	
	public void setX(float x) {
		this.bounds.x = x;
	}
	
	public void setY(float y) {
		this.bounds.y = y;
	}
	
	public EdgeCollider getCollider() {
		return collider;
	}
	
	public void update(float delta) {}
	public void draw(SpriteBatch batch) {}
	public void handleCollision(Entity entityTwo) {}
}