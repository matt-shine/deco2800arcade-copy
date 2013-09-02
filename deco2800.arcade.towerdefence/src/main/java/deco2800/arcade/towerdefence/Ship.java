package deco2800.arcade.towerdefence;

/**
 * The interface for the creation of a ship.
 * For single player games one ship per player only.
 * For co-op play one ship only and n(2?) players.
 * For head-to-head m ships, m players.
 * @author hadronn
 *
 */
public interface Ship {
	//current resources of the ship.
	//may need to be split into types later.
	public int resources();
	
	//current score for the player.
	//perhaps a formula of units killed, wave and ship health?
	public int score();
	
	//current wave the player is on.
	public int wave();
	
	//give the player more score.
	public void addScore();
	
	//give the ship more resources
	public void addResources();
	
	//increment the wave, keep to units of 1 don't be cute.
	public void nextWave();
	
	//Jump forward to wave n, useful for save/load.
	public void setWave(int n);

}
