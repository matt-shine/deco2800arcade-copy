package deco2800.arcade.mixmaze.domain;

import deco2800.arcade.mixmaze.domain.PlayerModel;
import java.util.ArrayList;
import java.util.List;

/**
 * WallModel represents a wall on a tile, which can be either active or 
 * inactive.
 */
class WallModel {

	/** Adjacent tiles */
	private final List<TileModel> tiles;

	/** Whether this wall is built or not */
	private boolean isBuilt;
	
	/** The player who builds this wall, <code>null</code> if not built */
	private PlayerModel builder;

	/**
	 * Constructor
	 */
	WallModel() {
		tiles = new ArrayList<TileModel>();
	}

	/**
	 * Adds a tile adjacent to this wall.
	 * 
	 * @param tile	the tile to add
	 */
	void addTile(TileModel tile) {
		if (tiles.contains(tile))
			throw new IllegalStateException("The tile is already present.");
		else
			tiles.add(tile);
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
		
		for (TileModel t : tiles) {
			t.validateBox(player, this);
			t.updateWall(this, true);
		}
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
		
		for (TileModel t : tiles) {
			t.validateBox(player, this);
			t.updateWall(this, false);
		}
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder("<WallModel: ");
		
		str.append((isBuilt) ? "built" : "not built");
		str.append(">");
		return str.toString();
	}
}
