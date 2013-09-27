package deco2800.cyra.world;

import com.badlogic.gdx.utils.Array;
import deco2800.cyra.model.CutsceneObject;
import deco2800.cyra.model.MovableEntity;
import deco2800.cyra.model.Ship;

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
	
	public abstract Array<Object> start(int scenePosition, float rank);
	public abstract boolean update(float delta);
	public abstract float[] getStartValues();
	//public abstract boolean isPlaying();
}
