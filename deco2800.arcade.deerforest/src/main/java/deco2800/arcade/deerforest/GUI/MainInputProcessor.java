package deco2800.arcade.deerforest.GUI;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    	Rectangle emptyZone = null;
    	
    	if(currentSelection != null) {
    		//get the empty zone at point (only check zones allowed)
        	emptyZone = view.getArena().emptyZoneAtPoint(x, y, currentSelectionPlayer, true, currentSelectionMonster);
        	//if current zone is a hand zone then check for empty zone at that point
        	if(!currentSelectionField && emptyZone == null) {
        		emptyZone = view.getArena().emptyZoneAtPoint(x, y, currentSelectionPlayer, false, currentSelectionMonster);
        	}
       		//print the empty zone
        	System.out.println("touch down empty zone is: " + emptyZone);
        	System.out.println("currentPlayer: " + currentSelectionPlayer + " Field: " + currentSelectionField +  " monster: " + currentSelectionMonster);
        	
        	if(emptyZone != null) {
            	//reassign the currentSelection to the emptyZone in arena
        		view.getArena().removeSprite(currentSelection);
        		String newArea = view.getArena().setSpriteToZone(currentSelection, emptyZone, currentSelectionPlayer);
        		
        		//reassign the currentSelection to emptyZone in the view
        		System.out.println("NewArea was: " + newArea);
        		boolean successRemove = view.removeSpriteFromArea(currentSelection, currentSelectionArea);
	    		boolean successAdd = view.setSpriteToArea(currentSelection, newArea);
	    		System.out.println("removed: " + successRemove + " Added:" + successAdd);
	    		
        		view.setHighlightedZones(new ArrayList<Rectangle>());
        		return true;
        	}
    	}
    	    	
    	currentSelection = checkIntersection(x, y);
    	
    	if(currentSelection != null) {
    		
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
    		return true;
    		
    	} else {
    		view.setHighlightedZones(new ArrayList<Rectangle>());
    		return true;
    	}
    }

	@Override
    public boolean touchUp (int x, int y, int pointer, int button) {
		
		Rectangle emptyZone = null;
		
		if(currentSelection != null) {
			Rectangle currentSpriteRect = currentSelection.getBoundingRectangle();
			emptyZone = view.getArena().emptyZoneAtRectangle(currentSpriteRect, currentSelectionPlayer, true, currentSelectionMonster);
			//If current Selection is in hand check if dragged to a hand zone
			if(!currentSelectionField && emptyZone == null) {
				view.getArena().emptyZoneAtRectangle(currentSpriteRect, currentSelectionPlayer, false, currentSelectionMonster);
			}
			
        	System.out.println("touch up empty zone is: " + emptyZone);

			if(emptyZone != null) {
				//reassign the currentSelection to the emptyZone in arena
				view.getArena().removeSprite(currentSelection);
	    		String newArea = view.getArena().setSpriteToZone(currentSelection, emptyZone, currentSelectionPlayer);
        		System.out.println("NewArea was: " + newArea);
	    		//reassign the currentSelection to emptyZone in the view
	    		boolean successRemove = view.removeSpriteFromArea(currentSelection, currentSelectionArea);
	    		boolean successAdd = view.setSpriteToArea(currentSelection, newArea);
	    		System.out.println("removed: " + successRemove + " Added:" + successAdd);
	    		
	    		if(dragged) {
	    			view.setHighlightedZones(new ArrayList<Rectangle>());
	    			dragged = false;
	    		}
	    		return true;
	    		
			} else {
				//No right zone, set back to its original zone
	    		view.getArena().setSpriteToZone(currentSelection, view.getArena().emptyZoneAtRectangle(currentSelectionOriginZone, 1, true, true), currentSelectionPlayer);
	    		view.setHighlightedZones(new ArrayList<Rectangle>());
	    		return true;
			}
		}
        return false;
    }

    @Override
    public boolean touchDragged (int x, int y, int pointer) {
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
			List<Rectangle> availableZones = new ArrayList<Rectangle>();
			availableZones.addAll(view.getArena().getAvailableZones(1, true, true));
			availableZones.addAll(view.getArena().getAvailableZones(1, false, true));
			view.setHighlightedZones(availableZones);
			
		} else if(spriteArea.equals("P1SpellZone")) {
			view.setHighlightedZones(view.getArena().getAvailableZones(1, true, false));
			
		} else if(spriteArea.equals("P1MonsterZone")) {
			view.setHighlightedZones(view.getArena().getAvailableZones(1, true, true));
			
		} else if(spriteArea.equals("P2HandZone")) {
			List<Rectangle> availableZones = new ArrayList<Rectangle>();
			availableZones.addAll(view.getArena().getAvailableZones(2, true, true));
			availableZones.addAll(view.getArena().getAvailableZones(2, false, true));
			view.setHighlightedZones(availableZones);
			
		} else if(spriteArea.equals("P2MonsterZone")) {
			view.setHighlightedZones(view.getArena().getAvailableZones(1, true, true));
			
		} else if(spriteArea.equals("P2SpellZone")) {
			view.setHighlightedZones(view.getArena().getAvailableZones(1, true, false));
			
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
