package deco2800.arcade.deerforest.models.cards;

import java.util.List;

import deco2800.arcade.deerforest.models.effects.Attack;

public class FireMonster extends AbstractMonster {

	public FireMonster(int health, List<Attack> attacks, String cardFilePath) {
		super("Fire", health, attacks, cardFilePath);
		this.cardFilePath = cardFilePath;
		// TODO Auto-generated constructor stub
	}
	
	public String toString() {
		return super.toString();
	}
}
