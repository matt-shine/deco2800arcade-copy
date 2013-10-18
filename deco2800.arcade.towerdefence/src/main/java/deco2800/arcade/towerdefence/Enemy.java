package deco2800.arcade.towerdefence;

import java.util.List;

import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * The class for enemys invading the ship.
 * 
 * @author hadronn
 * 
 */
public class Enemy extends Mobile implements Melee {
	// Fields
	// The number of attacks per second the enemy can do.
	private float attackRate;
	// The amount of damage the enemy can inflict with an attack.
	private int damage;
	// The amount of armour the enemy's attack ignores.
	private int penetration;
	// The current target.
	private GridObject target;

	// Constructor
	public Enemy(int maxHealth, int armour, int x, int y, double speed,
			Grid grid) {
		super(maxHealth, armour, x, y, speed, grid);
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

	/**
	 * Return the sprites for melee attacking animation.
	 */
	public List<Sprite> meleeSprites() {
		return null;
	}

	// Setters
	/**
	 * Sets the attacks per second the enemy can make.
	 * 
	 * @param ar
	 *            The new attack rate, in attacks per second
	 */
	public void attackRate(float ar) {
		this.attackRate = ar;
	}

	/**
	 * Sets the amount of damage per attack the enemy can do.
	 * 
	 * @param dmg
	 *            The new amount of damage dealt by each attack
	 */
	public void damage(int dmg) {
		this.damage = dmg;
	}

	/**
	 * Sets the amount of penetration each enemy's attack has.
	 * 
	 * @param pen
	 *            The armour penetration of each attack
	 */
	public void penetration(int pen) {
		this.penetration = pen;
	}

	/**
	 * Sets the current target of the enemy.
	 * 
	 * @param target
	 *            The new object to target
	 */
	public void target(GridObject target) {
		this.target = target;
	}

	/**
	 * Sets the sprites for melee attacking animation.
	 */
	public void meleeSprites(List<Sprite> meleeSprites) {
	}

	// Methods
}
