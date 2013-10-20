package deco2800.arcade.deerforest.models.gameControl;

import java.util.ArrayList;
import java.util.List;

import deco2800.arcade.deerforest.models.cardContainers.*;
import deco2800.arcade.deerforest.models.cards.AbstractCard;

/**
 * Defines a player class, owns a deck, field and graveyard CardCollection
 */
public class DeerForestPlayer {

	//Have variables for field, deck, graveyard and hand
	private Hand hand;
	private Deck deck;
	private Graveyard graveyard;
	private Field field;
	private int currentLife;
	private int currentShield;

    /**
     * Initialise the player with deck
     * @param playerDeck the players deck
     */
	public DeerForestPlayer(Deck playerDeck) {
		this.deck = playerDeck;
		this.hand = new Hand();
		this.graveyard = new Graveyard();
		this.field = new Field();
		this.currentLife = 200;
		this.currentShield = 0;
	}

    /**
     * draw n cards from deck to hand
     * @param no the amount of cards to draw
     * @return the list of the drawn cards
     */
	public List<AbstractCard> draw(int no) {
		List<AbstractCard> cardsDrawn = new ArrayList<AbstractCard>();
		while(no > 0) {
			AbstractCard c = draw();
			if(c != null) cardsDrawn.add(c);
			no--;
		}
		return cardsDrawn;
	}

    /**
     * draw 1 card from deck to hand
     * @return the drawn card
     */
	public AbstractCard draw() {
		AbstractCard c = this.deck.draw();
		if(c!=null && addCard(c, this.hand)) return c;
		return null;
	}

    /**
     * move card to location
     * @param card the card to move
     * @param dest the new location of the card
     * @return true if successful, false otherwise
     */
	public boolean addCard(AbstractCard card, CardCollection dest) {
		if(card == null || dest == null) return false;
		if(dest.add(card)) return true;
		return false;
	}

    /**
     * move cards from location to location
     *
     * @param cardsToMove list of all the cards to move
     * @param locSrc where the cards are currently stored
     * @param locDest where the cards will be moved to
     * @return true if successful, false otherwise
     */
	public boolean moveCards(List<AbstractCard> cardsToMove, CardCollection locSrc, CardCollection locDest) {
		for(AbstractCard card: cardsToMove) {
			if(!locSrc.remove(card) || !locDest.add(card)) return false;
		}
		return true;
	}

    /**
     * Inflicts damage to lifepoints
     * @param damage the amount of damage to inflict
     */
	public void inflictDamage(int damage) {
		//Inflict Life point damage
		currentLife -= damage;
		if(currentLife < 0) {
			currentLife = 0;
		}
	}

    /**
     * Getters
     */
	
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
