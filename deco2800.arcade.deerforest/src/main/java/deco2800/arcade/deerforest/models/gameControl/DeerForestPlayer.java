package deco2800.arcade.deerforest.models.gameControl;

import java.util.ArrayList;
import java.util.List;

import deco2800.arcade.deerforest.models.cardContainers.*;
import deco2800.arcade.deerforest.models.cards.AbstractCard;

public class DeerForestPlayer {

	//Have variables for field, deck, graveyard and hand
	private Hand hand;
	private Deck deck;
	private Graveyard graveyard;
	private Field field;
	private int currentLife;
	private int currentShield;
	
	//Initialise the player with deck
	public DeerForestPlayer(Deck playerDeck) {
		this.deck = playerDeck;
		this.hand = new Hand();
		this.graveyard = new Graveyard();
		this.field = new Field();
		this.currentLife = 50;
		this.currentShield = 0;
	}

	//draw n cards from deck to hand
	public List<AbstractCard> draw(int no) {
		List<AbstractCard> cardsDrawn = new ArrayList<AbstractCard>();
		while(no > 0) {
			AbstractCard c = draw();
			if(c != null) cardsDrawn.add(c);
			no--;
		}
		return cardsDrawn;
	}

	//draw 1 card from deck to hand
	public AbstractCard draw() {
		AbstractCard c = this.deck.draw();
		if(c!=null && addCard(c, this.hand)) return c;
		return null;
	}
	
	//move card to location
	public boolean addCard(AbstractCard card, CardCollection dest) {
		if(card == null || dest == null) return false;
		if(dest.add(card)) return true;
		return false;
	}
	
	//move cards from location to location
	public boolean moveCards(List<AbstractCard> cardsToMove, CardCollection locSrc, CardCollection locDest) {
		for(AbstractCard card: cardsToMove) {
			if(!locSrc.remove(card) || !locDest.add(card)) return false;
		}
		return true;
	}
	
	public void inflictDamage(int damage) {
		//If shield exists decrement it
		if(currentShield > 0 && currentShield >= damage) {
			currentShield -= damage;
			damage = 0;
		} else if(currentShield > 0 && currentShield < damage) {
			damage -= currentShield;
			currentShield = 0;
		}
		
		//Inflict Life point damage
		currentLife -= damage;
		if(currentLife < 0) {
			currentLife = 0;
		}
	}
	
	public Field getField() {
		return field;
	}
	
	public Hand getHand() {
		return hand;
	}
	
	public Deck getDeck() {
		return deck;
	}
	
	public int getLifePoints() {
		return currentLife;
	}
	
	public int getShield() {
		return currentShield;
	}
	
	public Graveyard getGraveyard() {
		return graveyard;
	}
}
