package deco2800.arcade.mixmaze.domain;

import java.util.ArrayList;
import java.util.List;

import deco2800.arcade.mixmaze.domain.PlayerModel;

/**
 * WallModel represents a wall on a tile, which can be either active or
 * inactive.
 */
public class WallModel {

	/**
	 * The tile of the left side of the wall if you were facing from end-to-end.
	 */
	private TileModel leftTile;

	/**
	 * The tile of the right side of the wall if you were facing from
	 * end-to-end.
	 */
	private TileModel rightTile;

	/** Whether this wall is built or not */
	private boolean isBuilt;

	/** The player who builds this wall, <code>null</code> if not built */
	private PlayerModel builder;

	/** Observers to this tile */
	private List<WallModelObserver> observers = new ArrayList<WallModelObserver>();

	/**
	 * Adds an observer to this wall.
	 * 
	 * @param observer
	 *            the observer
	 */
	public void addObserver(WallModelObserver observer) {
		observers.add(observer);
	}

	/**
	 * Updates all observers on the wall status.
	 */
	private void updateWall() {
		for (WallModelObserver o : observers) {
			o.updateWall(isBuilt);
		}
	}

	/**
	 * Gets the tile on the left side of the wall.
	 * 
	 * @return <code>null</code> if there is no tile on the left side, otherwise
	 *         the associated <code>TileModel</code>
	 */
	public TileModel getLeftTile() {
		return leftTile;
	}

	/**
	 * Specifies the tile on the left side of the wall if you were facing from
	 * end-to-end.
	 * 
	 * @param left
	 *            tile on the left side of the wall
	 */
	public void setLeftTile(TileModel left) {
		leftTile = left;
	}

	/**
	 * Gets the tile on the right side of the wall.
	 * 
	 * @return <code>null</code> if there is no tile on the right side,
	 *         otherwise the associated <code>TileModel</code>
	 */
	public TileModel getRightTile() {
		return rightTile;
	}

	/**
	 * Specifies the tile on the right side of the wall if you were facing from
	 * end-to-end.
	 * 
	 * @param right
	 *            tile on the right side of the wall
	 */
	public void setRightTile(TileModel right) {
		rightTile = right;
	}

	/**
	 * Returns if the wall is built.
	 * 
	 * @return true if the wall is built, otherwise false
	 */
	boolean isBuilt() {
		return isBuilt;
	}

	public boolean isInBox() {
		return (leftTile != null && leftTile.isBoxBuilt())
				|| (rightTile != null && rightTile.isBoxBuilt());
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
	 * @param player
	 *            the builder
	 */
	void build(PlayerModel player) {
		if (player == null) {
			throw new IllegalArgumentException("player cannot be null");
		}
		if (isBuilt) {
			throw new IllegalStateException("this wall is already built");
		}

		isBuilt = true;
		builder = player;
		updateWall();
		updateTiles(player);
	}

	/**
	 * Destroys this wall.
	 * 
	 * @param player
	 *            the player who destroys this wall
	 */
	public void destroy(PlayerModel player) {
		if (!isBuilt) {
			throw new IllegalStateException("wall not built");
		}

		isBuilt = false;
		builder = null;
		updateWall();
		updateTiles(player);
	}

	/**
	 * Validates the box status of both associated tiles.
	 * 
	 * @param player
	 *            the player that builds/destroys this wall
	 */
	private void updateTiles(PlayerModel player) {
		if (leftTile != null) {
			leftTile.validateBox(player);
		}
		if (rightTile != null) {
			rightTile.validateBox(player);
		}
	}

	@Override
	public String toString() {
		return String.format("<WallModel: %s>", ((isBuilt) ? "built"
				: "not built"));
	}
}
