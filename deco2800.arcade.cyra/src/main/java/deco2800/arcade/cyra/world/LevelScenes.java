package deco2800.arcade.cyra.world;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import deco2800.arcade.cyra.model.CutsceneObject;
import deco2800.arcade.cyra.model.MovableEntity;
import deco2800.arcade.cyra.model.ResultsScreen;
import deco2800.arcade.cyra.model.Player;

public abstract class LevelScenes {
	protected Player ship;
	protected ParallaxCamera cam;
	protected ResultsScreen resultsScreen;
	protected boolean isPlaying;
	
	public LevelScenes(Player ship, ParallaxCamera cam, ResultsScreen resultsScreen) {
		this.ship = ship;
		this.cam = cam;
		this.resultsScreen = resultsScreen;
		isPlaying = false;
	}
	
	public boolean isPlaying() {
		
		return isPlaying;
	}
	
	public abstract Array<Object> start(int scenePosition, float rank, int time);
	public abstract boolean update(float delta);
	public abstract float[] getStartValues();
	public abstract Vector2 getPlayerReloadPosition(int scene);
	public abstract int getScenePositionAfterReload(int scene);
	public boolean isGameWon() {
		return false;
	}
}
