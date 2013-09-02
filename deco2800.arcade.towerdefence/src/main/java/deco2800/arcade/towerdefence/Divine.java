package deco2800.arcade.towerdefence;

/**
 * The interface for divine objects.
 * Divines can die.
 * They don't have characteristic-based events to call death.
 * @author hadronn
 *
 */
public interface Divine extends GridObject {
	//TO DO: Constructor
	
	//Starts any on death behaviour. Possibly ends by calling destroy to remove from grid view and from game model.
	public void die();
	
	//Return the position pair of the divine.
	public Position position();
	
	//Return the grid the divine occupies.
	public Grid grid();
}
