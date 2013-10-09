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

    //the current level map
    private Level currentMap = null;

    //the name of the current level
    private String currentLevel = "nothing";

    //the player
    private Player player = null;

    //the player spawn point
    private Vector2 spawn = new Vector2(0, 0);
    private float spawnAngle = 0;

    //All the entities
    private LinkedList<Doodad> doodads = new LinkedList<Doodad>();

    //Delta time
    private float delta = 0;

    //Doodads to delete
    private ArrayList<Doodad> toDelete = new ArrayList<Doodad>();





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

        //remove the old player and create a new one
        if (doodads.contains(player)) {
            destroyDoodad(player);
        }

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
    
    
}
