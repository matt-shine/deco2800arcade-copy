package deco2800.arcade.towerdefence;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

/**
 * The interface for an object that can be created on a grid, required to be unique when instantiated.
 * @author hadronn
 *
 */
public interface GridObject {
	//Returns the grid the object belongs to.
	public Grid grid();
	
	//Returns the x and y coordinates of the object.
	public Vector2 vector();
	
	//Returns whether the object should be drawn to the grid.
	public boolean visible();
	
	//Render the object invisible, remove any persistent effects and then remove the object from the model.
	public void destroy();
	
	//To be invoked while instantiating or destroying an object to keep track of any required state such as numbers on the grid.
	public void updateGameState();
	
	//Returns a list of status effects it can apply to the grid or other objects at any time.
	public ArrayList<Effect> effects();
	
	//Returns whether the effects in its effect list is greater than 0.
	public boolean hasStatusEffects();
	
	//Returns whether the object currently has collision or not.
	public boolean physical();
	
	//All grid objects must have an opaqueness value for drawing.
	public int opaqueness();
	
	//Returns the direction the object is facing, for determining sprite to use.
	public Direction facing();
	
	//Returns the sprite to display based on a direction.
	public Sprite sprite(Direction direction);
	
	//Return the side the object is affiliated with, for score and avoiding friendly-fire.
	public Team team();

	
}
