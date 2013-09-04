package deco2800.arcade.deerforest.models.cardContainers;

import java.util.ArrayList;

import deco2800.arcade.deerforest.models.cards.AbstractCard;

public class Hand extends AbstractCardCollection {
	private int limit;
	private ArrayList<AbstractCard> hand;
	
	//Initialize the hand
	public Hand() {
		limit = 6;
		hand = new ArrayList<AbstractCard>();
	}
	
	//Change card limit
	public void changeLimit(int newLimit) {
		limit = newLimit;
	}
		
	//Check if over limit
	public boolean overLimit() {
		if(hand.size() > limit) {
			return true;
		}
		return false;
	}
}
