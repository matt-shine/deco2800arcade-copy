package deco2800.arcade.towerdefence.model.effects;

import java.util.concurrent.TimeUnit;

import deco2800.arcade.towerdefence.model.creationclasses.Tower;

/**
 * An Effect.
 * @author hadronn
 *
 */
public class EffDotTower extends Effect {
	
	/**
	 * The Effect wrapper for the damage tower over time function.
	 * @param name
	 * @param amount
	 * @param duration
	 */
	public EffDotTower(String name, int amount, int duration) {
		super(name, amount, duration);
	}
	
	/**
	 * Set a damage over time on a tower. Duration is truncated using floor.
	 * This will only stack up to the stacking limit.
	 */
	public void function(Tower target, int duration) {
		// Get the number of effects stacked
		int stacks = target.effectStacks();
		if (stacks == target.effectStackingLimit()) {
			// do nothing;
			return;
		}
		// Start the effect
		// increment the effect stack
		target.effectStacks(stacks + 1);
		// loop over it for duration truncated seconds
		for (int i = 0; i < Math.floor(duration); i++) {
			int currentHealth = target.health();
			target.health((int) (currentHealth - amount));
			// wait 1000ms
			try {
				TimeUnit.MILLISECONDS.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		// status finished
		// decrement the effect stack
		if (target.effectStacks() != 0) {
			target.effectStacks(stacks - 1);
		} else {
			target.effectStacks(0);
		}
	}
}
