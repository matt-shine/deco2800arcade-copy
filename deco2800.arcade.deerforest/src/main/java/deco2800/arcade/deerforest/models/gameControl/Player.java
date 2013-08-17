package deco2800.arcade.deerforest.models.gameControl;

import deco2800.arcade.deerforest.models.cardContainers.*;

public class Player {

	//Have variables for field, deck, graveyard and hand
	private Hand hand;
	private Deck deck;
	private Graveyard graveyard;
	private Field field;
	private int currentLife;
	private int currentShield;
	
	//Initialise the player with deck
	public Player(Deck playerDeck) {
		this.deck = playerDeck;
		this.hand = new Hand();
		this.graveyard = new Graveyard();
		this.field = new Field();
		this.currentLife = 50;
		this.currentShield = 0;
	}

	//draw n cards from deck to hand
	public boolean draw(int no) {
		return false;
	}

	//draw 1 card from deck to hand
	public boolean draw() {
		return false;
	}
	
	//move n cards from location to location
	public boolean moveCards(int n, CardCollection loc1, CardCollection loc2) {
		return false;
	}
	
	public void inflictDamage(int damage) {
		//If shield exists decriment it
		if(currentShield > 0 && currentShield >= damage) {
			currentShield -= damage;
			damage = 0;
		} else if(currentShield > 0 && currentShield < damage) {
			damage -= currentShield;
			currentShield = 0;
		}
		
		//Infict Life point damage
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
