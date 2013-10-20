package deco2800.arcade.towerdefence.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

import deco2800.arcade.towerdefence.model.effects.Effect;

/**
 * The class for an object that can be created on a grid, required to be unique
 * when instantiated.
 * 
 * @author hadronn
 * 
 */
public class GridObject {
	// Fields
	private UUID id;
	// The grid this object is on.
	protected Grid grid;
	// The position of this object on the grid.
	protected Vector2 position;
	// Whether the object is visible.
	private boolean visible = true;
	// The list of status effects this GridObject can apply.
	private List<Effect> effects = new ArrayList<Effect>();
	// Whether this object has collision.
	private boolean physical = true;
	// The opaqueness of the object as a percentage.
	private int opaqueness = 100;
	// The direction the object is facing.
	private Direction facing = Direction.S;
	// The standing sprites this object uses.
	protected List<Sprite> sprStanding;
	// The team this object belongs to.
	protected Team team;
	// The maximum number of effects that can be stacked on this GridObject.
	private final static int effectStackingLimit = 15;
	// The number of effects applied to this GridObject.
	private int effectStacks = 0;

	// Constructor
	/**
	 * The GridObject constructor.
	 * 
	 * @param x
	 *            The x position of the object, in pixels.
	 * @param y
	 *            The y position of the object, in pixels.
	 * @param grid
	 *            The grid the object belongs to.
	 */
	public GridObject(int x, int y, Grid grid, Team team,
			List<Sprite> sprStanding) {
		this.position = new Vector2(x, y);
		this.grid = grid;
		this.id = UUID.randomUUID();
		this.team = team;
		this.sprStanding = sprStanding;
	}

	// Getters
	/**
	 * Returns the grid the object belongs to.
	 * 
	 * @return The grid the object belongs to.
	 */
	public Grid grid() {
		return grid;
	}

	/**
	 * Returns the x and y coordinates of the object.
	 * 
	 * @return The position of the object as a 2D vector.
	 */
	public Vector2 position() {
		return position;
	}

	/**
	 * Returns whether the object should be drawn to the grid.
	 * 
	 * @return Whether the object is visible.
	 */
	public boolean visible() {
		return visible;
	}

	/**
	 * Returns a list of status effects it can apply to the grid or other
	 * objects at any time.
	 * 
	 * @return A list of effects belonging to this object.
	 */
	public List<Effect> effects() {
		return effects;
	}

	/**
	 * Returns whether the object currently has collision or not.
	 * 
	 * @return Whether the object can collide.
	 */
	public boolean physical() {
		return physical;
	}

	/**
	 * All grid objects must have an opaqueness value for drawing.
	 * 
	 * @return The opaqueness of the object, as a percentage.
	 */
	public int opaqueness() {
		return opaqueness;
	}

	/**
	 * Returns the direction the object is facing, for determining sprite to
	 * use.
	 * 
	 * @return A Direction representing where the object is facing.
	 */
	public Direction facing() {
		return facing;
	}

	/**
	 * Returns the List of sprites for the object.
	 * 
	 * @return A List of sprites for the standing animation.
	 */
	public List<Sprite> sprites() {
		return sprStanding;
	}

	/**
	 * Returns the ID of this object.
	 * 
	 * @return The ID of the object, as a UUID.
	 */
	public UUID getID() {
		return id;
	}

	/**
	 * Return the side the object is affiliated with, for score and avoiding
	 * friendly-fire.
	 * 
	 * @return A Team representing which side the object belongs to.
	 */
	public Team team() {
		return team;
	}

	/**
	 * Return the effect stacking limit of the GridObject.
	 */
	public int effectStackingLimit() {
		return effectStackingLimit;
	}

	/**
	 * Return the number of effects currently applied to the GridObject.
	 */
	public int effectStacks() {
		return effectStacks;
	}

	/**
	 * Return the list of standing sprites.
	 */
	public List<Sprite> standingSprites() {
		return sprStanding;
	}

	// Setters
	/**
	 * Set the grid the object belongs to.
	 * 
	 * @param grid
	 *            The grid the object now belongs to.
	 */
	public void grid(Grid grid) {
		this.grid = grid;
	}

	/**
	 * Set the position the GridObject has on the grid.
	 * 
	 * @param vector
	 *            The new position of the object.
	 */
	public void position(Vector2 vector) {
		this.position = vector;
	}

	/**
	 * Set whether the GridObject is visible on the grid.
	 * 
	 * @param visibility
	 *            THe new visibility state of the object.
	 */
	public void visible(Boolean visibility) {
		this.visible = visibility;
	}

	/**
	 * Set the effects that this GridObject can apply.
	 * 
	 * @param effects
	 *            The new list of effects for the object
	 */
	public void effects(ArrayList<Effect> effects) {
		this.effects = effects;
	}

	/**
	 * Set whether the GridObject has collision or not.
	 * 
	 * @param physical
	 *            The new state of collision for the object
	 */
	public void physical(Boolean physical) {
		this.physical = physical;
	}

	/**
	 * Set the opaqueness value of the GridObject.
	 * 
	 * @param opaqueness
	 *            The new percentage opacity of the object.
	 */
	public void opaqueness(int opaqueness) {
		this.opaqueness = opaqueness;
	}

	/**
	 * Set the facing of the GridObject.
	 * 
	 * @param facing
	 *            The new direction the object is facing.
	 */
	public void facing(Direction facing) {
		this.facing = facing;
	}

	/**
	 * Set the array list of standing sprites this object uses.
	 * 
	 * @param standingSprites
	 *            The new standing sprites for the object
	 */
	public void standingSprites(List<Sprite> sprites) {
		this.sprStanding = sprites;
	}

	/**
	 * Set the team this GridObject is on.
	 * 
	 * @param team
	 *            The new team for the object.
	 */
	public void team(Team team) {
		this.team = team;
	}

	/**
	 * Set the number of effects currently applied to this GridObject.
	 */
	public void effectStacks(int amount) {
		this.effectStacks = amount;
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
	 * @return Whether the object has status effects.
	 */
	public boolean canApplyStatusEffects() {
		if (effects.size() == 0) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * write this method to record each discrete unit of object creation, action
	 * and destruction for replay.
	 */
	public void pushToReplay(String name, GameAction action, int value) {
	}

	/**
	 * Get the position of the object as a tile on the grid.
	 * 
	 * @return a Vector2 representing the coordinates of the object's tile on
	 *         the grid.
	 */
	public Vector2 positionInTiles() {
		Vector2 tilesPosition = new Vector2();
		tilesPosition.x = position.x;
		tilesPosition.x /= grid.getTileSize();
		tilesPosition.y = position.y;
		tilesPosition.y /= grid.getTileSize();
		return tilesPosition;
	}

	/**
	 * Get the contents of the grid the object is currently in.
	 * 
	 * @return A list of objects in the current tile.
	 */
	public List<GridObject> getCurrentGrid() {
		return grid.getGridContents((int) positionInTiles().x,
				(int) positionInTiles().y);
	}

	/**
	 * Begin running the AI for the object, if any. Objects with AI should
	 * overwrite this.
	 */
	public void start() {

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((effects == null) ? 0 : effects.hashCode());
		result = prime * result + ((facing == null) ? 0 : facing.hashCode());
		result = prime * result + ((grid == null) ? 0 : grid.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + opaqueness;
		result = prime * result + (physical ? 1231 : 1237);
		result = prime * result
				+ ((position == null) ? 0 : position.hashCode());
		result = prime * result
				+ ((sprStanding == null) ? 0 : sprStanding.hashCode());
		result = prime * result + ((team == null) ? 0 : team.hashCode());
		result = prime * result + (visible ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object o) {
		if (o.getClass() == this.getClass()
				&& ((GridObject) o).getID() == this.id) {
			return true;
		} else {
			return false;
		}
	}

}
