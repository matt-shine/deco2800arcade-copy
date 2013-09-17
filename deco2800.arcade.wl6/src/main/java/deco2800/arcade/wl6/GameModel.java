package deco2800.arcade.wl6;

import java.util.Iterator;
import java.util.LinkedList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;


/**
 * The Game.
 * This will eventually work on the server as well.
 * @author Simon
 *
 */
public class GameModel {
	
	//the current level map
	private Level currentMap = null;
	
	//the name of the current level
	private String currentLevel = "nothing";
	
	//the player
	private Player player = new Player();
	
	//the player spawn point
	private Vector2 spawn = new Vector2(0, 0);
	
	//All the entities
	private LinkedList<Doodad> doodads = new LinkedList<Doodad>();
	
	
	
	public GameModel() {
	}
	
	
	/**
	 * Change the current level. Performs blocking file IO if the
	 * level is not the current level.
	 * @param level
	 */
	public void goToLevel(String level) {
		//create the map
		if (currentLevel != level) {
			currentMap = new Level(loadFile("wl6maps/" + level + ".json"));
		}
		
		currentLevel = level;
		
		//remove the old player and create a new one
		if (doodads.contains(player)) {
			destroyDoodad(player);
		}
		
		MapProcessor.processEverything(this);
		
		player = new Player();
		player.setPos(spawn);
		this.addDoodad(player);
	}
	
	
	
	/**
	 * The level name
	 * @return
	 */
	public String getLevel() {
		return currentLevel;
	}
	
	
	
	/**
	 * Restart the level
	 */
	public void reset() {
		goToLevel(currentLevel);
	}
	
	
	/**
	 * returns the current map
	 * @return
	 */
	public Level getMap() {
		return this.currentMap;
	}
	
	
	/**
	 * Returns the player
	 * @return
	 */
	public Player getPlayer() {
		return player;
	}
	
	
	
	/**
	 * Reads a file
	 * @param name
	 * @return
	 */
	private String loadFile(String name) {
		return Gdx.files.internal(name).readString();
	}
	
	
	/**
	 * Sets the player's spawn
	 * @param x
	 * @param y
	 */
	public void setSpawnPoint(float x, float y) {
		spawn = new Vector2(x, y);
	}
	
	
	/**
	 * Spawn an item
	 * @param doodad
	 */
	public void addDoodad(Doodad doodad) {
		doodads.add(doodad);
	}
	
	
	
	/**
	 * remove an item. This should be safe to call during a tick call
	 * @param doodad
	 */
	public void destroyDoodad(Doodad doodad) {
		doodads.remove(doodad);
	}
	
	
	/**
	 * An iterator for all the doodads
	 * @return
	 */
	public Iterator<Doodad> getDoodadIterator() {
		return this.doodads.iterator();
	}
	
	
}
