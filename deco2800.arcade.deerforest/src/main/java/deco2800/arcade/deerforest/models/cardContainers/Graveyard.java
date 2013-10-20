package deco2800.arcade.deerforest.models.cardContainers;


import deco2800.arcade.deerforest.models.cards.AbstractCard;
import deco2800.arcade.deerforest.models.cards.AbstractMonster;

/**
 * Holds the cards that have been destroyed or defeated
 */
public class Graveyard extends AbstractCardStack {

	/**
	 * Initialize the graveyard class
	 */
	public Graveyard() {
	}
	
	/**
	 * Check if the graveyard only only contains spell cards
	 */
	public boolean onlySpells() {
		for (AbstractCard card: cardList) {
			if (card.getCardType().equals("Monster")) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Check if graveyard only contains monster cards
	 */
	public boolean onlyMonsters() {
		for (AbstractCard card: cardList) {
			if (card.getCardType().equals("Spell")) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Check if graveyard only contains monster cards of specific type
	 */
	public boolean onlyMonsters(String type) {
		for (AbstractCard card: cardList) {
			
			// Return false if the card isn't a monster 
			if (card.getCardType().equals("Spell")) {
				return false;
			} else {
				// Otherwise cast to a monster and check for the type
				AbstractMonster monster = (AbstractMonster)card;
				
				// If the monsters type doesn't match return false
				if (!monster.getType().equals(type)) {
					return false;
				}
			}
		}
		return true;
	}
	
	/** 
	 * Sort the graveyard
	 */
	public void sort() {
		// Not going to happen at this point :'(
	}

}
