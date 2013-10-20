package deco2800.arcade.junglejump;

import java.io.*;
import java.net.URL;
import java.util.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;


/**
 * Class holding a list of all the levels in the game
 * Levels are sorted into worlds
 * @author Cameron
 *
 */
public class LevelContainer {
	static ArrayList<Level> levels;
	private static int currentLevel;
	private static int currentWorld;
	private static int levelAmount;
	private static int worldAmount;
	public int TOTAL_BANANAS;
	
	/**
	 * Constructor where levels are created and placed
	 * into the list.]
	 */
	public LevelContainer() {
		levels = new ArrayList<Level>();
		setCurrentLevel(0);
		setCurrentWorld(0);
		levelAmount = 5;
		worldAmount = 5;
		
		// Read level from file
		for(int i=0;i<worldAmount;i++) {
			for(int j=0; j<levelAmount; j++) {
				addLevel(i, j);
			}
		}
		
	}
	
	/**
	 * Adds a specific level to the game
	 * by reading file contents and creating platforms
	 * Level is added to arraylist of levels
	 * @param levelNum
	 */
	public void addLevel(int worldNum, int levelNum) {
		BufferedReader br;
		Level level = new Level(); // Creating and adding to a level
		
		URL path = this.getClass().getResource("/");
		
		
		try {
			String resource = path.toString().replace(".arcade/build/classes/main/", 
					".arcade.junglejump/src/main/").replace("file:", "") + 
					"resources/levels/world" + (worldNum+1) + "/level" + 
					(levelNum+1) + ".txt" ;
			System.out.println(resource);
			br = new BufferedReader(new FileReader(resource));
		} catch (FileNotFoundException e1) {
			System.out.println("No file");
			return;
		}
	    try {
	        String line = br.readLine();
	        
	        int xLength = 40;
	        int yLength = 40;
	        
	        int y = junglejump.SCREENHEIGHT/yLength;
	        while (line != null) {
	        	for(int x=0; x<junglejump.SCREENWIDTH/xLength; x++) {
	        		char c = line.charAt(x);
	        		Platform p;
	        		if(c == 'b') {
	        				TOTAL_BANANAS++;
	        				level.addBanana(); // false means not found
	        				p = new Platform(c, (x*xLength), (y*xLength), xLength, yLength);
		        			level.addPlatform(p);
	        		} else if(c!='*' && c!= '.') {
	        			p = new Platform(c, (x*xLength), (y*xLength), xLength, yLength);
	        			level.addPlatform(p);
	        		}
	        	}
	        	line = br.readLine();
	        	y--;
	        }
	    } catch (IOException e) {
			return;
		} finally {
	        try {
	        	addLevel(level);	
				br.close();
			} catch (IOException e) {
				return;
			}
	    }
	}
	
	
	/**
	 * Returns the next level after the given level
	 * @return
	 */
	public static void nextLevel() {
		System.out.println("loading next level");
		clearCurrentLevel();
		setCurrentLevel(getCurrentLevel() + 1);
		if(getCurrentLevel() > levelAmount-1) {
			setCurrentLevel(0);
			setCurrentWorld(getCurrentWorld() + 1);
			if(getCurrentWorld() > worldAmount-1) {
				setCurrentWorld(0);
			}
			junglejump.world = getCurrentWorld();
			junglejump.gameBackground = new Texture(Gdx.files.internal("world" + (getCurrentWorld()+1) + "/background.png"));
		}
		junglejump.currentLevel = getLevel(getCurrentLevel());
		//currentLevel = newLevel;
		junglejump.monkeyX = junglejump.monkeyDefaultX;
		junglejump.monkeyY = junglejump.monkeyDefaultY;
		junglejump.isFalling = true;
		
		int size = junglejump.currentLevel.platformAmount();
		for (int i = 0; i < size; i++) {
			Platform p = junglejump.currentLevel.getPlatforms().get(i);
			// Place platform onto screen
			p.refreshTexture(); // Make sure texture has changed to current world
			if(p.getX() >= 1000) {
				p.setX(p.getX()-1000);
			}
		}
		junglejump thing = null;
		thing.incrementAchievement("junglejump.firststeps");
		return;
	}
	
	/**
	 * Adds a level to the game
	 * @param level
	 */
	private void addLevel(Level level) {
		levels.add(level);
	}
	
	public static Level getLevel(int currentLevel) {
		return levels.get((getCurrentWorld() * levelAmount) + currentLevel);
	}
	
	/**
	 * Returns the index of a level
	 * @param level
	 * @return
	 */
	public static int getLevelIndex(Level level) {
		return levels.indexOf(level);
	}
	
	public static void clearCurrentLevel() {
		for (Platform p : getLevel(getCurrentLevel()).getPlatforms()) {
			p.setX(p.getX()+1000);
		}
	}

	public static int getCurrentLevel() {
		return currentLevel;
	}

	public static void setCurrentLevel(int currentLevel) {
		LevelContainer.currentLevel = currentLevel;
	}

	public static int getCurrentWorld() {
		return currentWorld;
	}

	public static void setCurrentWorld(int currentWorld) {
		LevelContainer.currentWorld = currentWorld;
	}
	

}
