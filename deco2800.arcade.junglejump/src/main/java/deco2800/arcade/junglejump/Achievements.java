package deco2800.arcade.junglejump;

import java.util.*;

/**
 * Keeps track of achievement progress and which achievements have
 * been unlocked.
 * @author Cameron
 *
 */
public class Achievements {
	public ArrayList<Boolean> earned; // List of booleans representing unlock status of achievement
	
	/**
	 * Constructor for achievements
	 */
	public Achievements() {
		int size = 20; // Amount of achievements in the game, should maybe read in from xml file
		earned = new ArrayList<Boolean>();
		// Set all achievements to be locked
		for(int i = 0; i<size; i++) {
			lockAchievement(i);
		}
	}
	
	/**
	 * Sets an achievement to be unlocked
	 * @param achievement
	 */
	public void earnAchievement(int achievement) {
		earned.set(achievement, true);
	}
	
	/**
	 * Returns list of earned achievements
	 * @return
	 */
	public ArrayList<Boolean> getList() {
		return earned;
	}
	
	private void lockAchievement(int achievement) {
		earned.set(achievement, false);
	}

}
