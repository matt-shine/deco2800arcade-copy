package deco2800.arcade.deerforest.models.cards;

public abstract class AbstractCard {

	protected String cardFilePath;
	
	//Initialize card, assuming no effect
	public AbstractCard() {
		cardFilePath = null;
	}
	
	public String getPictureFilePath() {
		return cardFilePath;
	}
	
	public String getCardType() {
		if (this instanceof AbstractMonster) {
			return "Monster";
		} else {;
			return "Spell";
		}
	}
	
	
	
}
