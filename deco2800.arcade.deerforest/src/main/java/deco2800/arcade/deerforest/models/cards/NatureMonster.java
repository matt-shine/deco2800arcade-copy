package deco2800.arcade.deerforest.models.cards;

/**
 * Implements the Nature monster card
 */
public class NatureMonster extends AbstractMonster {

	/**
	 * Initiliases the Nature monster class
	 */
	public NatureMonster(int health, int attack, String cardFilePath) {
		super("Nature", health, attack, cardFilePath);
		this.cardFilePath = cardFilePath;
		// TODO Auto-generated constructor stub
	}
	
	public String toString() {
		return super.toString();
	}
}
