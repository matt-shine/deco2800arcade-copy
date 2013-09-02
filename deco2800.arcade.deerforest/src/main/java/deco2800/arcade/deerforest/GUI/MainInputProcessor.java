package deco2800.arcade.deerforest.GUI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

import deco2800.arcade.deerforest.models.cardContainers.CardCollection;
import deco2800.arcade.deerforest.models.cardContainers.CardCollectionList;
import deco2800.arcade.deerforest.models.cards.AbstractCard;

//This class functions basically as the controller
public class MainInputProcessor implements InputProcessor {

	private MainGame game;
	private MainGameScreen view;
	private ExtendedSprite currentSelection;
	private Rectangle currentSelectionOriginZone;
	private int currentSelectionPlayer;
	private boolean currentSelectionField;
	private boolean currentSelectionMonster;
	private String currentSelectionArea;
	private float xClickOffset;
	private float yClickOffset;
	private boolean dragged;
	
	//define array of keys for P1 / P2 zones
	final String[] P1Keys = {"P1HandZone", "P1MonsterZone", "P1SpellZone"};
	final String[] P2Keys = {"P2HandZone", "P2MonsterZone", "P2SpellZone"};
	
	public MainInputProcessor(MainGame game, MainGameScreen view) {
		this.game = game;
		this.view = view;
		dragged = false;
	}
	
	@Override
	public boolean keyDown (int keycode) {
		
		//Go to next phase
		if(keycode == Keys.SPACE && game.getPhase() != null) {
			game.nextPhase();
			//Set stuff up at the start phase
			if(game.getPhase().equals("StartPhase")) {
				currentSelection = null;
				view.setHighlightedZones(new ArrayList<Rectangle>());
				game.nextPhase();
			} 
			//draw a card
			if(game.getPhase().equals("DrawPhase")) {
				//TODO DRAW ALL THE CARDS!!!
				doDraw();
			}
			currentSelection = null;
			return true;
		} 
		
		//Change player turns
		if(keycode == Keys.ALT_LEFT) {
			game.changeTurns();
			game.nextPhase();
			doDraw();
			currentSelection = null;
			currentSelection = null;
			view.setHighlightedZones(new ArrayList<Rectangle>());
			return true;
		}
		
		//Print debug stuff
		if(keycode == Keys.ALT_RIGHT) {
			System.out.println("Screen data");
			view.printSpriteMap();
			System.out.println();
			System.out.println("Arena data");
			view.getArena().printZoneInfo();
			System.out.println();
			System.out.println("CurrentSelection: " + currentSelection + " player: " + currentSelectionPlayer 
					+ " monster: " + currentSelectionMonster + " field" + currentSelectionField + " area: " + currentSelectionArea);
			System.out.println();
	    	
	    	//Print out data about hand / deck / field
			System.out.println("Model data");
			CardCollection p1Hand = game.getCardCollection(1, "Hand");
			System.out.println("P1Hand: " + Arrays.toString(p1Hand.toArray()));
			System.out.println();
			
			CardCollection p2Hand = game.getCardCollection(2, "Hand");
			System.out.println("P2Hand: " + Arrays.toString(p2Hand.toArray()));
			System.out.println();
			
			CardCollection p1Field = game.getCardCollection(1, "Field");
			System.out.println("P1Field: " + Arrays.toString(p1Field.toArray()));
			System.out.println();
			
			CardCollection p2Field = game.getCardCollection(2, "Field");
			System.out.println("P2Field: " + Arrays.toString(p2Field.toArray()));
			System.out.println();
			System.out.println();
			
			return true;
		}
		
		if(keycode == Keys.CONTROL_LEFT) {
			System.out.println(SpriteLogic.getCardModelFromSprite(currentSelection, currentSelectionPlayer, currentSelectionArea));
		}
        return false;
    }

	@Override
    public boolean keyUp (int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped (char character) {
        return false;
    }

    @Override
    public boolean touchDown (int x, int y, int pointer, int button) {

    	//Check it was a single click
    	if(button != Buttons.LEFT) return false;
    	
    	//If there is already a current selected card then try to move it to
    	// the clicked space, set currentSelection to null then return
    	if(currentSelection != null) {
    		//completed successfully, so set current to null
    		if(setCurrentSelectionToPoint(x,y)) {
    			//Successfully moved card, update model
    			String oldArea = currentSelectionArea;
    			String newArea = view.getArena().getAreaAtPoint(x, y);
    			List<AbstractCard> cards = new ArrayList<AbstractCard>();
    			AbstractCard c = getCardModelFromSprite(currentSelection, currentSelectionPlayer, oldArea);
    			cards.add(c);
    			game.moveCards(currentSelectionPlayer, cards, oldArea, newArea);
    		}
    		//clear available zones and currentSelection
    		currentSelection = null;
    		view.setHighlightedZones(new ArrayList<Rectangle>());
        	return true;
    	}
    	
    	//Get the current Selection at point if it exists
    	currentSelection = checkIntersection(x, y);
    	
    	//There is a new currentSelection, set its parameters accordingly
    	if(currentSelection != null) {
    		setCurrentSelectionData(x,y);
    		return true;
    		
    	} else {
    		//currentSelection is null, so set the highlighted zones 
    		//to be nothing then return
    		view.setHighlightedZones(new ArrayList<Rectangle>());
    		return true;
    	}
    }

	@Override
    public boolean touchUp (int x, int y, int pointer, int button) {
		
		//Only perform action if card has been dragged and left button was released
		//Note that dragged can only be true if there is a current selection
		if(dragged && button == Buttons.LEFT) {
			
			if(setCurrentSelectionToRectangle(currentSelection.getBoundingRectangle())) {
				//successfully moved card
				//Do stuff with model here
				String oldArea = currentSelectionArea;
    			String newArea = view.getArena().getAreaAtPoint((int)currentSelection.getX()+10, (int)currentSelection.getY()+10);
    			List<AbstractCard> cards = new ArrayList<AbstractCard>();
    			AbstractCard c = getCardModelFromSprite(currentSelection, currentSelectionPlayer, oldArea);
    			cards.add(c);
    			game.moveCards(currentSelectionPlayer, cards, oldArea, newArea);
			} else {
				//card was not moved, set it back to original position
				Rectangle r = view.getArena().emptyZoneAtRectangle(currentSelectionOriginZone, currentSelectionPlayer, currentSelectionField, currentSelectionMonster);
	    		view.getArena().setSpriteToZone(currentSelection, r, currentSelectionPlayer);
	    		view.setHighlightedZones(new ArrayList<Rectangle>());
			}
			//Reset current selection and highlighted zones
			currentSelection = null;
			view.setHighlightedZones(new ArrayList<Rectangle>());
			dragged = false;
		}
		return true;
    }

	@Override
    public boolean touchDragged (int x, int y, int pointer) {
    	//move card to where it was dragged
    	if(currentSelection != null) {
    		currentSelection.setPosition(x - xClickOffset, y - yClickOffset);
    		dragged = true;
    		return true;
    	}
        return false;
    }

    @Override
    public boolean mouseMoved (int x, int y) {
        return false;
    }

    @Override
    public boolean scrolled (int amount) {
        return false;
    }


    private void doDraw() {
    	
    	//Get current hand
    	int player = game.getCurrentPlayer();
    	CardCollection currentHand = game.getCardCollection(player, "Hand");
    	
    	//Check that the hand is not already full
    	if(currentHand.size() >= 6) return;
    	
    	//Draw a card
    	AbstractCard c = game.draw(player);

		//Add card to the hand (in view, already added to player hand in model)
		
		//update currentSelection to be the drawn card
		currentSelection = new ExtendedSprite(view.manager.get(c.getPictureFilePath(), Texture.class));
		//set the current selection data
		currentSelectionField = false;
		currentSelectionMonster = false; //doesn't matter as in hand
		currentSelectionPlayer = player;
		currentSelectionArea = this.getCurrentSelectionArea(currentSelectionPlayer, currentSelectionField, currentSelectionMonster);
		
		//Set to hand rectangle
		Rectangle r = view.getArena().getAvailableZones(player, false, false).get(0);
		setCurrentSelectionToRectangle(r);
	}
    
    /**
     * returns the sprite at the point, if one exists and belongs to the
     * current player
     * 
     * @param x
     * @param y
     * @return Sprite intersecting the 
     */
    private ExtendedSprite checkIntersection(int x, int y) {
    	
    	Map<String,List<ExtendedSprite>> spriteMap = view.getSpriteMap();

    	//check maps according to whose turn it is
    	if(game.getCurrentPlayer() == 1) {
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
    
    private void setCurrentSelectionData(int x, int y) {
    	
		//Get all relevant data about the current selection
		Rectangle r = currentSelection.getBoundingRectangle();
		currentSelectionOriginZone = new Rectangle(r.getX(), r.getY(), r.getWidth(), r.getHeight());
		currentSelectionPlayer = view.getSpritePlayer(currentSelection);
		boolean[] b = getSpriteZoneType(currentSelection);
		currentSelectionField = b[0];
		currentSelectionMonster = b[1];
		currentSelectionArea = getCurrentSelectionArea(currentSelectionPlayer, currentSelectionField, currentSelectionMonster);
		
		//Set the correct zones to highlight based on selection
		setHighlightedZones();
		
		//Get the offset of where the user clicked on the card compared to its actual position
		xClickOffset = x - currentSelection.getX();
		yClickOffset = y - currentSelection.getY();
    }
    
    private boolean setCurrentSelectionToPoint(int x, int y) {
    	
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
    

    private boolean setCurrentSelectionToRectangle(Rectangle r) {
		
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
    
    private void setHighlightedZones() {
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

    private String getCurrentSelectionArea(int player, boolean field, boolean monster) {
    	
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

    //returns the AbstractCard based on the given sprite and what area it is in (area based on model)
	private AbstractCard getCardModelFromSprite(ExtendedSprite sprite, int player, String area) {
		
		CardCollection collection = new CardCollectionList();
		
		if(area.contains("Hand")) collection = game.getCardCollection(player, "Hand");
		else if(area.contains("Deck")) collection = game.getCardCollection(player, "Deck");
		else if(area.contains("Field")) collection = game.getCardCollection(player, "Field");
		else if(area.contains("Graveyard")) collection = game.getCardCollection(player, "Graveyard");
		
		for(AbstractCard card : collection) {
			if(card.getPictureFilePath().equals(view.manager.getAssetFileName(sprite.getTexture()))) {
				return card;
			}
		}
		
		return null;
	}
	
	public boolean[] getSpriteZoneType(ExtendedSprite s) {
		
		boolean[] b = new boolean[2];
		Map<String, List<ExtendedSprite>> spriteMap = view.getSpriteMap();
		
		for(String key : spriteMap.keySet()) {
			if(spriteMap.get(key).contains(s)) {
				if(key.contains("Hand")) {
					//check what type of card the sprite is
					String filepath = view.manager.getAssetFileName(s.getTexture());
					//iterate over selection to find what card model this corresponds to
					for(AbstractCard c : game.getCardCollection(currentSelectionPlayer, "Hand")) {
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
