package deco2800.arcade.mixmaze.domain;

/*
 * Direction helper class to translate integers into their associated direction.
 */
public class Direction {
	public static int WEST = 0;
	public static int NORTH = 1;
	public static int EAST = 2;
	public static int SOUTH = 3;
	public final static IllegalArgumentException NOTADIRECTION = new IllegalArgumentException("Direction is invalid.");
	
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
		return isWest(direction) || isNorth(direction) || isEast(direction) || isSouth(direction);
	}
	
	public static int getPolarDirection(int direction) {
		if(!isDirection(direction)) {
			throw NOTADIRECTION;
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
