package deco2800.arcade.towerdefence.model.creationclasses;

import java.util.List;

import deco2800.arcade.towerdefence.model.Grid;
import deco2800.arcade.towerdefence.model.Mortal;
import deco2800.arcade.towerdefence.model.Team;

/**
 * Models GridObjects that are designed as walls or barriers to GridObjects.
 * Essentially the same as Mortal, new class for appropriate name.
 * 
 * @author hadronn
 * 
 */
public class Barrier extends Mortal {
	// Fields

	/**
	 * The constructor for Barrier.
	 * 
	 * @param maxHealth
	 *            The maximum health of the object.
	 * @param armour
	 *            The armor of the object.
	 * @param x
	 *            The x position of the object, in pixels
	 * @param y
	 *            The y position of the object, in pixels
	 * @param grid
	 *            The grid the object belongs to
	 */
	public Barrier(int maxHealth, int armour, int x, int y, Grid grid,
			Team team, List<String> fileStanding, List<String> fileDeath) {
		super(maxHealth, armour, x, y, grid, team, fileStanding, fileDeath);
	}

	// Getters

	// Setters

	// Methods

}
