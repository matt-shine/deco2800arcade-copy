package deco2800.arcade.deerforest.GUI;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

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
	
	static Logger logger;

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
		
		// Create the logger
		logger = Logger.getLogger("GUILogger");
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
        game.description = "Battle your friends with unique cards and unique possibilities.";
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

        //WATER CARDS
        AbstractCard card1 = new WaterMonster(20, 50, "DeerForestAssets/aqua pura.png");
        cardList.add(card1);
        cardList.add(card1);

        AbstractCard card2 = new WaterMonster(100, 10, "DeerForestAssets/cyclone.png");
        cardList.add(card2);

        AbstractCard card3 = new WaterMonster(30, 30, "DeerForestAssets/boiling Pot.png");
        cardList.add(card3);

        AbstractCard card4 = new WaterMonster(50, 25, "DeerForestAssets/loch ness.png");
        cardList.add(card4);

        AbstractCard card5 = new WaterMonster(5, 120, "DeerForestAssets/pirahna.png");
        cardList.add(card5);

        AbstractCard card6 = new WaterMonster(200, 15, "DeerForestAssets/sea dragon.png");
        cardList.add(card6);

        AbstractCard card7 = new WaterMonster(40, 40, "DeerForestAssets/slimyeel.png");
        cardList.add(card7);

        AbstractCard card8 = new WaterMonster(60, 25, "DeerForestAssets/tsunami wave.png");
        cardList.add(card8);

        AbstractCard card9 = new WaterMonster(1, 500, "DeerForestAssets/waterspirit.png");
        cardList.add(card9);

        //NATURE CARDS
        AbstractCard card10 = new NatureMonster(50, 50, "DeerForestAssets/bear.png");
        cardList.add(card10);

        AbstractCard card11 = new NatureMonster(100, 100, "DeerForestAssets/deerforest.png");
        cardList.add(card11);

        AbstractCard card12 = new NatureMonster(25, 75, "DeerForestAssets/eagle.png");
        cardList.add(card12);

        AbstractCard card13 = new NatureMonster(75, 25, "DeerForestAssets/lioness.png");
        cardList.add(card13);

        AbstractCard card14 = new NatureMonster(999, 1, "DeerForestAssets/oaky.png");
        cardList.add(card14);

        AbstractCard card15 = new NatureMonster(20, 80, "DeerForestAssets/poisonivy.png");
        cardList.add(card15);

        AbstractCard card16 = new NatureMonster(40, 60, "DeerForestAssets/rhino.png");
        cardList.add(card16);

        AbstractCard card17 = new NatureMonster(20, 20, "DeerForestAssets/snake.png");
        cardList.add(card17);

        AbstractCard card18 = new NatureMonster(20, 100, "DeerForestAssets/trex.png");
        cardList.add(card18);

        AbstractCard card19 = new NatureMonster(50, 50, "DeerForestAssets/turkey.png");
        cardList.add(card19);
        cardList.add(card19);

        //DARKNESS CARDS
        AbstractCard card20 = new DarkMonster(20, 70, "DeerForestAssets/bloodhoundv2.png");
        cardList.add(card20);

        AbstractCard card21 = new DarkMonster(25, 65, "DeerForestAssets/cavetroll.png");
        cardList.add(card21);

        AbstractCard card22 = new DarkMonster(50, 50, "DeerForestAssets/darkmystic.png");
        cardList.add(card22);

        AbstractCard card23 = new DarkMonster(1, 999, "DeerForestAssets/death.png");
        cardList.add(card23);

        AbstractCard card24 = new DarkMonster(100, 100, "DeerForestAssets/madking.png");
        cardList.add(card24);

        AbstractCard card25 = new DarkMonster(75, 30, "DeerForestAssets/shadow.png");
        cardList.add(card25);

        AbstractCard card26 = new DarkMonster(30, 80, "DeerForestAssets/undeadtrex.png");
        cardList.add(card26);

        AbstractCard card27 = new DarkMonster(50, 50, "DeerForestAssets/witch.png");
        cardList.add(card27);

        AbstractCard card28 = new DarkMonster(20, 90, "DeerForestAssets/zombiearcher.png");
        cardList.add(card28);

        //SPELL CARDS
        //BATTERY
        Set<String> typeEffects1 = null;
        List<String> effectCategory1 = new ArrayList<String>();
        effectCategory1.add("Monster");
        List<List<Integer>> effectParams1 = new ArrayList<List<Integer>>();
        ArrayList<Integer> monsterEffect1 = new ArrayList<Integer>();
        monsterEffect1.add(1); //Amount to buff
        monsterEffect1.add(0); //Player to affect
        monsterEffect1.add(1); //Affect health
        monsterEffect1.add(20); //give +20
        monsterEffect1.add(0); //On activation
        monsterEffect1.add(0); //Affect any types
        effectParams1.add(monsterEffect1);
        SpellEffect spellEffect1;
        try {
            spellEffect1 = new SpellEffect(typeEffects1, effectCategory1, effectParams1);
        } catch (Exception e) {
            DeerForest.logger.info("Error making effect battery");
            spellEffect1 = null;
        }
        String filePath1 = "DeerForestAssets/battery.png";
        AbstractCard spell1 = new GeneralSpell(spellEffect1, filePath1);
        cardList.add(spell1);
        cardList.add(spell1);
        cardList.add(spell1);
        cardList.add(spell1);

        //SWORD
        Set<String> typeEffects2 = null;
        List<String> effectCategory2 = new ArrayList<String>();
        effectCategory2.add("Destroy");
        List<List<Integer>> effectParams2 = new ArrayList<List<Integer>>();
        ArrayList<Integer> destroyEffect2 = new ArrayList<Integer>();
        destroyEffect2.add(1); //Amount to destroy
        destroyEffect2.add(1); //affect field
        destroyEffect2.add(1); //Affect opponent
        destroyEffect2.add(0); //destroy monster
        destroyEffect2.add(0); //On activation
        destroyEffect2.add(0); //Affect any types
        effectParams2.add(destroyEffect2);
        SpellEffect spellEffect2;
        try {
            spellEffect2 = new SpellEffect(typeEffects2, effectCategory2, effectParams2);
        } catch (Exception e) {
            DeerForest.logger.info("Error making effect sword");
            spellEffect2 = null;
        }
        String filePath2 = "DeerForestAssets/sword of light.png";
        AbstractCard spell2 = new GeneralSpell(spellEffect2, filePath2);
        cardList.add(spell2);
        cardList.add(spell2);
        cardList.add(spell2);
        cardList.add(spell2);

        //ADD DECK TO PLAYER
		Deck deck = new Deck(cardList);
		deck.shuffle();
		DeerForestPlayer p = new DeerForestPlayer(deck);
		return p;
	}

	
	public int getCurrentPlayer() {
		return mainGame.getCurrentPlayer();
		
	}
}
