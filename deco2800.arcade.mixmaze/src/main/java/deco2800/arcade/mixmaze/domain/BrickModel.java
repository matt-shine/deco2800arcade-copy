package deco2800.arcade.mixmaze.domain;

/**
 * Brick model represents a collection of bricks.
 */
public class BrickModel extends ItemModel {

	public final static int MAX_BRICKS = 10;

	private int amount;

	/**
	 * Returns the amount of bricks in this <code>BrickModel</code>.
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

	/**
	 * Removes the specified number of bricks from this <code>BrickModel</code>
	 * @param number amount of bricks to be removed.This must be a positive number
	 * @throws IllegalArgumentException If the amount after the deletion
	 * 				    is be negative.
	 */
	public void removeAmount(int number) {
		int balance = amount - number;
		// why do we check if its >Max, this only removes bricks? dumindu
		if (balance < 0 || balance > MAX_BRICKS) {
			throw new IllegalArgumentException(
					"number must result in a amount that is"
					+ " positive and less than or equal to "
					+ "MAX_BRICKS.");
		}
		amount = balance;
	}

	/**
	 * Remove one brick from this <code>BrickModel</code>
	 */
	void removeOne() {
		removeAmount(1);
	}

	@Override
	public Type getType() {
		return ItemModel.Type.BRICK;
	}

	/**
	 * Constructs a new <code>BrickModel</code> by setting the
	 * Specified <code>tileModel</code> and the <code>amount</code> of bricks.
	 * 
	 * @param spawnedOn the tileModel on which the brick is spawned
	 * @param number amount of bricks spawned
	 */
	public BrickModel(TileModel spawnedOn, int number) {
		super(spawnedOn);
		amount = number;
	}
}
