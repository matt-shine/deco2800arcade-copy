package deco2800.arcade.mixmaze.domain;

import deco2800.arcade.mixmaze.domain.view.IBrickModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Brick model represents a collection of bricks.
 */
public class BrickModel extends ItemModel implements IBrickModel {

	public static final int DEFAULT_MAX_BRICKS = 10;

	final Logger logger = LoggerFactory.getLogger(BrickModel.class);

	/**
	 * Thrown when an operation results in an invalid amount of bricks.
	 */
	private final static IllegalArgumentException NUM_OUT_OF_RANGE = new IllegalArgumentException("number must result in a amount greater than 0 and less then maxBricks.");

	/**
	 * The maximum number of bricks in a stack.
	 */
	private static int maxBricks = DEFAULT_MAX_BRICKS;

	public static int getMaxBricks() {
		return maxBricks;
	}

	public static void setMaxBricks(int max) {
		if(max < 1) {
			throw new IllegalArgumentException("max must be greater than or equal to 1.");
		}
		maxBricks = max;
	}

	/**
	 * The amount of bricks in this stack.
	 */
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
	 * 				    <code>maxBricks</code>.
	 */
	public void setAmount(int number) {
		if (number < 0 || number > maxBricks) {
			throw NUM_OUT_OF_RANGE;
		}
		amount = number;
	}

	/**
	 * Increases the amount of bricks by <code>number</code>.
	 *
	 * @param number the number of bricks to add
	 * @throws IllegalArgumentException If the amount after the addition
	 * 				    will be negative or greater than
	 * 				    <code>maxBricks</code>.
	 */
	public void addAmount(int number) {
		int addedAmount = amount + number;
		if (addedAmount < 0 || addedAmount > maxBricks) {
			throw NUM_OUT_OF_RANGE;
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
		if (balance < 0 || balance > maxBricks) {
			throw NUM_OUT_OF_RANGE;
		}
		amount = balance;
	}

	/**
	 * Remove one brick from this <code>BrickModel</code>
	 */
	public void removeOne() {
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

		if (this.amount +  brick.getAmount() <= this.maxBricks)
			pickup = brick.getAmount();
		else
			pickup = this.maxBricks - this.amount;

		logger.debug("mergeBricks: this {} that {} maxBricks {} pickup{}",
				amount, brick.getAmount(), maxBricks, pickup);
		brick.removeAmount(pickup);
		this.amount += pickup;
	}

	/**
	 * Constructor
	 *
	 * @param amount	initial amount of bricks
	 */
	public BrickModel(int amount) {
		super(ItemType.BRICK);
		if (amount < 0 || amount > maxBricks) {
			throw NUM_OUT_OF_RANGE;
		}
		this.amount = amount;
	}

}
