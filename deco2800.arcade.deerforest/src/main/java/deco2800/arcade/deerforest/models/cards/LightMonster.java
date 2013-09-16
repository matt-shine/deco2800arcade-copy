package deco2800.arcade.deerforest.models.cards;

public class LightMonster extends AbstractMonster {

	public LightMonster(int health, int attack, String cardFilePath) {
		super("Light", health, attack, cardFilePath);
		this.cardFilePath = cardFilePath;
		// TODO Auto-generated constructor stub
	}
	
	public String toString() {
		return super.toString();
	}
}
