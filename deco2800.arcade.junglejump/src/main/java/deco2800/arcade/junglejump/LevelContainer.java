package deco2800.arcade.junglejump;

import java.io.*;
import java.util.*;

import com.badlogic.gdx.graphics.Texture;

import deco2800.arcade.junglejump.GUI.junglejump;


/**
 * Class holding a list of all the levels in the game
 * Levels are sorted into worlds
 * @author Cameron
 *
 */
public class LevelContainer {
	static ArrayList<Level> levels;
	public static int currentLevel;
	public static int currentWorld;
	private static int levelAmount;
	private int worldAmount = 3;
	
	/**
	 * Constructor where levels are created and placed
	 * into the list.]
	 */
	public LevelContainer() {
		levels = new ArrayList<Level>();
		currentLevel = 0;
		currentWorld = 0;
		levelAmount = 4;
		
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
		int bananaCounter = 0;
		Level level = new Level(); // Creating and adding to a level
		
		try {
			br = new BufferedReader(new FileReader("junglejumpassets/levels/world" + (worldNum+1) + "/level" + (levelNum+1) + ".txt"));
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
	        				level.addBanana(); // false means not found
	        				p = new Platform(c, false, (x*xLength), (y*xLength), xLength, yLength);
		        			level.addPlatform(p);
		        			bananaCounter++;
	        		}
	        		if(c!='*') {
	        			p = new Platform(c, false, (x*xLength), (y*xLength), xLength, yLength);
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
		clearCurrentLevel();
		currentLevel++;
		if(currentLevel > levelAmount-1) {
			currentLevel = 0;
			currentWorld++;
			if(currentWorld > 5) {
				currentWorld = 0;
			}
			junglejump.world = currentWorld;
		}
		junglejump.gameBackground = new Texture(("junglejumpassets/world" + currentWorld + "/background.png"));
		junglejump.currentLevel = getLevel(currentLevel);
		//currentLevel = newLevel;
		junglejump.monkeyX = junglejump.monkeyDefaultX;
		junglejump.monkeyY = junglejump.monkeyDefaultY;
		return;
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
	
	public static void clearCurrentLevel() {
		for (Platform p : getLevel(currentLevel).getPlatforms()) {
			p.setX(p.getX()+1000);
		}
	}
	

}
