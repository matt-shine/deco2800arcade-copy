package deco2800.arcade.towerdefence;

/**
 * The interface for GridObjects that can shoot projectiles at a position
 * (probably at another GridObject).
 * 
 * @author hadronn
 * 
 */
public interface Ranged {
	// Returns the number of attacks the tower can make in one second.
	public float attackRate();

	// Fire a projectile from your own position at another.
	public void shoot();

	// Returns the maximum distance a GridObject can be from another GridObject
	// in order to fire at it.
	public float range();

	// Returns the projectile that the GridObject currently fires.
	public Projectile projectile();

	// Returns the target the GridObject is currently focused on.
	public GridObject target();
}
