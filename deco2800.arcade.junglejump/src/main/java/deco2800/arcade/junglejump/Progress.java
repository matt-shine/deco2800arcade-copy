package main.java.deco2800.arcade.junglejump;


/**
 * Class which keeps track of the progress of the game.
 * Lives, levels and collectables will be written into this
 * when loading a game.
 * 
 * @author Cameron
 *
 */
public class Progress {
	
	 // bananas should be List of each level with a list of which bananas were found
	
	/**
	 * Initialises progress variables
	 */
	public Progress(int levels, int bananas, int life) {
		int levelsComplete = levels;
		//bananas = 0;
		int foundBananas = bananas;
		int lives = life;
	}
	
	public resetProgress() {
		int levelsComplete = 0;
		bananas = 0;
		int foundBananas = 0;
		int lives = 3;
	}
	
	/**
	 * Adds a banana to the total found
	 */
	public void addBanana(int level, int banana) {
	}

}