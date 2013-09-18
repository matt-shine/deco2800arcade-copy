package com.test.game.world;

import com.badlogic.gdx.utils.Array;
import com.test.game.model.CutsceneObject;
import com.test.game.model.MovableEntity;
import com.test.game.model.Ship;

public abstract class LevelScenes {
	protected Ship ship;
	protected ParallaxCamera cam;
	protected boolean isPlaying;
	
	public LevelScenes(Ship ship, ParallaxCamera cam) {
		this.ship = ship;
		this.cam = cam;
		isPlaying = false;
	}
	
	public boolean isPlaying() {
		return isPlaying;
	}
	
	public abstract Array<Object> start(float rank);
	public abstract boolean update(float delta);
	public abstract float[] getStartValues();
	//public abstract boolean isPlaying();
}
