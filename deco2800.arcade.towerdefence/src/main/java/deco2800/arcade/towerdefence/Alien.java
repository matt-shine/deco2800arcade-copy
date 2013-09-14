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
	//the current target
	private GridObject target;
	
	//Constructor
	
	
	//Getters
	public int health() {
		return health;
	}

	public int maxHealth() {
		return maxHealth;
	}

	public int armour() {
		return armour;
	}
	
	public float attackRate() {
		return attackRate;
	}

	public int damage() {
		return damage;
	}

	public GridObject target() {
		return target;
	}
	
	public int penetration() {
		return penetration;
	}
	
	//Methods
	public void heal(int amount) {
		health += amount;
		if(health > maxHealth){
			health = maxHealth;
		}
		
	}

	public void takeDamage(int amount) {
		amount -= armour;
		if (amount <= 0){
			return;
		}
		health -= amount;
		if (health <= 0){
			die();
		}
		
	}

	@Override
	public void takeDamage(int amount, int penetration) {
		if (penetration >= armour){
			takeDamage(amount);
			return;
		}
		amount -= (armour - penetration);
		if (amount <= 0){
			return;
		}
		health -= amount;
		if (health <= 0){
			die();
		}
		
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
