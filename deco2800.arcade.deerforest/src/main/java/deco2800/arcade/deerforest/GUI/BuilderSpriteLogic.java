package deco2800.arcade.deerforest.GUI;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

import deco2800.arcade.deerforest.models.cardContainers.CardCollection;
import deco2800.arcade.deerforest.models.cardContainers.CardCollectionList;
import deco2800.arcade.deerforest.models.cardContainers.Field;
import deco2800.arcade.deerforest.models.cards.AbstractCard;

public class BuilderSpriteLogic extends Sprite {
	
	//array of keys
	final static String[] Keys = {"deckZone", "cardZone", "zoomZone"};
	private Rectangle originZone;
	private int player;
	private boolean field;
	private boolean monster;
	private String area;
    private boolean hasAttacked;
    private boolean isSelected;
    private AbstractCard card;
    private DeckBuilder game;
    private float glow;
    private boolean glowDirection;

    //Variables for moving sprite
    private float deltaX;
    private float deltaY;
    private float timeToMove;
    private float currentMoveTime;

    //Variables for scaling sprite
    private float scaleDeltaX;
    private float scaleDeltaY;
    private float scaleTimeToMove;
    private float scaleCurrentMoveTime;
	
	public BuilderSpriteLogic(Texture t) {
		super(t);
		setOrigin(0,0);
		this.flip(false, true);
		originZone = null;
		setArea(null);
        this.card = null;
        this.isSelected = false;
        game = DeerForest.deckBuilder;

        this.deltaX = 0;
        this.deltaY = 0;
        this.timeToMove = 0;
        this.currentMoveTime = 0;
	}
	
	public void setCard(AbstractCard c) {
	        this.card = c;
	}
	
	public void setArea(String area) {
		this.area = area;
	}
	
	public static boolean setCurrentSelectionToRectangle(Rectangle r) {
		
    	BuilderSpriteLogic currentSelection = DeerForest.deckInputProcessor.getCurrentSelection();
    	DeckBuilderScreen view = DeerForest.deckBuilderView;
    	DeckBuilder game = DeerForest.deckBuilder;
    	
    	Rectangle emptyZone = null;
		//get the empty zone at rectangle (only check zones allowed)
    	//Hand can only go to field if main phase and no summoned (if monster)
    	
    	emptyZone = view.getArena().emptyZoneAtRectangle(r, "Deck");
    	
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
	
	 public void moveTo(float x, float y, int time) {
	        this.deltaX = x - this.getX();
	        this.deltaY = y - this.getY();
	        this.timeToMove = time;
	        this.currentMoveTime = 0;
	    }

	    public void scaleTo(float xScale, float yScale, int time) {
	        this.scaleDeltaX = xScale - this.getScaleX();
	        this.scaleDeltaY = yScale - this.getScaleY();
	        this.scaleTimeToMove = time;
	        this.scaleCurrentMoveTime = 0;
	    }
}
