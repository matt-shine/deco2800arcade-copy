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
	private ArrayList<Object> objects; // list of all objects available to be rendered
	private ArrayList<Collectable> bananas;
	
	public Level() {
		objects = new ArrayList<Object>();
		bananas = new ArrayList<Collectable>();
	}
	
	/**
	 * Returns the next level
	 * @return
	 */
	public Level nextLevel() {
		return LevelContainer.nextLevel(this);
	}
	
	/**
	 * Adds a platform to the objects in the game
	 * @param thing
	 */
	public void addPlatform(Platform thing) {
		objects.add(thing);
	}
	
	public void addBanana(Collectable banana) {
		objects.add(banana);
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
