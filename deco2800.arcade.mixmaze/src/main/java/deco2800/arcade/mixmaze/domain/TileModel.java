package deco2800.arcade.mixmaze.domain;

import java.util.Random;

import static deco2800.arcade.mixmaze.domain.Direction.*;
import static deco2800.arcade.mixmaze.domain.PlayerModel.PlayerAction.USE_BRICK;

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
	 * Returns the column number of this tile on game board. origin is at the top left corner.
	 *
	 * @return the column number
	 */
	public int getX() {
		return tileX;
	}

	/**
	 * Returns the row number of this tile on game board.origin is at the top left corner.
	 *
	 * @return the row number
	 */
	public int getY() {
		return tileY;
	}

	/**
	 * Returns the adjacent wall specified by <code>direction</code>.
	 *
	 * @param direction a direction specifying the adjacent wall this tile.
	 * @return the adjacent wall in the specified <code>direction</code>
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

	/**
	 * Tries to build a wall on this tile facing the specified <code>direction</code>. 
	 * A wall is built if these conditions are satisfied:
	 * <ul>
	 *   <li>there is no wall already in the specified <code>direction</code>
	 *   <li>the <code>player</code> has at least one brick
	 *   <li>the <code>player</code>'s active action is to use brick
	 * </ul>
	 *
	 * @param player the player in this tile
	 * @param direction the direction which the wall needs to be built.
	 * @throws IllegalArgumentException If <code>player</code> is
	 * 				    <code>null</code>
	 * @return <code>true</code> if the wall is built,<code>false</code> otherwise
	 */
	public boolean buildWall(PlayerModel player, int direction) {
		if (player == null) {
			throw new IllegalArgumentException("player cannot be null.");
		}

		WallModel wall = getWall(player.getDirection());

		if (wall.isBuilt() || player.getBrick().getAmount() <= 0
				|| player.getPlayerAction() != USE_BRICK) {
			return false;
		} else {
			player.getBrick().removeOne();
			wall.build(player);
			return true;
		}
	}

	/**
	 * Tries to destroy the wall on the specified <code>direction</code> of this tile.
	 * A wall is destroyed if these conditions are satisfied:
	 * <ul>
	 *   <li>the wall is already built
	 *   <li>the <code>player</code> is using a pick or TNT.
	 * </ul>
	 *
	 * @param player the player in this tile
	 * @param direction the direction which the wall needs to be destroyed.
	 * @throws IllegalArgumentException If <code>player</code> is
	 * 				    <code>null</code>
	 * @return <code>true</code> if the wall is destroyed,<code>false</code> otherwise
	 */
	public boolean destroyWall(PlayerModel player, int direction) {
		if (player == null) {
			throw new IllegalArgumentException(
					"player cannot be null.");
		}

		WallModel wall = getWall(direction);
		if (!wall.isBuilt() || player.getPlayerAction() == USE_BRICK) {
			return false;
		} else {
			wall.destroy(player);
			return true;
		}
	}

	/**
	 * Assign this tile's boxer to the specified <code>player</code>. 
	 * Boxer is only assign to this tile given that this tile is a complete box.
	 * Otherwise, the boxer is set to <code>null</code>.
	 *
	 * @param player the player in this tile
	 */
	//probably good if the method name can be changed to something like assignBoxer
	public void checkBox(PlayerModel player) {
		if (player == null) {
			throw new IllegalArgumentException("player cannot be null.");
		}

		boxer = isBox() ? player : null;
	}

	/**
	 * Checks if this tile is a complete box.
	 * 
	 * @return <code>true</code> if this tile is a complete box, <code>false</code> otherwise.
	 */
	public boolean isBox() {
		return getWall(WEST).isBuilt()
				&& getWall(NORTH).isBuilt()
				&& getWall(EAST).isBuilt()
				&& getWall(SOUTH).isBuilt();
	}

	/**
	 * Returns the boxer of this tile. 
	 * 
	 * @return the <code>player</code>, if there is a complete box, <code>null</code> otherwise
	 */
	public PlayerModel getBoxer() {
		return boxer;
	}

	/**
	 * Returns the current <code>item</code> on this tile.The item can be one of <code>Brick</code>,
	 * <code>Pick</code>,<code> TNT</code>
	 * 
	 * @return the <code>item</item> if it's present, <code>null</code> otherwise.
	 */
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

	/**
	 * Spawns a item on this tile. The item can be a <code>Pick</code>,<code> TNT</code> or a 
	 *  collection of <code>Bricks</code>
	 */
	public void spawnItem() {
		if (spawnedItem == null && (System.currentTimeMillis() - lastSpawned) >= (25 * 1000)) {
			if(spawner.nextDouble() <= 0.15) {
				spawnedItem = getRandomItem();
			}
			lastSpawned = System.currentTimeMillis();
		}
	}

	/**
	 * Destroy the <code>item</code> on this tile. If there is no spawned item in this tile, an 
	 * <code>IllegalStateException</code> is thrown
	 * 
	 * @throws IllegalStateException if there is no item present in this tile
	 */
	//probably a good idea to change the method name to destroyItem. coz this method get confused 
	// with the playermodel.pickUpItem and also it destroy rhe spawned item? dumi
	public void pickUpItem() {
		if(spawnedItem == null) {
			throw new IllegalStateException("No spawnedItem to consume.");
		}
		spawnedItem = null;
	}

	/**
	 * checks if the an <code>item</code> is present in this tile.If so, 
	 * <code>player</code> picks up the item.
	 * 
	 * @param player the player in this tile
	 * 
	 * @throws IllegalStateException if the <code>player's</code> coordinates doesn't match up with this tile's coordinates
	 */
	public void onPlayerEnter(PlayerModel player) {
		if(player.getX() != tileX || player.getY() != tileY) {
			throw new IllegalStateException("The player did not enter the tile.");
		}

		if(spawnedItem != null) {
			player.pickUpItem(spawnedItem);
		}
	}

	@Override
	public String toString() {
		return LOG + "row: " + tileY + "\tcolumn: " + tileX;
	}

	/**
	 * Constructs a new <code>TileModel</code> at <code>x</code>, <code>y</code> with <code>adjWalls</code> 
	 * surrouding the this <code>TileModel</>
	 *
	 * @param x		the column number on game board. origin starts at top left
	 * @param y		the row number on game board.origin starts at top left
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
