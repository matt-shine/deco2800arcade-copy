package deco2800.arcade.towerdefence.model.effects;

import deco2800.arcade.towerdefence.model.creationclasses.Enemy;

/**
 * An Effect.
 * 
 * @author hadronn
 * 
 */
public class EffCryoEnemy extends Effect {

	/**
	 * The Effect wrapper for the Cryo enemy function.
	 * 
	 * @param name
	 * @param amount
	 * @param duration
	 */
	public EffCryoEnemy(String name, int amount, int duration) {
		super(name, amount, duration);
	}

	/**
	 * Slow the move speed and attack rate of an enemy by the effect amount. Use
	 * for Cryo towers.
	 * 
	 * @param target
	 */
	public void function(Enemy target) {
		Effect slowMS = new EffSlowEnemyMS("Cryo SlowEnemyMS", (int) amount(),
				duration());
		Effect slowAR = new EffSlowEnemyAR("Cryo SlowEnemyAR", (int) amount(),
				duration());
		slowMS.function(target);
		slowAR.function(target);
	}
}
