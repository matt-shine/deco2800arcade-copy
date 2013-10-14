package deco2800.arcade.burningskies.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Entity extends Image {
	
	// how much we scale our image by to create our hitbox
	protected float hitboxScale = 1f;
	
	public Entity(Texture tex) {
		super(tex);
		setOrigin(getWidth()/2, getHeight()/2);
	}
	
	public Entity() {
		super(); // Why this is even needed is completely beyond me. Java, seriously
	}

	public boolean hasCollided(Entity other) {
		return Intersector.overlapConvexPolygons(getHitbox(), other.getHitbox());
	}
	
	public boolean hasCollidedUnscaled(Entity other) {
		return Intersector.overlapConvexPolygons(getUnscaledHitbox(), other.getUnscaledHitbox());
	}
	
	public float getCenterX() {
		return getX() + getOriginX();
	}
	
	public float getCenterY() {
		return getY() + getOriginY();
	}
	
	public float getRotatedX() {
		Vector2 pos = new Vector2(-getOriginX(), -getOriginY());
		pos.rotate(getRotation());
		return pos.x+getCenterX();
	}
	
	public float getRotatedY() {
		Vector2 pos = new Vector2(-getOriginX(), -getOriginY());
		pos.rotate(getRotation());
		return pos.y+getCenterY();
	}

	public Polygon getHitbox() {
		float w = getWidth()*hitboxScale;
		float h = getHeight()*hitboxScale;
		float x = (getWidth()-w)/2 + getX();
		float y = (getHeight()-h)/2 + getY();
		float[] points = {x,y,x+w,y,x+w,y+h,x,y+h};
		Polygon hitbox = new Polygon(points);
		hitbox.setOrigin(this.getCenterX(), this.getCenterY());
		hitbox.setRotation(this.getRotation());
		return hitbox;
	}
	
	public Polygon getUnscaledHitbox() {
		float x = getX();
		float y = getY();
		float w = getWidth();
		float h = getHeight();
		float[] points = {x,y,x+w,y,x+w,y+h,x,y+h};
		Polygon hitbox = new Polygon(points);
		hitbox.setOrigin(this.getCenterX(), this.getCenterY());
		hitbox.setRotation(this.getRotation());
		return hitbox;
	}
}
