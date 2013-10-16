package deco2800.arcade.towerdefence;

import com.badlogic.gdx.math.Vector2;

/**
 * The interface for GridObjects that can fly on any angle from a source
 * GridObject towards its target.
 * 
 * @author hadronn
 * 
 */
public class Projectile extends GridObject {
	// Fields
	// The amount of damage this projectile does if it collides.
	private int damage;
	// The amount of armour this projectile ignores.
	private int penetration;
	//The projectile's speed and direction, in pixels per second.
	private Vector2 speed;
	//The projectile's range
	private float range;

	// Constructor

	// Getters
	/**
	 * Returns the damage the projectile inflicts on collision.
	 * 
	 * @return
	 */
	public int damage() {
		return damage;
	}

	/**
	 * Returns the penetration amount on the projectile. The amount of armor
	 * this projectile ignores.
	 * 
	 * @return
	 */
	public int penetration() {
		return penetration;
	}

	// Setters
	/**
	 * Set the damage this projectile inflicts on collision.
	 * 
	 * @param damage
	 */
	public void damage(int damage) {
		this.damage = damage;
	}

	/**
	 * Set the amount of penetration on the projectile.
	 * 
	 * @param penetration
	 */
	public void penetration(int penetration) {
		this.penetration = penetration;
	}

	// Methods
	private void move(){
		//Move at speed in the object's direction until it collides with something
		float moved = 0;
		long t0, t1;
		while (moved < range){
			t0 = System.currentTimeMillis();
			t1 = t0;
			// Move
			position.add(speed.mul(1/30));
			moved += speed.mul(1/30).len();
			// Wait 1/30th of a second before moving again
			while (t1 - t0 < 33) {
				t1 = System.currentTimeMillis();
			}
		}
		
		//do nothing for now TODO
	}

}
