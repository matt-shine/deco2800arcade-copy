package deco2800.arcade.deerforest.GUI;

import deco2800.arcade.deerforest.models.cardContainers.CardCollection;
import deco2800.arcade.deerforest.models.cardContainers.CardCollectionList;
import deco2800.arcade.deerforest.models.cards.AbstractCard;

public class SpriteLogic {

	//returns the AbstractCard based on the given sprite and what area it is in (area based on model) 
	public static AbstractCard getCardModelFromSprite(ExtendedSprite sprite, int player, String area) {
		
		CardCollection collection = new CardCollectionList();
		DeerForest deerForest = DeerForestSingletonGetter.getDeerForest();
		if(sprite == null || area == null) return null;
		
		if(area.contains("Hand")) collection = deerForest.mainGame.getCardCollection(player, "Hand");
		else if(area.contains("Deck")) collection = deerForest.mainGame.getCardCollection(player, "Deck");
		else if(area.contains("Field")) collection = deerForest.mainGame.getCardCollection(player, "Field");
		else if(area.contains("Graveyard")) collection = deerForest.mainGame.getCardCollection(player, "Graveyard");
		
		for(AbstractCard card : collection) {
			if(card.getPictureFilePath().equals(deerForest.view.manager.getAssetFileName(sprite.getTexture()))) {
				return card;
			}
		}
		
		return null;
	}
}
