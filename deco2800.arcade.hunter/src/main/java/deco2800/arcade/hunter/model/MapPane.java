package deco2800.arcade.hunter.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import deco2800.arcade.hunter.Hunter.Config;

public class MapPane {
	/**
	 * Stores a fixed size 2D array of integers which represent the map tiles
	 */
	
	private int[][] background = new int[Config.PANE_SIZE][Config.PANE_SIZE];
	private int[][] foreground = new int[Config.PANE_SIZE][Config.PANE_SIZE];
	private int[][] collision = new int[Config.PANE_SIZE][Config.PANE_SIZE];
	private int startOffset;
	private int endOffset;
	public enum MapType {
		GRASS,
		ROCK
	}
	private MapType type;
	private TextureRegion rendered;
	
	/**
	 * @param filename file path for map relative to src/resources/
	 * @param type The MapType of the map pane.
	 */
	public MapPane(String filename, MapType type) {
		this.type = type;

		loadPane(filename);
		renderPane();
		
		//Get a rendered version of the map pane
		rendered = MapPaneRenderer.renderPane(this);
		
	}
	
	private void renderPane() {
		// TODO Auto-generated method stub
		
	}

	private void loadPane(String filename) {
		FileHandle mapFile = Gdx.files.internal(filename);
		
		String mapDataString = mapFile.readString();
		
		mapDataString = mapDataString.replace("\n", "").replace("\r", "");
		String[] dataArray = mapDataString.replace("[", "").split("]");
		
		//Parse offsets
		String[] offsets = dataArray[0].split(",");
		this.startOffset = Integer.parseInt(offsets[0]) * Config.TILE_SIZE;
		this.endOffset = Integer.parseInt(offsets[1]) * Config.TILE_SIZE;
		
		String[] backgroundData = dataArray[1].split(",");
		String[] foregroundData = dataArray[2].split(",");
		String[] collisionData = dataArray[3].split(",");
		
		//Parse map data
		for (int row = 0; row < Config.PANE_SIZE; row++) {
			for (int col = 0; col < Config.PANE_SIZE; col++) {
				background[row][col] = Integer.parseInt(backgroundData[row*Config.PANE_SIZE + col]);
				foreground[row][col] = Integer.parseInt(foregroundData[row*Config.PANE_SIZE + col]);
				collision[row][col] = Integer.parseInt(collisionData[row*Config.PANE_SIZE + col]);
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
	
	public int getCollisionTile(int x, int y) {
		//Tiles aren't in this order...
		if (x >= 0 && x < Config.PANE_SIZE &&
			y >= 0 && y < Config.PANE_SIZE) {
			return collision[Config.PANE_SIZE - 1 - y][x];
		}
		return -3;
	}
}
