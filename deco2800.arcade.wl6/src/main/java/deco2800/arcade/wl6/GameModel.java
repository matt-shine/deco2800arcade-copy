package deco2800.arcade.wl6;

import java.util.ArrayList;
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

    // The current level map
    private Level currentMap = null;

    // The name of the current level
    private String currentLevel = "nothing";

    // The player
    private Player player = null;

    // The player spawn point
    private Vector2 spawn = new Vector2(0, 0);
    private float spawnAngle = 0;

    // All the entities
    private LinkedList<Doodad> doodads = new LinkedList<Doodad>();

    // Array of the waypoints on the current map
    private WL6Meta.DIRS[][] waypoints = new WL6Meta.DIRS[64][64];



    // Delta time
    private float delta = 0;

    // Doodads to delete
    private ArrayList<Doodad> toDelete = new ArrayList<Doodad>();

	private int difficulty = 1;



    public GameModel() {
    }


    /**
     * Change the current level. Performs blocking file IO if the
     * level is not the current level.
     * @param level
     */
    public void goToLevel(String level) {
        //create the map
        if (!currentLevel.equals(level)) {
            currentMap = new Level(loadFile("wl6maps/" + level + ".json"));
        }

        currentLevel = level;

        for (Doodad d : doodads) {
        	this.destroyDoodad(d);
        }
        waypoints = new WL6Meta.DIRS[64][64];
        
        MapProcessor.processEverything(this);

        player = new Player(MapProcessor.doodadID());
        player.setPos(spawn);
        player.setAngle(this.spawnAngle);
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
    public void setSpawnPoint(float x, float y, float angle) {
        spawn = new Vector2(x, y);
        spawnAngle = angle;
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
        toDelete.add(doodad);
    }


    /**
     * An iterator for all the doodads
     * @return
     */
    public Iterator<Doodad> getDoodadIterator() {
        return this.doodads.iterator();
    }


    public void addWaypoint(WL6Meta.DIRS angle, int x, int y) {
        waypoints[x][y] = angle;
    }

    public WL6Meta.DIRS[][] getWapoints() {
        return waypoints.clone();
    }


    /**
     * returns the time that the last frame took
     * @return
     */
    public float delta() {
        return this.delta;
    }



    /**
     * Update the delta time
     * @param delta
     */
    public void setDelta(float delta) {
        this.delta = delta;
    }



    /**
     * Call this before ticking.
     */
    public void beginTick() {
        //nothing to do yet
    }


    /**
     * Call this after finishing ticking.
     */
    public void endTick() {
        for (Doodad d : toDelete) {
            doodads.remove(d);
        }
    }

    public int getDifficulty() {
		return difficulty;
	}


	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
		this.reset();
	}

	public String getChapter() {
		return currentLevel.substring(1, 2);
	}
	
	public String getLevelInChapter() {
		if (!currentLevel.substring(2, 3).equals("l")) {
			return currentLevel.substring(2, 3);
		}
		return currentLevel.substring(3, 4);
	}
	
	
	/**
	 * Sends the player to the next level
	 * if current level = 1-7 : current level++
	 * if current level = 8 : current level = b
	 * if current level = b : chapter++, current level = 1
	 * if current level = s : current level = 2
	 */
	public void nextLevel() {
		if (getLevelInChapter().equals("b")) {
			if (getChapter().equals("6")) {
				goToLevel("e1l1");
			} else {
				goToLevel("e" + (Integer.parseInt(getChapter()) + 1) + "l1");
			}
		} else if (getLevelInChapter().equals("s")) {
			goToLevel("e" + getChapter() + "l2");
		} else if (getLevelInChapter().equals("8")) {
			goToLevel("e" + getChapter() + "boss");
		} else {
			goToLevel("e" + getChapter() + "l" + (Integer.parseInt(getLevelInChapter()) + 1));
		}
	}

	
	/**
	 * go to the secret level
	 */
	public void secretLevel() {
		goToLevel("e" + (Integer.parseInt(getChapter())) + "secret");
	}


    
}
