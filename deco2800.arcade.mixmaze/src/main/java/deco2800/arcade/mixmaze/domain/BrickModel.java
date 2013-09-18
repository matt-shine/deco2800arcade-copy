package deco2800.arcade.mixmaze.domain;

/**
 * Brick model represents a collection of bricks.
 */
public class BrickModel extends ItemModel {
	/**
	 * Thrown when an operation results in an invalid amount of bricks.
	 */
	private final static IllegalArgumentException NUMOUTOFRANGE = new IllegalArgumentException("number must result in a amount greater than 0 and less then MAX_BRICKS."); 
	
	/**
	 * Stores maximum number of bricks in a stack.
	 */
	private static int MAX_BRICKS = 10;
	
	// Brick Data
	private int amount;

	public static int getMaxBricks() {
		return MAX_BRICKS;
	}
	
	public static void setMaxBricks(int max) {
		if(max < 1) {
			throw new IllegalArgumentException("max must be greater than or equal to 1.");
		}
		MAX_BRICKS = max;
	}
	
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
			throw NUMOUTOFRANGE;
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
			throw NUMOUTOFRANGE;
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
		if (balance < 0 || balance > MAX_BRICKS) {
			throw NUMOUTOFRANGE;
		}
		amount = balance;
	}

	/**
	 * Remove one brick from this <code>BrickModel</code>
	 */
	public void removeOne() {
		removeAmount(1);
	}
	
	public void mergeBricks(BrickModel brick) {
		int canPickup = (MAX_BRICKS - brick.getAmount());
		if(canPickup <= brick.getAmount()) {
			brick.removeAmount(canPickup);
		} else {
			canPickup = canPickup - brick.getAmount();
			brick.removeAmount(canPickup);
		}
		amount += canPickup;
	}
	
	public BrickModel(int number) {
		super(ItemType.BRICK);
		if (number < 0 || number > MAX_BRICKS) {
			throw NUMOUTOFRANGE;
		}
		amount = number;
	}
}
