package deco2800.arcade.mixmaze.domain;

import deco2800.arcade.mixmaze.domain.view.ITileModel;
import deco2800.arcade.mixmaze.domain.view.IWallModel;

import static deco2800.arcade.mixmaze.domain.Direction.*;

import java.util.ArrayList;
import java.util.List;

/**
 * TileModel represents a tile on game board.
 */
public class TileModel implements ITileModel {

	// Tile data
	private int tileX;
	private int tileY;
	private WallModel[] walls = null;
	private PlayerModel boxer = null;
	private TileModel[] adjTiles;

	@Override
	public int getX() {
		return tileX;
	}

	@Override
	public int getY() {
		return tileY;
	}

	@Override
	public boolean isWallBuilt(int direction) {
		return getWall(direction).isBuilt();
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
	IWallModel getWall(int direction) {
		// Check the specified direction is in range.
		if (!isDirection(direction)) {
			throw NOT_A_DIRECTION;
		}
		return walls[direction];
	}

	public void buildBox(PlayerModel player) {
		if (player == null) {
			throw new IllegalArgumentException("player cannot be null.");
		}
		boxer = isBox() ? player : null;
	}

	public List<TileModel> findPath(List<TileModel> path)
	{
		for(int direction = 0; direction < 4; ++direction) {
			WallModel wall = walls[direction];
			if(wall.isBuilt()) {
				if(Direction.isXDirection(direction)) {
					TileModel northTile = adjTiles[Direction.NORTH];
					if(northTile.getWall(Direction.SOUTH).isBuilt() || northTile.getWall(direction).isBuilt()) {

					}

					TileModel adjTile = adjTiles[direction];
					if(adjTile.getWall(Direction.NORTH).isBuilt() || adjTile.getWall(Direction.SOUTH).isBuilt()) {

					}
				} else {

				}

				switch(direction) {
				case Direction.WEST:
					break;
				case Direction.NORTH:
					break;
				case Direction.EAST:
					break;
				case Direction.SOUTH:
					break;
				}
			}
		}
		return path;
	}

	private boolean checkWalls(int wallDirection, int tileDirection) {
		if(Direction.isXDirection(wallDirection)) {

		} else {

		}
		return false;
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

	public int getBoxerId() {
		return (boxer == null) ? 0 : boxer.getPlayerID();
	}

	/**
	 * Returns the boxer of this tile.
	 *
	 * @return the <code>player</code>, if there is a complete box, <code>null</code> otherwise
	 */
	PlayerModel getBoxer() {
		return boxer;
	}

	@Override
	public String toString() {
		return LOG + "row: " + tileY + "\tcolumn: " + tileX;
	}

	private TileModel getAdjacent(int direction) {
		if(!Direction.isDirection(direction)) {
			throw Direction.NOT_A_DIRECTION;
		}
		return adjTiles[direction];
	}

	private void addAdjacent(TileModel tile, int direction) {
		TileModel cTile = adjTiles[direction];
		if(cTile != null && cTile != tile) {
			throw new IllegalStateException("tile adjacency cannot be changed once set.");
		}
		adjTiles[direction] = tile;
	}

	/**
	 * Constructs a new <code>TileModel</code> at <code>x</code>, <code>y</code> with <code>adjWalls</code>
	 * surrounding the this <code>TileModel</>
	 *
	 * @param x		the column number on game board. origin starts at top left
	 * @param y		the row number on game board.origin starts at top left
	 * @param adjWalls	the wall adjacent to this tile
	 */
	public TileModel(int x, int y, TileModel[] tiles) {
		tileX = x;
		tileY = y;
		adjTiles = tiles;
		walls = new WallModel[4];
		for (int direction = 0; direction < 4; ++direction) {
			TileModel tile = tiles[direction];
			int polarDir = Direction.getPolarDirection(direction);
			if(tile != null) {
				walls[direction] = (WallModel) tile.getWall(polarDir);
				tile.addAdjacent(tile, polarDir);
			} else {
				walls[direction] = new WallModel();
			}
			walls[direction].addTile(this);
		}
	}

	/**
	 * Set the boxer of this tile.
	 *
	 * @param p	the builder
	 */
	void setBoxer(PlayerModel p) {
		boxer = p;
	}

}
