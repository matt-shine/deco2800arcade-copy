package deco2800.arcade.towerdefence;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

/**
 * The class for Alien's invading the ship.
 * @author hadronn
 *
 */
public class Alien extends Mobile implements GridObject, Mortal, Melee {
	//Fields
	//The number of attacks per second the alien can do.
	private float attackRate;
	//The amount of damage the alien can inflict with an attack.
	private int damage;
	//The maximum health the alien can have.
	private int maxHealth;
	//the current health the alien has.
	private int health;
	//the armour the alien has.
	private int armour;
	//the amount of armour the alien's attack ignores
	private int penetration;
	//The side the alien is on.
	private Team team;
	//The grid the alien is currently on.
	private Grid grid;
	//The current position of the alien on the grid.
	private Vector2 vector;
	//Is the alien currently visible?
	private boolean visibility;
	//The list of status effects this object can apply. Can be 0 length.
	private ArrayList<Effect> effects;
	//Does it currently have collision?
	private boolean physical;
	//Draw the object with this Opaqueness.
	private int opaqueVal;
	//The direction the Alien is facing.
	private Direction direction;
	//The List of sprites for all facings.
	private ArrayList<Sprite> sprites;
	//The alien's speed in moves per second.
	private float speed;
	
	//Constructor
	
	
	//Getters
	@Override
	public int health() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int maxHealth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int armour() {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public boolean hasStatusEffects() {
		// TODO Auto-generated method stub
		return false;
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
	
	@Override
	public float attackRate() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int damage() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public GridObject target() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int penetration() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	//Methods
	@Override
	public void heal(int amount) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void takeDamage(int amount) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void takeDamage(int amount, int penetration) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void die() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void moving(Vector2 vector) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateGameState() {
		// TODO Auto-generated method stub
		
	}
}
