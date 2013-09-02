package deco2800.arcade.towerdefence;

/**
 * 
 * The interface for mortal objects.
 * Will be the majority of interesting objects.
 * Can die and have curious attributes such as health.
 * @author hadronn
 *
 */
public interface Mortal extends GridObject {
	//TO DO: Constructor
	
	//Return the current health of the mortal, nonnegative.
	public int health();
	
	//Return the maximum health of the mortal, nonnegative.
	public int maxHealth();
	
	//Return the current armour of the mortal, nonnegative.
	public int armour();
	
	//Return the maximum armour of the mortal, nonnegative.
	public int maxArmour();
	
	//Increase the current health, not beyond maxHealth().
	public void heal(int amount);
	
	//Decrease the current health, not below 0.
	public void damage(int amount); //Michael, what's int pen?
	
	//Starts any on death behaviour. Possibly ends by calling destroy to remove from grid view and from game model.
	public void die();
	
	//Return the position pair of the mortal
	public Position position();
	
	//Return the grid the mortal occupies.
	public Grid grid();
}
