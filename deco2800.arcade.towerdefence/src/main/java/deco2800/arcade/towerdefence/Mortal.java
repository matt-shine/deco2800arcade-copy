package deco2800.arcade.towerdefence;

/**
 * The interface for mortal objects.
 * Will be the majority of interesting objects.
 * Can die through game mechanics and have curious attributes such as health.
 * @author hadronn
 *
 */
public interface Mortal {
	//Return the current health of the mortal, nonnegative.
	public int health();
	
	//Return the maximum health of the mortal, nonnegative.
	public int maxHealth();
	
	//Return the armour of the mortal, nonnegative.
	public int armour();
	
	//Increase the current health, not beyond maxHealth().
	public void heal(int amount);
	
	//Decrease the current health, not below 0.
	public void takeDamage(int amount);
	
	//Decrease the current health, not below 0. Using penetration calculations.
	//Penetration is a direct armour debuff for that attack alone.
	public void takeDamage(int health, int penetration);
}
