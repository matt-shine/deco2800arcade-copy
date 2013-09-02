package deco2800.arcade.deerforest.models.cardContainers;


import deco2800.arcade.deerforest.models.cards.AbstractCard;
import deco2800.arcade.deerforest.models.cards.AbstractMonster;

public class Graveyard extends AbstractCardStack {

	//Consider having a variable called "isSorted", this will allow searching
	//be faster as you can check only a section of the card list
	//Alternatively, have different holders (array, Linked list, etc) for each card type
	
	//Initialize graveyard
	public Graveyard() {
	}
	
	//check if grave only has spell cards
	public boolean onlySpells() {
		for (AbstractCard card: cardList) {
			if (card.getCardType().equals("Monster")) {
				return false;
			}
		}
		return true;
	}
	
	//check if grave only has monsters
	public boolean onlyMonsters() {
		for (AbstractCard card: cardList) {
			if (card.getCardType().equals("Spell")) {
				return false;
			}
		}
		return true;
	}
	
	//check if grave only has monsters of specific type
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
	
	//Sort graveyard by cards
	public void sort() {
		// Not going to happen
	}

}
