package deco2800.arcade.cyra.world;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import deco2800.arcade.cyra.model.ResultsScreen;
import deco2800.arcade.cyra.model.Player;

public abstract class LevelScenes {
	protected Player ship;
	protected ParallaxCamera cam;
	protected ResultsScreen resultsScreen;
	protected boolean isPlaying;
	
	/**
	 * LevelScenes controls the cutscenes on a per level basis
	 * @param ship
	 * @param cam
	 * @param resultsScreen
	 */
	public LevelScenes(Player ship, ParallaxCamera cam, ResultsScreen resultsScreen) {
		this.ship = ship;
		this.cam = cam;
		this.resultsScreen = resultsScreen;
		isPlaying = false;
	}
	
	public boolean isPlaying() {
		
		return isPlaying;
	}
	
	/**
	 * start the scenePosition scene
	 * @param scenePosition
	 * @param rank
	 * @param time the time taken found in World and displayed on top right of screen
	 * @return
	 */
	public abstract Array<Object> start(int scenePosition, float rank, int time);
	public abstract boolean update(float delta);
	
	/**
	 * 
	 * @return the x-values to start the scenes at
	 */
	public abstract float[] getStartValues();
	
	/**
	 * 
	 * @param scene the scenePosition the game was cancelled at
	 * @return the position to place the player in when the level reloads
	 */
	public abstract Vector2 getPlayerReloadPosition(int scene);
	
	/**
	 * 
	 * @param scene the scenePosition before level reload
	 * @return the scenePosition after level reload
	 */
	public abstract int getScenePositionAfterReload(int scene);
	
	/**
	 * 
	 * @return true if game is completed
	 */
	public boolean isGameWon() {
		return false;
	}
}
