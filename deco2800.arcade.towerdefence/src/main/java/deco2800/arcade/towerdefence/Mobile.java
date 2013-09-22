package deco2800.arcade.towerdefence;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

/**
 * The interface for objects that can move to different positions on the grid.
 * @author hadronn
 *
 */
public abstract class Mobile extends Mortal {
	// Fields
	// The GridObject's speed in moves per second.
	private Vector2 vector = new Vector2();
	// The GridObject's sprites to animate movement down.
	private ArrayList<Sprite> downMovingSprites;
	// The GridObject's sprites to animate movement left.
	private ArrayList<Sprite> leftMovingSprites;
	// The GridObject's sprites to animate movement up.
	private ArrayList<Sprite> upMovingSprites;
	// The GridObject's sprites to animate movement right.
	private ArrayList<Sprite> rightMovingSprites;
				
	
	// Constructor
	public Mobile(int maxHealth, int armour){
		super(maxHealth, armour);
	}
	
	//Getters
	// Returns the GridObject's sprites to animate movement down.
	public ArrayList<Sprite> downMovingSprites() {
		return downMovingSprites;
	}
	// Returns the GridObject's sprites to animate movement left.
	public ArrayList<Sprite> leftMovingSprites() {
		return leftMovingSprites;
	}
	// Returns the GridObject's sprites to animate movement up.
	public ArrayList<Sprite> upMovingSprites() {
		return upMovingSprites;
	}
	// Returns the GridObject's sprites to animate movement right.
	public ArrayList<Sprite> rightMovingSprites() {
		return rightMovingSprites;
	}
	
	// Returns the GridObject's speed in moves per second.
	public float speed(){
		return vector.len();
	}
	
	// Setters
	// Sets the GridObject's sprites to animate movement down.
	public void downMovingSprites(ArrayList<Sprite> downMovingSprites) {
		this.downMovingSprites = downMovingSprites;
	}
	// Sets the GridObject's sprites to animate movement left.
	public void leftMovingSprites(ArrayList<Sprite> leftMovingSprites) {
		this.leftMovingSprites = leftMovingSprites;
	}
	// Sets the GridObject's sprites to animate movement up.
	public void rightMovingSprites(ArrayList<Sprite> upMovingSprites) {
		this.upMovingSprites = upMovingSprites;
	}
	// Sets the GridObject's sprites to animate movement right.
	public void movingSprites(ArrayList<Sprite> rightMovingSprites) {
		this.rightMovingSprites = rightMovingSprites;
	}
	
	
	// Methods
	// Move the GridObject one unit in the vector specified.
	public void move(Vector2 vector) {
		/**
		 * TODO implement position modifier based on vector given.
		 */
	}
	
	// Move the GridObject some speed calculated units in the direction specified over one second.
	public void moving(Vector2 vector){
		/**
		 * TODO implement position modifier based on vector given.
		 */
	}
	
	public Sprite MovingSprite(Direction facing, int phase){
		/**
		 * TODO Implement returning correct sprite based structure below using facing and phase of movement. 
		 * phase - sprite
		 * 0 - begin movement
		 * 1 - continue movement
		 * 2 - end movement
		 * 
		 * Can simply cycle the 3 phases for each 1 square moved
		 * For example if facing is left and we are halfway to the next Grid square then we'd want to display continued movement.
		 * return leftMovingSprites(1);
		 */
		return null;
		
	}
}
