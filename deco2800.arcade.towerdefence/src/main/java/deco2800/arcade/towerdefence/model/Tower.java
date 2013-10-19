package deco2800.arcade.towerdefence.model;

/**
 * The interface for all Towers available throughout the game to the player. Do
 * implement the interface Ranged or if created AreaEffect alongside Tower.
 * 
 * @author hadronn
 * 
 */
public interface Tower {
	// Returns the category type of the tower.
	public TowerType type();
}
