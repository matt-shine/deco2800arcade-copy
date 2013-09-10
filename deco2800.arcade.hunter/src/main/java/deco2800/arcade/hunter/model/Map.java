package deco2800.arcade.hunter.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public abstract class Map {
	protected Vector2 offset = new Vector2();
	protected float speedModifier;
	
	protected Map(float speedModifier) {
		this.speedModifier = speedModifier;
	}
	
	public abstract void update(float delta, float gameSpeed);
	public abstract void draw(SpriteBatch batch);
	
	/**
	 * @return xOffset
	 */
	public float getXOffset() {
		return offset.x;
	}
	
	public float getYOffset() {
		return offset.y;
	}
	
	/**
	 * @param speedModifier
	 */
	public void setSpeedModifier(float speedModifier) {
		this.speedModifier = speedModifier;
	}
}
