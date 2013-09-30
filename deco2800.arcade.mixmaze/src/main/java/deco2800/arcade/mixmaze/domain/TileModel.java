package deco2800.arcade.mixmaze.domain;

import deco2800.arcade.mixmaze.TileViewModel;
import deco2800.arcade.mixmaze.domain.view.IItemModel.ItemType;
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
	private boolean isBoxBuilt;
	private WallModel[] walls = null;
	private PlayerModel boxer = null;
	private TileModel[] adjTiles;
	private ArrayList<TileViewModel> viewers;

	/**
	 * Constructs a new <code>TileModel</code> at <code>x</code>, <code>y</code> with <code>adjWalls</code>
	 * surrounding the this <code>TileModel</>
	 *
	 * @param x		the column number on game board. origin starts at top left
	 * @param y		the row number on game board.origin starts at top left
	 * @param adjWalls	the wall adjacent to this tile
	 */
	public TileModel(int x, int y, TileModel[] tiles) {
		viewers = new ArrayList<TileViewModel>();
		tileX = x;
		tileY = y;
		isBoxBuilt = false;
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

	public void addViewer(TileViewModel v) {
		viewers.add(v);
	}

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

	private int getDirection(WallModel w) {
		for (int i = 0; i < 4; i++)
			if (w == walls[i])
				return i;
		return -1;
	}

	/**
	 * Validates the status of the box on this tile, and modifies the
	 * <code>boxer</code> based on any change.
	 *
	 * @param player	the player who used an action against this tile
	 */
	void validateBox(PlayerModel player) {
		if (!isBoxBuilt && isBox()) {
			isBoxBuilt = true;
			boxer = player;
			boxer.incrementScore();
			updateBoxer(player.getId());
		} else if (isBoxBuilt && !isBox()) {
			isBoxBuilt = false;
			boxer.decrementScore();
			boxer = null;
			updateBoxer(0);
		}
	}

	void updateWall(WallModel w, boolean isBuilt) {
		for (TileViewModel v : viewers)
			v.updateWall(getDirection(w), isBuilt);
	}

	void updateType(ItemType type) {
		for (TileViewModel v : viewers)
			v.updateType(type);
	}

	private void updateBoxer(int id) {
		for (TileViewModel v : viewers)
			v.updateBoxer(id);
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
		return (boxer == null) ? 0 : boxer.getId();
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
	 * Set the boxer of this tile.
	 *
	 * @param p	the builder
	 */
	void setBoxer(PlayerModel p) {
		boxer = p;
	}

}
