package deco2800.arcade.towerdefence.model.creationclasses;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.Sprite;

import deco2800.arcade.towerdefence.controller.TowerDefence;
import deco2800.arcade.towerdefence.model.Grid;
import deco2800.arcade.towerdefence.model.Mortal;
import deco2800.arcade.towerdefence.model.Team;
import deco2800.arcade.towerdefence.view.GameScreen;

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
	public List<String> fileStanding() {
		return fileStanding;
	}

	// Setters
	public void fileStanding(List<String> files) {
		this.fileStanding = files;
	}

	// Methods
	public void start() {
		// Create the list of filenames for standingFiles
		// The hard-coded file path for the frame
		String frame = "frames\\Barrier\\Standing\\BarrierStanding.png";
		// The list
		List<String> frameList = new ArrayList<String>();
		// put the frame in the list
		frameList.add(frame);
		// set the fileStanding to the list of frame(s)
		this.fileStanding(frameList);
		
		// Remember to adjust the rotation before building the sprite if necessary
		this.rotation(0);
		
		// Build the idle sprite list
		List<Sprite> sprList = (GameScreen.spriteBuild(this, fileStanding()));
		
		// Add the list of sprites to the currently animating model
		TowerDefence.toRender.add(sprList);
	}
}