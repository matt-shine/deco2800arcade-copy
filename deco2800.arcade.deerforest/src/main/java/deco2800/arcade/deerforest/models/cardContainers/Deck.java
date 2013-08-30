package deco2800.arcade.deerforest.models.cardContainers;

import java.util.List;

import deco2800.arcade.deerforest.models.cards.AbstractCard;

public class Deck extends AbstractCardStack {

	// Initialise the deck with list of cards (gotten from user deck database)
	public Deck(List<AbstractCard> cards) {
		
		cardList.addAll(cards);
	}

	//Draw a card from deck, null if not possible
	public AbstractCard draw() {
		
		// Return the first card is the deck isn't empty
		if (!cardList.isEmpty()) {
			AbstractCard card = cardList.get(0);
			remove(card);
			return card;
			
		} else {
			// Otherwise return null
			return null;
		}
	}

}
