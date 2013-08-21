package deco2800.arcade.deerforest.models.cards;

import java.util.List;

import deco2800.arcade.deerforest.models.effects.Attack;

public class NatureMonster extends AbstractMonster {

	public NatureMonster(int health, List<Attack> attacks) {
		super("Nature", health, attacks);
		// TODO Auto-generated constructor stub
	}
	
	public String toString() {
		return super.toString();
	}
}
