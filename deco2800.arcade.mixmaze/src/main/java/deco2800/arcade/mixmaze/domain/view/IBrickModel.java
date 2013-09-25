/*
 * IBrickModel
 */
package deco2800.arcade.mixmaze.domain.view;

public interface IBrickModel extends IItemModel {

	/**
	 * Returns the amount of bricks in this <code>BrickModel</code>.
	 *
	 * @return the number of bricks
	 */
	int getAmount();

	/**
	 * Sets the amount of bricks to <code>number</code>.
	 *
	 * @param number the number of bricks to set
	 * @throws IllegalArgumentException If <code>number</code> is negative
	 * 				    or greater than
	 * 				    <code>MAX_BRICKS</code>.
	 */
	void setAmount(int number);

	/**
	 * Increases the amount of bricks by <code>number</code>.
	 *
	 * @param number the number of bricks to add
	 * @throws IllegalArgumentException If the amount after the addition
	 * 				    will be negative or greater than
	 * 				    <code>MAX_BRICKS</code>.
	 */
	void addAmount(int number);

	/**
	 * Removes the specified number of bricks from this <code>BrickModel</code>
	 * @param number amount of bricks to be removed.This must be a positive number
	 * @throws IllegalArgumentException If the amount after the deletion
	 * 				    is be negative.
	 */
	void removeAmount(int number);

	/**
	 * Remove one brick from this <code>BrickModel</code>
	 */
	void removeOne();

	void mergeBricks(IBrickModel brick);

}
