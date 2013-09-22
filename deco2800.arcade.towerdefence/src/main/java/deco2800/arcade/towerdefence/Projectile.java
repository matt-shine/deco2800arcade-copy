package deco2800.arcade.towerdefence;

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

	// Constructor

	// Getters
	public int damage() {
		// Return the damage this projectile does if it hits.
		return damage;
	}

	public int penetration() {
		// Return the amount of armor this projectile ignores.
		return penetration;
	}

	// Setters

	// Methods

}
