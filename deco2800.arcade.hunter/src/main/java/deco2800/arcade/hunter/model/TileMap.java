package deco2800.arcade.hunter.model;

import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import deco2800.arcade.hunter.model.MapPane.MapType;

public class TileMap {
	public static final int TILE_SIZE = 64;
	public static final int PANE_SIZE = 16;
	private Queue<MapPane> panes;
	private int paneCount; //How many panes should we keep loaded at a time, should be the number that can fit on the screen plus one
	private float xOffset = 0;
	private float speedModifier = 3; //Used to control parallax effect, 1 for the primary map layer etc
	
	/**
	 * @param width map panes to keep loaded at a time
	 */
	public TileMap(int paneCount) {	
		this.paneCount = paneCount;
		panes = new ArrayBlockingQueue<MapPane>(paneCount);
		while (panes.size() < this.paneCount) {
			panes.add(getRandomPane(MapType.GRASS));
		}
	}
	
	/**
	 * @param speedModifier
	 */
	public void setSpeedModifier(float speedModifier) {
		this.speedModifier = speedModifier;
	}
	
	/**
	 * 
	 * @return
	 */
	public Queue<MapPane> getPanes() {
		return panes;
	}
	
	public ArrayList<MapPane> getPaneArray() {
		ArrayList<MapPane> paneArray = new ArrayList<MapPane>(panes);
		return paneArray;
	}
	
	/**
	 * Update the state of the map, should be called each time the main render loop is called
	 * @param delta delta time of the render loop
	 * @param gameSpeed current game speed
	 */
	public void update(float delta, float gameSpeed) {
		xOffset -= delta * gameSpeed * speedModifier;
		
		if (xOffset < -(PANE_SIZE * TILE_SIZE)) {
			//Remove the first MapPane and add in another one
			panes.poll();
			xOffset += PANE_SIZE * TILE_SIZE;
			panes.add(getRandomPane(MapType.GRASS));
		}
	}
	
	/**
	 * Get a random map pane of the requested type
	 * Loads the map from a CSV file in the resources/maps/ folder
	 * @param type map type
	 * @return new MapPane
	 */
	public MapPane getRandomPane(MapType type) {
		int [][] paneData = new int[TileMap.PANE_SIZE][TileMap.PANE_SIZE];
		//This should pull a random map from a file/cache
		FileHandle mapFile = Gdx.files.internal("maps/exampleMap.csv"); //Fixed map file should instead be selected from a list in another file
		String mapDataString = mapFile.readString();
		String[] dataArray = mapDataString.split(",");
		
		if (dataArray.length != Math.pow(TileMap.PANE_SIZE, 2)) {
			//Create an exception here TODO, invalid map data
		} else {
			for (int y = 0; y < TileMap.PANE_SIZE; y++) {
				for (int x = 0; x < TileMap.PANE_SIZE; x++) {
					paneData[y][x] = Integer.parseInt(dataArray[y*TileMap.PANE_SIZE + x]);
				}
			}
		}
		
		return new MapPane(paneData, 5, 6, type); //Fixed start/end offset should be read from a file
	}
	
	/**
	 * @return xOffset
	 */
	public float getXOffset() {
		return xOffset;
	}
}