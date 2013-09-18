package deco2800.arcade.junglejump;

import java.io.*;
import java.util.*;

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
	public int currentWorld;
	
	/**
	 * Constructor where levels are created and placed
	 * into the list.]
	 */
	public LevelContainer() {
		levels = new ArrayList<Level>();
		currentLevel = 0;
		currentWorld = 0;
		
		// Read level from file
		for(int i=0;i<2;i++) {
			addLevel(i);
		}
		
	}
	
	public void addLevel(int levelNum) {
		BufferedReader br;
		Level level = new Level(); // Creating and adding to a level
		try {
			br = new BufferedReader(new FileReader("junglejumpassets/levels/world1/level" + (levelNum+1) + ".txt"));
		} catch (FileNotFoundException e1) {
			System.out.println("No file");
			return;
		}
	    try {
	        String line = br.readLine();
	        
	        int xLength = 40;
	        int yLength = 20;
	        
	        int y = junglejump.SCREENHEIGHT/yLength;
	        while (line != null) {
	        	for(int x=0; x<junglejump.SCREENWIDTH/xLength; x++) {
	        		char c = line.charAt(x);
	        		Platform p;
	        		switch(c) {
	        		case '-':
	        			p = new Platform(c, false, (x*xLength), (y*yLength), xLength, yLength);
	        			level.addPlatform(p);
	        			break;
	        		case '^':
	        			p = new Platform(c, false, (x*xLength), (y*yLength), xLength, yLength);
	        			level.addPlatform(p);
	        			break;
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
		int newLevel = currentLevel+1;
		junglejump.currentLevel = getLevel(newLevel);
		clearCurrentLevel();
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
			p.setX(1000);
		}
	}
	

}
