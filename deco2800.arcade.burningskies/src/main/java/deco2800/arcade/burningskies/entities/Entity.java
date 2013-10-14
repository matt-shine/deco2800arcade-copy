package deco2800.arcade.burningskies.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Entity extends Image {
	
	// how much we scale our image by to create our hitbox
	protected float hitboxScale = 1f;
	
	public Entity(Texture tex) {
		super(tex);
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
