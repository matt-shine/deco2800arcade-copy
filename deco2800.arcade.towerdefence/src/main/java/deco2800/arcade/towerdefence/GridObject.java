package deco2800.arcade.towerdefence;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

/**
 * The interface for an object that can be created on a grid, required to be
 * unique when instantiated.
 * 
 * @author hadronn
 * 
 */
public abstract class GridObject {
	// Fields
	// The grid this object is on.
	private Grid grid;
	// The position of this object on the grid.
	private Vector2 position = new Vector2();
	// Whether the object is visible.
	private boolean visible;
	// The list of status effects this GridObject can apply.
	private ArrayList<Effect> effects = new ArrayList<Effect>();
	// Whether this object has collision.
	private boolean physical;
	// The opaqueness of the object as a percentage.
	private int opaqueness = 100;
	// The direction the object is facing.
	private Direction facing;
	// The standing sprites this object uses.
	private ArrayList<Sprite> standingSprites;
	// The death sprite this object uses.
	private ArrayList<Sprite> deathSprites;
	// The team this object belongs to.
	private Team team;

	// Getters
	/**
	 * Returns the grid the object belongs to.
	 * 
	 * @return
	 */
	public Grid grid() {
		return grid;
	}

	/**
	 * Returns the x and y coordinates of the object.
	 * 
	 * @return
	 */
	public Vector2 position() {
		return position;
	}

	/**
	 * Returns whether the object should be drawn to the grid.
	 * 
	 * @return
	 */
	public boolean visible() {
		return visible;
	}

	/**
	 * Returns a list of status effects it can apply to the grid or other
	 * objects at any time.
	 * 
	 * @return
	 */
	public ArrayList<Effect> effects() {
		return effects;
	}

	/**
	 * Returns whether the object currently has collision or not.
	 * 
	 * @return
	 */
	public boolean physical() {
		return physical;
	}

	/**
	 * All grid objects must have an opaqueness value for drawing.
	 * 
	 * @return
	 */
	public int opaqueness() {
		return opaqueness;
	}

	/**
	 * Returns the direction the object is facing, for determining sprite to
	 * use.
	 * 
	 * @return
	 */
	public Direction facing() {
		return facing;
	}

	/**
	 * Returns the ArrayList of sprites for the object.
	 * 
	 * @return
	 */
	public ArrayList<Sprite> sprites() {
		return standingSprites;
	}

	/**
	 * Returns the sprite to display based on a direction when the object is
	 * still.
	 * 
	 * TODO implement this method according to the structure below so that it
	 * returns the correct sprite for a facing.
	 * 
	 * index - sprite 0 - standing facing down 1 - standing facing left 2 -
	 * standing facing up 3 - standing facing right
	 * 
	 * sprites().get(0) would return facing down sprite
	 * 
	 * @param facing
	 * @return
	 */
	public Sprite standingSprite(Direction facing) {
		return null;
	}

	/**
	 * Returns the sprites to display on death.
	 * 
	 * @return
	 */
	public ArrayList<Sprite> deathSprites() {
		return deathSprites;
	}

	/**
	 * Return the side the object is affiliated with, for score and avoiding
	 * friendly-fire.
	 * 
	 * @return
	 */
	public Team team() {
		return team;
	}

	// Setters
	/**
	 * Set the grid the object belongs to.
	 * 
	 * @param grid
	 */
	public void grid(Grid grid) {
		this.grid = grid;
	}

	/**
	 * Set the position the GridObject has on the grid.
	 * 
	 * @param vector
	 */
	public void position(Vector2 vector) {
		this.position = vector;
	}

	/**
	 * Set whether the GridObject is visible on the grid.
	 * 
	 * @param visibility
	 */
	public void visible(Boolean visibility) {
		this.visible = visibility;
	}

	/**
	 * Set the effects that this GridObject can apply.
	 * 
	 * @param effects
	 */
	public void effects(ArrayList<Effect> effects) {
		this.effects = effects;
	}

	/**
	 * Set whether the GridObject has collision or not.
	 * 
	 * @param physical
	 */
	public void physical(Boolean physical) {
		this.physical = physical;
	}

	/**
	 * Set the opaqueness value of the GridObject.
	 * 
	 * @param opaqueness
	 */
	public void opaqueness(int opaqueness) {
		this.opaqueness = opaqueness;
	}

	/**
	 * Set the facing of the GridObject.
	 * 
	 * @param facing
	 */
	public void facing(Direction facing) {
		this.facing = facing;
	}

	/**
	 * Set the array list of standing sprites this object uses.
	 * 
	 * @param standingSprites
	 */
	public void standingSprites(ArrayList<Sprite> standingSprites) {
		this.standingSprites = standingSprites;
	}

	/**
	 * Set the array list of death sprites this object uses.
	 * 
	 * @param standingSprites
	 */
	public void deathSprites(ArrayList<Sprite> deathSprites) {
		this.deathSprites = deathSprites;
	}

	/**
	 * Set the team this GridObject is on.
	 * 
	 * @param team
	 */
	public void team(Team team) {
		this.team = team;
	}

	// Methods
	/**
	 * Remove the object from the model.
	 * 
	 * TODO implement generic destruction method of GridObject. Render the
	 * object invisible, update any game state through the current instantiated
	 * ship object, remove any persistent effects and then delete it from the
	 * model.
	 */
	public void destroy() {

	}

	/**
	 * Removes any effect the unit has on the grid and begins death animation.
	 * Precondition: Must call destroy() before returning.
	 * 
	 * TODO implement death animation and clear any status effects this object
	 * is applying to the grid. There should be a death sprite on any object
	 * that can die.
	 */
	public void die() {
	}

	/**
	 * Returns whether the number of effects in its effect list is greater than
	 * 0.
	 * 
	 * @return
	 */
	public boolean canApplyStatusEffects() {
		if (effects.size() == 0) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * write this method to record each discrete unit of object creation,
	 * action and destruction for replay.
	 */
	public void pushToReplay(String name, GameAction action, int value) {
	}
}
