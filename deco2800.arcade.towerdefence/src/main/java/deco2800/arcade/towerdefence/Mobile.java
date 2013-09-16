package deco2800.arcade.towerdefence;

import com.badlogic.gdx.math.Vector2;

/**
 * The interface for objects that can move to different positions on the grid.
 * @author hadronn
 *
 */
public abstract class Mobile extends Mortal {
	//The object's speed in moves per second
	private Vector2 vector = new Vector2();
	
	//Move the GridObject one unit in the vector specified.
	public void move(Vector2 vector) {
		/**
		 * TODO implement position modifier based on vector given.
		 */
	}
	//Returns the GridObject's speed in moves per second.
	public float speed(){
		return vector.len();
	}
	
	//Move the GridObject some speed calculated units in the direction specified over one second.
	public void moving(Vector2 vector){
		/**
		 * TODO implement position modifier based on vector given.
		 */
	}
}
