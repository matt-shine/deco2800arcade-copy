package deco2800.arcade.deerforest.GUI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

import deco2800.arcade.deerforest.models.cardContainers.CardCollection;
import deco2800.arcade.deerforest.models.cards.AbstractCard;

//This class functions basically as the controller
public class MainInputProcessor implements InputProcessor {

	private MainGame game;
	private MainGameScreen view;
	private ExtendedSprite currentSelection;
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
			if(currentSelection != null) {
				System.out.println("CurrentSelection: " + currentSelection + " player: " + currentSelection.getPlayer() 
						+ " monster: " + currentSelection.isMonster() + " field" + currentSelection.isField() + " area: " + currentSelection.getArea());
			}
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
			System.out.println(SpriteLogic.getCardModelFromSprite(currentSelection, currentSelection.getPlayer(), currentSelection.getArea()));
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
    		if(SpriteLogic.setCurrentSelectionToPoint(x,y)) {
    			//Successfully moved card, update model
    			String oldArea = currentSelection.getArea();
    			String newArea = view.getArena().getAreaAtPoint(x, y);
    			List<AbstractCard> cards = new ArrayList<AbstractCard>();
    			AbstractCard c = SpriteLogic.getCardModelFromSprite(currentSelection, currentSelection.getPlayer(), oldArea);
    			cards.add(c);
    			game.moveCards(currentSelection.getPlayer(), cards, oldArea, newArea);
    		}
    		//clear available zones and currentSelection
    		currentSelection = null;
    		view.setHighlightedZones(new ArrayList<Rectangle>());
        	return true;
    	}
    	
    	//Get the current Selection at point if it exists
    	currentSelection = SpriteLogic.checkIntersection(x, y);
    	
    	//There is a new currentSelection, set its parameters accordingly
    	if(currentSelection != null) {
    		SpriteLogic.setCurrentSelectionData(x,y);
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
			
			if(SpriteLogic.setCurrentSelectionToRectangle(currentSelection.getBoundingRectangle())) {
				//successfully moved card
				//Do stuff with model here
				String oldArea = currentSelection.getArea();
    			String newArea = view.getArena().getAreaAtPoint((int)currentSelection.getX()+10, (int)currentSelection.getY()+10);
    			List<AbstractCard> cards = new ArrayList<AbstractCard>();
    			AbstractCard c = SpriteLogic.getCardModelFromSprite(currentSelection, currentSelection.getPlayer(), oldArea);
    			cards.add(c);
    			game.moveCards(currentSelection.getPlayer(), cards, oldArea, newArea);
			} else {
				//card was not moved, set it back to original position
				Rectangle r = view.getArena().emptyZoneAtRectangle(currentSelection.getOriginZone(), currentSelection.getPlayer(), 
						currentSelection.isField(), currentSelection.isMonster());
	    		view.getArena().setSpriteToZone(currentSelection, r, currentSelection.getPlayer());
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

    public ExtendedSprite getCurrentSelection() {
    	return currentSelection;
    }
    
    public void setOffset(float x, float y) {
    	xClickOffset = x;
    	yClickOffset = y;
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
		currentSelection.setField(false);
		currentSelection.setMonster(false); //doesn't matter as in hand
		currentSelection.setPlayer(player);
		currentSelection.setArea(SpriteLogic.getCurrentSelectionArea(currentSelection.getPlayer(), currentSelection.isField(), currentSelection.isMonster()));
		
		//Set to hand rectangle
		Rectangle r = view.getArena().getAvailableZones(player, false, false).get(0);
		SpriteLogic.setCurrentSelectionToRectangle(r);
	}

}