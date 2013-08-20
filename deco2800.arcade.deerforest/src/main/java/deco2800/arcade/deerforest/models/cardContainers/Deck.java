package deco2800.arcade.deerforest.models.cardContainers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import deco2800.arcade.deerforest.models.cards.AbstractCard;
import deco2800.arcade.deerforest.models.effects.AbstractEffect;

public class Deck extends AbstractCardStack {
	
	private List<AbstractCard> cardList;

	// Initialise the deck with list of cards (gotten from user deck database)
	public Deck(List<AbstractCard> cards) {
		
		// Add all the cards to a new list
		cardList = new ArrayList<AbstractCard>();
		cardList.addAll(cards);
	}

	//Draw a card from deck, null if not possible
	public AbstractCard draw() {
		
		// Return the first card is the deck isn't empty
		if (!cardList.isEmpty()) {
			return cardList.get(0);
		} else {
			// Otherwise return null
			return null;
		}
	}

}
