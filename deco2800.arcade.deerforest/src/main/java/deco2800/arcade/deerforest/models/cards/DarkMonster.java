package deco2800.arcade.deerforest.models.cards;

import java.util.List;

import deco2800.arcade.deerforest.models.effects.Attack;

public class DarkMonster extends AbstractMonster {

	public DarkMonster(int health, List<Attack> attacks, String cardFilePath) {
		super("Dark", health, attacks, cardFilePath);
		this.cardFilePath = cardFilePath;
	}
	
	public String toString() {
		return super.toString();
	}

}
