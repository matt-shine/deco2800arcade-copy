package deco2800.arcade.hunter.model;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MapPane {
	/**
	 * Stores a fixed size 2D array of integers which represent the map tiles
	 */
	
	private int[][] data;
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
	public MapPane(int[][] data, int startOffset, int endOffset, MapType type) {
		this.data = data;
		this.startOffset = startOffset;
		this.endOffset = endOffset;
		this.type = type;
		
		//Get a rendered version of the map pane
		rendered = MapPaneRenderer.renderPane(this, type);
		
	}
	
	/**
	 * Getter for map data array
	 * @return map data
	 */
	public int[][] getData() {
		return data;
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
