package com.test.game.model;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.Array;

public abstract class BlockMaker {
	
	protected boolean isActive;
	
	public Boolean isActive() {
		return isActive;
	}

	public void setActive(Boolean isActive) {
		this.isActive = isActive;
	}
	
	public abstract Array<Block> getBlocks();
	public abstract void update(float delta, OrthographicCamera cam);
	
	
}
