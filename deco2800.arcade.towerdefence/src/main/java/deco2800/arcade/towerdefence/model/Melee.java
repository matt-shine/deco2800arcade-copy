package deco2800.arcade.towerdefence.model;

import java.util.List;

/**
 * The interface for GridObjects that can attack another GridObject on it's
 * facing.
 * 
 * @author hadronn
 * 
 */
public interface Melee {
	/**
	 * Returns the number of attacks per second it is capable of
	 * 
	 * @return The number of attacks per second as a floating point number.
	 */
	public double attackRate();

	/**
	 * Returns the damage of an attack
	 * 
	 * @return The amount of damage dealt with each attack.
	 */
	public int damage();

	/**
	 * Returns the penetration of the attack
	 * 
	 * @return The penetration of each attack
	 */
	public int penetration();

	/**
	 * Returns the target the GridObject is currently focused on.
	 * 
	 * @return The current target.
	 */
	public GridObject target();

	/**
	 * Returns the array of files to animate melee attacking.
	 * 
	 * @return The list of files for the melee animation.
	 */
	public List<String> fileAttacking();

}
