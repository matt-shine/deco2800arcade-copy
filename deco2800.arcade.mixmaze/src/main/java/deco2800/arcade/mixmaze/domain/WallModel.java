package deco2800.arcade.mixmaze.domain;

import deco2800.arcade.mixmaze.domain.view.IPlayerModel;
import deco2800.arcade.mixmaze.domain.view.IWallModel;
import java.util.ArrayList;
import java.util.List;

/**
 * WallModel represents an active/inactive wall on a tile.
 */
public class WallModel implements IWallModel {

	private final List<TileModel> tiles;

	private boolean built;
	private PlayerModel builder;

	/**
	 * Constructor
	 */
	public WallModel() {
		tiles = new ArrayList<TileModel>();
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

	public void build(IPlayerModel iplayer) {
		PlayerModel player = (PlayerModel) iplayer;

		if (player == null) {
			throw new IllegalArgumentException("player cannot be null.");
		}

		if (built) {
			throw new IllegalStateException("The wall is already built.");
		}
		built = true;
		builder = player;

		// Executes recursive box building
		for (TileModel t : tiles) {
			t.validateBox(player);
			t.updateWall(this, built);
		}
	}

	public void destroy(PlayerModel player) {
		if (!built) {
			throw new IllegalStateException("wall not built");
		}
		built = false;
		builder = null;
		for (TileModel t : tiles) {
			t.validateBox(player);
			t.updateWall(this, built);
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

}
