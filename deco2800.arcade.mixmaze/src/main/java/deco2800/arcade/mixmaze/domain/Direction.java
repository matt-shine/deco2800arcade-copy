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

	public static boolean isWest(int direction) {
		return direction == WEST;
	}

	public static boolean isNorth(int direction) {
		return direction == NORTH;
	}

	public static boolean isEast(int direction) {
		return direction == EAST;
	}

	public static boolean isSouth(int direction) {
		return direction == SOUTH;
	}

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

	public static boolean isXDirection(int direction) {
		return isWest(direction) || isEast(direction);
	}

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
