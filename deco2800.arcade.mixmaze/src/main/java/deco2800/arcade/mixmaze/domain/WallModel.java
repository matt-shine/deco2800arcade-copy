package deco2800.arcade.mixmaze.domain;

import deco2800.arcade.mixmaze.domain.PlayerModel;
import java.util.ArrayList;
import java.util.List;

/**
 * WallModel represents a wall on a tile, which can be either active or 
 * inactive.
 */
class WallModel {
	/** Is this a horizontal wall? */
	private boolean isHorizontal = false;
	
	/** The tile of the left side of the wall if
	 * you were facing from end-to-end.
	 **/
	private TileModel leftTile;
	
	/** The tile of the right side of the wall if
	 * you were facing from end-to-end.
	 **/
	private TileModel rightTile;

	/** Whether this wall is built or not */
	private boolean isBuilt;
	
	/** The player who builds this wall, <code>null</code> if not built */
	private PlayerModel builder;

	/**
	 * Constructor
	 */
	WallModel(boolean isVertical) {
		isHorizontal = !isVertical;
	}
	
	/**
	 * Determines whether the wall faces North to South (vertically), or
	 * West to East (horizontally).
	 * @return A value indicating the orientation of the wall
	 */
	public boolean isHorizontalWall() {
		return isHorizontal;
	}
	
	/**
	 * Specifies the tile on the left side of the wall if
	 * you were facing from end-to-end.
	 * @param left tile on the left side of the wall
	 */
	public void setLeftTile(TileModel left) {
		leftTile = left;
	}
	
	/**
	 * Specifies the tile on the right side of the wall if
	 * you were facing from end-to-end.
	 * @param right tile on the right side of the wall
	 */
	public void setRightTile(TileModel right) {
		rightTile = right;
	}
	
	/**
	 * Determines whether the wall is on the edge of 
	 * the game board.
	 * @return <CODE>true</CODE> if the wall is on the
	 * edge of the board, <CODE>false</CODE> otherwise
	 */
	public boolean isEdgeWall() {
		return leftTile == null || rightTile == null;
	}

	/**
	 * Returns if the wall is built.
	 *
	 * @return true if the wall is built, otherwise false
	 */
	boolean isBuilt() {
		return isBuilt;
	}

	/**
	 * Returns the <code>PlayerModel</code> that built this wall, or
	 * <code>null</code> is not built.
	 *
	 * @return the <code>PlayerModel</code> or <code>null</code>
	 */
	PlayerModel getBuilder() {
		return builder;
	}

	/**
	 * Builds this wall.
	 * 
	 * @param player	the builder
	 */
	void build(PlayerModel player) {
		if (player == null)
			throw new IllegalArgumentException("player cannot be null");
		if (isBuilt)
			throw new IllegalStateException("this wall is already built");
		
		isBuilt = true;
		builder = player;
		updateTile(leftTile, player, true);
		updateTile(rightTile, player, true);
	}
	
	public List<WallModel> findRegion(WallModel wall, List<WallModel> path) {
		
		return null;
	}
	
	/**
	 * Destroys this wall.
	 * 
	 * @param player	the player who destroys this wall
	 */
	public void destroy(PlayerModel player) {
		if (!isBuilt)
			throw new IllegalStateException("wall not built");

		isBuilt = false;
		builder = null;
		updateTile(leftTile, player, false);
		updateTile(rightTile, player, false);
	}

	private void updateTile(TileModel tile, PlayerModel player, boolean isBuilt) {
		if(tile == null) {
			return;
		}
		tile.validateBox(player);
		tile.updateWall(this, isBuilt);
	}
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder("<WallModel: ");
		
		str.append((isBuilt) ? "built" : "not built");
		str.append(">");
		return str.toString();
	}
}
