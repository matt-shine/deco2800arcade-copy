package deco2800.arcade.towerdefence.model.effects;

import deco2800.arcade.towerdefence.model.creationclasses.Enemy;

/**
 * An Effect.
 * 
 * @author hadronn
 * 
 */
public class EffSlowEnemyAR extends Effect {

	/**
	 * The Effect wrapper for the slow attack rate of enemy function.
	 * 
	 * @param name
	 * @param amount
	 * @param duration
	 */
	public EffSlowEnemyAR(String name, int amount, int duration) {
		super(name, amount, duration);
	}

	/**
	 * Slow the attack rate of an enemy by the effect amount.
	 * 
	 * @param target
	 */
	public void function(Enemy target) {
		// Get the towers Attack Rate
		double originalAR = target.attackRate();
		// Lower the target's Attack Rate by amount
		if (originalAR - amount <= 0) {
			target.attackRate(0);
		} else {
			target.attackRate(originalAR - amount);
		}
	}
}
