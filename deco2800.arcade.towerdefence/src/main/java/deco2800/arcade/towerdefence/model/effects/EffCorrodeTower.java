package deco2800.arcade.towerdefence.model.effects;

import deco2800.arcade.towerdefence.model.creationclasses.Tower;

/**
 * An Effect.
 * 
 * @author hadronn
 * 
 */
public class EffCorrodeTower extends Effect {

	/**
	 * The Effect wrapper for the corrode tower function.
	 * 
	 * @param name
	 * @param amount
	 * @param duration
	 */
	public EffCorrodeTower(String name, int amount, int duration) {
		super(name, amount, duration);
	}

	/**
	 * Reduce the armour of the tower by the effect amount. Use for Corrosive
	 * enemies.
	 * 
	 * @param target
	 */
	public void function(Tower target) {
		// Get the armour of the tower
		int originalArmour = target.armour();
		// Lower the enemy's armour by amount
		if (originalArmour - amount() <= 0) {
			target.armour(0);
		} else {
			target.armour((int) (originalArmour - amount()));
		}
	}

}
