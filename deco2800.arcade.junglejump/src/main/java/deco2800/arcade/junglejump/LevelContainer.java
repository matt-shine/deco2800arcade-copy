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
	
	/**
	 * Constructor where levels are created and placed
	 * into the list.]
	 */
	public LevelContainer() {
		levels = new ArrayList<Level>();
		
		Level level1 = new Level(); // Creating and adding to a test level
		
		// Read level from file
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader("junglejumpassets/levels/level1.txt"));
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
	        		if(c != '*') {
	        			Platform p = new Platform(c, false, (x*xLength), (y*yLength), xLength, yLength);
	        			level1.addPlatform(p);
	        		}
	        	}
	        	line = br.readLine();
	        	y--;
	        }
	    } catch (IOException e) {
			return;
		} finally {
	        try {
				br.close();
			} catch (IOException e) {
				return;
			}
	    }
		
	    
		Collectable testBanana = new Collectable();
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
