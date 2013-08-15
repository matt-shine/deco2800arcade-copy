package com.test.game.world;

import com.badlogic.gdx.utils.Array;
import com.test.game.model.CutsceneObject;
import com.test.game.model.Ship;

public abstract class LevelScenes {
	protected Ship ship;
	public LevelScenes(Ship ship) {
		this.ship = ship;
		
	}
	
	public abstract Array<CutsceneObject> start();
	public abstract void update(float delta);
	public abstract float[] getStartValues();
	public abstract boolean isPlaying();
}
