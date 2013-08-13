package deco2800.arcade.deerforest.models.effects;

import java.util.List;

public abstract class AbstractEffect {

	public AbstractEffect(List<String> typeEffects, String effectCategory, 
			List<Integer> effectParams) {
		
	}
	
	//Define what type of card it affects
	public List<String> affectsTypes() {
		return null;
	}

	//Returns what category of effect the card has
	public String effectCategory() {
		return null;
	}
		
	//Returns the parameters of the effect (different depending on the category)
	public List<?> effectParameter() {
		return null;
	}
	
}
