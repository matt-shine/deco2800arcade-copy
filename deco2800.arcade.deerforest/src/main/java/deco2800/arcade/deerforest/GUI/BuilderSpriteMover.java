package deco2800.arcade.deerforest.GUI;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import com.badlogic.gdx.math.Rectangle;

import deco2800.arcade.deerforest.models.cardContainers.CardCollection;
import deco2800.arcade.deerforest.models.cardContainers.CardCollectionList;
import deco2800.arcade.deerforest.models.cards.AbstractCard;

/**
 * Handles the selection of cards for the deck builder inteface.
 */
public class BuilderSpriteMover {
	//TODO Large Changes still to come with database implementation
	
	final static String[] Keys = {"DeckZone", "CardZone", "ZoomZone"};
	
	/**
     * Sets the currentSelection to fit within the given rectangle
     * @precondition currentSelection != null
     * @param r the rectangle to check whether it overlaps a zone
     * @return true if sprite was set to a zone, false if couldn't set to zone or
     * if there was no zone that overlapped enough
     */
	public static boolean setCurrentSelectionToRectangle(Rectangle r) {
		//TODO Fix this
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
    		
    		//reassign the currentSelection to emptyZone in the view
    		/*view.removeSpriteFromArea(currentSelection, currentSelectionArea);*/
    		view.setSpriteToArea(currentSelection, newArea);
    		
    		return true;
    	}
    	
    	return false;
	}
	
	/**
     * returns the AbstractCard based on the given sprite and what area it is in (area based on model)
     * @param sprite the sprite to use
     * @param area the area to search for the sprite
     * @return the card the sprite represents
     */
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
	
	/**
     * Returns a boolean array with information about the type of zone the
     * sprite is in. b[0] is if the card is on the field, b[1] is if the card
     * is a monster card
     * @param s the sprite to check
     * @return a boolean array defining what zone type the sprite is in
     */
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
					for(AbstractCard c : deerForest.deckBuilder.getCardCollection("CardZone")) {
						b[0] = false;
						b[1] = true;
						
					}
				
				} else if(key.contains("ZoomZone")) {
					String filepath = deerForest.view.manager.getAssetFileName(s.getTexture());
					b[0] = false;
					b[1] = false;
				}
				return b;
			}
		}
		return null;
	}
	
	/**
	 * Gets the current selection area
	 * @param deck
	 * @param card
	 * @return String indicating the current selection area
	 */
	public static String getCurrentSelectionArea( boolean deck, boolean card) {
			if(deck) {
				return "DeckZone";
			} else if(card){
				return "CardZone";
			} else {
				return "ZoomZone";
			}
		
		
	}
	
	/**
     * Sets the data of the current selection based on it containing the point
     * x, y.
     * @precondition currentSelection is not null (with nullPointerException otherwise)
     * @param x an x point within current selection
     * @param y a y point within current selection
     */
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
		
		//Get the offset of where the user clicked on the card compared to its actual position
		deckInputProcessor.setOffset(x - currentSelection.getX(), y - currentSelection.getY());
    }
	
	 /**
     * Sets the current selection to be at the zone contained by the point x,y
     * @precondition currentSelection != null
     * @param x an x point within the zone to set the currentSelection to
     * @param y a y point within the zone to set the currentSelection to
     * @return true if successfully set it to a zone, false if couldn't set or
     * their was no zone containing that point
     */
	public static boolean setCurrentSelectionToPoint(int x, int y) {
    	
    	BuilderSprite currentSelection = DeerForestSingletonGetter.getDeerForest().deckInputProcessor.getCurrentSelection();
    	DeckBuilderScreen view = DeerForestSingletonGetter.getDeerForest().deckBuilderView;
    	DeckBuilder game = DeerForestSingletonGetter.getDeerForest().deckBuilder;
    	
    	String currentSelectionArea = currentSelection.getArea();
    	
    	Rectangle emptyZone = null;
    	
    	//TODO change this!
   
    	return false;
    }
	
	
	/**
     * returns the sprite at the point, if one exists and belongs to the
     * player
     * 
     * @param x
     * @param y
     * @return Sprite intersecting the 
     */
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
	
	
}
