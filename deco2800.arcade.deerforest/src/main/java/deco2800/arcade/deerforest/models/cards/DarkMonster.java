package deco2800.arcade.deerforest.models.cards;

import java.util.List;

import deco2800.arcade.deerforest.models.effects.Attack;

public class DarkMonster extends AbstractMonster {

	public DarkMonster(int health, int attack, String cardFilePath) {
		super("Dark", health, attack, cardFilePath);
		this.cardFilePath = cardFilePath;
	}
	
	public String toString() {
		return super.toString();
	}

}
