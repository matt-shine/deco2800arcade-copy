package deco2800.arcade.hunter.model;

import java.util.ArrayList;

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
	 * Get a random map pane of the requested type
	 * @param type map type
	 * @return new MapPane
	 */
	private MapPane getRandomPane(MapType type) {
		String filename;
		filename = "maps/exampleMap.csv";
		
		return new MapPane(filename, type); //Fixed start/end offset should be read from a file
	}
	
	/**
	 * Update the state of the map, should be called each time the main render loop is called
	 * @param delta delta time of the render loop
	 * @param gameSpeed current game speed
	 */
	public void update(float delta, float cameraX) {
		if (cameraX - Config.PANE_SIZE_PX * paneCount > offset.x * Config.PANE_SIZE_PX) {
			offset.x++;
			
			offset.y += (panes.get(0).getEndOffset() - panes.get(1).getStartOffset()) * Config.TILE_SIZE;
			
			panes.remove(0);
			panes.add(getRandomPane(MapType.GRASS));
		}
	}

	@Override
	public void draw(SpriteBatch batch) {
		// TODO Auto-generated method stub
		
		float yOffset = this.offset.y;
		
		for (int i = 0; i < panes.size(); i++) {
			if (i != 0) {
				yOffset += (panes.get(i-1).getEndOffset() - panes.get(i).getStartOffset()) * Config.TILE_SIZE;
			}
			
			batch.draw(panes.get(i).getRendered(), i * Config.PANE_SIZE_PX + offset.x * Config.PANE_SIZE_PX, yOffset);
		}		
	}
}