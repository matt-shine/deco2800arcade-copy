package deco2800.arcade.deerforest.models.cards;

import deco2800.arcade.deerforest.models.effects.SpellEffect;

/**
 * Implements the general spell class
 */
public class GeneralSpell extends AbstractSpell {

	/**
	 * Initialises the General spell class
	 */
	public GeneralSpell(SpellEffect effect, String cardFilePath) {
		super("General", effect, cardFilePath);
		this.cardFilePath = cardFilePath;
	}

}
