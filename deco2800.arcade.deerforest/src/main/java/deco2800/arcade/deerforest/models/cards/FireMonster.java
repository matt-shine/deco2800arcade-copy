package deco2800.arcade.deerforest.models.cards;

public class FireMonster extends AbstractMonster {

	public FireMonster(int health, int attack, String cardFilePath) {
		super("Fire", health, attack, cardFilePath);
		this.cardFilePath = cardFilePath;
		// TODO Auto-generated constructor stub
	}
	
	public String toString() {
		return super.toString();
	}
}
