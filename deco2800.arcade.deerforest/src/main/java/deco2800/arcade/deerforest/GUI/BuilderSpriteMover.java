package deco2800.arcade.deerforest.GUI;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import com.badlogic.gdx.math.Rectangle;

import deco2800.arcade.deerforest.models.cardContainers.CardCollection;
import deco2800.arcade.deerforest.models.cardContainers.CardCollectionList;
import deco2800.arcade.deerforest.models.cards.AbstractCard;

public class BuilderSpriteMover {
	
	final static String[] Keys = {"DeckZone", "CardZone", "ZoomZone"};
	
	public static boolean setCurrentSelectionToRectangle(Rectangle r) {
		
    	BuilderSprite currentSelection = DeerForest.deckInputProcessor.getCurrentSelection();
    	DeckBuilderScreen view = DeerForest.deckBuilderView;
    	DeckBuilder game = DeerForest.deckBuilder;
    	
    	Rectangle emptyZone = null;
    	String selectionArea = currentSelection.getArea();
		//get the empty zone at rectangle (only check zones allowed)
    	//Hand can only go to field if main phase and no summoned (if monster)
    	
    	emptyZone = view.getArena().emptyZoneAtRectangle(r, selectionArea);
    	
    	if(emptyZone != null) {
        	//reassign the currentSelection to the emptyZone in arena
    		view.getArena().removeSprite(currentSelection);
    		String newArea = view.getArena().setSpriteToZone(currentSelection, emptyZone);/*
    		
    		//if moved from hand to field set summoned to be true
    		if(currentSelectionPlayer == 1) {
        		if(newArea.equals("P1MonsterZone") && currentSelectionArea.equals("P1HandZone")) {
        			game.setSummoned(true);
        		}
    		} else {
        		if(newArea.equals("P2MonsterZone") && currentSelectionArea.equals("P2HandZone")) {
        			game.setSummoned(true);
        		}
    		}*/
    		
    		//reassign the currentSelection to emptyZone in the view
    		/*view.removeSpriteFromArea(currentSelection, currentSelectionArea);*/
    		view.setSpriteToArea(currentSelection, newArea);
    		
    		return true;
    	}
    	
    	return false;
	}
	
	public static AbstractCard getCardModelFromSprite(BuilderSprite sprite, String area) {
		
		CardCollection collection = new CardCollectionList();
		DeerForest deerForest = DeerForestSingletonGetter.getDeerForest();
		if(sprite == null || area == null) return null;
		
		if(area.contains("CardZone")) collection = deerForest.deckBuilder.getCardCollection("CardZone");
		else if(area.contains("DeckZone")) collection = deerForest.deckBuilder.getCardCollection("DeckZone");
		else if(area.contains("ZoomZone")) collection = deerForest.deckBuilder.getCardCollection("ZoomZone");
		
		for(AbstractCard card : collection) {
			if(card.getPictureFilePath().equals(deerForest.deckBuilderView.manager.getAssetFileName(sprite.getTexture()))) {
				return card;
			}
		}
		
		return null;
	}
	
	public static boolean[] getSpriteZoneType(BuilderSprite s) {
		
		boolean[] b = new boolean[2];
		DeerForest deerForest = DeerForestSingletonGetter.getDeerForest();
		Map<String, Set<BuilderSprite>> spriteMap = deerForest.deckBuilderView.getSpriteMap();
		
		for(String key : spriteMap.keySet()) {
			if(spriteMap.get(key).contains(s)) {
				if(key.contains("DeckZone")) {
					//check what type of card the sprite is
					String filepath = deerForest.view.manager.getAssetFileName(s.getTexture());
					//iterate over selection to find what card model this corresponds to
					for(AbstractCard c : deerForest.deckBuilder.getCardCollection("DeckZone")) {
						b[0] = true;
						b[1] = false;
						
					}
				} else if(key.contains("CardZone")) {
					String filepath = deerForest.view.manager.getAssetFileName(s.getTexture());
					b[0] = false;
					b[1] = true;
				}
				return b;
			}
		}
		return null;
	}
	
	public static String getCurrentSelectionArea( boolean deck, boolean card) {
    	
			if(deck) {
				return "DeckZone";
			} else {
				return "CardZone";
			}
		
		
	}
	
	public static void setCurrentSelectionData(int x, int y) {
    	
    	DeckBuilderScreen view = DeerForestSingletonGetter.getDeerForest().deckBuilderView;
    	BuilderSprite currentSelection = DeerForestSingletonGetter.getDeerForest().deckInputProcessor.getCurrentSelection();
    	DeckBuilderInputProcessor deckInputProcessor = DeerForestSingletonGetter.getDeerForest().deckInputProcessor;
    	
		//Get all relevant data about the current selection
		Rectangle r = currentSelection.getBoundingRectangle();
		currentSelection.setOriginZone(new Rectangle(r.getX(), r.getY(), r.getWidth(), r.getHeight()));
		boolean[] b = BuilderSpriteMover.getSpriteZoneType(currentSelection);
		currentSelection.setDeck(b[0]);
		currentSelection.setCard(b[1]);
		currentSelection.setArea(BuilderSpriteMover.getCurrentSelectionArea(currentSelection.isDeck(), currentSelection.isCard()));
		
		//Set the correct zones to highlight based on selection
		
		//Get the offset of where the user clicked on the card compared to its actual position
		deckInputProcessor.setOffset(x - currentSelection.getX(), y - currentSelection.getY());
    }
	
	public static boolean setCurrentSelectionToPoint(int x, int y) {
    	
    	BuilderSprite currentSelection = DeerForestSingletonGetter.getDeerForest().deckInputProcessor.getCurrentSelection();
    	DeckBuilderScreen view = DeerForestSingletonGetter.getDeerForest().deckBuilderView;
    	DeckBuilder game = DeerForestSingletonGetter.getDeerForest().deckBuilder;
    	
    	boolean currentSelectionDeck = currentSelection.isDeck();
    	boolean currentSelectionCard = currentSelection.isCard();
    	String currentSelectionArea = currentSelection.getArea();
    	
    	Rectangle emptyZone = null;
		//get the empty zone at point (only check zones allowed) 
    	//Hand can only go to field if main phase and no summoned (if monster)
    	/*if(!currentSelectionField && currentSelectionMonster) {
    		//Monster in hand
        	emptyZone = view.getArena().emptyZoneAtPoint(x, y, true, true);
    	} else if(!currentSelectionField && !currentSelectionMonster) {
    		//Spell Card in hand
        	emptyZone = view.getArena().emptyZoneAtPoint(x, y, true, false);
    	} else if(currentSelectionField) {
    		//On field
        	emptyZone = view.getArena().emptyZoneAtPoint(x, y, true, currentSelectionMonster);
    	}
    	//if current zone is a hand zone then check for empty zone at that point
    	if(!currentSelectionField && emptyZone == null) {
    		emptyZone = view.getArena().emptyZoneAtPoint(x, y, false, currentSelectionMonster);
    	}*/

    	if(emptyZone != null) {
            setSelectionHelper(view, game, currentSelection, emptyZone, currentSelectionArea);
    		return true;
    	}
    	return false;
    }
	
	public static BuilderSprite checkIntersection(int x, int y) {
	    	
			DeerForest deerForest = DeerForestSingletonGetter.getDeerForest();

	    	Map<String,Set<BuilderSprite>> spriteMap = deerForest.deckBuilderView.getSpriteMap();
	    	
	    	DeerForest.logger.info("Up to keys");
	    	
	    	for(String key : Keys) {
	    		DeerForest.logger.info("Key is: " + key);
	    		for(BuilderSprite s : spriteMap.get(key)) {
	    			if(s.containsPoint(x, y)) {
	    				return s;
	    			}
	    		}
	    	}
	    	return null;
	 }
	
	private static void setSelectionHelper(DeckBuilderScreen view, DeckBuilder game, BuilderSprite currentSelection,
            Rectangle emptyZone, String currentSelectionArea) {

        //reassign the currentSelection to the emptyZone in arena
        view.getArena().removeSprite(currentSelection);
        String newArea = view.getArena().setSpriteToZone(currentSelection, emptyZone);

        //reassign the currentSelection to emptyZone in the view
        view.removeSpriteFromArea(currentSelection, currentSelectionArea);
        view.setSpriteToArea(currentSelection, newArea);

    }
	
	
}
