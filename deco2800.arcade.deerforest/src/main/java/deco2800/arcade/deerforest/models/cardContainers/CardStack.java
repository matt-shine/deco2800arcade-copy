package deco2800.arcade.deerforest.models.cardContainers;

import deco2800.arcade.deerforest.models.cards.AbstractCard;

public interface CardStack extends CardCollection {

	//number of cards left
	public int count();

	//shuffle
	public void shuffle() ;

	//search
	public CardCollection searchCard(AbstractCard card) ;
	
	//search, null parameter means disregard that search field
	public CardCollection searchMonster(String type, int minHealth, int maxHealth, int minAttack, int MaxAttack) ;

	//search, null parameter means disregard that search field
	public CardCollection searchMonster(String type ) ;

	//search, null parameter means disregard that search field
	public CardCollection searchMonster(String type, int minAttack, int minHealth) ;

	//search, null parameter means disregard that search field
	public CardCollection searchSpell() ;

	//search, null parameter means disregard that search field
	public CardCollection searchSpell(String type) ;
	
}
