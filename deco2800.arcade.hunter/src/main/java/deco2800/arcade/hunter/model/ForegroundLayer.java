package deco2800.arcade.hunter.model;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import deco2800.arcade.hunter.Hunter.Config;
import deco2800.arcade.hunter.model.MapPane.MapType;

public class ForegroundLayer extends Map {
	private ArrayList<MapPane> panes;
	private int paneCount; //How many panes should we keep loaded at a time, should be the number that can fit on the screen plus one
	
	/**
	 * @param width map panes to keep loaded at a time
	 */
	public ForegroundLayer(float speedModifier, int paneCount) {
		super(speedModifier);
		this.paneCount = paneCount;
		
		panes = new ArrayList<MapPane>(paneCount);
		while (panes.size() < this.paneCount) {
			panes.add(getRandomPane(MapType.GRASS));
		}
	}
	
	public ArrayList<MapPane> getPanes() {
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
		
		if (xOffset < -(Config.PANE_SIZE * Config.TILE_SIZE)) {
			//Remove the first MapPane and add in another one
			panes.remove(0);
			xOffset += Config.PANE_SIZE * Config.TILE_SIZE;
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
		int [][] paneData = new int[Config.PANE_SIZE][Config.PANE_SIZE];
		//This should pull a random map from a file/cache
		FileHandle mapFile = Gdx.files.internal("maps/exampleMap.csv"); //Fixed map file should instead be selected from a list in another file
		String mapDataString = mapFile.readString();
		String[] dataArray = mapDataString.split(",");
		
		if (dataArray.length != Math.pow(Config.PANE_SIZE, 2)) {
			//Create an exception here TODO, invalid map data
		} else {
			for (int y = 0; y < Config.PANE_SIZE; y++) {
				for (int x = 0; x < Config.PANE_SIZE; x++) {
					paneData[y][x] = Integer.parseInt(dataArray[y*Config.PANE_SIZE + x]);
				}
			}
		}
		
		return new MapPane(paneData, 5, 6, type); //Fixed start/end offset should be read from a file
	}

	@Override
	public void draw(SpriteBatch batch) {
		// TODO Auto-generated method stub
		
		int offset = 0;
		
		for (int i = 0; i < panes.size(); i++) {
			if (i != 0) {
				offset += (panes.get(i-1).getEndOffset() - panes.get(i).getStartOffset()) * Config.TILE_SIZE;
			}
			
			batch.draw(panes.get(i).getRendered(), getXOffset() + (i * Config.TILE_SIZE * Config.PANE_SIZE), offset);
		}
		
	}
}