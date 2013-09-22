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
	// The grid this object is on
	private Grid grid;
	// The position of this object on the grid
	private Vector2 position = new Vector2();
	// Whether the object is visible
	private boolean visible;
	// The list of status effects this GridObject can apply.
	private ArrayList<Effect> effects = new ArrayList<Effect>();
	// Whether this object has collision
	private boolean physical;
	// The opaqueness of the object as a percentage
	private int opaqueness = 100;
	// The direction the object is facing
	private Direction facing;
	// The standing sprites this object uses
	private ArrayList<Sprite> standingSprites;
	// The team this object belongs to
	private Team team;
	
	// Getters
	// Returns the grid the object belongs to.
	public Grid grid() {
		return grid;
	}

	// Returns the x and y coordinates of the object.
	public Vector2 position() {
		return position;
	}

	// Returns whether the object should be drawn to the grid.
	public boolean visible() {
		return visible;
	}
	
	// Returns a list of status effects it can apply to the grid or other
	// objects at any time.
	public ArrayList<Effect> effects() {
		return effects;
	}
	
	// Returns whether the object currently has collision or not.
	public boolean physical(){
		return physical;
	}

	// All grid objects must have an opaqueness value for drawing.
	public int opaqueness(){
		return opaqueness;
	}

	// Returns the direction the object is facing, for determining sprite to
	// use.
	public Direction facing(){
		return facing;
	}
	
	// Returns the ArrayList of sprites for the object.
	public ArrayList<Sprite> sprites() {
		return standingSprites;
	}

	// Returns the sprite to display based on a direction when the object is still.
	public Sprite standingSprite(Direction facing){
		/**
		 * TODO implement this method according to the structure below so that it returns the correct sprite for a facing.
		 * index - sprite
		 * 0 - standing facing down
		 * 1 - standing facing left 
		 * 2 - standing facing up
		 * 3 - standing facing right
		 * 
		 * sprites().get(0) would return facing down sprite
		 */
		return null;
	}

	// Return the side the object is affiliated with, for score and avoiding friendly-fire.
	public Team team(){
		return team;
	}
	
	// Setters
	// Set the grid the object belongs to.
	public void grid(Grid grid){
		this.grid = grid;
	}
	
	// Set the position the GridObject has on the grid.
	public void position(Vector2 vector){
		this.position = vector;
	}
	
	// Set whether the GridObject is visible on the grid.
	public void visible(Boolean visibility){
		this.visible = visibility;
	}
	
	// Set the effects that this GridObject can apply.
	public void effects(ArrayList<Effect> effects){
		this.effects = effects;
	}
	
	// Set whether the GridObject has collision or not.
	public void physical(Boolean physical){
		this.physical = physical;
	}
	
	// Set the opaqueness value of the GridObject.
	public void opaqueness(int opaqueness){
		this.opaqueness = opaqueness;
	}
	
	// Set the facing og the GridObject.
	public void facing(Direction facing){
		this.facing = facing;
	}
	
	// Set the array list of standing sprites this object uses.
	public void standingSprites(ArrayList<Sprite> standingSprites) {
		this.standingSprites = standingSprites;
	}
	
	// Set the team this GridObject is on.
	public void team(Team team){
		this.team = team;
	}
	
	// Methods
	// remove the object from the model.
	public void destroy(){
		/**
		 * TODO implement generic destruction method of GridObject.
		 * Render the object invisible, update any game state, remove any persistent effects and then delete it from the model.
		 */
	}

	// To be invoked while instantiating or destroying an object to keep track
	// of any required state such as numbers on the grid.
	public abstract void updateGameState();
	/** 
	 * TODO decide what game state will be kept, calculated and if calculated when.
	 * @return
	 */

	// Returns whether the number of effects in its effect list is greater than
	// 0.
	public boolean canApplyStatusEffects() {
		if (effects.size() == 0) {
			return false;
		} else {
			return true;
		}
	}

}
