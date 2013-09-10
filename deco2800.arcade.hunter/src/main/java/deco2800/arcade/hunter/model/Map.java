package deco2800.arcade.hunter.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Map {
	protected float xOffset = 0;
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
		return xOffset;
	}
	
	/**
	 * @param speedModifier
	 */
	public void setSpeedModifier(float speedModifier) {
		this.speedModifier = speedModifier;
	}
}
