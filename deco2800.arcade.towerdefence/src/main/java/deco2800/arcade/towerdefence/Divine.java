package deco2800.arcade.towerdefence;

/**
 * The interface for divine objects.
 * Divines can die.
 * They don't have characteristic-based events to call death.
 * @author hadronn
 *
 */
public interface Divine{
	//Starts any on death behaviour. Possibly ends by calling destroy to remove from grid view and from game model.
	public void die();
}
