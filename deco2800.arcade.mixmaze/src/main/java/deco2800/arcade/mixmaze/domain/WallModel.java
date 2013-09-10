package deco2800.arcade.mixmaze.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * WallModel represents an active/inactive wall on a tile.
 */
public class WallModel {
	private int direction;
	private List<TileModel> tiles;
	private boolean built;
	private PlayerModel builder;

	/**
	 * Returns the direction this wall is attached to its tile.
	 *
	 * @return the direction
	 */
	// i dnt understand the meaning if this method?dumi
	public int getDirection() {
		return direction;
	}

	public void addTile(TileModel tile) {
		if (tiles.contains(tile)) {
			throw new IllegalStateException(
					"The tile is already present.");
		}
		tiles.add(tile);
	}

	/**
	 * Returns if the wall is built.
	 *
	 * @return true if the wall is built, otherwise false
	 */
	public boolean isBuilt() {
		return built;
	}

	/**
	 * Returns the <code>PlayerModel</code> that builds this wall.
	 *
	 * @return the <code>PlayerModel</code>
	 */
	public PlayerModel getBuilder() {
		return builder;
	}

	public void build(PlayerModel player) {
		if (player == null) {
			throw new IllegalArgumentException(
					"player cannot be null.");
		}

		if (built) {
			throw new IllegalStateException(
					"The wall is already built.");
		}

		built = true;
		builder = player;
		checkTiles(player);
	}

	public void destroy(PlayerModel player) {
		if (!built) {
			throw new IllegalStateException(
					"The wall is not built.");
		}

		built = false;
		builder = null;
		checkTiles(player);
	}

	/**
	 * Check if any of the tiles incident on this wall has its boxer
	 * changed.
	 *
	 * @param player the player to check
	 */
	private void checkTiles(PlayerModel player) {
		if (player == null) {
			throw new IllegalArgumentException(
					"player cannot be null.");
		}

		for (TileModel tile : tiles) {
			tile.checkBox(player);
		}
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder("WallModel: ");

		for (TileModel tile : tiles) {
			str.append(tile);
			str.append(", ");
		}

		return str.toString();
	}

	/**
	 * Constructor.
	 *
	 * @param dir the direction this wall is attached to its parent tile
	 */
	public WallModel(int dir) {
		if (!Direction.isDirection(dir)) {
			throw Direction.NOT_A_DIRECTION;
		}

		direction = dir;
		tiles = new ArrayList<TileModel>();
	}
}
