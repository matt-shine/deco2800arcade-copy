package deco2800.arcade.towerdefence;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

/**
 * The interface for GridObjects that can fly on any angle from a source GridObject towards its target.
 * @author hadronn
 *
 */
public class Projectile extends Mobile implements GridObject, Divine {
	//Fields
	//The amount of damage this projectile does if it collides.
	private int damage;
	//The amount of armour this projectile ignores.
	private int penetration;
	//The side of the projectile (maybe overkill but could be used for stats).
	private Team team;
	//The grid that the projectile is currently on.
	private Grid grid;
	//The position of the projectile on the grid.
	private Vector2 vector;
	//Should the object be drawn on the in the view?
	private boolean visibility;
	//The list of status effects this object can apply. Can be 0 length.
	private ArrayList<Effect> effects;
	//Does it currently have collision?
	private boolean physical;
	//Draw the object with this Opaqueness.
	private int opaqueVal;
	//What direction is it facing? Probably unimportant for projectiles.
	private Direction direction;
	//The List of sprites for all facings. Probably just one for projectiles.
	private ArrayList<Sprite> sprites;
	//The projectile's speed in moves per second.
	private float speed;
	
	//Constructor
	
	
	//Getters
	public int damage() {
		return 0;
	}
	
	@Override
	public Grid grid() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Vector2 vector() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public boolean visible() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public ArrayList<Effect> effects() {
		return null;
		// TODO Auto-generated method stub
	}
	
	@Override
	public boolean physical() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public int opaqueness() {
		return 100;
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public Direction facing() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Sprite sprite(Direction direction) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Team team() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public float speed() {
		// TODO Auto-generated method stub
		return 0;
	}

	//Setters
	
	
	//Methods
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void moving(Vector2 vector) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void die() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void updateGameState() {
		// TODO Auto-generated method stub
	}
	
	@Override
	public boolean hasStatusEffects() {
		// TODO Auto-generated method stub
		return false;
	}
}
