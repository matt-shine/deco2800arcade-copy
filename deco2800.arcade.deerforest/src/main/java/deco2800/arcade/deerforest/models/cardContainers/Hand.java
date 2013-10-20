package deco2800.arcade.deerforest.models.cardContainers;

import java.util.ArrayList;

import deco2800.arcade.deerforest.models.cards.AbstractCard;

/**
 * Holds the cards in the hand for a particular player
 */
public class Hand extends AbstractCardCollection {
	private int limit;
	private ArrayList<AbstractCard> hand;
	
	/**
	 * Initialises the Hand class.
	 */
	public Hand() {
		limit = 6;
		hand = new ArrayList<AbstractCard>();
	}
	
	/**
	 * Change the card limit
	 */
	public void changeLimit(int newLimit) {
		limit = newLimit;
	}
		
	/**
	 * Check if the cards in the hand exceed the limit
	 */
	public boolean overLimit() {
		if(hand.size() > limit) {
			return true;
		}
		return false;
	}
}
