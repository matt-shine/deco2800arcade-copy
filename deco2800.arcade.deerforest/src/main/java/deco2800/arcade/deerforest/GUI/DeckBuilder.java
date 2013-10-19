package deco2800.arcade.deerforest.GUI;

import java.util.List;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import deco2800.arcade.deerforest.models.cardContainers.CardCollection;
import deco2800.arcade.deerforest.models.cards.AbstractCard;
import deco2800.arcade.deerforest.models.cards.AbstractMonster;
import deco2800.arcade.deerforest.models.gameControl.DeckSystem;



//This class functions as sort of a higher level game system controller
//As well as (most importantly) being an instance of a game (according to Gdx)
//to run
public class DeckBuilder extends Game {

	SpriteBatch batch;
	BitmapFont font;
	final private DeckSystem model;
	
	/**
	 * Initializes DeckBuilder 
	 * @param model
	 */
	public DeckBuilder(DeckSystem model) {
		this.model = model;
	}
	
	@Override
	public void create() {
		batch = new SpriteBatch();
		//Use LibGDX's default Arial font
        Texture texture = new Texture(Gdx.files.internal("DeerForestAssets/Deco_0.tga"), true); // true enables mipmaps
        texture.setFilter(Texture.TextureFilter.MipMapLinearNearest, Texture.TextureFilter.Linear);
        font = new BitmapFont(Gdx.files.internal("DeerForestAssets/Deco.fnt"), true);
        font.setScale(0.5f);

		this.setScreen(new DeckBuilderScreen(this));

	}
	
	/**
	 * Renders the screen
	 */
	public void render() {
        super.render();
	}
	
	public void dispose() {
		batch.dispose();
		font.dispose();
	}
	
	/**
	 * Moves a list of cards from one location to another
	 * @param cards
	 * @param oldLocation
	 * @param newLocation
	 * @return
	 */
	public boolean moveCards(List<AbstractCard> cards, String oldLocation, String newLocation) {
		//Check not null
		if(cards == null || oldLocation == null || newLocation == null) return false;
		
		CardCollection srcCards = null;
		CardCollection destCards = null;
		
		if(oldLocation.contains("DeckZone")) {
			srcCards = model.getCardCollection("DeckZone");
		} else if(oldLocation.contains("CardZone")) {
			srcCards = model.getCardCollection("CardZone");
		} 
		
		if(newLocation.contains("DeckZone")) {
			destCards = model.getCardCollection("DeckZone");
		} else if(newLocation.contains("CardZone")) {
			destCards = model.getCardCollection("CardZone");
		} 

        boolean b = model.moveCards(cards, srcCards, destCards);
        if(!b) {
            DeerForest.logger.warning("Error moving with cards: " + cards + " srcCards: " + srcCards + " destCards: " + destCards);
        } else {
            DeerForest.logger.info("No error moving with cards: " + cards + " srcCards: " + srcCards + " destCards: " + destCards);
        }
		return b;
	}
	
	/**
	 * Gets all the cards within an area
	 * 
	 * @return the card collection in that area
	 */
	public CardCollection getCardCollection(String area) {
		return model.getCardCollection(area);
	}
	
	/**
	 * Gets the model
	 * 
	 * @return DeckSystem model
	 */
	public DeckSystem getModel() {
		return this.model;
	}
	
}
