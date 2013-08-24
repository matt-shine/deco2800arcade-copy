package com.test.game.model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class Entity {
	protected Vector2 position;
	protected float width;
	protected float height;
	protected Rectangle bounds;
	protected boolean onMovPlatform;
	
	public Entity (Vector2 pos, float width, float height) {
		this.position = pos;
		this.width = width;
		this.height = height;
		bounds = new Rectangle(pos.x, pos.y, width, height);
	}
	public Rectangle getBounds() {
		return bounds;
	}
	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}
	
	public void setPosition(Vector2 pos) {
		this.position = pos;
	}
	
	public void setOnMovPlatform(boolean isOnMovPlatform) {
		onMovPlatform = isOnMovPlatform;
		return;
	}
	
	public Vector2 getPosition() {
		return position;
	}
	
	public float getWidth() {
		return bounds.width;
	}
	
	public float getHeight() {
		return bounds.height;
	}
	
	public boolean getOnMovPlatform() {
		return onMovPlatform;
	}
}
