package deco2800.arcade.towerdefence.model.effects;

import deco2800.arcade.towerdefence.model.creationclasses.Enemy;

/**
 * An Effect.
 * 
 * @author hadronn
 * 
 */
public class EffFrostEnemy extends Effect {

	/**
	 * The Effect wrapper for the frost enemy function.
	 * 
	 * @param name
	 * @param amount
	 * @param duration
	 */
	public EffFrostEnemy(String name, int amount, int duration) {
		super(name, amount, duration);
	}

	/**
	 * Slow the move speed and attack rate of an enemy by the effect amount. Use
	 * for Frost towers.
	 * 
	 * @param target
	 */
	public void function(Enemy target) {
		Effect SlowMS = new EffSlowEnemyMS("Frosty SlowEnemyMS", (int) amount,
				duration);
		Effect SlowAR = new EffSlowEnemyAR("Frosty SlowEnemyAR", (int) amount,
				duration);
		SlowMS.function(target);
		SlowAR.function(target);
	}
}
