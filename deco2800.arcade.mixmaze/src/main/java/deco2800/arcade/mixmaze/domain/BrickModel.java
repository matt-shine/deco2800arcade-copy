package deco2800.arcade.mixmaze.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Brick model represents a collection of bricks.
 */
class BrickModel extends ItemModel {

	/** Default value of the maximum number of bricks */
	public static final int DEFAULT_MAX_BRICKS = 10;

	/**
	 * Thrown when an operation results in an invalid amount of bricks.
	 */
	private static final IllegalArgumentException NUM_OUT_OF_RANGE =
			new IllegalArgumentException("number must result in a amount greater than 0 and less then maxBricks.");

	/** The maximum number of bricks in every stack */
	private static int maxBricks = DEFAULT_MAX_BRICKS;

	final Logger logger = LoggerFactory.getLogger(BrickModel.class);

	/** The amount of bricks in this stack */
	private int amount;

	/**
	 * Constructor
	 *
	 * @param amount	initial amount of bricks
	 */
	BrickModel(int amount) {
		super(Type.BRICK);
		if (amount < 0 || amount > maxBricks) {
			throw NUM_OUT_OF_RANGE;
		}
		this.amount = amount;
	}

	/**
	 * Returns the maximum number of bricks.
	 *
	 * @return the maximum number
	 */
	static int getMaxBricks() {
		return maxBricks;
	}

	/**
	 * Sets the maximum number of bricks.
	 *
	 * @param max	the maximum number
	 */
	static void setMaxBricks(int max) {
		if (max < 1) {
			throw new IllegalArgumentException("max must be greater than or equal to 1.");
		}
		maxBricks = max;
	}

	/**
	 * Returns the amount of bricks in this <code>BrickModel</code>.
	 *
	 * @return the number of bricks
	 */
	int getAmount() {
		return amount;
	}

	/**
	 * Sets the amount of bricks to <code>number</code>.
	 *
	 * @param number the number of bricks to set
	 * @throws IllegalArgumentException If <code>number</code> is negative
	 * 				    or greater than
	 * 				    <code>maxBricks</code>.
	 */
	void setAmount(int number) {
		if (number < 0 || number > maxBricks) {
			throw NUM_OUT_OF_RANGE;
		}
		amount = number;
	}

	/**
	 * Increases the amount of bricks by <code>number</code>.
	 *
	 * @param number	the number of bricks to add
	 * @throws IllegalArgumentException If the amount after the addition
	 * 				    will be negative or greater than
	 * 				    <code>maxBricks</code>.
	 */
	void addAmount(int number) {
		int result = amount + number;

		if (result < 0 || result > maxBricks) {
			throw NUM_OUT_OF_RANGE;
		}
		amount = result;
	}

	/**
	 * Removes the specified number of bricks from this
	 * <code>BrickModel</code>.
	 *
	 * @param number 	amount of bricks to be removed.
	 * This must be a positive number.
	 * @throws IllegalArgumentException If the amount after the deletion
	 * 				    is negative.
	 */
	void removeAmount(int number) {
		int result = amount - number;

		if (result < 0 || result > maxBricks) {
			throw NUM_OUT_OF_RANGE;
		}
		amount = result;
	}

	/**
	 * Remove one brick from this <code>BrickModel</code>
	 */
	void removeOne() {
		removeAmount(1);
	}

	/**
	 * Merges the bricks in <code>brick</code> into this brick as many
	 * as possible.
	 *
	 * @param brick		the brick to merge from
	 */
	void mergeBricks(BrickModel brick) {
		int pickup;

		if (this.amount +  brick.getAmount() <= maxBricks)
			pickup = brick.getAmount();
		else
			pickup = maxBricks - this.amount;

		brick.removeAmount(pickup);
		this.amount += pickup;
	}
}
