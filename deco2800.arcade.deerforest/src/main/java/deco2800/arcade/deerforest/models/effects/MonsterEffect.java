package deco2800.arcade.deerforest.models.effects;

import java.util.List;
import java.util.Set;

public class MonsterEffect extends AbstractEffect {

	public MonsterEffect(Set<String> typeEffects, List<String> effectCategories,
			List<List<Integer>> effectParams) throws IncorrectEffectException {
		
		super(typeEffects, effectCategories, effectParams);
	}

}
