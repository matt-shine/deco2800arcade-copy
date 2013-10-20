package deco2800.arcade.deerforest.GUI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

import deco2800.arcade.deerforest.models.cardContainers.CardCollection;
import deco2800.arcade.deerforest.models.cards.AbstractCard;
import deco2800.arcade.deerforest.models.cards.AbstractSpell;

/** 
 * This class functions for the main game as the controller
 */
public class MainInputProcessor implements InputProcessor {

	private MainGame game;
	private MainGameScreen view;
	private ExtendedSprite currentSelection;
    private ExtendedSprite currentZoomed;
    private ExtendedSprite zoomSelection;
    private float currentScaleX;
    private float currentScaleY;
    private float currentX;
    private float currentY;
	private float xClickOffset;
	private float yClickOffset;
	private boolean dragged;
    private boolean zoomed;
    private boolean gameFinished;
    private boolean drawn;
    private boolean gameStarted;
    
    private int cardsDrawn;
    static int turns;

    SpellLogic spellHandler;

	//define array of keys for P1 / P2 zones
	final String[] P1Keys = {"P1HandZone", "P1MonsterZone", "P1SpellZone"};
	final String[] P2Keys = {"P2HandZone", "P2MonsterZone", "P2SpellZone"};

    /**
     * Constructs the input processor with the given game and view.
     *
     * @param game the game to be used by the input processor
     * @param view the view to be used by the input processor
     */
	public MainInputProcessor(MainGame game, MainGameScreen view) {
		this.game = game;
		this.view = view;
		dragged = false;
        this.gameFinished = false;
        this.drawn = false;
        this.gameStarted = false;
        //Set up a spellLogic handler
        spellHandler = new SpellLogic(game, view, this);
        
        cardsDrawn = 0;
	}

    /**
     * Interprets a key press, performing the proper action.
     * @param keycode the key that was pressed
     * @return true if key press was dealt with
     */
	@Override
	public boolean keyDown (int keycode) {
        if(!gameStarted) {
            this.initialDraw(4);
            gameStarted = true;
            game.nextPhase();
            return true;
        }
        //Check for new game
        if(keycode == Keys.N) {
            this.newGame();
        }
        //Check for muted
        if(keycode == Keys.M) {
            game.toggleMuted();
        }
        if(gameFinished) {
            return false;
        }
        //Zoom in on card
        if(keycode == Keys.SHIFT_LEFT) {
            doZoom();
            return true;
        }
        //If currently zoomed return
        if(zoomed) return false;
        //Check if spell is waiting to be resolved
        if(spellHandler.needsSelection()) {
            return false;
        }
		//Go to next phase
		if(keycode == Keys.SPACE && game.getPhase() != null) {
            doNextPhase();
            return true;
		}
		//Change player turns
		if(keycode == Keys.ALT_LEFT) {
            doChangeTurns();
			return true;
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

    /**
     * Interprets a click, generally this relates to selecting card for
     * various purposes.
     *
     * @param x the x point of the click
     * @param y the y point of the click
     * @param pointer
     * @param button what button was used (left, right, middle)
     * @return true if click was dealt with
     */
    @Override
    public boolean touchDown (int x, int y, int pointer, int button) {
        //Check it was a single click
        if(button != Buttons.LEFT) return false;
        //If currently zoomed / gamefinished return
        if(zoomed || gameFinished) return false;
        //Reset zoomSelection color
        if(zoomSelection != null) zoomSelection.setSelected(false);
        //Check if spell is waiting to be resolved
        if(spellHandler.needsSelection()) {
            spellHandler.handleClick(x, y);
            return true;
        }
        //get zoomSelection
        zoomSelection = SpriteLogic.checkIntersection(1, x, y);
        if(zoomSelection == null) {
            //must be in P2
            zoomSelection = SpriteLogic.checkIntersection(2, x, y);
            if(zoomSelection != null) {
                zoomSelection.setPlayer(2);
            }
        } else {
            zoomSelection.setPlayer(1);
        }
        //Set zoom selection data
        if(zoomSelection != null) {
            zoomSelection.setField(SpriteLogic.getSpriteZoneType(zoomSelection)[0]);
            zoomSelection.setSelected(true);
        }
        //Check if click was on deck and try to draw
        if(!drawn && game.getPhase().equals("DrawPhase")) {
            if(view.deckAtPoint(x, y)) {
                doDraw();
                
                cardsDrawn++;
                DeerForest.logger.info("Cards drawn: " + cardsDrawn);
                
                if (cardsDrawn == 30) {
            		// Give the draw master card achievement
            		if (DeerForestSingletonGetter.getDeerForest() != null) {
            			DeerForestSingletonGetter.getDeerForest().incrementAchievement("deerforest.drawMaster");
            		}
                }
                
        		// Give the draw card achievement
        		if (DeerForestSingletonGetter.getDeerForest() != null) {
        			DeerForestSingletonGetter.getDeerForest().incrementAchievement("deerforest.draw");
        		}
                
                this.drawn = true;
            }
        }
    	//If there is already a current selected card then try to move it to
    	// the clicked space, set currentSelection to null then return
        //Can't move cards during battle phase, can only attack with cards on field
    	if(currentSelection != null && (!game.getPhase().equals("BattlePhase") || !currentSelection.isField())) {
    		tryBattle(x, y);
        	return true;
    	}
        //Handle battle phase attack selection
        if(currentSelection != null && game.getPhase().equals("BattlePhase") && currentSelection.isField()) {
            PhaseLogic.battlePhaseSelection(x,y);
        }
    	//Get the current Selection at point if it exists
    	currentSelection = SpriteLogic.checkIntersection(game.getCurrentPlayer(),x, y);
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

    /**
     * Interprets the realising of a click, generally used when user is dragging
     * a card around to place it into a zone
     * @param x the x point of release
     * @param y the y point of release
     * @param pointer
     * @param button what button was released
     * @return true if the release was dealth with
     */
	@Override
    public boolean touchUp (int x, int y, int pointer, int button) {

        //If currently zoomed / game finished return
        if(zoomed || gameFinished) return false;

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
                //Check if a spell was moved from the hand to the field
                if(currentSelection.getCard() instanceof AbstractSpell && oldArea.contains("Hand") && newArea.contains("Spell")) {
                    //Play spell
                    spellHandler.activateSpell(currentSelection.getPlayer(), (AbstractSpell)currentSelection.getCard(), currentSelection);
                }
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

    /**
     * Handles the movement of a mouse, used for dragging cards around
     * @param x the x point the mouse was dragged to
     * @param y the y point the mouse was dragged to
     * @param pointer
     * @return
     */
	@Override
    public boolean touchDragged (int x, int y, int pointer) {

        //If currently zoomed / game finshed return
        if(zoomed || gameFinished) return false;

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
     * Returns the current selection as an ExtendedSprite
     */
    public ExtendedSprite getCurrentSelection() {
    	return currentSelection;
    }

    /**
     * Sets the click offset used when dragging cards around
     * @param x the x offset
     * @param y the y offset
     */
    public void setOffset(float x, float y) {
    	xClickOffset = x;
    	yClickOffset = y;
    }

    /**
     * Returns true if the current sprite is zoomed or false otherwise.
     */
    public ExtendedSprite getCurrentZoomed() {
        return currentZoomed;
    }

    /**
     * Zooms in on a given card, setting the new position and scale correctly,
     * or returns card to place if already zoomed
     */
    private void doZoom() {
        //Check if zoom selection
        if(zoomSelection != null) {
            if(!zoomed) {
                currentScaleX = zoomSelection.getScaleX();
                currentScaleY = zoomSelection.getScaleY();
                currentX = zoomSelection.getX();
                currentY = zoomSelection.getY();
                zoomSelection.setScale(Gdx.graphics.getHeight()/(2*zoomSelection.getHeight()));
                float x = Gdx.graphics.getWidth()/2 - zoomSelection.getBoundingRectangle().getWidth()/2;
                float y = Gdx.graphics.getHeight()/2 - zoomSelection.getBoundingRectangle().getHeight()/2;
                zoomSelection.setPosition(x, y);
                zoomed = true;
                currentZoomed = zoomSelection;
            } else {
                zoomSelection.setPosition(currentX, currentY);
                zoomSelection.setScale(currentScaleX, currentScaleY);
                currentZoomed = null;
                zoomed = false;
            }
        }
    }

    /**
     * Changes the phase to the next one, performing any relevant actions
     */
    private void doNextPhase() {
        //Don't let go to next phase until drawn
        if(game.getPhase().equals("DrawPhase") && !drawn) return;

        game.nextPhase();

        //Activate all continuous spells for this phase
        spellHandler.activateContinuousEffects(game.getCurrentPlayer(), game.getPhase());

        //Set stuff up at the start phase
        if(game.getPhase().equals("StartPhase")) {
            currentSelection = null;
            view.setHighlightedZones(new ArrayList<Rectangle>());
            game.nextPhase();
            //reset hasAttacked
            SpriteLogic.resetHasAttacked();
            //reset drawn
            this.drawn = false;
        }

        if(game.getPhase().equals("BattlePhase")) {
            CardCollection field = game.getCardCollection(1, "Field");
            field.addAll(game.getCardCollection(2, "Field"));
            DeerForest.logger.info(new ArrayList<AbstractCard>(field).toString());
        }

        view.setPhaseDisplayed(false);
        currentSelection = null;
        view.setHighlightedZones(new ArrayList<Rectangle>());
    }

    /**
     * Changes between player turns
     */
    private void doChangeTurns() {
        game.changeTurns();
        game.nextPhase();
        doDraw();
        currentSelection = null;
        view.setPhaseDisplayed(false);
        view.setHighlightedZones(new ArrayList<Rectangle>());
        
        // Increment the number of turns
        turns++;
    }

    /**
     * Tries to battle the current selection against whatever was clicked on
     * at x, y
     * @param x the x click point
     * @param y the y click point
     */
    private void tryBattle(int x, int y) {
        //completed successfully, so set current to null
        if(SpriteLogic.setCurrentSelectionToPoint(x,y)) {
            //Successfully moved card, update model
            String oldArea = currentSelection.getArea();
            String newArea = view.getArena().getAreaAtPoint(x, y);
            List<AbstractCard> cards = new ArrayList<AbstractCard>();
            AbstractCard c = SpriteLogic.getCardModelFromSprite(currentSelection, currentSelection.getPlayer(), oldArea);
            cards.add(c);
            game.moveCards(currentSelection.getPlayer(), cards, oldArea, newArea);
            //Check if a spell was moved from the hand to the field
            if(currentSelection.getCard() instanceof AbstractSpell && oldArea.contains("Hand") && newArea.contains("Spell")) {
                //Play spell
                spellHandler.activateSpell(currentSelection.getPlayer(), (AbstractSpell)currentSelection.getCard(), currentSelection);
            }
        }
        //clear available zones and currentSelection
        currentSelection = null;
        view.setHighlightedZones(new ArrayList<Rectangle>());
    }

    /**
     * Draws a card for the current player, adding it to their hand. Note that
     * if the draw would cause them to have more than 6 cards it is not performed
     */
    public void doDraw() {
    	
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
        currentSelection.setCard(c);
		//set the current selection data
		currentSelection.setField(false);
		currentSelection.setMonster(false); //doesn't matter as in hand
		currentSelection.setPlayer(player);
		currentSelection.setArea(SpriteLogic.getCurrentSelectionArea(currentSelection.getPlayer(), currentSelection.isField(), currentSelection.isMonster()));

        //Set origin and scale to be that of the players deck (makes for nicer transition
        view.setSpriteToDeck(player, currentSelection);

		//Set to hand rectangle
		Rectangle r = view.getArena().getAvailableZones(player, false, false).get(0);
		SpriteLogic.setCurrentSelectionToRectangle(r);
		
	}

    /**
     * Draws the initial n number of cards for each player
     * @param n number of cards to draw
     */
    public void initialDraw(int n) {
        //Draw initial cards
        for(int i = 0; i < 2*n; i++) {
            this.doDraw();
            game.changeTurns();
        }
        game.setFirstTurn(true);
    }

    /**
     * Creates a new game
     */
    public void newGame() {
        DeerForestSingletonGetter.getDeerForest().dispose();
        DeerForestSingletonGetter.getDeerForest().create();
    }

    /**
     * Sets whether the game has been finished or not
     */
    public void setGameFinished(boolean b) {
        this.gameFinished = b;
    }

    /**
     * Returns the current selection
     */
    public ExtendedSprite getSelection() {
        return this.zoomSelection;
    }

    /**
     * Returns true if the sprite has been drawn
     */
    public boolean hasDrawn() {
        return drawn;
    }
}
