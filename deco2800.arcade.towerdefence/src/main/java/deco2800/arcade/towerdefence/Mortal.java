package deco2800.arcade.towerdefence;

/**
 * The interface for mortal objects. Will be the majority of interesting
 * objects. Can die through game mechanics and have curious attributes such as
 * health.
 * 
 * @author hadronn
 * 
 */
public abstract class Mortal extends GridObject {
	// Fields
	// The maximum health the alien can have.
	private int maxHealth;
	// The current health the alien has.
	private int health;
	// The armour the alien has.
	private int armour;

	// Constructor
	/**
	 * Should only be used for super, don't instantiate a pure mortal, it should
	 * be an enemy sub-type.
	 */
	public Mortal(int maxHealth, int armour) {
		this.maxHealth = maxHealth;
		this.health = maxHealth;
		this.armour = armour;
	}

	// Getters
	/**
	 * Return the current health of the mortal, nonnegative.
	 * 
	 * @return
	 */
	public int health() {
		return health;
	}

	/**
	 * Return the maximum health of the mortal, nonnegative.
	 * 
	 * @return
	 */
	public int maxHealth() {
		return maxHealth;
	}

	/**
	 * Return the armour of the mortal, nonnegative.
	 * 
	 * @return
	 */
	public int armour() {
		return armour;
	}

	// Setters
	/**
	 * Sets the maximum health of the Mortal.
	 * 
	 * @param maxHealth
	 */
	public void maxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
	}

	/**
	 * Sets the current health of the Mortal.
	 * 
	 * @param health
	 */
	public void health(int health) {
		this.health = health;
	}

	/**
	 * Sets the armour of the Mortal.
	 * 
	 * @param armour
	 */
	public void armour(int armour) {
		this.armour = armour;
	}

	// Methods
	/**
	 * Increase the current health, not beyond maxHealth().
	 * 
	 * @param amount
	 */
	public void heal(int amount) {
		health += amount;
		if (health > maxHealth) {
			health = maxHealth;
		}

	}

	/**
	 * Decrease the current health, not below 0.
	 * 
	 * @param amount
	 */
	public void takeDamage(int amount) {
		amount -= armour;
		if (amount <= 0) {
			return;
		}
		health -= amount;
		if (health <= 0) {
			die();
		}

	}

	/**
	 * Decrease the current health, not below 0. Using penetration calculations.
	 * Penetration is a direct armour debuff for that attack alone.
	 * 
	 * @param amount
	 * @param penetration
	 */
	public void takeDamage(int amount, int penetration) {
		if (penetration < armour) {
			amount -= (armour - penetration);
		}

		if (amount <= 0) {
			return;
		}
		health -= amount;
		if (health <= 0) {
			die();
		}
	}
}
