package deco2800.arcade.mixmaze.domain;

/**
 * Abstraction of items in MixMaze.
 */
public abstract class ItemModel {

	/**
	 * Item types
	 */
	public enum Type {
		/** Brick */
		BRICK,

		/** Pick */
		PICK,

		/** TNT */
		TNT,

		/** Unrecognised item */
		UNKNOWN,

		/** Not an item */
		NONE
	}

	/** The type of this item */
	private Type type;

	/**
	 * Constructor
	 * 
	 * @param type
	 *            the item type
	 */
	protected ItemModel(Type type) {
		this.type = type;
	}

	/**
	 * Returns the type of this item.
	 * 
	 * @return the type
	 */
	Type getType() {
		return type;
	}
}
