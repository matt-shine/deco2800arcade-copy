package deco2800.arcade.deerforest.GUI;

import java.util.List;
import java.util.Map;

import deco2800.arcade.deerforest.models.cardContainers.CardCollection;
import deco2800.arcade.deerforest.models.cardContainers.CardCollectionList;
import deco2800.arcade.deerforest.models.cards.AbstractCard;

public class SpriteLogic {

	//define array of keys for P1 / P2 zones
	final static String[] P1Keys = {"P1HandZone", "P1MonsterZone", "P1SpellZone"};
	final static String[] P2Keys = {"P2HandZone", "P2MonsterZone", "P2SpellZone"};
	
    /**
     * returns the sprite at the point, if one exists and belongs to the
     * current player
     * 
     * @param x
     * @param y
     * @return Sprite intersecting the 
     */
    public static ExtendedSprite checkIntersection(int x, int y) {
    	
		DeerForest deerForest = DeerForestSingletonGetter.getDeerForest();

    	Map<String,List<ExtendedSprite>> spriteMap = deerForest.view.getSpriteMap();

    	//check maps according to whose turn it is
    	if(deerForest.mainGame.getCurrentPlayer() == 1) {
    		//Check each zone in sprite map P1Keys
    		for(String key : P1Keys) {
    			for(ExtendedSprite s : spriteMap.get(key)) {
    		    	if(s.containsPoint(x, y)) {
    		    		return s;
    		    	}
    		    }
    		}
    		
    	} else {
    		//Check each zone in sprite map P2Keys
    		for(String key : P2Keys) {
    			for(ExtendedSprite s : spriteMap.get(key)) {
    		    	if(s.containsPoint(x, y)) {
    		    		return s;
    		    	}
    		    }
    		}
    	}

    	return null;
    }
    
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
	
	public static boolean[] getSpriteZoneType(ExtendedSprite s) {
		
		boolean[] b = new boolean[2];
		DeerForest deerForest = DeerForestSingletonGetter.getDeerForest();
		Map<String, List<ExtendedSprite>> spriteMap = deerForest.view.getSpriteMap();
		
		for(String key : spriteMap.keySet()) {
			if(spriteMap.get(key).contains(s)) {
				if(key.contains("Hand")) {
					//check what type of card the sprite is
					String filepath = deerForest.view.manager.getAssetFileName(s.getTexture());
					//iterate over selection to find what card model this corresponds to
					for(AbstractCard c : deerForest.mainGame.getCardCollection(deerForest.getCurrentPlayer(), "Hand")) {
						if(c.getPictureFilePath().equals(filepath)) {
							if(c.getCardType().equals("Monster")) {
								b[0] = false;
								b[1] = true;
							} else {
								b[0] = false;
								b[1] = false;
							}
						}
					}
				} else if(key.contains("Monster")) {
					b[0] = true;
					b[1] = true;
				} else {
					b[0] = true;
					b[1] = false;
				}
				return b;
			}
		}
		return null;
	}
}
