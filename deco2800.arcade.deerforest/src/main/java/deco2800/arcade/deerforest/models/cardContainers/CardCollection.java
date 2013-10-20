package deco2800.arcade.deerforest.models.cardContainers;

import java.util.Collection;
import java.util.List;

import deco2800.arcade.deerforest.models.cards.AbstractCard;

public interface CardCollection extends Collection<AbstractCard> {

	//Move card to different section, returns true if successful (move first instance of card)
	public boolean moveCard(AbstractCard card, CardCollection moveLocation);

	//list all cards in collection
	public List<AbstractCard> cards();

	//destroy specific cards, returns cards destroyed
	public CardCollection destroyCards(List<AbstractCard> cardsToDestroy) ;

	//destroy type of card,returns cards destroyed (note type is field/continuous
	// or monster type, any should work for this method
	public CardCollection destroyCardType(String type) ;

	//Destroy all cards, returns cards destroyed
	public CardCollection destroyAllCards() ;

	//Destroy n random cards
	public CardCollection destroyRandom(int number) ;
	
}
