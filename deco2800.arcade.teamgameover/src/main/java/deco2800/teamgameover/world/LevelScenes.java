package deco2800.teamgameover.world;

import com.badlogic.gdx.utils.Array;

import deco2800.teamgameover.model.MovableEntity;
import deco2800.teamgameover.model.Ship;

public abstract class LevelScenes {
	protected Ship ship;
	public LevelScenes(Ship ship) {
		this.ship = ship;
		
	}
	
	public abstract Array<MovableEntity> start();
	public abstract void update(float delta);
	public abstract float[] getStartValues();
	public abstract boolean isPlaying();
}
