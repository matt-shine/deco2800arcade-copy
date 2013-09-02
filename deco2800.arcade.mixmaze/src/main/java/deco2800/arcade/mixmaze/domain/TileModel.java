package deco2800.arcade.mixmaze.domain;

import java.util.Random;

import static deco2800.arcade.mixmaze.domain.Direction.*;

/**
 * TileModel represents a tile on game board.
 */
public class TileModel {

	private static final String LOG = "TileModel: ";

	// Tile data
	private int tileX;
	private int tileY;
	private WallModel[] walls;

	private PlayerModel boxer;

	private Random spawner = new Random();
	private ItemModel spawnedItem;
	private long lastSpawned;

	/**
	 * Returns the column number of this tile on game board.
	 *
	 * @return the column number
	 */
	public int getX() {
		return tileX;
	}

	/**
	 * Returns the row number of this tile on game board.
	 *
	 * @return the row number
	 */
	public int getY() {
		return tileY;
	}

	/**
	 * Returns the wall specified by <code>direction</code>.
	 *
	 * @param direction specifying the wall to get
	 * @return the specified wall
	 * @throws IllegalArgumentException If <code>direction</code> is not one
	 * 				    of <code>WEST</code>,
	 * 				    <code>NORTH</code>,
	 *				    <code>EAST</code>,
	 *				    or <code>SOUTH</code>.
	 */
	public WallModel getWall(int direction) {
		// Check the specified direction is in range.
		if (!isDirection(direction)) {
			throw NOT_A_DIRECTION;
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

	/**
	 * Checks if the box on this tile should be built.
	 * If so, <code>player</code> is set to be the boxer of this tile.
	 * Otherwise, the boxer is set to <code>null</code>.
	 *
	 * @param player the player to check
	 */
	public void checkBox(PlayerModel player) {
		if(player == null) {
			throw new IllegalArgumentException("player cannot be null.");
		}
		boxer = isBox() ? player : null;
	}

	public boolean isBox() {
		return getWall(WEST).isBuilt()
				&& getWall(NORTH).isBuilt()
				&& getWall(EAST).isBuilt()
				&& getWall(SOUTH).isBuilt();
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
			System.err.println(LOG + "spawning TNT");
			return new TNTModel(this);
		} else if(spawnFactor <= 0.2) {
			System.err.println(LOG + "spawning pick");
			return new PickModel(this);
		} else {
			int amount = spawner.nextInt(3) + 1;
			System.err.println(LOG + "spawning brick");
			return new BrickModel(this, amount);
		}
	}

	public void spawnItem() {
		if (spawnedItem == null && (System.currentTimeMillis() - lastSpawned) >= (25 * 1000)) {
			if(spawner.nextDouble() <= 0.15) {
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

	/**
	 * Constructor.
	 *
	 * @param x		the column number on game board
	 * @param y		the row number on game board
	 * @param adjWalls	the wall adjacent to this tile
	 */
	public TileModel(int x, int y, WallModel[] adjWalls) {
		tileX = x;
		tileY = y;
		walls = new WallModel[4];
		for (int direction = 0; direction < 4; ++direction) {
			if (adjWalls[direction] == null) {
				walls[direction] = new WallModel(direction);
			} else {
				walls[direction] = adjWalls[direction];
			}
			walls[direction].addTile(this);
		}
	}
}
