package deco2800.arcade.towerdefence.model.effects;

import deco2800.arcade.towerdefence.model.creationclasses.Enemy;

/**
 * An Effect.
 * 
 * @author hadronn
 * 
 */
public class EffPierceEnemy extends Effect {

	/**
	 * The Effect wrapper for the pierce enemy function.
	 * 
	 * @param name
	 * @param amount
	 * @param duration
	 */
	public EffPierceEnemy(String name, int amount, int duration) {
		super(name, amount, duration);
	}

	/**
	 * Reduce the armour of the enemy by the effect amount. Use for Piercing
	 * towers.
	 * 
	 * @param target
	 */
	public void function(Enemy target) {
		// Get the armour of the enemy
		int originalArmour = target.armour();
		// Lower the target's armour by amount
		if (originalArmour - amount() <= 0) {
			target.armour(0);
		} else {
			target.armour((int) (originalArmour - amount()));
		}
	}
}
