package deco2800.arcade.towerdefence.model.creationclasses;

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
 * tower causing large amounts of slowing, reduce it's attack speed to
 * compensate. We don't want high attack speeds and permanent effects that can
 * reduce a statistic to 0 before an enemy can get a decent way through the
 * defense. It's a balance consideration.
 * 
 * If you must use a duration, make sure that timer can clear up and not stack
 * too many times. I have chosen to for the most part not use duration for the
 * time being but have left the field in for later use.
 * 
 * Nevermind, have introduced a stacking and stack limit on GridObject to
 * prevent performance issues.
 * 
 */
public class Effect {
	// Fields
	// the name of the effect
	private final String name;
	// the amount of the effect as a float.
	private final double amount;
	// the duration of the effect in milliseconds.
	// use this sparingly in actual effects as it could cause performance
	// issues.
	private final int duration;

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
	// Create methods here for the variety of statuses that an effect inflicts

	/**
	 * Slow the movement speed of an enemy by the effect amount.
	 * 
	 * @param target
	 */
	public void slowEnemyMS(Enemy target) {
		// Get the Enemy Movement Speed
		double originalSpeed = target.speed();
		// Set the Enemy Movement Speed lower by amount
		if (originalSpeed - amount <= 0) {
			target.speed(0);
		} else {
			target.speed(originalSpeed - amount);
		}
	}

	/**
	 * Slow the attack rate of an enemy by the effect amount.
	 */
	public void slowEnemyAR(Enemy target) {
		// Get the towers Attack Rate
		double originalAR = target.attackRate();
		// Lower the target's Attack Rate by amount
		if (originalAR - amount <= 0) {
			target.attackRate(0);
		} else {
			target.attackRate(originalAR - amount);
		}
	}

	/**
	 * Slow the attack rate of a tower by the effect amount. Use for 'Tank'
	 * enemies to apply to towers.
	 * 
	 * @param target
	 */
	public void slowTowerAR(Tower target) {
		// Get the towers Attack Rate
		double originalAR = target.attackRate();
		// Lower the target's Attack Rate by amount
		if (originalAR - amount <= 0) {
			target.attackRate(0);
		} else {
			target.attackRate(originalAR - amount);
		}
	}

	/**
	 * Slow the move speed and attack rate of an enemy by the effect amount. Use
	 * for Frost towers.
	 * 
	 * @param target
	 */
	public void frostEnemy(Enemy target) {
		slowEnemyMS(target);
		slowEnemyAR(target);
	}

	/**
	 * Reduce the armour of the enemy by the effect amount. Use for Piercing
	 * towers.
	 * 
	 * @param target
	 */
	public void pierceEnemy(Enemy target) {
		// Get the armour of the enemy
		int originalArmour = target.armour();
		// Lower the target's armour by amount
		if (originalArmour - amount <= 0) {
			target.armour(0);
		} else {
			target.armour((int) (originalArmour - amount));
		}
	}

	/**
	 * Reduce the armour of the tower by the effect amount. Use for Corrosive
	 * enemies.
	 * 
	 * @param target
	 */
	public void corrodeTower(Tower target) {
		// Get the armour of the tower
		int originalArmour = target.armour();
		// Lower the enemy's armour by amount
		if (originalArmour - amount <= 0) {
			target.armour(0);
		} else {
			target.armour((int) (originalArmour - amount));
		}
	}

	/**
	 * Set a damage over time on a tower.
	 */
	public void dotTower(Tower target, int duration) {
		for (int i=0; i<duration; i++){
			int currentHealth = target.health();
			target.health((int) (currentHealth - amount));
			//wait 1000ms
		}
	}

}
