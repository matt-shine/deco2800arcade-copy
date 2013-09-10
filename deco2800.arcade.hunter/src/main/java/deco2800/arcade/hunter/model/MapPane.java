package deco2800.arcade.hunter.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import deco2800.arcade.hunter.Hunter.Config;

public class MapPane {
	/**
	 * Stores a fixed size 2D array of integers which represent the map tiles
	 */
	
	private int[][] background;
	private int[][] foreground;
	private int startOffset;
	private int endOffset;
	public enum MapType {
		GRASS,
		ROCK
	}
	private MapType type;
	private TextureRegion rendered;
	
	/**
	 * 
	 * @param data map data
	 * @param startOffset The first tile from the bottom on the left hand
	 * 		  side of the map, which is a space where the player can stand.
	 * @param endOffset The first tile from the bottom on the right hand
	 * 		  side of the map, which is a space where the player can stand.
	 * @param type the current map type
	 */
	public MapPane(String filename, MapType type) {
		
		//Rewrite to take file path string, make parser for file of format
		/*
		 * [startOffset, endOffset]
		 * [backgroundLayerData]
		 * [foregroundLayerData]
		 */
		
		this.background = new int[Config.PANE_SIZE][Config.PANE_SIZE];
		this.foreground = new int[Config.PANE_SIZE][Config.PANE_SIZE];
		this.type = type;
		
		loadPane(filename);
		renderPane();
		
		//Get a rendered version of the map pane
		rendered = MapPaneRenderer.renderPane(this, type);
		
	}
	
	private void renderPane() {
		// TODO Auto-generated method stub
		
	}

	private void loadPane(String filename) {
		FileHandle mapFile = Gdx.files.internal(filename);
		
		String mapDataString = mapFile.readString();
		String[] dataArray = mapDataString.replace("[", "").replace("]", "").split("\n");
		
		//Parse offsets
		String[] offsets = dataArray[0].split(",");
		this.startOffset = Integer.parseInt(offsets[0]);
		this.endOffset = Integer.parseInt(offsets[1]);
		
		String[] backgroundData = dataArray[1].split(",");
		String[] foregroundData = dataArray[2].split(",");
		
		//Parse map data
		for (int row = 0; row < Config.PANE_SIZE; row++) {
			for (int col = 0; col < Config.PANE_SIZE; col++) {
				background[row][col] = Integer.parseInt(backgroundData[row*Config.PANE_SIZE + col]);
				foreground[row][col] = Integer.parseInt(foregroundData[row*Config.PANE_SIZE + col]);
			}
		}
		
	}

	/**
	 * Getter for map data array
	 * @return map data
	 */
	public int[][] getForegroundData() {
		return foreground;
	}
	
	public int[][] getBackgroundData() {
		return background;
	}
	
	/**
	 * Getter for startOffset
	 * @return start offset
	 */
	public int getStartOffset() {
		return startOffset;
	}
	
	/**
	 * Getter for end offset
	 * @return end offset
	 */
	public int getEndOffset() {
		return endOffset;
	}
	
	/**
	 * Getter for type
	 * @return MapType
	 */
	public MapType getType() {
		return type;
	}
	
	/**
	 * Getter for rendered version of the map
	 * @return rendered version of the map
	 */
	public TextureRegion getRendered() {
		return rendered;
	}
}
