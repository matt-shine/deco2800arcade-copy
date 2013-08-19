package deco2800.arcade.deerforest.models.cards;

import java.util.List;

import deco2800.arcade.deerforest.models.effects.Attack;

public class WaterMonster extends AbstractMonster {

	public WaterMonster(int health, List<Attack> attacks) {
		super("Water", health, attacks);
		// TODO Auto-generated constructor stub
	}
	
	public String toString() {
		return super.toString();
	}
}
