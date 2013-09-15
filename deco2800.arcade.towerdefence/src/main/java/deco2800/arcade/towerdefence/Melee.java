package deco2800.arcade.towerdefence;

/**
 * The interface for GridObjects that can attack another GridObject on it's facing.
 * @author hadronn
 *
 */
public interface Melee {
	//Returns the number of attacks per second it is capable of
	public float attackRate();
	
	//Returns the damage of an attack
	public int damage();
	
	//Returns the penetration of the attack
	public int penetration();
	
	//Returns the target the GridObject is currently focused on.
	public GridObject target();
	
}
