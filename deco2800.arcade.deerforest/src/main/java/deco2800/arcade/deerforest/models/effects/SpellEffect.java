package deco2800.arcade.deerforest.models.effects;

import java.util.List;
import java.util.Set;

public class SpellEffect extends AbstractEffect {

	/**
	 * Constructs a monster effect, throwing an error if inputed data is incorrect
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
	public SpellEffect(Set<String> typesEffect, List<String> effectCategories, 
			List<List<Integer>> effectParams) throws IncorrectEffectException {
		
		super(typesEffect, effectCategories, effectParams);
	}

}
