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
	
	public boolean buildWall(PlayerModel player, int direction) {
		if(player == null) {
			throw new IllegalArgumentException("player cannot be null.");
		}
		
		WallModel wall = getWall(direction);
		if(wall.isBuilt()) {
			return false;
		}
		wall.build(player);
		return true;
	}
	
	public boolean destroyWall(PlayerModel player, int direction) {
		if(player == null) {
			throw new IllegalArgumentException("player cannot be null.");
		}
		
		WallModel wall = getWall(direction);
		if(!wall.isBuilt()) {
			return false;
		}
		wall.destroy(player);
		return true;
	}
	
	public void checkBox(PlayerModel player) {
		if(player == null) {
			throw new IllegalArgumentException("player cannot be null.");
		}
		boxer = isBox() ? player : null;
	}
	
	public boolean isBox() {
		return getWall(Direction.WEST).isBuilt() && 
				getWall(Direction.NORTH).isBuilt() && 
				getWall(Direction.EAST).isBuilt() && 
				getWall(Direction.SOUTH).isBuilt();
	}
	
	public PlayerModel getBoxer() {
		return boxer;
	}
	
	public ItemModel getSpawnedItem() {
		return spawnedItem;
	}
	
	private ItemModel getRandomItem() {
		double spawnFactor = spawner.nextDouble();
		if(spawnFactor <= 0.1) {
			return new TNTModel(this);
		} else if(spawnFactor <= 0.2) {
			return new PickModel(this);
		} else {
			int amount = spawner.nextInt(5) + 1;
			return new BrickModel(this, amount);
		}
	}
	
	public void spawnItem() {
		if(spawnedItem == null && (System.currentTimeMillis() - lastSpawned) >= (10 * 1000)) {
			if(spawner.nextDouble() <= 0.2) {
				spawnedItem = getRandomItem();
			}
			lastSpawned = System.currentTimeMillis();
		}
	}
	
	public void pickUpItem() {
		if(spawnedItem == null) {
			throw new IllegalStateException("No spawnedItem to consume.");
		}
		spawnedItem = null;
	}
	
	public void onPlayerEnter(PlayerModel player) {
		if(player.getX() != tileX || player.getY() != tileY) {
			throw new IllegalStateException("The player did not enter the tile.");
		}
		
		if(spawnedItem != null) {
			player.pickUpItem(spawnedItem);
		}
	}
	
	public TileModel(int x, int y, WallModel[] adjWalls) {
		tileX = x;
		tileY = y;
		walls = new WallModel[4];
		for(int direction = 0; direction < 4; ++direction) {
			if(adjWalls[direction] == null) {
				walls[direction] = new WallModel(direction);
			} else {
				walls[direction] = adjWalls[direction];
			}
			walls[direction].addTile(this);
		}
	}
}
