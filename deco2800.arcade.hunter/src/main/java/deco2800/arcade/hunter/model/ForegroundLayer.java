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
		//REPLACE TODO with getPaneOffset()
		for (int i = 0; i < panes.size(); i++) {
			if (i != 0) {
				yOffset += (panes.get(i-1).getEndOffset() - panes.get(i).getStartOffset()) * Config.TILE_SIZE;
			}
			
			batch.draw(panes.get(i).getRendered(), i * Config.PANE_SIZE_PX + offset.x * Config.PANE_SIZE_PX, yOffset);
		}		
	}
	
	public int getPaneAt(int x, int y) {
		if (x / Config.PANE_SIZE >= offset.x && 
			x / Config.PANE_SIZE < offset.x + Config.PANE_SIZE * paneCount) {
			
		}
		
		return -1;
	}
	
	/*
	 * Pane y offset relative to the main map offset
	 */
	private float getPaneOffset(int paneIndex) {
		float yOffset = this.offset.y;
		
		if (paneIndex == 0) {
			return yOffset;
		} else if (paneIndex > 0 && paneIndex <= paneCount) {
			for (int i = 1; i <= paneIndex; i++) {
				yOffset += (panes.get(i-1).getEndOffset() - panes.get(i).getStartOffset()) * Config.TILE_SIZE;
			}
			
			return yOffset;
		}
		return -1;
	}
	
	/**
	 * Get the collision tile at a given world-space coordinate
	 * @param x
	 * @param y
	 * @return
	 */
	public int getCollisionTileAt(int x, int y) {
		float tileOffsetX = x - offset.x * Config.PANE_SIZE_PX;
		float paneOffsetY;
		int tileX, tileY;
		int pane = (int) (tileOffsetX / Config.PANE_SIZE_PX);
		
		if (pane >= 0 && pane < paneCount) {
			
			tileX = Config.PANE_SIZE - (int) (tileOffsetX / Config.TILE_SIZE);
			
			paneOffsetY = getPaneOffset(pane);
			
			if (y >= paneOffsetY && y <= paneOffsetY + Config.PANE_SIZE_PX){
				tileY = Config.PANE_SIZE - (int) ((y + paneOffsetY) / Config.TILE_SIZE);
				
				int tile = panes.get(pane).getCollisionTile(tileX, tileY);
				return tile;
			}
		}
		return -1;
	}
}
