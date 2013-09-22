package deco2800.arcade.towerdefence;

/**
 * The class for Aliens invading the ship.
 * 
 * @author hadronn
 * 
 */
public class Enemy extends Mobile implements Melee {
	// Fields
	// The number of attacks per second the alien can do.
	private float attackRate;
	// The amount of damage the alien can inflict with an attack.
	private int damage;
	// The amount of armour the alien's attack ignores.
	private int penetration;
	// The current target.
	private GridObject target;

	// Constructor
	public Enemy(int maxHealth, int armour) {
		super(maxHealth, armour);
	}

	// Getters
	/**
	 * Returns the attacks per second the enemy can make.
	 */
	public float attackRate() {
		return attackRate;
	}

	/**
	 * Returns the amount of damage per attack the enemy can do.
	 */
	public int damage() {
		return damage;
	}

	/**
	 * Returns the amount of penetration each enemy's attack has.
	 */
	public int penetration() {
		return penetration;
	}

	/**
	 * Returns the current target of the enemy.
	 */
	public GridObject target() {
		return target;
	}

	// Setters
	/**
	 * Sets the attacks per second the enemy can make.
	 * 
	 * @param ar
	 */
	public void attackRate(float ar) {
		this.attackRate = ar;
	}

	/**
	 * Sets the amount of damage per attack the enemy can do.
	 * 
	 * @param dmg
	 */
	public void damage(int dmg) {
		this.damage = dmg;
	}

	/**
	 * Sets the amount of penetration each enemy's attack has.
	 * 
	 * @param pen
	 */
	public void penetration(int pen) {
		this.penetration = pen;
	}

	/**
	 * Sets the current target of the enemy.
	 * 
	 * @param target
	 */
	public void target(GridObject target) {
		this.target = target;
	}

	// Methods
}
