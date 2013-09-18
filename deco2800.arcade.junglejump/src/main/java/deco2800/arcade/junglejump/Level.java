package deco2800.arcade.junglejump;

import java.util.*;


/**
 * Class containing a level.
 * Keeps track of all objects within that level
 * and their locations
 * @author Cameron
 *
 */
public class Level {
	private int length;
	private int height;
	private ArrayList<Platform> platforms; // list of all objects available to be rendered
	private ArrayList<Collectable> bananas;
	
	public Level() {
		platforms = new ArrayList<Platform>();
		bananas = new ArrayList<Collectable>();
	}
	
	public ArrayList<Platform> getPlatforms() {
		return platforms;
	}
	
	public int platformAmount() {
		return platforms.size();
	}
	
	/**
	 * Returns the next level
	 * @return
	 */
	public Level nextLevel() {
		return null;
	}
	
	/**
	 * Adds a platform to the objects in the game
	 * @param thing
	 */
	public void addPlatform(Platform thing) {
		platforms.add(thing);
	}
	
	public void addBanana(Collectable banana) {
		bananas.add(banana);
	}
	
	/**
	 * Returns the index of the current level
	 * @return
	 */
	public int getIndex() {
		return LevelContainer.getLevelIndex(this);
	}

}
