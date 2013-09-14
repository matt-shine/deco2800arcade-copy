package deco2800.arcade.junglejump;

import java.util.*;

/**
 * Class holding a list of all the levels in the game
 * Levels are sorted into worlds
 * @author Cameron
 *
 */
public class LevelContainer {
	static ArrayList<Level> levels;
	
	/**
	 * Constructor where levels are created and placed
	 * into the list.
	 */
	public LevelContainer() {
		levels = new ArrayList<Level>();
		
		Level level1 = new Level(); // Creating and adding to a test level
		
		Platform platform = new Platform(300,50,100,25);
		Platform platform2 = new Platform(50,100,100,25);
		Collectable testBanana = new Collectable();
		level1.addPlatform(platform);
		level1.addPlatform(platform2);
		level1.addBanana(testBanana);
		addLevel(level1);	
	}
	
	
	/**
	 * Returns the next level after the given level
	 * @param currentLevel
	 * @return
	 */
	public static Level nextLevel(Level currentLevel) {
		int newLevel = levels.indexOf(currentLevel);
		return levels.get(newLevel);
	}
	
	/**
	 * Adds a level to the game
	 * @param level
	 */
	private void addLevel(Level level) {
		levels.add(level);
	}
	
	public static Level getLevel(int i) {
		return levels.get(i);
	}
	
	/**
	 * Returns the index of a level
	 * @param level
	 * @return
	 */
	public static int getLevelIndex(Level level) {
		return levels.indexOf(level);
	}

}
