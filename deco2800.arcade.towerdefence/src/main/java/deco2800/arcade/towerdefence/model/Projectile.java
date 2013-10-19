package deco2800.arcade.towerdefence.model;

import com.badlogic.gdx.math.Vector2;

/**
 * The class for GridObjects that can fly on any angle from a source
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
	// The projectile's speed and direction, in pixels per second.
	private Vector2 speed;
	// The projectile's range
	private float range;

	// Constructor
	/**
	 * The Projectile constructor.
	 * 
	 * @param x
	 *            The starting x position of the object
	 * @param y
	 *            The starting y position of the object
	 * @param grid
	 *            The grid the object belongs to
	 * @param speed
	 *            The speed and direction of the object in pixels per second,
	 *            represented as a vector
	 * @param range
	 */
	public Projectile(int x, int y, Grid grid, Vector2 speed, float range) {
		super(x, y, grid);
		this.speed = speed;
		this.range = range;
	}

	// Getters
	/**
	 * Returns the damage the projectile inflicts on collision.
	 * 
	 * @return The damage dealt by the projectile.
	 */
	public int damage() {
		return damage;
	}

	/**
	 * Returns the penetration amount on the projectile. The amount of armour
	 * this projectile ignores.
	 * 
	 * @return The armour penetration of the projectile.
	 */
	public int penetration() {
		return penetration;
	}

	// Setters
	/**
	 * Set the damage this projectile inflicts on collision.
	 * 
	 * @param damage
	 *            The new damage dealt by the projectile.
	 */
	public void damage(int damage) {
		this.damage = damage;
	}

	/**
	 * Set the amount of penetration on the projectile.
	 * 
	 * @param penetration
	 *            The new penetration of the projectile.
	 */
	public void penetration(int penetration) {
		this.penetration = penetration;
	}

	// Methods
	/**
	 * Continually move in the given vector until a collision, or the maximum
	 * range is reached.
	 */
	private void move() {
		// Move at speed in the object's direction until it collides with
		// something
		float moved = 0;
		long t0, t1;
		while (moved < range) {
			t0 = System.currentTimeMillis();
			t1 = t0;
			// Move
			position.add(speed.mul(1 / 30));
			moved += speed.mul(1 / 30).len();
			// Wait 1/30th of a second before moving again
			while (t1 - t0 < 33) {
				t1 = System.currentTimeMillis();
			}
		}

		// do nothing for now TODO
	}

}
