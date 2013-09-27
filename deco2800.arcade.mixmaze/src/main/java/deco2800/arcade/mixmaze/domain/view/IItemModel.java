/*
 * IItemModel
 */
package deco2800.arcade.mixmaze.domain.view;

public interface IItemModel {

	/**
	 * Item types.
	 */
	enum ItemType {
		BRICK, PICK, TNT, UNKNOWN
	}

	/**
	 * Return the type of this item.
	 *
	 * @return the type
	 */
	ItemType getType();

}
