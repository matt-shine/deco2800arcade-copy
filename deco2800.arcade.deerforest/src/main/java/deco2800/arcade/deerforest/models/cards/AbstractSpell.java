package deco2800.arcade.deerforest.models.cards;

import deco2800.arcade.deerforest.models.effects.SpellEffect;

public abstract class AbstractSpell extends AbstractCard {

	private String spellType;
	
	//Initialize card with its effect
	public AbstractSpell(String type, SpellEffect effect, String cardFilePath) {
		this.cardFilePath = cardFilePath;
		this.spellType = type;
	}
	
	public String getSpellType() {
		return this.spellType;
	}
}
