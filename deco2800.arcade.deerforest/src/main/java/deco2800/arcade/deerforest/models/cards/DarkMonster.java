package deco2800.arcade.deerforest.models.cards;

/**
 * Implmentation of the Dark monster card
 */
public class DarkMonster extends AbstractMonster {

	/**
	 * Initialises the the Dark monster card
	 */
	public DarkMonster(int health, int attack, String cardFilePath) {
		super("Dark", health, attack, cardFilePath);
		this.cardFilePath = cardFilePath;
	}
	
	public String toString() {
		return super.toString();
	}

}
