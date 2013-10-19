package deco2800.arcade.deerforest.models.gameControl;

import java.util.List;

import deco2800.arcade.deerforest.models.cardContainers.CardCollection;
import deco2800.arcade.deerforest.models.cardContainers.Deck;
import deco2800.arcade.deerforest.models.cards.AbstractCard;


public class DeckSystem {
	
	private Deck deck;
	private Deck cards;
	
	/**
	 * Initializes DeckSystem
	 *  
	 * @param playerDeck
	 * @param cards
	 */
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
	
	
	/**
	 * Allows outer classes to get the deck
	 * 
	 * @return the deck
	 */
	public Deck getDeck(){
		return this.deck;
	}
	
	/**
	 * Allows outer classes to get the cards
	 * 
	 * @return the cards
	 */
	public Deck getCards(){
		return this.cards;
	}
	
	/**
	 * Move cards from one location to another
	 * 
	 * @param cardsToMove the collection of cards you would like to be moved
	 * @param locSrc The source of the cards
	 * @param locDest the destination of the cards
	 * @return true when completed 
	 */
	public boolean moveCards(List<AbstractCard> cardsToMove, CardCollection locSrc, CardCollection locDest) {
		for(AbstractCard card: cardsToMove) {
			if(!locSrc.remove(card) || !locDest.add(card)) return false;
		}
		return true;
	}
	
	
	
}
