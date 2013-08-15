package deco2800.arcade.deerforest.models.effects;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Attack extends AbstractEffect {

	private int damage;
	private String typeOfAttack;
	
	/**
	 * Constructs an Attack, throwing an error if inputed data is incorrect
	 * 
	 * @param damage amount of damage that the attack does
	 * 
	 * @param typeOfAttack String representing the attack type
	 * 
	 * @param typeEffects Set of all the monster types that the effect affects
	 * 			null if it can affect any type
	 * 
	 * @param effectCategory List of each effect category that this effect has
	 * 			must be in same order as the parameters list
	 * 
	 * @param effectParams List of list of parameters, with one list for each 
	 * 			effect category
	 * 
	 * @throws IncorrectEffectException if the inputed parameters are invalid
	 */
	public Attack(int damage, String typeOfAttack, Set<String> typeEffects, 
			List<String> effectCategories, List<List<Integer>> effectParams) 
					throws IncorrectEffectException {
		
		super(typeEffects, effectCategories, effectParams);
		if(damage < 0) {
			throw new IncorrectEffectException("Damage is negative");
		}
		this.damage = damage;
		
		//Define a set of valid types
		Set<String> validCategories = new HashSet<String>();
		validCategories.add("Destroy");
		validCategories.add("Draw");
		validCategories.add("Monster");
		validCategories.add("Search");
		validCategories.add("Player");
		
		if(typeOfAttack == null || !validCategories.contains(typeOfAttack)) {
			throw new IncorrectEffectException("Type of attack is invalid");
		}
		
		this.typeOfAttack = typeOfAttack;
	}
	
	public int getDamage() {
		return this.damage;
	}
	
	public String getAttackType() {
		return this.typeOfAttack;
	}

}
