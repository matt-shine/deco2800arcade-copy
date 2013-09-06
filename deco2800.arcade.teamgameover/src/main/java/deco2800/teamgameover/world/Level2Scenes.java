package deco2800.teamgameover.world;

import com.badlogic.gdx.utils.Array;

import deco2800.teamgameover.model.MovableEntity;
import deco2800.teamgameover.model.Ship;

public class Level2Scenes extends LevelScenes {

	public Level2Scenes(Ship ship) {
		super(ship);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Array<MovableEntity> start() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public float[] getStartValues() {
		float[] starts = {999};
		return starts;
	}

	@Override
	public boolean isPlaying() {
		// TODO Auto-generated method stub
		return false;
	}

}
