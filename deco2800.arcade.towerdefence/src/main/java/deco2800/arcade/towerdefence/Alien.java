package deco2800.arcade.towerdefence;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

/**
 * The class for Alien's invading the ship.
 * @author hadronn
 *
 */
public class Alien extends Mortal implements Melee {
	//Fields
	//The number of attacks per second the alien can do.
	private float attackRate;
	//The amount of damage the alien can inflict with an attack.
	private int damage;
	//the amount of armour the alien's attack ignores
	private int penetration;
	//the current target
	private GridObject target;
	
	
	//Constructor
	
	
	//Getters
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
