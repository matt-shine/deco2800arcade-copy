package deco2800.arcade.junglejump;



import java.util.ArrayList;

public class Progress {
	
	ArrayList<ArrayList> bananas; // List of each level with a list of which bananas were found
	
	/**
	 * Initialises progress variables
	 */
	public Progress() {
		int levelsComplete = 0;
		bananas = new ArrayList();
		
		int foundBananas = 0;
		int lives = 3;
	}
	
	/**
	 * Adds a banana to the total found
	 */
	public void addBanana(int level, int banana) {
	}

}