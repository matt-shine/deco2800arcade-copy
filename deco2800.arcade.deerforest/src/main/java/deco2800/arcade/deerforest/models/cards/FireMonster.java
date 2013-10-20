package deco2800.arcade.deerforest.models.cards;

/**
 * Implements the Fire monster card
 */
public class FireMonster extends AbstractMonster {

	/**
	 * Initialises the Fire monster card
	 */
	public FireMonster(int health, int attack, String cardFilePath) {
		super("Fire", health, attack, cardFilePath);
		this.cardFilePath = cardFilePath;
		// TODO Auto-generated constructor stub
	}
	
	public String toString() {
		return super.toString();
	}
}
