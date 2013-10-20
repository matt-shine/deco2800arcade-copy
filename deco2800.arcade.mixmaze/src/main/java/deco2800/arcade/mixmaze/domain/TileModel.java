package deco2800.arcade.mixmaze.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import deco2800.arcade.mixmaze.Achievements;
import static deco2800.arcade.mixmaze.domain.Direction.*;

/**
 * TileModel represents a tile on the game board.
 */
public class TileModel {

	/** Column number */
	private int x;

	/** Row number */
	private int y;

	/** Whether the box is built or not */
	private boolean isBoxBuilt = false;

	/** Tiles adjacent to this tile */
	private TileModel[] adjacentTiles;

	/** Walls adjacent to this tile */
	private WallModel[] walls = new WallModel[4];

	/** Player who built the box or <code>null</code> if not built */
	private PlayerModel boxer = null;

	/** Observers to this tile */
	private List<TileModelObserver> observers = new ArrayList<TileModelObserver>();

	/**
	 * Constructs a new <code>TileModel</code> at (<code>x</code>,
	 * <code>y</code>) with <code>adjWalls</code> surrounding this
	 * <code>TileModel</>.
	 * 
	 * @param x
	 *            the column number (origin at top left)
	 * @param y
	 *            the row number (origin at top left)
	 * @param newAdjacentTiles
	 *            the tiles adjacent to this tile
	 */
	public TileModel(int x, int y, TileModel[] newAdjacentTiles) {
		this.x = x;
		this.y = y;
		if (newAdjacentTiles == null) {
			this.adjacentTiles = new TileModel[0];
		} else {
			this.adjacentTiles = newAdjacentTiles;
		}
		initializeWalls();
	}

	/**
	 * Initialises the walls adjacent to this tile.
	 */
	private void initializeWalls() {
		for (int direction = 0; direction < 4; ++direction) {
			TileModel adjTile = adjacentTiles[direction];

			if (adjTile != null) {
				int polarDir = Direction.getPolarDirection(direction);
				walls[direction] = adjTile.getWall(polarDir);
				adjTile.addAdjacentTile(this, polarDir);
			} else {
				walls[direction] = new WallModel();
			}

			if (direction == Direction.NORTH || direction == Direction.EAST) {
				walls[direction].setLeftTile(this);
			} else {
				walls[direction].setRightTile(this);
			}
		}
	}

	/**
	 * Adds an observer to this tile.
	 * 
	 * @param observer
	 *            the observer
	 */
	public void addObserver(TileModelObserver observer) {
		observers.add(observer);
	}

	/**
	 * Updates all observers on the item status.
	 * 
	 * @param type
	 *            the item type
	 */
	public void updateType(ItemModel.Type type) {
		for (TileModelObserver o : observers) {
			o.updateType(type);
		}
	}

	/**
	 * Updates all observers on the boxer on this tile.
	 * 
	 * @param id
	 *            the id of the boxer
	 */
	private void updateBoxer(int id) {
		for (TileModelObserver o : observers) {
			o.updateBoxer(id);
		}
	}

	private TileModel getAdjacentTile(int direction) {
		if (!Direction.isDirection(direction)) {
			throw Direction.NOT_A_DIRECTION;
		}
		return adjacentTiles[direction];
	}

	/**
	 * Records an adjacent tile to this tile.
	 * 
	 * @param tile
	 *            the adjacent tile
	 * @param direction
	 *            the direction of the adjacent tile to this tile
	 */
	private void addAdjacentTile(TileModel tile, int direction) {
		TileModel cTile = adjacentTiles[direction];

		if (cTile != null && cTile != tile) {
			throw new IllegalStateException(
					"tile adjacency cannot be changed once set.");
		}
		adjacentTiles[direction] = tile;
	}

	/**
	 * Determines if the tile is on the edge of the game board.
	 * 
	 * @return <CODE>true</CODE> if the tile is at the edge of of the game
	 *         board, <CODE>false</CODE> otherwise
	 */
	public boolean isEdgeTile() {
		for (int direction = 0; direction < 4; ++direction) {
			if (adjacentTiles[direction] == null) {
				return true;
			}
		}
		return false;
	}

	public boolean isBoxBuilt() {
		return isBoxBuilt;
	}

	/**
	 * Returns the column number of this tile on game board. Origin is at the
	 * top left corner.
	 * 
	 * @return the column number
	 */
	public int getX() {
		return x;
	}

	/**
	 * Returns the row number of this tile on game board. Origin is at the top
	 * left corner.
	 * 
	 * @return the row number
	 */
	public int getY() {
		return y;
	}

	/**
	 * Returns the adjacent wall specified by <code>direction</code>.
	 * 
	 * @param direction
	 *            the direction of the requested wall
	 * @return the adjacent wall in the specified <code>direction</code>
	 * @throws IllegalArgumentException
	 *             If <code>direction</code> is not one of <code>WEST</code>,
	 *             <code>NORTH</code>, <code>EAST</code>, or <code>SOUTH</code>.
	 */
	public WallModel getWall(int direction) {
		if (!isDirection(direction)) {
			throw NOT_A_DIRECTION;
		}

		return walls[direction];
	}

	/**
	 * Checks if the wall on the specified <code>direction</code> is built.
	 * 
	 * @param direction
	 *            the direction
	 * @return <code>true</code> if the wall is built, <code>false</code>
	 *         otherwise.
	 */
	public boolean isWallBuilt(int direction) {
		return getWall(direction).isBuilt();
	}

	/**
	 * Checks if this tile is a complete box.
	 * 
	 * @return <code>true</code> if this tile is a complete box,
	 *         <code>false</code> otherwise.
	 */
	public boolean isWallBox() {
		return isWallBuilt(WEST) && isWallBuilt(NORTH) && isWallBuilt(EAST)
				&& isWallBuilt(SOUTH);
	}

	/**
	 * Returns the boxer of this tile.
	 * 
	 * @return the <code>player</code> if there is a complete box,
	 *         <code>null</code> otherwise
	 */
	public PlayerModel getBoxer() {
		return boxer;
	}

	/**
	 * Sets the boxer of this tile.
	 * 
	 * @param p
	 *            the builder
	 */
	public void setBoxer(PlayerModel p) {
		boxer = p;
	}

	/**
	 * Validates the status of the box on this tile, and modifies the
	 * <code>boxer</code> based on any change.
	 * 
	 * @param player
	 *            the player who used an action against this tile
	 */
	public void validateBox(PlayerModel player) {
		validateBox(player, false, false);
	}

	private void validateBox(PlayerModel player, boolean multiboxing,
			boolean build) {
		// Check multi-boxing
		if (!multiboxing) {
			List<TileModel> boxes = new ArrayList<TileModel>();
			List<TileModel> builtBoxes = new ArrayList<TileModel>();
			if (findBoxes(this, boxes) != null) {
				for (TileModel box : boxes) {
					box.validateBox(player, true, true);
				}
				
				if(player.getId() == 1 && boxes.size() >= 5) {
					Achievements.incrementAchievement(Achievements.AchievementType.BuildBig);
				}
				
				if(player.getId() == 1 && boxes.size() >= 10) {
					Achievements.incrementAchievement(Achievements.AchievementType.Strategist);
				}
				return;
			} else if (findBuiltBoxes(this, builtBoxes) != null) {
				for (TileModel box : builtBoxes) {
					box.validateBox(player, true, false);
				}
				return;
			}
		}

		if (!isBoxBuilt() && ((multiboxing && build) || isWallBox())) {
			isBoxBuilt = true;
			boxer = player;
			boxer.incrementScore();
			updateBoxer(player.getId());
		} else if (isBoxBuilt() && ((multiboxing && !build) || !isWallBox())) {
			isBoxBuilt = false;
			boxer.decrementScore();
			boxer = null;
			updateBoxer(0);
		}
	}

	private List<TileModel> findBoxes(TileModel tile, List<TileModel> tileList) {
		if (tile == null) {
			return null;
		}

		List<TileModel> tiles = (tileList != null) ? tileList
				: new ArrayList<TileModel>();
		tiles.add(tile);
		for (int direction = 0; direction < 4; ++direction) {
			WallModel wall = tile.getWall(direction);
			if (!wall.isBuilt()) {
				TileModel adjTile = tile.getAdjacentTile(direction);
				if (!tiles.contains(adjTile)
						&& findBoxes(adjTile, tiles) == null) {
					return null;
				}
			}
		}
		return tiles;
	}

	private List<TileModel> findBuiltBoxes(TileModel tile,
			List<TileModel> tileList) {
		if (tile == null || !tile.isBoxBuilt()) {
			return null;
		}

		List<TileModel> tiles = (tileList != null) ? tileList
				: new ArrayList<TileModel>();
		tiles.add(tile);
		for (int direction = 0; direction < 4; ++direction) {
			WallModel wall = tile.getWall(direction);
			TileModel adjacentTile = tile.getAdjacentTile(direction);
			if (!tileList.contains(adjacentTile) && !wall.isBuilt()) {
				findBuiltBoxes(adjacentTile, tiles);
			}
		}
		return tiles;
	}

	@Override
	public String toString() {
		return String.format("<TileModel: %d,%d>", x, y);
	}
}
