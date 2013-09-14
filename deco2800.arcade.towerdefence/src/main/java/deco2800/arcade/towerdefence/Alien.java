package deco2800.arcade.towerdefence;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

/**
 * The class for Alien's invading the ship.
 * @author hadronn
 *
 */
public class Alien extends Mobile implements Mortal, Melee {
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

	public void die() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateGameState() {
		// TODO Auto-generated method stub
		
	}
}
