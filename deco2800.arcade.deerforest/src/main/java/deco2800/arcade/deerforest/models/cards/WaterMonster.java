package deco2800.arcade.deerforest.models.cards;

public class WaterMonster extends AbstractMonster {

	public WaterMonster(int health, int attack, String cardFilePath) {
		super("Water", health, attack, cardFilePath);
		this.cardFilePath = cardFilePath;
		// TODO Auto-generated constructor stub
	}
	
	public String toString() {
		return super.toString();
	}
}
