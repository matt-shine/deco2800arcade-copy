package deco2800.arcade.deerforest.models.cards;

import deco2800.arcade.deerforest.models.effects.SpellEffect;

public class FieldSpell extends AbstractSpell {

	public FieldSpell(SpellEffect effect, String cardFilePath) {
		super("Field", effect, cardFilePath);
		this.cardFilePath = cardFilePath;
		// TODO Auto-generated constructor stub
	}

}
