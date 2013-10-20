package deco2800.arcade.towerdefence.model.effects;

import deco2800.arcade.towerdefence.model.creationclasses.Enemy;

/**
 * An Effect.
 * 
 * @author hadronn
 * 
 */
public class EffSlowEnemyMS extends Effect {

	/**
	 * The Effect wrapper for the slow movement speed of enemy function.
	 * 
	 * @param name
	 * @param amount
	 * @param duration
	 */
	public EffSlowEnemyMS(String name, int amount, int duration) {
		super(name, amount, duration);
	}

	/**
	 * Slow the movement speed of an enemy by the effect amount.
	 * 
	 * @param target
	 */
	public void function(Enemy target) {
		// Get the Enemy Movement Speed
		double originalSpeed = target.speed();
		// Set the Enemy Movement Speed lower by amount
		if (originalSpeed - amount <= 0) {
			target.speed(0);
		} else {
			target.speed(originalSpeed - amount);
		}
	}
}
