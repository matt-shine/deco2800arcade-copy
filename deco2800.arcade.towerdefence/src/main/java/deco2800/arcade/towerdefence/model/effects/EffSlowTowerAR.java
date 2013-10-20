package deco2800.arcade.towerdefence.model.effects;

import deco2800.arcade.towerdefence.model.creationclasses.Tower;

/**
 * An Effect.
 * 
 * @author hadronn
 * 
 */
public class EffSlowTowerAR extends Effect {

	/**
	 * The Effect wrapper for the slow attack rate of tower function.
	 * 
	 * @param name
	 * @param amount
	 * @param duration
	 */
	public EffSlowTowerAR(String name, int amount, int duration) {
		super(name, amount, duration);
	}

	/**
	 * Slow the attack rate of a tower by the effect amount. Use for 'Tank'
	 * enemies to apply to towers.
	 * 
	 * @param target
	 */
	public void function(Tower target) {
		// Get the towers Attack Rate
		double originalAR = target.attackRate();
		// Lower the target's Attack Rate by amount
		if (originalAR - amount() <= 0) {
			target.attackRate(0);
		} else {
			target.attackRate(originalAR - amount());
		}
	}

}
