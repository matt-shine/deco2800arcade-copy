package deco2800.arcade.deerforest.models.cards;

import java.util.List;

import deco2800.arcade.deerforest.models.effects.Attack;

public class WaterMonster extends AbstractMonster {

	public WaterMonster(int health, List<Attack> attacks, String cardFilePath) {
		super("Water", health, attacks, cardFilePath);
		this.cardFilePath = cardFilePath;
		// TODO Auto-generated constructor stub
	}
	
	public String toString() {
		return super.toString();
	}
}
