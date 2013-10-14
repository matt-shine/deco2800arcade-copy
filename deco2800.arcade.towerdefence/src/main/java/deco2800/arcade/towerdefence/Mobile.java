package deco2800.arcade.towerdefence;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

import deco2800.arcade.towerdefence.pathfinding.Path;
import deco2800.arcade.towerdefence.pathfinding.Path.Step;

/**
 * The interface for objects that can move to different positions on the grid.
 * 
 * @author hadronn
 * 
 */
public abstract class Mobile extends Mortal {
	// Fields
	// The GridObject's speed in pixels per second.
	private float speed;
	// The GridObject's sprites to animate movement down.
	private ArrayList<Sprite> downMovingSprites;
	// The GridObject's sprites to animate movement left.
	private ArrayList<Sprite> leftMovingSprites;
	// The GridObject's sprites to animate movement up.
	private ArrayList<Sprite> upMovingSprites;
	// The GridObject's sprites to animate movement right.
	private ArrayList<Sprite> rightMovingSprites;
	// THe path the object is following
	private Path path;

	// Constructor
	public Mobile(int maxHealth, int armour) {
		super(maxHealth, armour);
	}

	// Getters
	/**
	 * Returns the GridObject's sprites to animate movement down.
	 * 
	 * @return
	 */
	public ArrayList<Sprite> downMovingSprites() {
		return downMovingSprites;
	}

	/**
	 * Returns the GridObject's sprites to animate movement left.
	 * 
	 * @return
	 */
	public ArrayList<Sprite> leftMovingSprites() {
		return leftMovingSprites;
	}

	/**
	 * Returns the GridObject's sprites to animate movement up.
	 * 
	 * @return
	 */
	public ArrayList<Sprite> upMovingSprites() {
		return upMovingSprites;
	}

	/**
	 * Returns the GridObject's sprites to animate movement right.
	 * 
	 * @return
	 */
	public ArrayList<Sprite> rightMovingSprites() {
		return rightMovingSprites;
	}

	/**
	 * Returns the GridObject's speed in moves per second.
	 * 
	 * @return
	 */
	public float speed() {
		return speed;
	}

	// Setters
	/**
	 * Sets the GridObject's sprites to animate movement down.
	 * 
	 * @param downMovingSprites
	 */
	public void downMovingSprites(ArrayList<Sprite> downMovingSprites) {
		this.downMovingSprites = downMovingSprites;
	}

	/**
	 * Sets the GridObject's sprites to animate movement left.
	 * 
	 * @param leftMovingSprites
	 */
	public void leftMovingSprites(ArrayList<Sprite> leftMovingSprites) {
		this.leftMovingSprites = leftMovingSprites;
	}

	/**
	 * Sets the GridObject's sprites to animate movement up.
	 * 
	 * @param upMovingSprites
	 */
	public void rightMovingSprites(ArrayList<Sprite> upMovingSprites) {
		this.upMovingSprites = upMovingSprites;
	}

	/**
	 * Sets the GridObject's sprites to animate movement right.
	 * 
	 * @param rightMovingSprites
	 */
	public void movingSprites(ArrayList<Sprite> rightMovingSprites) {
		this.rightMovingSprites = rightMovingSprites;
	}

	// Methods
	/**
	 * Follows the path until the object reached the end. If obstacles are
	 * encountered, recalculate the path.
	 */
	public void followPath() {
		Step current;
		// Infinite loop - continue while path is being followed
		while (true) {
			for (int i = 0; i < path.getLength(); i++) {
				current = path.getStep(i);
				// Make a vector based on the current position and next step
				// position
				if (!moving(positionInTiles().sub(current.getX(),
						current.getY()))) {
					path = grid.pathfinder.findPath(this,
							(int) this.positionInTiles().x,
							(int) this.positionInTiles().y, 50, 50);
					break;
				}
			}
			// Mobile object has reached the target, what do TODO
		}
	}

	/**
	 * Move the GridObject one unit in the vector specified.
	 * 
	 * @param vector
	 */
	public void move(Vector2 vector) {
		position.add(vector);
	}

	/**
	 * Move the GridObject one tile in the specified direction at its speed.
	 * 
	 * @param vector
	 *            A vector of length 1 (or sqrt 2) to indicate the movement
	 *            direction
	 */
	public boolean moving(Vector2 vector) {
		// Check for block in given direction
		if (grid.blocked(this, (int) position().add(vector).x, (int) position()
				.add(vector).y)) {
			// Grid is blocked return false to indicate a new path should be
			// found
			return false;
		}
		// Move it from this grid position to the next one
		grid.moveObject(this, position, position().add(vector));

		// Go into a wait-while loop changing the position 30 times per second
		int distance = grid.getTileSize();
		// Check if it is moving diagonally
		if (vector.x != 0 && vector.y != 0) {
			distance = (int) Math.sqrt((double) (2 * (distance * distance)));
		}
		long t0, t1;
		Vector2 addVector = vector.mul(speed / 33);
		for (int i = 0; i < distance; i += addVector.len()) {
			t0 = System.currentTimeMillis();
			t1 = t0;
			// Move
			move(addVector);
			// Wait 1/30th of a second before moving again
			while (t1 - t0 < 33) {
				t1 = System.currentTimeMillis();
			}
		}
		// Moved to the next tile successfully
		return true;
	}

	/**
	 * Return the correct sprite as the GridObject moves using
	 * <direction>MovingSprites getters in the class.
	 * 
	 * TODO Implement returning correct sprite based structure below using
	 * facing and phase of movement 0, 1 or 2.
	 * 
	 * 0 begin movement, 1 continue movement, 2 end movement.
	 * 
	 * Can simply cycle the 3 phases for each 1 square moved For example if
	 * facing is left and we are moving to the next Grid square then we'd want
	 * to continue cycling being careful to match the distance and speed so the
	 * animation ends on phase 2 at destination. Should be taken care of by
	 * cycling once per square moved.
	 * 
	 * E.g. returned at some point in movement: return leftMovingSprites(1);
	 */
	public Sprite MovingSprite(Direction facing, int phase) {
		return null;

	}
}
