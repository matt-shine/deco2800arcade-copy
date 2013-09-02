package deco2800.arcade.mixmaze.domain;

/**
 * Brick model represents a collection of bricks.
 */
public class BrickModel extends ItemModel {

	public final static int MAX_BRICKS = 10;

	private int amount;

	/**
	 * Returns the amount of bricks.
	 *
	 * @return the number of bricks
	 */
	public int getAmount() {
		return amount;
	}

	/**
	 * Sets the amount of bricks to <code>number</code>.
	 *
	 * @param number the number of bricks to set
	 * @throws IllegalArgumentException If <code>number</code> is negative
	 * 				    or greater than
	 * 				    <code>MAX_BRICKS</code>.
	 */
	public void setAmount(int number) {
		if (number < 0 || number > MAX_BRICKS) {
			throw new IllegalArgumentException(
					"number must be positive and less than "
					+ "or equal to MAXBRICKS.");
		}
		amount = number;
	}

	/**
	 * Increases the amount of bricks by <code>number</code>.
	 *
	 * @param number the number of bricks to add
	 * @throws IllegalArgumentException If the amount after the addition
	 * 				    will be negative or greater than
	 * 				    <code>MAX_BRICKS</code>.
	 */
	public void addAmount(int number) {
		int addedAmount = amount + number;

		if (addedAmount < 0 || addedAmount > MAX_BRICKS) {
			throw new IllegalArgumentException(
					"number must result in a amount that is"
					+ " positive and less than or equal to "
					+ "MAX_BRICKS.");
		}
		amount = addedAmount;
	}

	@Override
	public Type getType() {
		return ItemModel.Type.BRICK;
	}

	/**
	 * Constructor.
	 */
	public BrickModel(TileModel spawnedOn, int number) {
		super(spawnedOn);
		amount = number;
	}
}
