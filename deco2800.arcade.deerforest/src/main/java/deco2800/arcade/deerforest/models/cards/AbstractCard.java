package deco2800.arcade.deerforest.models.cards;

public abstract class AbstractCard {

	//Initialize card, assuming no effect
	public AbstractCard() {
		
	}
	
	public String getPictureFilePath() {
		return null;
	}
	
	public String getCardType() {
		if (this instanceof AbstractMonster) {
			return "monster";
		} else if (this instanceof AbstractSpell) {;
			return "spell";
		} else {
			return "field";
		}
	}
	
}
