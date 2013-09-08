package deco2800.arcade.mixmaze.domain;

/**
 * Abstraction of items in mixmaze.
 */
public abstract class ItemModel {

	/**
	 * Item types.
	 */
	public enum ItemType {
		BRICK, PICK, TNT, UNKNOWN
	}

	private ItemType itemType;
	private TileModel tileSpawned;

	protected ItemModel(ItemType type) {
		itemType = type;
	}
	
	protected ItemModel(ItemType type, TileModel spawnedOn) {
		if(spawnedOn == null) {
			throw new IllegalArgumentException("spawnedOn cannot be null.");
		}
		itemType = type;
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
	public ItemType getType()
	{
		return itemType;
	}
}
