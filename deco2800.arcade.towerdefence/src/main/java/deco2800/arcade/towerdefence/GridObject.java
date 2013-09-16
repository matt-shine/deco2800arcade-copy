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
	// The grid this object is on
	private Grid grid;
	// The position of this object on the grid
	private Vector2 position = new Vector2();
	// Whether the object is visible
	private boolean visible;
	// The list of status effects
	private ArrayList<Effect> effects = new ArrayList<Effect>();
	// Whether this object has collision
	private boolean physical;
	//The opaqueness of the object as a percentage
	private int opaqueness = 100;
	//The direction the object is facing
	private Direction facing;
	//The sprite this object uses
	private ArrayList<Sprite> sprites;
	//The team this object belongs to
	private Team team;

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

	// Render the object invisible, remove any persistent effects and then
	// remove the object from the model.
	public abstract void destroy();

	// To be invoked while instantiating or destroying an object to keep track
	// of any required state such as numbers on the grid.
	public abstract void updateGameState();

	// Returns a list of status effects it can apply to the grid or other
	// objects at any time.
	public ArrayList<Effect> effects() {
		return effects;
	}

	// Returns whether the number of effects in its effect list is greater than
	// 0.
	public boolean hasStatusEffects() {
		if (effects.size() == 0) {
			return false;
		} else {
			return true;
		}
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

	// Returns the sprite to display based on a direction.
	public Sprite sprite(Direction direction){
		return null;
	}

	// Return the side the object is affiliated with, for score and avoiding
	// friendly-fire.
	public Team team(){
		return team;
	}
	
	public abstract void die();

}
