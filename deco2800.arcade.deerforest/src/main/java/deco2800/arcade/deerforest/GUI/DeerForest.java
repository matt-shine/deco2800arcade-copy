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
import deco2800.arcade.deerforest.models.gameControl.DeckSystem;
/**
 * A card game for use in the Arcade
 * @author deerforest
 *
 */
@ArcadeGame(id="deerforest")
public class DeerForest extends GameClient {

    //Variables for the games, screens and input processors to swap between
	MainGameScreen view;
	MainGame mainGame;
	MainMenuScreen menuView;
	MainMenu mainMenu;
	MainMenuInputProcessor menuInputProcessor;
	MainInputProcessor inputProcessor;
	static DeckBuilder deckBuilder;
	static DeckBuilderScreen deckBuilderView;
	static DeckBuilderInputProcessor deckInputProcessor;

	public DeerForest(Player player, NetworkClient networkClient){
		super(player, networkClient);
	}

    /**
     * Creates the initial data for deerforest, sets up the mainMenu, game and
     * deckBuilder screens, games and input processors
     */
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
		DeckSystem deck = new DeckSystem(createDeerForestPlayer().getDeck(), createDeerForestPlayer().getDeck());
		deckBuilder = new DeckBuilder(deck);
		deckBuilderView = new DeckBuilderScreen(deckBuilder);
		deckInputProcessor = new DeckBuilderInputProcessor(deckBuilder, deckBuilderView);
        
		// Set the menu as the screen
		changeScreen("menu");
		
    }

    /**
     * Dispose of all assets
     */
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
		game.description = "A battle of minds, fought with pieces of paper with"
				+ "deer on them (probably). More dramatic than it sounds.";
	}

	public Game getGame() {
		return game;
	}

    /**
     * Changes the screen, for example menu ->game
     *
     * @param scene the scene to change to
     */
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

    /**
     * Creates a player for use in the game, note that really this should be
     * filled by user profiles, however for initial testing purposes and
     * while the user profile system / database system is in its infancy we use
     * this
     *
     * @return a deerforst player
     */
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
            effectCategory.add("Destroy");

            List<List<Integer>> effectParams = new ArrayList<List<Integer>>();
            ArrayList<Integer> drawEffect = new ArrayList<Integer>();

            drawEffect.add(2);
            drawEffect.add(1);
            drawEffect.add(1);
            drawEffect.add(0);
            drawEffect.add(0); //On activation

            ArrayList<Integer> destroyEffect = new ArrayList<Integer>();

            destroyEffect.add(2); //Amount to destroy
            destroyEffect.add(0); //Location on hand
            destroyEffect.add(1); //Opponents cards
            destroyEffect.add(2); //Affect monsters
            destroyEffect.add(0); //On activation
            destroyEffect.add(0); //On activation

            effectParams.add(drawEffect);
            effectParams.add(destroyEffect);

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
            effectCategory.add("Monster");
            effectCategory.add("Player");

            List<List<Integer>> effectParams = new ArrayList<List<Integer>>();
            ArrayList<Integer> monsterEffect = new ArrayList<Integer>();
            ArrayList<Integer> playerEffect = new ArrayList<Integer>();

            monsterEffect.add(2); //Amount to buff
            monsterEffect.add(0); //Player to affect
            monsterEffect.add(0); //Affect health
            monsterEffect.add(10); //give +10
            monsterEffect.add(0); //On activation
            monsterEffect.add(0); //Affect any types

            playerEffect.add(0);
            playerEffect.add(0);
            playerEffect.add(20);
            playerEffect.add(0);
            playerEffect.add(0);

            effectParams.add(monsterEffect);
            effectParams.add(playerEffect);

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
