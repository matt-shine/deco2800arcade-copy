package deco2800.arcade.towerdefence;

/**
 * The interface for objects that can move to different positions on the grid.
 * @author hadronn
 *
 */
public abstract class Mobile implements GridObject {
	//Move the GridObject one unit in the direction specified, a direction being one of UP, DOWN, LEFT and RIGHT.
	public void move(Direction direction) {
		/**
		 * TODO implement position modifier based on direction given.
		 */
	}
	//Returns the GridObject's speed in grid squares per second.
	public abstract int speed();
	
	//Move the GridObject some speed calculated units in the direction specified over one second.
	public abstract void moving(Direction direction);
}
