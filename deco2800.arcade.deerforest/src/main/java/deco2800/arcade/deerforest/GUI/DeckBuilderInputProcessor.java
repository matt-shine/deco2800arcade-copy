package deco2800.arcade.deerforest.GUI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

import deco2800.arcade.deerforest.models.cardContainers.CardCollection;
import deco2800.arcade.deerforest.models.cardContainers.Field;
import deco2800.arcade.deerforest.models.cards.AbstractCard;
import deco2800.arcade.deerforest.models.cards.AbstractSpell;

//This class functions basically as the controller
public class DeckBuilderInputProcessor implements InputProcessor {

	private DeckBuilder game;
	private DeckBuilderScreen view;
	private BuilderSprite currentSelection;
	private BuilderSprite currentZoomed;
    private BuilderSprite zoomSelection;
    private float currentScaleX;
    private float currentScaleY;
    private float currentX;
    private float currentY;
	private float xClickOffset;
	private float yClickOffset;
	private boolean dragged;
    private boolean zoomed;
    private boolean gameStarted;
	
	final String[] AreaKeys = {"DeckZone", "ZoomZone", "CardZone"};
	
	public DeckBuilderInputProcessor(DeckBuilder deckBuilder, DeckBuilderScreen deckBuilderView) {
		this.game = deckBuilder;
		this.view = deckBuilderView;
		this.gameStarted = false;
	}

	@Override
	public boolean keyDown(int keycode) {
		 if(!gameStarted) {
			 loadAll();
	         gameStarted = true;
	         return true;
	        }
		if(keycode == Keys.NUM_0){
			DeerForestSingletonGetter.getDeerForest().changeScreen("game");
			return true;
		}
		if(keycode == Keys.SHIFT_LEFT){
			if(currentSelection != null) {
				 view.setSpriteToArea(currentSelection, "ZoomZone");
			}
		}
        return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean mouseMoved(int x, int y) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {
		System.out.println("Touch Down");
		//Check it was a single click
        if(button != Buttons.LEFT) return false;
    	
    	//Get the current Selection at point if it exists
        System.out.println("got here");
        currentSelection = null;
        if(BuilderSpriteMover.checkIntersection(x,y)!= null) {
        	currentSelection = BuilderSpriteMover.checkIntersection(x, y);
        }
    	System.out.println("Current selection is: " + currentSelection);
    	System.out.println("Current selection area is: " + currentSelection.getArea());
    	//There is a new currentSelection, set its parameters accordingly
    	if(currentSelection != null) {
    		BuilderSpriteMover.setCurrentSelectionData(x,y);
    		return true;
    	} else {
    		//TODO set highlighted zones
    		return true;
    	}
	}

	@Override
	public boolean touchDragged(int x, int y, int pointer) {
		//move card to where it was dragged
    	if(currentSelection != null) {
    		currentSelection.setPosition(x - xClickOffset, y - yClickOffset);
    		dragged = true;
    		return true;
    	}
        return false;
		
	}

	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
		if(dragged && button == Buttons.LEFT) {
			
			if(BuilderSpriteMover.setCurrentSelectionToRectangle(currentSelection.getBoundingRectangle())) {
				//successfully moved card
				//Do stuff with model here
				String oldArea = currentSelection.getArea();
    			String newArea = view.getArena().getAreaAtPoint((int)currentSelection.getX()+10, (int)currentSelection.getY()+10);
    			List<AbstractCard> cards = new ArrayList<AbstractCard>();
    			AbstractCard c = BuilderSpriteMover.getCardModelFromSprite(currentSelection, oldArea);
    			cards.add(c);
    			game.moveCards(cards, oldArea, newArea);
    
			} else {
				//card was not moved, set it back to original position
				Rectangle r = view.getArena().emptyZoneAtRectangle(currentSelection.getOriginZone(), currentSelection.getArea());
				System.out.println("got here\n");
	    		view.getArena().setSpriteToZone(currentSelection, r);
			}
			//Reset current selection 
			currentSelection = null;
			dragged = false;
		}
		return true;
		
	}
	
	public BuilderSprite getCurrentSelection() {
    	return currentSelection;
    }
	
	public BuilderSprite getCurrentZoomed() {
        return currentZoomed;
    }
	
	public void loadAll(){
		for (int i = 0; i < 40; i++) {
			AbstractCard c = game.getModel().deck.draw();
				
			//update currentSelection to be the drawn card
			currentSelection = new BuilderSprite(view.manager.get(c.getPictureFilePath(), Texture.class));
		    currentSelection.setCard(c);
		    currentSelection.setArea("DeckZone");
			//set the current selection data
		    view.setSpriteToArea(currentSelection, "DeckZone");
 
		    //Set to hand rectangle
			Rectangle r = view.getArena().getAvailableZones("DeckZone").get(0);
			BuilderSpriteMover.setCurrentSelectionToRectangle(r);	
		}
		
		for(int i = 0; i < 7; i++) {
			AbstractCard c = game.getModel().cards.draw();
			
			currentSelection = new BuilderSprite(view.manager.get(c.getPictureFilePath(), Texture.class));
		    currentSelection.setCard(c);
		    currentSelection.setArea("CardZone");
		    
		    
		    view.setSpriteToArea(currentSelection, "CardZone");
		    Rectangle r = view.getArena().getAvailableZones("CardZone").get(0);
			BuilderSpriteMover.setCurrentSelectionToRectangle(r);
		}
	}
	
	 public void setOffset(float x, float y) {
	    	xClickOffset = x;
	    	yClickOffset = y;
	    }
	
}

