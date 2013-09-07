package deco2800.arcade.mixmaze.domain;

/**
 * Abstraction of items in mixmaze.
 */
public abstract class ItemModel {

	/**
	 * Item types.
	 */
	public enum Type {
		BRICK, PICK, TNT, UNKNOWN
	}

	private TileModel tileSpawned;

	protected ItemModel(TileModel spawnedOn) {
		tileSpawned = spawnedOn;
	}

	public void pickUpItem() {
		tileSpawned.pickUpItem();
	}

	/**
	 * Returns the type of this item.
	 *
	 * @return the type
	 */
	public abstract Type getType();
}
