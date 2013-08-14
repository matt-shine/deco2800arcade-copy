package deco2800.arcade.deerforest.models.effects;

import java.util.List;
import java.util.Set;

public class SpellEffect extends AbstractEffect {

	public SpellEffect(Set<String> typesEffect, List<String> effectCategories, 
			List<List<Integer>> effectParams) throws IncorrectEffectException {
		
		super(typesEffect, effectCategories, effectParams);
	}

}
