package deco2800.arcade.wolf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
    private String nextLevel = null;
    private String levelAfterSecret = null;

    // The player
    private Player player = null;

    // The player spawn point
    private Vector2 spawn = new Vector2(0, 0);
    private float spawnAngle = 0;

    // All the entities
    private LinkedList<Doodad> doodads = new LinkedList<Doodad>();

    // Array of the waypoints on the current map
    private WL6Meta.DIRS[][] waypoints = new WL6Meta.DIRS[64][64];

    private CollisionGrid collisionGrid = new CollisionGrid();

    // Delta time
    private float delta = 0;

    // Doodads to delete or add
    private List<Doodad> toDelete = new ArrayList<Doodad>();
    private List<Doodad> toAdd = new ArrayList<Doodad>();

	private int difficulty = 1;

	private boolean resetPlayer = false;
	
	private Map<String, String> levels = new HashMap<String, String>();
	

    public GameModel() {
    }


    /**
     * Change the current level. Performs blocking file IO if the
     * level is not the current level.
     * @param level
     */
    public void goToLevel(String level) {
        //create the new level
        doodads.clear();

        if (!currentLevel.equals(level)) {
            currentMap = new Level(levels.get(level));
        }

        currentLevel = level;
        nextLevel = null;
        

        waypoints = new WL6Meta.DIRS[64][64];
        collisionGrid = new CollisionGrid();
        
        MapProcessor.processEverything(this);
        
        endTick();
        
        Player oldPlayer = player;
        player = new Player(MapProcessor.doodadID());
        if (!resetPlayer && oldPlayer != null) {
        	 player.setAmmo(oldPlayer.getAmmo());
             player.setPoints(oldPlayer.getPoints());
             player.setHealth(oldPlayer.getHealth());
             player.setGuns(oldPlayer.getGuns());
             player.setCurrentGun(oldPlayer.getCurrentGun());
        }
        player.setPos(spawn);
        player.setAngle(this.spawnAngle);
        this.addDoodad(player);
        resetPlayer = false;
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
    	resetPlayer = true;
    	this.nextLevel = currentLevel;
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
     * add a level
     * @param l
     * @param s
     */
    public void addLevel(String l, String s) {
    	this.levels.put(l, s);
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
        toAdd.add(doodad);
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

    public WL6Meta.DIRS getWaypoint(int x, int y) {
        return waypoints[x][y];
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
        if (nextLevel != null) {
        	goToLevel(nextLevel);
        }
    }


    /**
     * Call this after finishing ticking.
     */
    public void endTick() {
    	
    	for (Doodad d : toDelete) {
            doodads.remove(d);
        }
        toDelete.clear();
        
        
        for (Doodad d : toAdd) {
            doodads.add(d);
            d.init(this);
        }
        toAdd.clear();
        
    }

    /**
     * get game difficulty
     * @return
     */
    public int getDifficulty() {
		return difficulty;
	}


    /**
     * set game difficulty
     * @param difficulty
     */
	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
		this.reset();
	}

	
	/**
	 * get the current episode as a string like "1" to "6"
	 * @return
	 */
	public String getChapter() {
		return currentLevel.substring(1, 2);
	}
	
	/**
	 * get the current level as a string like "1" to "8" or "b" or "s"
	 * @return
	 */
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
	 * if current level = s : current level = levelAfterSecret
	 */
	public void nextLevel() {
		if (getLevelInChapter().equals("b")) {
			if (getChapter().equals("6")) {
				nextLevel = "e1l1";
			} else {
				nextLevel = "e" + (Integer.parseInt(getChapter()) + 1) + "l1";
			}
		} else if (getLevelInChapter().equals("s")) {
			if (levelAfterSecret != null) {
				nextLevel = levelAfterSecret;
			} else {
				nextLevel = "e" + getChapter() + "l1";
			}
		} else if (getLevelInChapter().equals("8")) {
			nextLevel = "e" + getChapter() + "boss";
		} else {
			nextLevel = "e" + getChapter() + "l" + (Integer.parseInt(getLevelInChapter()) + 1);
		}
	}

	
	/**
	 * go to the secret level
	 */
	public void secretLevel() {
		nextLevel();
		levelAfterSecret = nextLevel;
		nextLevel = "e" + (Integer.parseInt(getChapter())) + "secret";
	}


	/**
	 * gets the collision grid
	 * @return
	 */
    public CollisionGrid getCollisionGrid() {
    	return this.collisionGrid;
    }
}
