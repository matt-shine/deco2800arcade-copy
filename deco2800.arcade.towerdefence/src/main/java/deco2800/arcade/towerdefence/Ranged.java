package deco2800.arcade.towerdefence;

import java.util.ArrayList;

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
	 * @return
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
	 * @return
	 */
	public float range();

	/**
	 * Returns the projectile that the GridObject currently fires.
	 * 
	 * @return
	 */
	public Projectile projectile();

	/**
	 * Returns the target the GridObject is currently focused on.
	 * 
	 * @return
	 */
	public GridObject target();
	
	/**
	 * Returns the array of sprites to animate shooting.
	 * @return
	 */
	public ArrayList<Sprite> shootingSprites();
}
