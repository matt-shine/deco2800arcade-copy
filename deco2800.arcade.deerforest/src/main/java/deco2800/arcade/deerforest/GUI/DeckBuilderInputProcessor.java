package deco2800.arcade.deerforest.GUI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

//This class functions basically as the controller
public class DeckBuilderInputProcessor implements InputProcessor {

	private DeckBuilder game;
	private DeckBuilderScreen view;
	private BuilderSpriteLogic currentSelection;
	private boolean gameStarted;
	
	final String[] AreaKeys = {"deckZone", "zoomZone", "cardZone"};
	
	public DeckBuilderInputProcessor(DeckBuilder deckBuilder, DeckBuilderScreen deckBuilderView) {
		this.game = deckBuilder;
		this.view = deckBuilderView;
		//this.gameStarted = false;
	}

	@Override
	public boolean keyDown(int keycode) {
		if(!gameStarted) {
			loadAll();
			gameStarted = true;
		}
        
		if(keycode == Keys.NUM_0){
			DeerForestSingletonGetter.getDeerForest().changeScreen("game");
		}
        return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {
		
		return false;
	}

	@Override
	public boolean touchDragged(int x, int y, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public BuilderSpriteLogic getCurrentSelection() {
    	return currentSelection;
    }
	
	public void loadAll(){

		for (int i = 0; i < 40; i++) {
			AbstractCard c = game.getModel().deck.draw();
				
			//update currentSelection to be the drawn card
			currentSelection = new BuilderSpriteLogic(view.manager.get(c.getPictureFilePath(), Texture.class));
		    currentSelection.setCard(c);
			/*//set the current selection data*/
		    view.setSpriteToArea(currentSelection, "Deck");

		    //Set origin and scale to be that of the players deck (makes for nicer transition
		    /*view.setSpriteToDeck(player, currentSelection);*/
 
		    //Set to hand rectangle
			Rectangle r = view.getArena().getAvailableZones("Deck").get(0);
			BuilderSpriteLogic.setCurrentSelectionToRectangle(r);
			
		}
	}
	
	
}

