package deco2800.arcade.deerforest.models.cards;

/**
 * Implements the Water monster card
 */
public class WaterMonster extends AbstractMonster {

	/**
	 * Initialises the water monster class
	 */
	public WaterMonster(int health, int attack, String cardFilePath) {
		super("Water", health, attack, cardFilePath);
		this.cardFilePath = cardFilePath;
		// TODO Auto-generated constructor stub
	}
	
	public String toString() {
		return super.toString();
	}
}
