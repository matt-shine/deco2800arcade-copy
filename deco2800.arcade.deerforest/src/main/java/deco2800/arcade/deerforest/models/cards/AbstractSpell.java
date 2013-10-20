package deco2800.arcade.deerforest.models.cards;

import deco2800.arcade.deerforest.models.effects.SpellEffect;

/**
 * Provides an abstraction for the spell card
 */
public abstract class AbstractSpell extends AbstractCard {

	private String spellType;
    private SpellEffect effect;
	
	/**
	 * Initialises the card with the given effects
	 */
	public AbstractSpell(String type, SpellEffect effect, String cardFilePath) {
		this.cardFilePath = cardFilePath;
		this.spellType = type;
        this.effect = effect;
	}
	
	/**
	 * Returns the type of the spell
	 */
	public String getSpellType() {
		return this.spellType;
	}

	/**
	 * Returns the spell effect of the card
	 */
    public SpellEffect getSpellEffect() {
        return this.effect;
    }
}
