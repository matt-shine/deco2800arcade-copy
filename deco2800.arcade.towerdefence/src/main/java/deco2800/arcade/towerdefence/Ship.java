package deco2800.arcade.towerdefence;

/**
 * The interface for the creation of a player ship. For single player games one
 * ship per player only. For cooperative play one ship only and n(starting with
 * 2) players. For head-to-head m ships, m players(starting with 2).
 * 
 * @author hadronn
 * 
 */
public interface Ship {
	// Current resources of the ship.
	// May need to be split into types later.
	public int resources();

	// Current score for the player.
	// Perhaps a formula of units killed, wave and ship health?
	public int score();

	// Current wave the player is on.
	public int wave();

	// Give the player more score.
	public void addScore();

	// Give the ship more resources
	public void addResources();

	// Increment the wave, keep to units of 1 don't be cute.
	public void nextWave();

	// Jump forward to wave n, useful for save/load.
	public void setWave(int n);
}
