package deco2800.arcade.deerforest.GUI;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;

import deco2800.arcade.model.Game;
import deco2800.arcade.model.Game.ArcadeGame;
import deco2800.arcade.model.Player;
import deco2800.arcade.client.ArcadeInputMux;
import deco2800.arcade.client.GameClient;
import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.deerforest.models.cardContainers.Deck;
import deco2800.arcade.deerforest.models.cards.*;
import deco2800.arcade.deerforest.models.effects.SpellEffect;
import deco2800.arcade.deerforest.models.gameControl.GameSystem;
import deco2800.arcade.deerforest.models.gameControl.DeerForestPlayer;
/**
 * A card game for use in the Arcade
 * @author deerforest
 *
 */
@ArcadeGame(id="deerforest")
public class DeerForest extends GameClient {
	
	MainGameScreen view;
	MainGame mainGame;
	MainMenuScreen menuView;
	MainMenu mainMenu;
	MainMenuInputProcessor menuInputProcessor;
	MainInputProcessor inputProcessor;
	DeckBuilder deckBuilder;
	DeckBuilderScreen deckBuilderView;
	DeckBuilderInputProcessor deckInputProcessor;
	
	public DeerForest(Player player, NetworkClient networkClient){
		super(player, networkClient);
	}
	
	@Override
	public void create() {
		
		super.create();
        
        // Setup menu
        mainMenu = new MainMenu(null);
        menuView = new MainMenuScreen(mainMenu);
        menuInputProcessor = new MainMenuInputProcessor(mainMenu, menuView);
        
        // Setup game
		GameSystem tempSystem = new GameSystem(createDeerForestPlayer(), createDeerForestPlayer());
		mainGame = new MainGame(tempSystem);
        view = new MainGameScreen(mainGame);
		inputProcessor = new MainInputProcessor(mainGame, view);
		
		// Setup deck-builder
		deckBuilder = new DeckBuilder(null);
		deckBuilderView = new DeckBuilderScreen(deckBuilder);
		deckInputProcessor = new DeckBuilderInputProcessor(deckBuilder, deckBuilderView);
        
		// Set the menu as the screen
		changeScreen("menu");
		
    }

	@Override
	public void dispose() {
		super.dispose();

        try {
            // Remove the current input processor
            ArcadeInputMux.getInstance().removeProcessor(ArcadeInputMux.getInstance().getProcessors().get(1));

            // Dispose the screen
            this.getScreen().dispose();

            // Dispose all of the models
            mainMenu.dispose();
            mainGame.dispose();
            deckBuilder.dispose();
        } catch(Exception e) {

        }
	}
	
	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public void resume() {
		super.resume();
	}

	@Override
	public void render() {
		super.render();
	}
	
	private static final Game game;
	static {
		game = new Game();
		game.id = "deerforest";
		game.name = "Deer Forest";
	}

	public Game getGame() {
		return game;
	}
	
	// Changes the scene. Eg menu -> game
	public void changeScreen(String scene) {
		if (scene.equals("game")) {

			mainGame.create();
			setScreen(view);
			ArcadeInputMux.getInstance().addProcessor(inputProcessor);
			
		} else if (scene.equals("deck builder")) {
			
			deckBuilder.create();
			setScreen(deckBuilderView);
			ArcadeInputMux.getInstance().addProcessor(deckInputProcessor);
			
		} else if (scene.equals("menu")) {
			
	        mainMenu.create();
	        setScreen(menuView);
	        ArcadeInputMux.getInstance().addProcessor(menuInputProcessor);
		}
	}
	
	private DeerForestPlayer createDeerForestPlayer() {
		ArrayList<AbstractCard> cardList = new ArrayList<AbstractCard>();
		//add monsters
		for(int i = 0; i < 20; i++) {
		    int attack = (int)(Math.random()*30) + 20;
			double rand = Math.random();
			AbstractCard card;
			if(rand > 0.8) {
				card = new WaterMonster((int)(Math.random()*50) + 50, attack, "DeerForestAssets/WaterMonsterShell.png");
			} else if(rand > 0.6) {
				card = new FireMonster((int)(Math.random()*50) + 50, attack, "DeerForestAssets/FireMonsterShell.png");
			} else if(rand > 0.4) {
				card = new NatureMonster((int)(Math.random()*50) + 50, attack, "DeerForestAssets/NatureMonsterShell.png");
			} else if(rand > 0.2) {
				card = new DarkMonster((int)(Math.random()*50) + 50, attack, "DeerForestAssets/DarkMonsterShell.png");
			} else {
				card = new LightMonster((int)(Math.random()*50) + 50, attack, "DeerForestAssets/LightMonsterShell.png");
			}
			cardList.add(card);
		}

		//Add general spells
		for(int i = 0; i < 10; i++) {

            Set<String> typeEffects = null;

            List<String> effectCategory = new ArrayList<String>();
            effectCategory.add("Draw");
            effectCategory.add("Draw");

            List<List<Integer>> effectParams = new ArrayList<List<Integer>>();
            ArrayList<Integer> drawEffect = new ArrayList<Integer>();

            drawEffect.add(2); //Amount to draw
            drawEffect.add(1); //Amount to discard before
            drawEffect.add(1); //Amount to discard after
            drawEffect.add(0); //Affect yourself
            drawEffect.add(0); //On activation

            ArrayList<Integer> drawEffect2 = new ArrayList<Integer>();

            drawEffect2.add(1); //Amount to draw
            drawEffect2.add(0); //Amount to discard before
            drawEffect2.add(0); //Amount to discard after
            drawEffect2.add(0); //Affect yourself
            drawEffect2.add(2); //On opponents end

            effectParams.add(drawEffect);
            effectParams.add(drawEffect2);

            SpellEffect spellEffect;
            try {
                spellEffect = new SpellEffect(typeEffects, effectCategory, effectParams);
            } catch (Exception e) {
                System.out.println("Error making effect");
                spellEffect = null;
            }

			String filePath = "DeerForestAssets/GeneralSpellShell.png";
			AbstractCard spell = new GeneralSpell(spellEffect, filePath);
			cardList.add(spell);
		}

		//add field Spells
		for(int i = 0; i < 10; i++) {

            Set<String> typeEffects = null;

            List<String> effectCategory = new ArrayList<String>();
            effectCategory.add("Draw");
            effectCategory.add("Draw");
            effectCategory.add("Draw");

            List<List<Integer>> effectParams = new ArrayList<List<Integer>>();
            ArrayList<Integer> drawEffect = new ArrayList<Integer>();

            drawEffect.add(2); //Amount to draw
            drawEffect.add(2); //Amount to discard before
            drawEffect.add(0); //Amount to discard after
            drawEffect.add(0); //Affect player
            drawEffect.add(0); //On activation

            ArrayList<Integer> drawEffect2 = new ArrayList<Integer>();

            drawEffect2.add(1); //Amount to draw
            drawEffect2.add(1); //Amount to discard before
            drawEffect2.add(0); //Amount to discard after
            drawEffect2.add(0); //Affect player
            drawEffect2.add(0); //On activation

            ArrayList<Integer> drawEffect3 = new ArrayList<Integer>();

            drawEffect3.add(2); //Amount to draw
            drawEffect3.add(0); //Amount to discard before
            drawEffect3.add(2); //Amount to discard after
            drawEffect3.add(0); //Affect player
            drawEffect3.add(1); //On Your end turn

            effectParams.add(drawEffect);
            effectParams.add(drawEffect2);
            effectParams.add(drawEffect3);

            SpellEffect spellEffect;
            try {
                spellEffect = new SpellEffect(typeEffects, effectCategory, effectParams);
            } catch (Exception e) {
                System.out.println("Error making effect");
                spellEffect = null;
            }

			String filePath = "DeerForestAssets/FieldSpellShell.png";
			AbstractCard spell = new FieldSpell(spellEffect, filePath);
			cardList.add(spell);
		}
		Deck deck = new Deck(cardList);
		deck.shuffle();
		DeerForestPlayer p = new DeerForestPlayer(deck);
		return p;
	}

	
	public int getCurrentPlayer() {
		return mainGame.getCurrentPlayer();
		
	}
}
