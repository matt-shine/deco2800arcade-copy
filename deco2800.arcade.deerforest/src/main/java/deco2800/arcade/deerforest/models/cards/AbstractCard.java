package deco2800.arcade.deerforest.models.cards;

/**
 * Provides an abstraction for the cards
 */
public abstract class AbstractCard {

	protected String cardFilePath;
	
	/** 
	 * Initialises a card, assuming no effect
	 */
	public AbstractCard() {
		cardFilePath = null;
	}
	
	/**
	 * Returns the picture file path of the card
	 */
	public String getPictureFilePath() {
		return cardFilePath;
	}
	
	/**
	 * Returns whether the card is a monster card or a spell card
	 */
	public String getCardType() {
		if (this instanceof AbstractMonster) {
			return "Monster";
		} else {;
			return "Spell";
		}
	}
	
}
