package deco2800.arcade.mixmaze.domain;

import java.util.Random;

public class TileModel {
	// Tile data
	private int tileX;
	private int tileY;
	private WallModel[] walls;
	
	private PlayerModel boxer;
	
	private Random spawner = new Random();
	private ItemModel spawnedItem;
	private long lastSpawned;
	
	public int getX() {
		return tileX;
	}
	
	public int getY() {
		return tileY;
	}
	
	public WallModel getWall(int direction) {
		// Check the specified direction is in range.
		if(!Direction.isDirection(direction)) {
			throw Direction.NOTADIRECTION;
		}
		return walls[direction];
	}
	
	public boolean isBox() {
		return getWall(Direction.WEST).isBuilt() && 
				getWall(Direction.NORTH).isBuilt() && 
				getWall(Direction.EAST).isBuilt() && 
				getWall(Direction.SOUTH).isBuilt();
	}
	
	public void spawnItem() {
		if((System.currentTimeMillis() - lastSpawned) >= (10 * 1000)) {
			if(spawner.nextDouble() <= 0.2) {
				// Spawn item
			}
			lastSpawned = System.currentTimeMillis();
		}
	}
	
	public TileModel(int x, int y) {
		tileX = x;
		tileY = y;
		walls = new WallModel[4];
		for(int direction = 0; direction < 4; ++direction) {
			walls[direction] = new WallModel(direction);
		}
	}
}
