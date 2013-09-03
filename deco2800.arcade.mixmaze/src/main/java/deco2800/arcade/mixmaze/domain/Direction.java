package deco2800.arcade.mixmaze.domain;

/**
 * Helper class of directions in mixmaze.
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
	public static final IllegalArgumentException NOT_A_DIRECTION =
			new IllegalArgumentException("Direction is invalid.");

	/**
	 * Returns true if and only the specified direction is <code>West</code>
	 * @param direction a integer representation of the direction
	 * @return true if the passed direction is <code>West</code>, false otherwise
	 */
	public static boolean isWest(int direction) {
		return direction == WEST;
	}

	/**
	 * Returns true if and only the specified direction is <code>North</code>
	 * @param direction a integer representation of the direction
	 * @return true if the passed direction is <code>North</code>, false otherwise
	 */
	public static boolean isNorth(int direction) {
		return direction == NORTH;
	}

	/**
	 * Returns true if and only the specified direction is <code>East</code>
	 * @param direction a integer representation of the direction
	 * @return true if the passed direction is <code>East</code>, false otherwise
	 */
	public static boolean isEast(int direction) {
		return direction == EAST;
	}

	/**
	 * Returns true if and only the specified direction is <code>South</code>
	 * @param direction a integer representation of the direction
	 * @return true if the passed direction is <code>South</code>, false otherwise
	 */
	public static boolean isSouth(int direction) {
		return direction == SOUTH;
	}

	/**
	 * returns true if and only the specified direction is one of <code>East, West,
	 *  South, North</code>
	 * @param direction a integer representation of the direction
	 * @return true if the specified direction valid, false otherwise
	 * @throws NOT_A_DIRECTION if the specified direction is not one of <code>WEST</code>, <code>NORTH</code>,
	 * <code>EAST</code>, or <code>SOUTH</code>.
	 */
	public static boolean isDirection(int direction) {
		return isWest(direction) || isNorth(direction)
				|| isEast(direction) || isSouth(direction);
	}

	public static int getPolarDirection(int direction) {
		if(!isDirection(direction)) {
			throw NOT_A_DIRECTION;
		}
		return isPositiveDirection(direction) ? (direction - 2) : (direction + 2);
	}

	/**
	 * compares the specified direction to check if it lies horizontally
	 * in the X-axis
	 * @param direction a integer representation of the direction
	 * @return true if the specified direction is <code>West</code> or
	 *  <code>East</code>, false otherwise
	 */
	public static boolean isXDirection(int direction) {
		return isWest(direction) || isEast(direction);
	}

	/**
	 * compares the specified direction to check if it lies vertically
	 * in the Y-axis
	 *
	 * @param direction a integer representation of the direction
	 * @return true if the specified direction is <code>North</code> or
	 *  "<code>South</code>, false otherwise
	 */
	public static boolean isYDirection(int direction) {
		return isNorth(direction) || isSouth(direction);
	}


	public static boolean isPositiveDirection(int direction) {
		return isEast(direction) || isSouth(direction);
	}

	public static boolean isNegativeDirection(int direction) {
		return isWest(direction) || isNorth(direction);
	}
}
