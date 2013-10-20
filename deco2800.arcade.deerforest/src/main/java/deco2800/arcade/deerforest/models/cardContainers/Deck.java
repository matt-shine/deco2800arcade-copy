package deco2800.arcade.deerforest.models.cardContainers;

import java.util.List;

import deco2800.arcade.deerforest.GUI.DeerForestSingletonGetter;
import deco2800.arcade.deerforest.models.cards.AbstractCard;

/**
 * The deck class holds a group of Cards and provides the ability to draw from
 * the deck one card at a time
 */
public class Deck extends AbstractCardStack {

	/**
	 *  Initialise the deck with list of cards
	 */
	public Deck(List<AbstractCard> cards) {
		
		cardList.addAll(cards);
	}

	/**
	 * Draw a card from deck, returning a card or null if not possible
	 */
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
