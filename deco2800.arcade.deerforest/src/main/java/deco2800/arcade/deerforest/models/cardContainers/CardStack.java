package deco2800.arcade.deerforest.models.cardContainers;

import deco2800.arcade.deerforest.models.cards.AbstractCard;

/**
 * The CardStack interface extends CardCollection and add methods to count, 
 * shuffle and search cards.
 */
public interface CardStack extends CardCollection {

	/**
	 * Returns the number of cards remaining in the CardStack
	 */
	public int count();

	/**
	 * Shuffles the card in place.
	 */
	public void shuffle() ;

	/** 
	 * Searches the CardStack for a specific card and returns all the cards
	 * that were found
	 */
	public CardCollection searchCard(AbstractCard card) ;
	
	/**
	 * Searches for cards using the monster type, attack and health. Null 
	 * parameter means disregard that search field
	 */
	public CardCollection searchMonster(String type, int minHealth, 
			int maxHealth, int minAttack, int MaxAttack) ;

	/**
	 * Searches for cards using the monster type. Null parameter means 
	 * disregard that search field
	 */
	public CardCollection searchMonster(String type) ;

	/**
	 * Search for cards using of a specific type with a minimum attack and
	 * health. Null parameter means disregard that search field
	 */
	public CardCollection searchMonster(String type, int minAttack, int minHealth) ;

	/**
	 * Search for spells, null parameter means disregard that search field
	 * @return
	 */
	public CardCollection searchSpell() ;

	/**
	 * Searches for spells of a particular type. Null parameter means disregard 
	 * that search field
	 */
	public CardCollection searchSpell(String type) ;
	
}
