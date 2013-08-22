package deco2800.arcade.mixmaze.domain;

public class Direction {
	public static int WEST = 0;
	public static int NORTH = 1;
	public static int EAST = 2;
	public static int SOUTH = 3;
	
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
}
