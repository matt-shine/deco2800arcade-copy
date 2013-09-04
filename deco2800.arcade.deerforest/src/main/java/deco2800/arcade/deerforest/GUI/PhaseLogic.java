package deco2800.arcade.deerforest.GUI;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

import deco2800.arcade.deerforest.models.cardContainers.CardCollection;
import deco2800.arcade.deerforest.models.cards.AbstractCard;

public class PhaseLogic {

    public static void doDraw() {
    	
    	
    	MainGame game = DeerForestSingletonGetter.getDeerForest().mainGame;
    	ExtendedSprite drawnSprite = DeerForestSingletonGetter.getDeerForest().inputProcessor.getCurrentSelection();
    	Arena arena = DeerForestSingletonGetter.getDeerForest().view.getArena();
    	AssetManager manager = DeerForestSingletonGetter.getDeerForest().view.manager;
    	
    	
    	//Get current hand
    	int player = game.getCurrentPlayer();
    	CardCollection currentHand = game.getCardCollection(player, "Hand");
    	
    	//Check that the hand is not already full
    	if(currentHand.size() >= 6) return;
    	
    	//Draw a card
    	AbstractCard c = game.draw(player);

		//Add card to the hand (in view, already added to player hand in model)
		
		//update currentSelection to be the drawn card
		drawnSprite = new ExtendedSprite(manager.get(c.getPictureFilePath(), Texture.class));
		//set the current selection data
		drawnSprite.setField(false);
		drawnSprite.setMonster(false); //doesn't matter as in hand
		drawnSprite.setPlayer(player);
		drawnSprite.setArea(SpriteLogic.getCurrentSelectionArea(drawnSprite.getPlayer(), drawnSprite.isField(), drawnSprite.isMonster()));
		
		//Set to hand rectangle
		System.out.println(arena.getAvailableZones(player, false, false).get(0));
		Rectangle r = arena.getAvailableZones(player, false, false).get(0);
		SpriteLogic.setCurrentSelectionToRectangle(r);
	}
    
}
