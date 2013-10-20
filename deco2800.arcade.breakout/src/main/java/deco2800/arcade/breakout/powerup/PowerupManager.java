package deco2800.arcade.breakout.powerup;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import deco2800.arcade.breakout.screens.GameScreen;
/**
 * Keeps track of the powerups in use and manages them as the game continues
 * @author Carlie Smits
 *
 */
public class PowerupManager {

	// Record the number of active of each powerup
	private ArrayList<Integer> numPowerups;
	private ArrayList<Powerup> powerups;
	
	private GameScreen gs;
	/**
	 * Initialise powerup and counting arrays
	 * Instantiate a new instance of powerup manager
	 * @param gs - the current game screen
	 */
	public PowerupManager(GameScreen gs) {
		this.gs = gs;
		powerups = new ArrayList<Powerup>();
		numPowerups = new ArrayList<Integer>();
		// 0 is LifePowerup
		numPowerups.add(0);
		// 1 is SlowBall
		numPowerups.add(0);
		// 2 is IncreasePaddle
		numPowerups.add(0);
		// 3 is DecreasePaddle
		numPowerups.add(0);
		// 4 is IncreaseBallNo
		numPowerups.add(0);
	}
	
	/**
	 * Adds the powerup to the array of powerups and drops the powerup at x,y
	 * @param x - the x coordinate of the brick where the powerup will drop
	 * @param y - the y coordintate of the brick where the powerup will drop
	 */
	public void handlePowerup(float x, float y) {
		Powerup p = dropPowerup();
		powerups.add(p);
		p.setPos(x, y);
	}
	
	/**
	 * Move each powerup in the powerups array 
	 */
	public void moveAll() {
		for (Powerup p : powerups) {
			p.move();
		}
	}
	
	/**
	 * renders the powerups in the powerups array
	 * @param batch - contains rendering details
	 */
	public void renderAll(SpriteBatch batch) {
		for (Powerup p : powerups) {
			p.render(batch, p.getSprite());
		}
	}
	
	/**
	 * Generates a random number between 0 and 4 that is linked to a specific powerup
	 * @return - the powerup to be dropped
	 */
	private Powerup dropPowerup() {
		Random randomNum = new Random();
		int index = randomNum.nextInt(5);
		return determinePowerup(index);
	}
	
	/**
	 * Determine the powerup associated with the given index
	 * @param index - a number between 0 and 4
	 * @return - the powerup associated with the index
	 */
	private Powerup determinePowerup (int index) {
		increaseNumPowerups(index);
		switch (index) {
		case 0:
			return new LifePowerup(gs);
		case 1:
			return new SlowBall(gs);
		case 2:
			return new IncreasePaddle(gs);
		case 3:
			return new DecreasePaddle(gs);
		default:
			return new IncreaseBallNo(gs);
		}
		
	}
	
	/**
	 * Increase the count of a specific powerup
	 * @param index - a number between 0 and 4
	 */
	private void increaseNumPowerups(int index) {
		numPowerups.set(index, numPowerups.get(index) + 1);
	}
	
	/**
	 * Decrease the count of a specific powerup
	 * @param index - a number between 0 and 4
	 */
	private void decreaseNumPowerups(int index) {
		numPowerups.set(index, numPowerups.get(index) - 1);
	}
	
	/**
	 * Determine the index associated with a specific powerup
	 * @param p - a powerup
	 * @return - the index associated with a specific powerup
	 */
	private int determineIndex(Powerup p) {
		if (p instanceof LifePowerup) {
			return 0;
		} else if (p instanceof SlowBall) {
			return 1;
		} else if (p instanceof IncreasePaddle) {
			return 2;
		} else if (p instanceof DecreasePaddle) {
			return 3;
		}
		return 4;
	}
	
	/**
	 * Checks whether a powerup has been caught with the paddle
	 * @param paddle - a rectangle shape defined by where the paddle is
	 */
	public void checkCollision(Rectangle paddle) {
		// Loop with iterator to avoid ConcurrentModificationException
		for (Iterator<Powerup> it = powerups.iterator(); it.hasNext(); ) {
			Powerup p = it.next();
			if (p.getBounds().overlaps(paddle)) {
				p.applyPowerup();
				decreaseNumPowerups(determineIndex(p));
				it.remove();
			}
		}
	}
	
	/**
	 * Checks whether a powerup has not been caught by the paddle 
	 * i.e. checks whether a powerup has fallen below the screen so it can remove it
	 */
	public void checkBelowScreen() {
		for (Iterator<Powerup> it = powerups.iterator(); it.hasNext(); ) {
			Powerup p = it.next();
			if ((p.getBounds().y + p.getBounds().height) < 0) {
				decreaseNumPowerups(determineIndex(p));
				it.remove();
			}
		}
	}
	
	/**
	 * A method that clears the number array
	 */
	private void resetNumPowerups() {
		for (int i = 0; i < 5; i++) {
			numPowerups.set(i, 0);
		}
	}
	
	/**
	 * Clears the array of powerups and number array
	 */
	public void dispose() {
		powerups.clear();
		resetNumPowerups();
	}
	
	/**
	 * 
	 * @return - the number of active powerups
	 */
	public int getPowerupArrayLength() {
		return powerups.size();
	}
	
	/**
	 * 
	 * @return - return the array for counting number of powerups
	 */
	public ArrayList<Integer> getNumArray() {
		return numPowerups;
	}
}
