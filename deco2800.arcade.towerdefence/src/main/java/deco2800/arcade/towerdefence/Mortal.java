package deco2800.arcade.towerdefence;

/**
 * The interface for mortal objects. Will be the majority of interesting
 * objects. Can die through game mechanics and have curious attributes such as
 * health.
 * 
 * @author hadronn
 * 
 */
public class Mortal extends GridObject {
	// Fields
	// The maximum health the alien can have.
	private int maxHealth;
	// The current health the alien has.
	private int health;
	// The armour the alien has.
	private int armour;

	// Constructor
	/**
	 * The constructor for Mortal.
	 * 
	 * @param maxHealth
	 *            The maximum health of the object.
	 * @param armour
	 *            The armor of the object.
	 * @param x
	 *            The x position of the object, in pixels
	 * @param y
	 *            The y position of the object, in pixels
	 * @param grid
	 *            The grid the object belongs to
	 */
	public Mortal(int maxHealth, int armour, int x, int y, Grid grid) {
		super(x, y, grid);
		this.maxHealth = maxHealth;
		this.health = maxHealth;
		this.armour = armour;
	}

	// Getters
	/**
	 * Return the maximum health of the mortal, nonnegative.
	 * 
	 * @return The maximum health of the object.
	 */
	public int maxHealth() {
		return maxHealth;
	}

	/**
	 * Return the current health of the mortal, nonnegative.
	 * 
	 * @return The current health of the object.
	 */
	public int health() {
		return health;
	}

	/**
	 * Return the armour of the mortal, nonnegative.
	 * 
	 * @return The armour of the object.
	 */
	public int armour() {
		return armour;
	}

	// Setters
	/**
	 * Sets the maximum health of the Mortal. If current health is above this
	 * value, reduce it.
	 * 
	 * @param maxHealth
	 *            The new maximum health.
	 */
	public void maxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
		if (health > maxHealth) {
			health = maxHealth;
		}
	}

	/**
	 * Sets the current health of the Mortal.
	 * 
	 * @param health
	 *            The new current health.
	 */
	public void health(int health) {
		this.health = health;
	}

	/**
	 * Sets the armour of the Mortal.
	 * 
	 * @param armour
	 *            The new armour.
	 */
	public void armour(int armour) {
		this.armour = armour;
	}

	// Methods
	/**
	 * Increase the current health, not beyond maxHealth().
	 * 
	 * @param amount
	 *            The amount to be healed.
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
	 *            The amount of damage dealt
	 */
	public void takeDamage(int amount) {
		int damage = amount - armour;
		if (damage <= 0) {
			return;
		}
		health -= damage;
		if (health <= 0) {
			die();
		}

	}

	/**
	 * Decrease the current health, not below 0. Using penetration calculations.
	 * Penetration is a direct armour debuff for that attack alone.
	 * 
	 * @param amount
	 *            The amount of damage dealt
	 * @param penetration
	 *            The amount of armour ignored by the damage
	 */
	public void takeDamage(int amount, int penetration) {
		int damage = amount;
		if (penetration < armour) {
			damage -= (armour - penetration);
		}

		if (damage <= 0) {
			return;
		}
		health -= damage;
		if (health <= 0) {
			die();
		}
	}
}
