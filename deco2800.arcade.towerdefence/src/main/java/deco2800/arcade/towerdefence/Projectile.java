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

}
