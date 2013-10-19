package deco2800.arcade.towerdefence.model.effects;

import deco2800.arcade.towerdefence.model.GridObject;

/**
 * This is the class of the effect that a GridObject can apply to another
 * GridObject(s) Instantiating this and placing it in a GridObject's effect list
 * allows it to apply it on another GridObject. To use this class construct with
 * a name and amount then call the appropriate method for the effect on the
 * target. This class contains methods for every effect in the game. This can be
 * abused in that if you wish to use a different effect from the name you've
 * constructed it with, you can.
 * 
 * A note on effect use in TD. in order to prevent large amounts of timers on
 * large amounts of enemies make an effect amount small and make it permanently
 * effect the target. If you want larger front-loaded effects, such as a frost
 * tower causing large amounts of slowing, reduce its attack speed to
 * compensate. We don't want high attack speeds and permanent effects that can
 * reduce a statistic to 0 before an enemy can get a decent way through the
 * defense. It's a balance consideration.
 * 
 * I've introduced a stacking and stack limit on GridObject to prevent
 * performance issues.
 * 
 */
public class Effect {
	// Fields
	// the name of the effect
	protected final String name;
	// the amount of the effect as a float.
	protected final double amount;
	// the duration of the effect in milliseconds.
	// use this sparingly in actual effects as it could cause performance
	// issues.
	protected final int duration;

	// Constructor
	/**
	 * The Effect Constructor.
	 * 
	 * @param name
	 *            Name the effect
	 * @param amount
	 *            Set the amount of the effect
	 * @param duration
	 *            The duration of the effect in milliseconds
	 */
	public Effect(String name, int amount, int duration) {
		this.name = name;
		this.amount = amount;
		this.duration = duration;
	}

	// Getters
	/**
	 * Returns the name of the effect.
	 * 
	 * @return name of the effect.
	 */
	public String name() {
		return name;
	}

	/**
	 * Returns the amount of the effect as an integer.
	 * 
	 * @return amount of the effect.
	 */
	public double amount() {
		return amount;
	}

	/**
	 * Returns the duration of the effect.
	 * 
	 * @return duration of the effect.
	 */
	public int duration() {
		return duration;
	}

	// Setters

	// Methods

	/**
	 * The main function that this effect performs. Designed to be overridden.
	 */
	public void function(GridObject target) {
		// override me
	}
}
