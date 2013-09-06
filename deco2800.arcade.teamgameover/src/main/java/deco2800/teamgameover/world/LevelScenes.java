package deco2800.teamgameover.world;

import com.badlogic.gdx.utils.Array;

import deco2800.teamgameover.model.MovableEntity;
import deco2800.teamgameover.model.Player;

public abstract class LevelScenes {
	protected Player ship;
	public LevelScenes(Player ship) {
		this.ship = ship;
		
	}
	
	public abstract Array<MovableEntity> start();
	public abstract void update(float delta);
	public abstract float[] getStartValues();
	public abstract boolean isPlaying();
}
