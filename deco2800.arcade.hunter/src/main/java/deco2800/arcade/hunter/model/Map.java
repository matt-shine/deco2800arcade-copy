package deco2800.arcade.hunter.model;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import deco2800.arcade.hunter.model.MapPane.MapType;

public class Map {
	public static final int TILE_SIZE = 64;
	public static final int PANE_SIZE = 16;
	private Queue<MapPane> panes;
	private int paneCount; //How many panes should we keep loaded at a time, should be the number that can fit on the screen plus one
	private float xOffset = 0;
	private float speedModifier = 1; //Used to control parallax effect, 1 for the primary map layer etc
	
	public Map(int paneCount) {
		/**
		 * @param width map panes to keep loaded at a time
		 */
		
		
		this.paneCount = paneCount;
		panes = new ArrayBlockingQueue<MapPane>(paneCount);
		while (panes.size() < this.paneCount) {
			panes.add(getRandomPane(MapType.GRASS));
		}
	}
	
	public void setSpeedModifier(float speedModifier) {
		this.speedModifier = speedModifier;
	}
	
	public Queue<MapPane> getPanes() {
		return panes;
	}
	
	public void update(float delta, float gameSpeed) {
		xOffset -= delta * gameSpeed * speedModifier;
		
		if (xOffset < -(PANE_SIZE * TILE_SIZE)) {
			//Remove the first MapPane and add in another one
			panes.poll();
			xOffset += PANE_SIZE * TILE_SIZE;
			panes.add(getRandomPane(MapType.GRASS));
		}
	}
	
	public MapPane getRandomPane(MapType type) {
		int [][] paneData = new int[Map.PANE_SIZE][Map.PANE_SIZE];
		//This should pull a random map from a file/cache
		FileHandle mapFile = Gdx.files.internal("maps/basicMap.csv"); //Fixed map file should instead be selected from a list in another file
		String mapDataString = mapFile.readString();
		String[] dataArray = mapDataString.split(",");
		
		if (dataArray.length != Math.pow(Map.PANE_SIZE, 2)) {
			//Create an exception here TODO, invalid map data
		} else {
			for (int y = 0; y < Map.PANE_SIZE; y++) {
				for (int x = 0; x < Map.PANE_SIZE; x++) {
					paneData[y][x] = Integer.parseInt(dataArray[y*Map.PANE_SIZE + x]);
				}
			}
		}
		
		return new MapPane(paneData, 3, 2, type); //Fixed start/end offset should be read from a file
	}
	
	public float getXOffset() {
		return xOffset;
	}
}
