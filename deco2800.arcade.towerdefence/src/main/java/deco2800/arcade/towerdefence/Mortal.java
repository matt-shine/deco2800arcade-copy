package deco2800.arcade.towerdefence;

/**
 * The interface for mortal objects.
 * Will be the majority of interesting objects.
 * Can die through game mechanics and have curious attributes such as health.
 * @author hadronn
 *
 */
public abstract class Mortal extends GridObject{
	//The maximum health the alien can have.
	private int maxHealth;
	//the current health the alien has.
	private int health;
	//the armour the alien has.
	private int armour;

	
	//Return the current health of the mortal, nonnegative.
	public int health() {
		return health;
	}

	//Return the maximum health of the mortal, nonnegative.
	public int maxHealth() {
		return maxHealth;
	}
	
	//Return the armour of the mortal, nonnegative.
	public int armour() {
		return armour;
	}
	
	//Increase the current health, not beyond maxHealth().	
	public void heal(int amount) {
		health += amount;
		if(health > maxHealth){
			health = maxHealth;
		}
		
	}

	//Decrease the current health, not below 0.
	public void takeDamage(int amount) {
		amount -= armour;
		if (amount <= 0){
			return;
		}
		health -= amount;
		if (health <= 0){
			die();
		}
		
	}

	//Decrease the current health, not below 0. Using penetration calculations.
	//Penetration is a direct armour debuff for that attack alone.
	public void takeDamage(int amount, int penetration) {
		if (penetration >= armour){
			takeDamage(amount);
			return;
		}
		amount -= (armour - penetration);
		if (amount <= 0){
			return;
		}
		health -= amount;
		if (health <= 0){
			die();
		}
		
	}
}
