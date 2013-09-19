package deco2800.arcade.towerdefence;

import com.badlogic.gdx.math.Vector2;

/**
 * The interface for objects that can move to different positions on the grid.
 * @author hadronn
 *
 */
public abstract class Mobile implements GridObject {
	//Move the GridObject one unit in the vector specified.
	public void move(Vector2 vector) {
		/**
		 * TODO implement position modifier based on vector given.
		 */
	}
	//Returns the GridObject's speed in moves per second.
	public abstract float speed();
	
	//Move the GridObject some speed calculated units in the direction specified over one second.
	public abstract void moving(Vector2 vector);
}
