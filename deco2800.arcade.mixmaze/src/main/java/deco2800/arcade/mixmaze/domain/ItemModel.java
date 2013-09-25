package deco2800.arcade.mixmaze.domain;

import deco2800.arcade.mixmaze.domain.view.IItemModel;

/**
 * Abstraction of items in mixmaze.
 */
public abstract class ItemModel implements IItemModel {

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
