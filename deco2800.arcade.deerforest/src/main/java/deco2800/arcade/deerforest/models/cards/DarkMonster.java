package deco2800.arcade.deerforest.models.cards;

public class DarkMonster extends AbstractMonster {

	public DarkMonster(int health, int attack, String cardFilePath) {
		super("Dark", health, attack, cardFilePath);
		this.cardFilePath = cardFilePath;
	}
	
	public String toString() {
		return super.toString();
	}

}
