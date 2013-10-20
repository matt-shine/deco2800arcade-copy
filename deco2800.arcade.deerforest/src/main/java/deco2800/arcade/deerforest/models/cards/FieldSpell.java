package deco2800.arcade.deerforest.models.cards;

import deco2800.arcade.deerforest.models.effects.SpellEffect;

/**
 * Implements the field spell card
 */
public class FieldSpell extends AbstractSpell {

	/**
	 * Initialises the field spell class
	 */
	public FieldSpell(SpellEffect effect, String cardFilePath) {
		super("Field", effect, cardFilePath);
		this.cardFilePath = cardFilePath;
	}

}
