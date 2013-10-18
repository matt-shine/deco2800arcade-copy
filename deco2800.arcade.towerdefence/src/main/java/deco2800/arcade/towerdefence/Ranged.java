package deco2800.arcade.towerdefence;

import java.util.List;

import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * The interface for GridObjects that can shoot projectiles at a position
 * (probably at another GridObject).
 * 
 * @author hadronn
 * 
 */
public interface Ranged {
	/**
	 * Returns the number of attacks the ranged GridObject can make in one
	 * second, for simplicity's sake this is the same as the attack rate if
	 * ranged and melee.
	 * 
	 * @return The attack rate of the object in attacks per second.
	 */
	public float attackRate();

	/**
	 * Fire a projectile from your own position at another.
	 */
	public void shoot();

	/**
	 * Returns the maximum distance a GridObject can be from another GridObject
	 * in order to fire at it.
	 * 
	 * @return The maximum range of projectiles created by this object.
	 */
	public float range();

	/**
	 * Returns the projectile that the GridObject currently fires.
	 * 
	 * @return The projectile fired by this object.
	 */
	public Projectile projectile();

	/**
	 * Returns the target the GridObject is currently focused on.
	 * 
	 * @return The current target of this object.
	 */
	public GridObject target();

	/**
	 * Returns the array of sprites to animate shooting.
	 * 
	 * @return A list of sprites for the shooting animation.
	 */
	public List<Sprite> shootingSprites();
}
