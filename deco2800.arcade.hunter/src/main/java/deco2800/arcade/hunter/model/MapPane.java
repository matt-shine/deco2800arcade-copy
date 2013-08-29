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
	
	public MapPane(int[][] data, int startOffset, int endOffset, MapType type) {
		this.data = data;
		this.startOffset = startOffset;
		this.endOffset = endOffset;
		this.type = type;
		
		//Get a rendered version of the map pane
		rendered = MapPaneRenderer.renderPane(this, type);
		
	}
	
	public int[][] getData() {
		return data;
	}
	
	public int getStartOffset() {
		return startOffset;
	}
	
	public int getEndOffset() {
		return endOffset;
	}
	
	public MapType getType() {
		return type;
	}
	
	public TextureRegion getRendered() {
		return rendered;
	}
}
