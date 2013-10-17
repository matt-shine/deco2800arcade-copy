package deco2800.arcade.burningskies.entities;

import com.badlogic.gdx.graphics.Texture;
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
		return getHitbox().overlaps(other.getHitbox());
	}
	
	public boolean hasCollidedUnscaled(Entity other) {
		return getUnscaledHitbox().overlaps(other.getUnscaledHitbox());
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

	private Rectangle getHitbox() {
		float newW = getWidth()*hitboxScale;
		float newH = getHeight()*hitboxScale;
		float newX = (getWidth()-newW)/2 + getX();
		float newY = (getHeight()-newH)/2 + getY();
		return new Rectangle(newX, newY, newW, newH);
	}
	
	private Rectangle getUnscaledHitbox() {
		return new Rectangle(getX(), getY(), getWidth(), getHeight());
	}
}
