package deco2800.arcade.junglejump;

import java.io.*;
import java.net.URL;
import java.util.*;

import com.badlogic.gdx.Gdx;
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
	private static int worldAmount;
	
	/**
	 * Constructor where levels are created and placed
	 * into the list.]
	 */
	public LevelContainer() {
		levels = new ArrayList<Level>();
		currentLevel = 0;
		currentWorld = 0;
		levelAmount = 5;
		worldAmount = 1;
		
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
	        				level.addBanana(); // false means not found
	        				p = new Platform(c, false, (x*xLength), (y*xLength), xLength, yLength);
		        			level.addPlatform(p);
		        			bananaCounter++;
	        		}
	        		if(c!='*' && c!= '.') {
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
		System.out.println("loading next level");
		clearCurrentLevel();
		currentLevel++;
		if(currentLevel > levelAmount-1) {
			currentLevel = 0;
			currentWorld++;
			if(currentWorld > worldAmount-1) {
				currentWorld = 0;
			}
			junglejump.world = currentWorld;
			junglejump.gameBackground = new Texture(Gdx.files.internal("world" + (currentWorld+1) + "/background.png"));
			junglejump.worldNumText = new Texture(Gdx.files.internal((currentWorld + 1) + ".png"));
		}
		junglejump.currentLevel = getLevel(currentLevel);
		//currentLevel = newLevel;
		junglejump.levelNumText = new Texture(Gdx.files.internal((currentLevel + 1) + ".png"));
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
	
	public static Level getLevel(int currentLevel) {
		return levels.get((currentWorld * levelAmount) + currentLevel);
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
