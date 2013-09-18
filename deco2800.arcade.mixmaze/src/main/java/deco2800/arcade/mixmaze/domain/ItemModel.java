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

	// Item Data
	private ItemType itemType;
	
	protected ItemModel(ItemType type) {
		itemType = type;
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
