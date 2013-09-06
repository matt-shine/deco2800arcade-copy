package deco2800.arcade.deerforest.models.cards;

import deco2800.arcade.deerforest.models.effects.SpellEffect;

public class GeneralSpell extends AbstractSpell {

	public GeneralSpell(SpellEffect effect, String cardFilePath) {
		super("General", effect, cardFilePath);
		this.cardFilePath = cardFilePath;
		// TODO Auto-generated constructor stub
	}

}
