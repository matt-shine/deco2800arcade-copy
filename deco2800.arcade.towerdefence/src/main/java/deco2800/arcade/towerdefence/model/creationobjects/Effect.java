package deco2800.arcade.towerdefence.model.creationobjects;

import deco2800.arcade.towerdefence.model.GridObject;
import deco2800.arcade.towerdefence.model.Mobile;

/**
 * This is the class of the effect that a GridObject can apply to another GridObject(s)
 * Instantiating this and placing it in a GridObject's effect list allows it to apply it on another GridObject.
 * To use this class construct with a name and amount then call the appropriate method for the effect on the target.
 * This class contains methods for every effect in the game. 
 * This can be abused in that if you wish to use a different effect from the name you've constructed it with, you can.
 * 
 */
public class Effect {
	// Fields
	// the name of the effect
	private final String name;
	// the amount of the effect as a float.
	private final double amount;
	
	// Constructor
	/**
	 * The Effect Constructor.
	 * @param name
	 * 			Name the effect.
	 * @param amount
	 * 			Set the amount of the effect.
	 */
	public Effect(String name, int amount){
		this.name = name;
		this.amount = amount;
	}
	// Getters
	/**
	 * Returns the name of the effect.
	 * @return name of the effect.
	 */
	public String name() {
		return name;
	}
	
	/**
	 * Returns the amount of the effect as an integer.
	 * @return amount of the effect.
	 */
	public double amount() {
		return amount;
	}

	// Setters
	
	// Methods
	// Create methods here for the variety of statuses that an effect inflicts
	
	/**
	 * A method to slow the attack rate and movement speed of an enemy by the effect amount.
	 * @param target
	 */
	public void slowEnemy(GridObject target){
		// Get the Enemy Attack Rate
		double originalAR = ((Enemy) target).attackRate();
		// Get the Enemy Movement Speed
		double originalSpeed = ((Mobile) target).speed();
		// Set the Enemy Attack Rate lower by amount
		((Enemy) target).attackRate(originalAR - amount);
		// Set the Enemy Movement Speed lower by amount
		((Mobile) target).speed(originalSpeed - amount);
	}
	
		
}
