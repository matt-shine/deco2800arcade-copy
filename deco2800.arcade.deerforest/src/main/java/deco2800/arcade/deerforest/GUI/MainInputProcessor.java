package deco2800.arcade.deerforest.GUI;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Rectangle;

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
	
	public MainInputProcessor(MainGame game, MainGameScreen view) {
		this.game = game;
		this.view = view;
		dragged = false;
	}
	
	@Override
	public boolean keyDown (int keycode) {
		if(keycode == Keys.SPACE) {
			game.changeTurns();
			view.printSpriteMap();
			System.out.println();
			view.getArena().printZoneInfo();
			System.out.println();
			System.out.println("CurrentSelection: " + currentSelection + " player: " + currentSelectionPlayer 
					+ " monster: " + currentSelectionMonster + " field" + currentSelectionField + " area: " + currentSelectionArea);
			System.out.println();
			System.out.println();
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
    			//Successfully moved card
    			//Do stuff with model here
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

    /**
     * returns the sprite at the point, if one exists
     * 
     * @param x
     * @param y
     * @return Sprite intersecting the 
     */
    private ExtendedSprite checkIntersection(int x, int y) {
    	Map<String,List<ExtendedSprite>> spriteMap = view.getSpriteMap();
    	for(String key : spriteMap.keySet()) {
	    	for(ExtendedSprite s : spriteMap.get(key)) {
		    	if(s.containsPoint(x, y)) {
		    		return s;
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
		boolean[] b = view.getSpriteZoneType(currentSelection);
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
    	emptyZone = view.getArena().emptyZoneAtPoint(x, y, currentSelectionPlayer, true, currentSelectionMonster);
    	//if current zone is a hand zone then check for empty zone at that point
    	if(!currentSelectionField && emptyZone == null) {
    		emptyZone = view.getArena().emptyZoneAtPoint(x, y, currentSelectionPlayer, false, currentSelectionMonster);
    	}

    	if(emptyZone != null) {
        	//reassign the currentSelection to the emptyZone in arena
    		view.getArena().removeSprite(currentSelection);
    		String newArea = view.getArena().setSpriteToZone(currentSelection, emptyZone, currentSelectionPlayer);
    		
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
		//get the empty zone at point (only check zones allowed)
    	emptyZone = view.getArena().emptyZoneAtRectangle(r, currentSelectionPlayer, true, currentSelectionMonster);
    	//if current zone is a hand zone then check for empty zone at that point
    	if(!currentSelectionField && emptyZone == null) {
    		emptyZone = view.getArena().emptyZoneAtRectangle(r, currentSelectionPlayer, false, currentSelectionMonster);
    	}

    	if(emptyZone != null) {
        	//reassign the currentSelection to the emptyZone in arena
    		view.getArena().removeSprite(currentSelection);
    		String newArea = view.getArena().setSpriteToZone(currentSelection, emptyZone, currentSelectionPlayer);
    		
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
			if(currentSelectionMonster) {
				availableZones.addAll(view.getArena().getAvailableZones(1, true, true));
			}
			else {
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
			if(currentSelectionMonster) {
				availableZones.addAll(view.getArena().getAvailableZones(2, true, true));
			} else {
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
}
