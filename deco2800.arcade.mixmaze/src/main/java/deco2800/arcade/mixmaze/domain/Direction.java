package deco2800.arcade.mixmaze.domain;

/**
 * Helper class of directions in MixMaze.
 */
public class Direction {
	public static final int WEST = 0;
	public static final int NORTH = 1;
	public static final int EAST = 2;
	public static final int SOUTH = 3;

	/**
	 * Not a direction exception. This exception should be thrown
	 * when an integer is not one of <code>WEST</code>, <code>NORTH</code>,
	 * <code>EAST</code>, or <code>SOUTH</code>.
	 */
	static final IllegalArgumentException NOT_A_DIRECTION = 
			new IllegalArgumentException("Direction is invalid.");

	/**
	 * Returns true if <code>direction</code> is <code>WEST</code>.
	 * 
	 * @param direction	the direction
	 * @return true if the direction is <code>WEST</code>, false otherwise
	 */
	static boolean isWest(int direction) {
		return direction == WEST;
	}

	/**
	 * Returns true if <code>direction</code> is <code>NORTH</code>.
	 * 
	 * @param direction	the direction
	 * @return true if the direction is <code>NORTH</code>, false otherwise
	 */
	static boolean isNorth(int direction) {
		return direction == NORTH;
	}

	/**
	 * Returns true if <code>direction</code> is <code>EAST</code>.
	 * 
	 * @param direction	the direction
	 * @return true if the direction is <code>EAST</code>, false otherwise
	 */
	static boolean isEast(int direction) {
		return direction == EAST;
	}

	/**
	 * Returns true if <code>direction</code> is <code>SOUTH</code>.
	 * 
	 * @param direction	the direction
	 * @return true if the direction is <code>SOUTH</code>, false otherwise
	 */
	static boolean isSouth(int direction) {
		return direction == SOUTH;
	}

	/**
	 * Checks if the direction is valid.
	 * 
	 * @param direction	the direction
	 * @return true if the specified direction valid, false otherwise
	 * @throws NOT_A_DIRECTION if the direction is not one of 
	 * <code>WEST</code>, <code>NORTH</code>,
	 * <code>EAST</code> or <code>SOUTH</code>.
	 */
	static boolean isDirection(int direction) {
		return isWest(direction) || isNorth(direction)
				|| isEast(direction) || isSouth(direction);
	}

	/**
	 * Gets the polar opposite of the specified direction.
	 * 
	 * @param direction	the direction
	 * @return <code>WEST</code> if <code>EAST</code> is specified.
	 * <code>NORTH</code> if <code>SOUTH</code> is specified.
	 * <code>EAST</code> if <code>WEST</code> is specified.
	 * <code>SOUTH</code> if <code>NORTH</code> is specified.
	 */
	static int getPolarDirection(int direction) {
		if (!isDirection(direction)){
			throw NOT_A_DIRECTION;
		}
		else{
			return isPositiveDirection(direction) 
			       ? (direction - 2) : (direction + 2);
		}
	}

	/**
	 * Checks if <code>direction</code> lies on the x-axis.
	 * 
	 * @param direction	the direction
	 * @return true if the direction is <code>WEST</code> or
	 *  <code>EAST</code>, false otherwise
	 */
	static boolean isXDirection(int direction) {
		return isWest(direction) || isEast(direction);
	}

	/**
	 * Checks if <code>direction</code> lies on the y-axis.
	 * 
	 * @param direction	the direction
	 * @return true if the direction is <code>NORTH</code> or
	 *  <code>SOUTH</code>, false otherwise
	 */
	static boolean isYDirection(int direction) {
		return isNorth(direction) || isSouth(direction);
	}

	/**
	 * Checks if <code>direction</code> is positive.
	 * Positive directions are <code>SOUTH</code> and <code>EAST</code>.
	 * 
	 * @param direction	the direction
	 * @return true if the direction is positive, false otherwise
	 */
	static boolean isPositiveDirection(int direction) {
		return isEast(direction) || isSouth(direction);
	}

	/**
	 * Checks if <code>direction</code> is negative.
	 * Positive directions are <code>NORTH</code> and <code>WEST</code>.
	 * 
	 * @param direction	the direction
	 * @return true if the direction is negative, false otherwise
	 */
	static boolean isNegativeDirection(int direction) {
		return isWest(direction) || isNorth(direction);
	}
}
