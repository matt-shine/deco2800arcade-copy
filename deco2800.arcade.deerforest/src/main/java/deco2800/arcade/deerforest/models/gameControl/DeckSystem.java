package deco2800.arcade.deerforest.models.gameControl;

import java.util.ArrayList;
import java.util.List;

import deco2800.arcade.deerforest.models.cardContainers.CardCollection;
import deco2800.arcade.deerforest.models.cardContainers.Deck;
import deco2800.arcade.deerforest.models.cards.AbstractCard;


public class DeckSystem {
	
	//TODO implement get functions
	public Deck deck;
	public Deck cards;
	
	public DeckSystem(Deck playerDeck, Deck cards) {
		this.deck = playerDeck;
		this.cards = cards;
	}

    /**
     * Returns the card collection of the specified area
     * @param area the key for the area to return
     * @return a CardCollection of the area
     */
	public CardCollection getCardCollection(String area) {
		
		if(area.contains("CardZone")) {
			return cards;
		} else if(area.contains("DeckZone")) {
			return deck;
		} 
		
		return null;
	}

	public boolean moveCards(List<AbstractCard> cardsToMove, CardCollection locSrc, CardCollection locDest) {
		for(AbstractCard card: cardsToMove) {
			if(!locSrc.remove(card) || !locDest.add(card)) return false;
		}
		return true;
	}
	
	
	
}
