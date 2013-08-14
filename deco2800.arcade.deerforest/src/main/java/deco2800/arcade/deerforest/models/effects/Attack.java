package deco2800.arcade.deerforest.models.effects;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Attack extends AbstractEffect {

	private int damage;
	private String typeOfAttack;
	
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
		
		if(!validCategories.contains(typeOfAttack)) {
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
