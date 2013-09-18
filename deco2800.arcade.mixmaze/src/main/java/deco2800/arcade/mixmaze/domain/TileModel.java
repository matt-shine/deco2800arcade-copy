package deco2800.arcade.mixmaze.domain;

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
	 * of <code>WEST</code>, <code>NORTH</code>, <code>EAST</code>,
	 * or <code>SOUTH</code>.
	 */
	public WallModel getWall(int direction) {
		// Check the specified direction is in range.
		if (!isDirection(direction)) {
			throw NOT_A_DIRECTION;
		}
		return walls[direction];
	}

	/**
	 * Assign this tile's boxer to the specified <code>player</code>.
	 * Boxer is only assign to this tile given that this tile is a complete box.
	 * Otherwise, the boxer is set to <code>null</code>.
	 *
	 * @param player the player in this tile
	 */
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

	@Override
	public String toString() {
		return LOG + "row: " + tileY + "\tcolumn: " + tileX;
	}

	/**
	 * Constructs a new <code>TileModel</code> at <code>x</code>, <code>y</code> with <code>adjWalls</code>
	 * surrounding the this <code>TileModel</>
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
				walls[direction] = new WallModel();
			} else {
				walls[direction] = adjWalls[direction];
			}
			walls[direction].addTile(this);
		}
	}
}
