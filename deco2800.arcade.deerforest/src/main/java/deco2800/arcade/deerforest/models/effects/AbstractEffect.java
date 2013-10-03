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
	 * 				0 = Hand
	 * 				1 = Field
	 * 			- Player
	 * 				0 = Self
	 * 				1 = Opponent
	 * 				2 = Either (so amount of cards is destroyed from whoever)
	 * 			- Category of card affected
	 * 				0 = Monsters
	 * 				1 = Spells
	 * 				2 = Both
 	 * 			- Frequency
	 * 				0 = On activation / Summon
	 * 				1 = once per turn (yours)
	 * 				2 = once per turn (opponents)
	 * 				3 = once per turn (each players turn)
	 * 			- Type card affects
	 * 				0 = any
	 * 				1 = Fire (if monster)  / Field (if spell)
	 * 				2 = Nature (if monster)
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
	 * 			- Type card affects
	 * 				0 = any
	 * 				1 = Fire
	 * 				2 = Nature
	 * 				3 = Water
	 * 				4 = Dark
	 * 				5 = Light
	 * 			- Frequency
	 * 				0 = while on field
	 * 				1 = during turn of activation
	 * 				2 = once per turn (yours)
	 * 				3 = once per turn (opponents)
	 * 				4 = once per turn (each players turn)
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
	 * 			- Category of card searched
	 * 				0 = Monsters
	 * 				1 = Spells
	 * 				2 = Both
	 * 			- Frequency
	 * 				0 = On activation / Summon
	 * 				1 = once per turn (yours)
	 * 				2 = once per turn (opponents)
	 * 			- Min attack (irrelevant if spell searching, still needs to exist)
	 * 				-1 = N/A
	 * 			- Max attack (irrelevant if spell searching, still needs to exist)
	 * 				-1 = N/A
	 * 			- Min health (irrelevant if spell searching, still needs to exist)
	 * 				-1 = N/A
	 * 			- Max health (irrelevant if spell searching, still needs to exist)
	 * 				-1 = N/A
	 * 			- Effect type
	 * 				-1 = N/A
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
	 * Initializes an abstract effect with the types it effects, the type of 
	 * effect it is and how that effect is parameterized
	 * 
	 * @param typeEffects Set of all the monster types that the effect affects
	 * 			null if it can affect any type
	 * 
	 * @param effectCategories List of each effect category that this effect has
	 * 			must be in same order as the parameters list
	 * 
	 * @param effectParams List of list of parameters, with one list for each 
	 * 			effect category
	 * 
	 * @throws IncorrectEffectException if the inputed parameters are invalid
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
	 * @return shallow copy of the typeEffect set
	 */
	//
	public Set<String> affectsTypes() {
		//check null
		if(this.typeEffects == null) {
			return null;
		}
		
		//create a new instance of the set to return
		HashSet<String> setToReturn = new HashSet<String>();
		setToReturn.addAll(this.typeEffects);
		return setToReturn;
	}

	/** 
	 * @return shallow copy of the effectCategories List
	 */
	public List<String> effectCategories() {
		ArrayList<String> setToReturn = new ArrayList<String>();
		setToReturn.addAll(this.effectCategories);
		return setToReturn;
	}
		
	/**
	 * @return copy of the effectParameters List of lists (Integers not copied)
	 */
	public List<? extends List<Integer>> effectParameter() {
		
		ArrayList<ArrayList<Integer>> paramsToReturn = 
				new ArrayList<ArrayList<Integer>>();
		
		for (List<Integer> singleList : effectParams) {
			ArrayList<Integer> currentList = new ArrayList<Integer>();
			currentList.addAll(singleList);
			paramsToReturn.add(currentList);
		}
		
		return paramsToReturn;
	}
	
	/**
	 * Checks whether the given set contains valid types or is null
	 * 
	 * @param setToCheck Set of strings to check validity of
	 * @return true if types are valid
	 */
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
	
	/**
	 * Checks whether the given list contains valid categories
	 * 
	 * @param listToCheck List to check the validity of
	 * @return true if list contains only valid categories
	 */
	private boolean checkCategories(List<String> listToCheck) {
		
		//Check not null
		if(listToCheck == null) {
			return false;
		}
		
		//Define a set of valid types
		Set<String> validCategories = new HashSet<String>();
		validCategories.add("Destroy");
		validCategories.add("Draw");
		validCategories.add("Monster");
		validCategories.add("Search");
		validCategories.add("Player");
		
		//Check if given set is subset of valid types
		if (validCategories.containsAll(listToCheck)) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * Checks each effect parameter list corresponds to valid parameters
	 * 
	 * @param effectCategories List of each effect categories
	 * @param effectParams List of List of effect parameters
	 * @return true if effect parameters are valid for given categories
	 */
	private boolean checkEffectParams(List<String> effectCategories, 
			List<? extends List<Integer>> effectParams) {
		
		//check not null
		if(effectCategories == null || effectParams == null) {
			return false;
		}
		
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

	/**
	 * Checks whether given parameters are valid for a destroy effect
	 * 
	 * @param params list of parameters for effect
	 * @return true if all parameters are valid for a destroy effect
	 */
	private boolean checkDestroy(List<Integer> params) {
		
		try {
			//check amount of cards is valid
			if(params.get(0) < 0) {
                System.out.println("amount problem");
				return false;
			}
			//check location of cards is valid
			if(params.get(1) < 0 || params.get(1) > 1) {
                System.out.println("location problem");
				return false;
			}
			//check player affected is valid
			if(params.get(2) < 0 || params.get(2) > 3) {
                System.out.println("player problem");
				return false;
			}
            //check category of card affected is valid
			if(params.get(3) < 0 || params.get(3) > 2) {
                System.out.println("category problem");
				return false;
			}
            //check frequency is valid
			if(params.get(4) < 0 || params.get(4) > 3) {
                System.out.println("frequency problem");
				return false;
			}
			//check type of card affected is valid
			if(params.get(5) < 0 || params.get(5) > 5) {
                System.out.println("type problem");
				return false;
			}
		} catch(IndexOutOfBoundsException e) {
            System.out.println("index problem");
			return false;
		}
		
		return true;
	}

	/**
	 * Checks whether given parameters are valid for a draw effect
	 * 
	 * @param params list of parameters for effect
	 * @return true if all parameters are valid for a draw effect
	 */
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

	/**
	 * Checks whether given parameters are valid for a monster effect
	 * 
	 * @param params list of parameters for effect
	 * @return true if all parameters are valid for a monster effect
	 */
	private boolean checkMonster(List<Integer> params) {
		
		try {
            //check no monsters
            if(params.get(0) < 0) {
                return false;
            }
			//check player is valid
			if(params.get(1) < 0 || params.get(1) > 2) {
				return false;
			}
			//check stat is valid
			if(params.get(2) < 0 || params.get(2) > 2) {
				return false;
			}
			//check frequency is valid
			if(params.get(4) < 0 || params.get(4) > 3) {
				return false;
			}
			//check type is valid
			if(params.get(5) < 0 || params.get(5) > 5) {
				return false;
			}
		} catch(IndexOutOfBoundsException e) {
			return false;
		}
		
		return true;
	}

	/**
	 * Checks whether given parameters are valid for a search effect
	 * 
	 * @param params list of parameters for effect
	 * @return true if all parameters are valid for a search effect
	 */
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
			if(params.get(5) < -1 || (params.get(6) < params.get(5) && params.get(6) != -1)) {
				return false;
			}
			//check min/max health is valid
			if(params.get(7) < -1 || (params.get(8) < params.get(7) && params.get(8) != -1)) {
				return false;
			}
			//check effect type is valid
			if(params.get(9) < -1 || params.get(9) > 4) {
				return false;
			}
		} catch(IndexOutOfBoundsException e) {
			return false;
		}
		
		return true;
	}

	/**
	 * Checks whether given parameters are valid for a player effect
	 * 
	 * @param params list of parameters for effect
	 * @return true if all parameters are valid for a player effect
	 */
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
            //check frequency is valid
            if(params.get(4) < 0 || params.get(4) > 4) {
                return false;
            }
		} catch(IndexOutOfBoundsException e) {
			return false;
		}
		
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((effectCategories == null) ? 0 : effectCategories.hashCode());
		result = prime * result
				+ ((effectParams == null) ? 0 : effectParams.hashCode());
		result = prime * result
				+ ((typeEffects == null) ? 0 : typeEffects.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractEffect other = (AbstractEffect) obj;
		if (effectCategories == null) {
			if (other.effectCategories != null)
				return false;
		} else if (!effectCategories.equals(other.effectCategories))
			return false;
		if (effectParams == null) {
			if (other.effectParams != null)
				return false;
		} else if (!effectParams.equals(other.effectParams))
			return false;
		if (typeEffects == null) {
			if (other.typeEffects != null)
				return false;
		} else if (!typeEffects.equals(other.typeEffects))
			return false;
		return true;
	}
}
