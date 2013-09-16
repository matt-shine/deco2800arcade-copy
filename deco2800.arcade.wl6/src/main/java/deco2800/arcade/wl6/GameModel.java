package deco2800.arcade.wl6;

public class GameModel {
	
	private Level currentMap = null;
	private int currentLevel = 0;
	
	public GameModel(int level) {
		currentLevel = level;
		reset();
	}
	
	
	public void setLevel(int level) {
		currentLevel = level;
	}
	public int getLevel() {
		return currentLevel;
	}
	
	/**
	 * resets everything to the level's default state
	 */
	public void reset() {
		currentMap = new Level("[0,1,2,3]");
	}
	
	public Level getMap() {
		return this.currentMap;
	}
	
	
}
