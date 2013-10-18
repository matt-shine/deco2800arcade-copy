package deco2800.arcade.towerdefence;

import java.util.List;

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
	private double speed;
	// The GridObject's sprites to animate movement down.
	private List<Sprite> downMovingSprites;
	// The GridObject's sprites to animate movement left.
	private List<Sprite> leftMovingSprites;
	// The GridObject's sprites to animate movement up.
	private List<Sprite> upMovingSprites;
	// The GridObject's sprites to animate movement right.
	private List<Sprite> rightMovingSprites;
	// THe path the object is following
	private Path path;

	// Constructor
	/**
	 * The Mobile constructor.
	 * 
	 * @param maxHealth
	 *            The maximum health of the object
	 * @param armour
	 *            The armour of the object
	 * @param x
	 *            The x position of the object, in pixels
	 * @param y
	 *            The y position of the object, in pixels
	 * @param speed
	 *            The speed of the object, in pixels per second
	 * @param grid
	 *            The grid the object belongs to
	 */
	public Mobile(int maxHealth, int armour, int x, int y, double speed,
			Grid grid) {
		super(maxHealth, armour, x, y, grid);
		this.speed = speed;
	}

	// Getters
	/**
	 * Returns the GridObject's sprites to animate movement down.
	 * 
	 * @return The sprites used in the downwards moving animation
	 */
	public List<Sprite> downMovingSprites() {
		return downMovingSprites;
	}

	/**
	 * Returns the GridObject's sprites to animate movement left.
	 * 
	 * @return The sprites used in the left moving animation
	 */
	public List<Sprite> leftMovingSprites() {
		return leftMovingSprites;
	}

	/**
	 * Returns the GridObject's sprites to animate movement up.
	 * 
	 * @return The sprites used in the up moving animation
	 */
	public List<Sprite> upMovingSprites() {
		return upMovingSprites;
	}

	/**
	 * Returns the GridObject's sprites to animate movement right.
	 * 
	 * @return The sprites used in the right moving animation
	 */
	public List<Sprite> rightMovingSprites() {
		return rightMovingSprites;
	}

	/**
	 * Returns the GridObject's speed in pixels per second.
	 * 
	 * @return The object's speed in pixels per second, represented by a
	 *         floating point number.
	 */
	public double speed() {
		return speed;
	}

	// Setters
	/**
	 * Sets the GridObject's sprites to animate movement down.
	 * 
	 * @param downMovingSprites
	 *            The new list of sprites to use for the down moving animation.
	 */
	public void downMovingSprites(List<Sprite> downMovingSprites) {
		this.downMovingSprites = downMovingSprites;
	}

	/**
	 * Sets the GridObject's sprites to animate movement left.
	 * 
	 * @param leftMovingSprites
	 *            The new list of sprites to use for the left moving animation.
	 */
	public void leftMovingSprites(List<Sprite> leftMovingSprites) {
		this.leftMovingSprites = leftMovingSprites;
	}

	/**
	 * Sets the GridObject's sprites to animate movement up.
	 * 
	 * @param upMovingSprites
	 *            The new list of sprites to use for the up moving animation.
	 */
	public void rightMovingSprites(List<Sprite> upMovingSprites) {
		this.upMovingSprites = upMovingSprites;
	}

	/**
	 * Sets the GridObject's sprites to animate movement right.
	 * 
	 * @param rightMovingSprites
	 *            The new list of sprites to use for the right moving animation.
	 */
	public void movingSprites(List<Sprite> rightMovingSprites) {
		this.rightMovingSprites = rightMovingSprites;
	}

	/**
	 * Set the path to the given path.
	 * 
	 * @param path
	 *            The new path to follow
	 */
	public void path(Path path) {
		this.path = path;
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
				if (!moving(positionInTiles().add(current.getX(),
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
	 * Move the GridObject a number of pixels based on the given vector.
	 * 
	 * @param vector
	 *            The relative position to move to.
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
		if (grid.blocked(this, (int) positionInTiles().add(vector).x, (int) positionInTiles()
				.add(vector).y)) {
			// Grid is blocked return false to indicate a new path should be
			// found
			return false;
		}
		// Move it from this grid position to the next one
		grid.moveObject(this, positionInTiles(), positionInTiles().add(vector));

		// Go into a wait-while loop changing the position 30 times per second
		int distance = grid.getTileSize();
		long t0, t1;
		Vector2 addVector = vector.mul((float) speed / 33);
		for (float i = 0; i < distance; i += addVector.len()) {
			System.out.println("addvector len: " + addVector.len() + " i: " + i + " distance: "+ distance);
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
