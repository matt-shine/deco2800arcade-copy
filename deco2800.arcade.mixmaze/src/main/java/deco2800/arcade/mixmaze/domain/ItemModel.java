package deco2800.arcade.mixmaze.domain;

/**
 * Abstraction of items in mixmaze.
 */
public abstract class ItemModel {

	private TileModel tileSpawned;

	public void pickUpItem() {
		tileSpawned.pickUpItem();
	}

	protected ItemModel(TileModel spawnedOn) {
		tileSpawned = spawnedOn;
	}
}
