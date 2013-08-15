package deco2800.arcade.deerforest.models.effects;

import java.util.HashSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public abstract class AbstractEffect {

	/* Effects can be defined as such (must follow these guidelines)
	 * 
	 * typeEffects:
	 * 	Set must either be null (no conditions), or contain a combination of:
	 * 		- "Fire"
	 * 		- "Water"
	 * 		- "Nature"
	 * 		- "Dark"
	 * 		- "Light"
	 * 
	 * effectCategory:
	 * 	Set cannot be null and must contain one (or more) of the following
	 * 		- "Destroy"
	 * 		- "Draw"
	 * 		- "Monster"
	 * 		- "Search"
	 * 		- "Player"
	 * 
	 * effectParams:
	 * 	For each effectCategory have a list of parameters about that effect
	 * 	parameters are (in order of appearance in list):
	 * 
	 * 		"Destroy" category:
	 * 			- Amount of cards
	 * 			- Location of cards
	 * 				0 = Deck
	 * 				1 = Hand
	 * 				2 = Field
	 * 				3 = Graveyard
	 * 			- Player
	 * 				0 = Self
	 * 				1 = Opponent
	 * 				2 = Both (so each player destroys the amount of cards)
	 * 				3 = Either (so amount of cards is destroyed from whoever)
	 * 			- Frequency
	 * 				0 = On activation / Summon
	 * 				1 = once per turn (yours)
	 * 				2 = once per turn (opponents)
	 * 				3 = once per turn (each players turn)
	 * 			- Category of card affected
	 * 				0 = Monsters
	 * 				1 = Spells
	 * 				2 = Both
	 * 			- Type card affects
	 * 				0 = any
	 * 				1 = Fire (if monster) / General (if spell)
	 * 				2 = Nature (if monster) / Field (if spell)
	 * 				3 = Water
	 * 				4 = Dark
	 * 				5 = Light
	 * 
	 * 		"Draw" category:
	 * 			- Amount of cards
	 * 			- Cards to be discarded before
	 * 			- Cards to discarded after
	 * 			- Player
	 * 				0 = Self
	 * 				1 = Opponent
	 * 				2 = Both
	 * 			- Frequency
	 * 				0 = On activation / Summon
	 * 				1 = once per turn (yours)
	 * 				2 = once per turn (opponents)
	 * 				3 = once per turn (each players turn)
	 * 
	 * 		"Monster" category:
	 * 			- Player to affect
	 * 				0 = Self
	 * 				1 = Opponent
	 * 				2 = Both
	 * 			- Stat to affect
	 * 				0 = Attack
	 * 				1 = Health
	 * 				2 = Both
	 * 			- Amount to affect by (can be negative for losing attack / health)
	 * 			- Frequency
	 * 				0 = while on field
	 * 				1 = during turn of activation
	 * 				2 = once per turn (yours)
	 * 				3 = once per turn (opponents)
	 * 				4 = once per turn (each players turn)
	 * 			- Type card affects
	 * 				0 = any
	 * 				1 = Fire
	 * 				2 = Nature
	 * 				3 = Water
	 * 				4 = Dark
	 * 				5 = Light
	 * 
	 * 		"Search" category:
	 * 			- Amount of cards
	 * 			- Location of cards
	 * 				0 = Deck
	 * 				1 = Hand
	 * 				2 = Field
	 * 				3 = Graveyard
	 * 			- Destination of cards
	 * 				0 = Deck
	 * 				1 = Hand
	 * 				2 = Field
	 * 				3 = Graveyard
	 * 			- Frequency
	 * 				0 = On activation / Summon
	 * 				1 = once per turn (yours)
	 * 				2 = once per turn (opponents)
	 * 			- Category of card searched
	 * 				0 = Monsters
	 * 				1 = Spells
	 * 				2 = Both
	 * 			- Min attack (irrelevant if spell searching, still needs to exist)
	 * 			- Max attack (irrelevant if spell searching, still needs to exist)
	 * 			- Min health (irrelevant if spell searching, still needs to exist)
	 * 			- Max health (irrelevant if spell searching, still needs to exist)
	 * 			- Effect type
	 * 				0 = Destroy
	 * 				1 = Draw
	 * 				2 = MonsterChanging
	 * 				3 = Search
	 * 				4 = PlayerChaning
	 * 
	 * 		"Player" category:
	 * 			- Player to affect
	 * 				0 = Self
	 * 				1 = Opponent
	 * 				2 = Both
	 * 			- Stat to affect
	 * 				0 = Lifepoints
	 * 				1 = Shield
	 * 			- Amount to affect by (can be negative for losing lifepoints / shield)
	 * 
	 */
	
	private Set<String> typeEffects;
	private List<String> effectCategories;
	private List<? extends List<Integer>> effectParams;
	
	/**
	 * 
	 * @param typeEffects
	 * @param effectCategory
	 * @param effectParams
	 */
	public AbstractEffect(Set<String> typeEffects, List<String> effectCategories, 
			List<? extends List<Integer>> effectParams) throws IncorrectEffectException {
		
		//Check typeEffects is valid
		if (!checkTypes(typeEffects)) {
			throw new IncorrectEffectException("Bad type effects, set " +
					"contained an invalid type");
		} 
		
		//Check effectCategories is valid
		if (!checkCategories(effectCategories)) {
			throw new IncorrectEffectException("Bad effect Category");
		}
		
		//Check effectParams is valid
		if (!checkEffectParams(effectCategories, effectParams)) {
			throw new IncorrectEffectException("Bad effect Parameters");
		} 
		
		this.typeEffects = typeEffects;
		this.effectCategories = effectCategories;
		this.effectParams = effectParams;
		
	}

	/**
	 * 
	 * @return what type of cards it affects
	 */
	//
	public Set<String> affectsTypes() {
		HashSet<String> setToReturn = new HashSet<String>();
		setToReturn.addAll(this.typeEffects);
		return setToReturn;
	}

	/**
	 * 
	 * @return what category of effect the card has
	 */
	public Set<String> effectCategories() {
		HashSet<String> setToReturn = new HashSet<String>();
		setToReturn.addAll(this.effectCategories);
		return setToReturn;
	}
		
	/**
	 * 
	 * @return the parameters of the effect (different depending on the category)
	 */
	public List<? extends List<Integer>> effectParameter() {
		
		ArrayList<ArrayList<Integer>> paramsToReturn = new ArrayList<ArrayList<Integer>>();
		for (List<Integer> singleList : effectParams) {
			ArrayList<Integer> currentList = new ArrayList<Integer>();
			currentList.addAll(singleList);
			paramsToReturn.add(currentList);
		}
		
		return paramsToReturn;
	}
	
	private boolean checkTypes(Set<String> setToCheck) {
		
		//Define a set of valid types
		Set<String> validTypes = new HashSet<String>();
		validTypes.add("Fire");
		validTypes.add("Nature");
		validTypes.add("Water");
		validTypes.add("Dark");
		validTypes.add("Light");
		
		//Check if given set is subset of valid types
		if (setToCheck == null || validTypes.containsAll(setToCheck)) {
			return true;
		}
		
		return false;
	}
	
	private boolean checkCategories(List<String> setToCheck) {
		
		//Define a set of valid types
		Set<String> validCategories = new HashSet<String>();
		validCategories.add("Destroy");
		validCategories.add("Draw");
		validCategories.add("Monster");
		validCategories.add("Search");
		validCategories.add("Player");
		
		//Check if given set is subset of valid types
		if (setToCheck == null || validCategories.containsAll(setToCheck)) {
			return true;
		}
		
		return false;
	}
	
	private boolean checkEffectParams(List<String> effectCategories, 
			List<? extends List<Integer>> effectParams) {
		
		//Check both lists are same size
		if(effectCategories.size() != effectParams.size()) {
			return false;
		}
		
		//iterate over each category
		for(int i = 0; i < effectCategories.size(); i++) {
			String currentCategory = effectCategories.get(i);
			List<Integer> currentParams = effectParams.get(i);
			
			//test based on currentCategory
			if(currentCategory.equals("Destroy")) {
				if(!checkDestroy(currentParams)) {
					return false;
				}
			} else if(currentCategory.equals("Draw")) {
				if(!checkDraw(currentParams)) {
					return false;
				}
			} else if(currentCategory.equals("Monster")) {
				if(!checkMonster(currentParams)) {
					return false;
				}
			} else if(currentCategory.equals("Search")) {
				if(!checkSearch(currentParams)) {
					return false;
				}
			} else if(currentCategory.equals("Player")) {
				if(!checkPlayer(currentParams)) {
					return false;
				}
			} else {
				return false;
			}
		}
		
		return true;
	}

	private boolean checkDestroy(List<Integer> params) {
		
		try {
			//check amount of cards is valid
			if(params.get(0) < 0) {
				return false;
			}
			//check location of cards is valid
			if(params.get(1) < 0 || params.get(1) > 3) {
				return false;
			}
			//check player affected is valid
			if(params.get(2) < 0 || params.get(2) > 3) {
				return false;
			}
			//check frequency is valid
			if(params.get(3) < 0 || params.get(3) > 3) {
				return false;
			}
			//check category of card affected is valid
			if(params.get(4) < 0 || params.get(4) > 2) {
				return false;
			}
			//check type of card affected is valid
			if(params.get(5) < 0 || params.get(5) > 5) {
				return false;
			}
		} catch(IndexOutOfBoundsException e) {
			return false;
		}
		
		return true;
	}

	private boolean checkDraw(List<Integer> params) {
		
		try {
			//check amount of cards, and discarded cards are valid
			if(params.get(0) < 0 || params.get(1) < 0 || params.get(2) < 0) {
				return false;
			}
			//check player is valid
			if(params.get(3) < 0 || params.get(3) > 2) {
				return false;
			}
			//check freq is valid
			if(params.get(4) < 0 || params.get(4) > 3) {
				return false;
			}
		} catch(IndexOutOfBoundsException e) {
			return false;
		}
		
		return true;
	}

	private boolean checkMonster(List<Integer> params) {
		
		try {
			//check player is valid
			if(params.get(0) < 0 || params.get(0) > 2) {
				return false;
			}
			//check stat is valid
			if(params.get(1) < 0 || params.get(1) > 2) {
				return false;
			}
			//check amount to affect by is valid
			if(params.get(2) < 0) {
				return false;
			}
			//check frequency is valid
			if(params.get(3) < 0 || params.get(3) > 4) {
				return false;
			}
			//check type is valid
			if(params.get(4) < 0 || params.get(4) > 5) {
				return false;
			}
		} catch(IndexOutOfBoundsException e) {
			return false;
		}
		
		return true;
	}

	private boolean checkSearch(List<Integer> params) {
		
		try {
			//check amount of cards is valid
			if(params.get(0) < 0) {
				return false;
			}
			//check check location of cards is valid
			if(params.get(1) < 0 || params.get(1) > 3) {
				return false;
			}
			//check destination of cards is valid
			if(params.get(2) < 0 || params.get(2) > 3) {
				return false;
			}
			//check frequency is valid
			if(params.get(3) < 0 || params.get(3) > 2) {
				return false;
			}
			//check category of card is valid
			if(params.get(4) < 0 || params.get(4) > 2) {
				return false;
			}
			//check min/max attack is valid
			if(params.get(5) < 0 || params.get(6) < params.get(5)) {
				return false;
			}
			//check min/max health is valid
			if(params.get(7) < 0 || params.get(8) < params.get(7)) {
				return false;
			}
			//check effect type is valid
			if(params.get(9) < 0 || params.get(9) > 4) {
				return false;
			}
		} catch(IndexOutOfBoundsException e) {
			return false;
		}
		
		return true;
	}

	private boolean checkPlayer(List<Integer> params) {
		
		try {
			//check player to affect is valid
			if(params.get(0) < 0 || params.get(0) > 2) {
				return false;
			}
			//check stat to affect is valid
			if(params.get(1) < 0 || params.get(1) > 1) {
				return false;
			}
			//check amount to affect by is valid
			if(params.get(2) < 0) {
				return false;
			}
		} catch(IndexOutOfBoundsException e) {
			return false;
		}
		
		return true;
	}

	
}
