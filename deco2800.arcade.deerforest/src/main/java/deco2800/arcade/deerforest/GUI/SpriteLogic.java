package deco2800.arcade.deerforest.GUI;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.math.Rectangle;

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
	
	
    public static String getCurrentSelectionArea(int player, boolean field, boolean monster) {
    	
		if(player == 1) {
			if(field) {
				if(monster) {
					return "P1MonsterZone";
				} else {
					return "P1SpellZone";
				}
			} else {
				return "P1HandZone";
			}
		} else {
			if(field) {
				if(monster) {
					return "P2MonsterZone";
				} else {
					return "P2SpellZone";
				}
			} else {
				return "P2HandZone";
			}
		}
	}
    
    public static void setCurrentSelectionData(int x, int y) {
    	
    	MainGameScreen view = DeerForestSingletonGetter.getDeerForest().view;
    	ExtendedSprite currentSelection = DeerForestSingletonGetter.getDeerForest().inputProcessor.getCurrentSelection();
    	MainInputProcessor inputProcessor = DeerForestSingletonGetter.getDeerForest().inputProcessor;
    	
		//Get all relevant data about the current selection
		Rectangle r = currentSelection.getBoundingRectangle();
		currentSelection.setOriginZone(new Rectangle(r.getX(), r.getY(), r.getWidth(), r.getHeight()));
		currentSelection.setPlayer(view.getSpritePlayer(currentSelection));
		boolean[] b = SpriteLogic.getSpriteZoneType(currentSelection);
		currentSelection.setField(b[0]);
		currentSelection.setMonster(b[1]);
		currentSelection.setArea(SpriteLogic.getCurrentSelectionArea(currentSelection.getPlayer(), currentSelection.isField(), currentSelection.isMonster()));
		
		//Set the correct zones to highlight based on selection
		SpriteLogic.setHighlightedZones();
		
		//Get the offset of where the user clicked on the card compared to its actual position
		inputProcessor.setOffset(x - currentSelection.getX(), y - currentSelection.getY());
    }
    
    public static void setHighlightedZones() {
    	
    	ExtendedSprite currentSelection = DeerForestSingletonGetter.getDeerForest().inputProcessor.getCurrentSelection();
    	MainGameScreen view = DeerForestSingletonGetter.getDeerForest().view;
    	MainGame game = DeerForestSingletonGetter.getDeerForest().mainGame;
    	
    	boolean currentSelectionMonster = currentSelection.isMonster();
    	
		//find out where this sprite is stored
		Map<String, List<ExtendedSprite>> sprites = view.getSpriteMap();
		String spriteArea = "";
		for(String key: sprites.keySet()) {
			if(sprites.get(key).contains(currentSelection)) {
				spriteArea = key;
			}
		}
		
		//Show all the available zones the card can go to
		if(spriteArea.equals("P1HandZone")) {
			//Card is in hand, find out if spell / monster
			List<Rectangle> availableZones = new ArrayList<Rectangle>();
			//Add monster / spell zones if in main phase and not summoned (if monster)
			if(currentSelectionMonster && !game.getSummoned() && game.getPhase().equals("MainPhase")) {
				availableZones.addAll(view.getArena().getAvailableZones(1, true, true));
			}
			else if(!currentSelectionMonster && game.getPhase().equals("MainPhase")){
				availableZones.addAll(view.getArena().getAvailableZones(1, true, false));
			}
			//add all the hand zones (as they can rearrange their hand)
			availableZones.addAll(view.getArena().getAvailableZones(1, false, true));
			view.setHighlightedZones(availableZones);
			
		} else if(spriteArea.equals("P1SpellZone")) {
			//Card is in P1SpellZone, thus can only be rearranged in hand
			view.setHighlightedZones(view.getArena().getAvailableZones(1, true, false));
			
		} else if(spriteArea.equals("P1MonsterZone")) {
			//Card in monsterZone, can only rearrange
			view.setHighlightedZones(view.getArena().getAvailableZones(1, true, true));
			
		} else if(spriteArea.equals("P2HandZone")) {
			//card is in p2 hand, find out if spell / monster
			List<Rectangle> availableZones = new ArrayList<Rectangle>();
			//Add monster / spell zones if in main phase and not summoned (if monster)
			if(currentSelectionMonster && !game.getSummoned() && game.getPhase().equals("MainPhase")) {
				availableZones.addAll(view.getArena().getAvailableZones(2, true, true));
			} else if(!currentSelectionMonster && game.getPhase().equals("MainPhase")){
				availableZones.addAll(view.getArena().getAvailableZones(2, true, false));
			}
			//add all the hnad zones (as they can rearrange)
			availableZones.addAll(view.getArena().getAvailableZones(2, false, true));
			view.setHighlightedZones(availableZones);
			
		} else if(spriteArea.equals("P2MonsterZone")) {
			//In monster zone, can only be rearranged in place
			view.setHighlightedZones(view.getArena().getAvailableZones(2, true, true));
			
		} else if(spriteArea.equals("P2SpellZone")) {
			//in spell zone, can only be rearranged
			view.setHighlightedZones(view.getArena().getAvailableZones(2, true, false));
			
		}
    }
    
    public static boolean setCurrentSelectionToPoint(int x, int y) {
    	
    	ExtendedSprite currentSelection = DeerForestSingletonGetter.getDeerForest().inputProcessor.getCurrentSelection();
    	MainGameScreen view = DeerForestSingletonGetter.getDeerForest().view;
    	MainGame game = DeerForestSingletonGetter.getDeerForest().mainGame;
    	
    	boolean currentSelectionField = currentSelection.isField();
    	boolean currentSelectionMonster = currentSelection.isMonster();
    	int currentSelectionPlayer = currentSelection.getPlayer();
    	String currentSelectionArea = currentSelection.getArea();
    	
    	Rectangle emptyZone = null;
		//get the empty zone at point (only check zones allowed) 
    	//Hand can only go to field if main phase and no summoned (if monster)
    	if(!currentSelectionField && !game.getSummoned() && currentSelectionMonster && game.getPhase().equals("MainPhase")) {
    		//Monster in hand
        	emptyZone = view.getArena().emptyZoneAtPoint(x, y, currentSelectionPlayer, true, true);
    	} else if(!currentSelectionField && !currentSelectionMonster && game.getPhase().equals("MainPhase")) {
    		//Spell Card in hand
        	emptyZone = view.getArena().emptyZoneAtPoint(x, y, currentSelectionPlayer, true, false);
    	} else if(currentSelectionField) {
    		//On field
        	emptyZone = view.getArena().emptyZoneAtPoint(x, y, currentSelectionPlayer, true, currentSelectionMonster);
    	}
    	//if current zone is a hand zone then check for empty zone at that point
    	if(!currentSelectionField && emptyZone == null) {
    		emptyZone = view.getArena().emptyZoneAtPoint(x, y, currentSelectionPlayer, false, currentSelectionMonster);
    	}

    	if(emptyZone != null) {
        	//reassign the currentSelection to the emptyZone in arena
    		view.getArena().removeSprite(currentSelection);
    		String newArea = view.getArena().setSpriteToZone(currentSelection, emptyZone, currentSelectionPlayer);
    		
    		//if moved from hand to field set summoned to be true
    		if(currentSelectionPlayer == 1) {
        		if(newArea.equals("P1MonsterZone") && currentSelectionArea.equals("P1HandZone")) {
        			game.setSummoned(true);
        		}
    		} else {
        		if(newArea.equals("P2MonsterZone") && currentSelectionArea.equals("P2HandZone")) {
        			game.setSummoned(true);
        		}
    		}
    		
    		//reassign the currentSelection to emptyZone in the view
    		view.removeSpriteFromArea(currentSelection, currentSelectionArea);
    		view.setSpriteToArea(currentSelection, newArea);
    		
    		view.setHighlightedZones(new ArrayList<Rectangle>());
    		return true;
    	}
    	return false;
    }
    
    public static boolean setCurrentSelectionToRectangle(Rectangle r) {
		
    	ExtendedSprite currentSelection = DeerForestSingletonGetter.getDeerForest().inputProcessor.getCurrentSelection();
    	MainGameScreen view = DeerForestSingletonGetter.getDeerForest().view;
    	MainGame game = DeerForestSingletonGetter.getDeerForest().mainGame;
    	
    	boolean currentSelectionField = currentSelection.isField();
    	boolean currentSelectionMonster = currentSelection.isMonster();
    	int currentSelectionPlayer = currentSelection.getPlayer();
    	String currentSelectionArea = currentSelection.getArea();
    	
    	Rectangle emptyZone = null;
		//get the empty zone at rectangle (only check zones allowed)
    	//Hand can only go to field if main phase and no summoned (if monster)
    	if(!currentSelectionField && !game.getSummoned() && currentSelectionMonster && game.getPhase().equals("MainPhase")) {
        	emptyZone = view.getArena().emptyZoneAtRectangle(r, currentSelectionPlayer, true, true);
    	} else if(!currentSelectionField && !currentSelectionMonster && game.getPhase().equals("MainPhase")) {
        	emptyZone = view.getArena().emptyZoneAtRectangle(r, currentSelectionPlayer, true, false);
    	} else if(currentSelectionField) {
        	emptyZone = view.getArena().emptyZoneAtRectangle(r, currentSelectionPlayer, true, currentSelectionMonster);
    	}
    	//if current zone is a hand zone then check for empty zone at that point
    	if(!currentSelectionField && emptyZone == null) {
    		emptyZone = view.getArena().emptyZoneAtRectangle(r, currentSelectionPlayer, false, currentSelectionMonster);
    	}

    	if(emptyZone != null) {
        	//reassign the currentSelection to the emptyZone in arena
    		view.getArena().removeSprite(currentSelection);
    		String newArea = view.getArena().setSpriteToZone(currentSelection, emptyZone, currentSelectionPlayer);
    		
    		//if moved from hand to field set summoned to be true
    		if(currentSelectionPlayer == 1) {
        		if(newArea.equals("P1MonsterZone") && currentSelectionArea.equals("P1HandZone")) {
        			game.setSummoned(true);
        		}
    		} else {
        		if(newArea.equals("P2MonsterZone") && currentSelectionArea.equals("P2HandZone")) {
        			game.setSummoned(true);
        		}
    		}
    		
    		//reassign the currentSelection to emptyZone in the view
    		view.removeSpriteFromArea(currentSelection, currentSelectionArea);
    		view.setSpriteToArea(currentSelection, newArea);
    		
    		view.setHighlightedZones(new ArrayList<Rectangle>());
    		return true;
    	}
    	
    	return false;
	}
}
