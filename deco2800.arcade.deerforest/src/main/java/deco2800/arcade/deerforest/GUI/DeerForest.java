package deco2800.arcade.deerforest.GUI;

import java.util.ArrayList;

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
import deco2800.arcade.deerforest.models.effects.Attack;
import deco2800.arcade.deerforest.models.effects.IncorrectEffectException;
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
	boolean inMenu;
	DeckBuilder deckBuilder;
	private DeckBuilderScreen deckBuilderView;
	private DeckBuilderInputProcessor deckInputProcessor;
	
	public DeerForest(Player player, NetworkClient networkClient){
		super(player, networkClient);
	}
	
	@Override
	public void create() {
		
		super.create();
		
		this.inMenu = true;
        
        // set up and run the menu
        mainMenu = new MainMenu(null);
        mainMenu.create();
        System.out.println("Main Menu created");
        
        menuView = new MainMenuScreen(mainMenu);
        this.setScreen(menuView);
        System.out.println("Menu view created");
        
        // set up input processor
        menuInputProcessor = new MainMenuInputProcessor(mainMenu, menuView);
        ArcadeInputMux.getInstance().addProcessor(menuInputProcessor);
        
    }

	@Override
	public void dispose() {
		super.dispose();
		ArcadeInputMux.getInstance().removeProcessor((inMenu) ? menuInputProcessor : inputProcessor);
		
		if (inMenu) {
			mainMenu.dispose();
			menuView.dispose();
		} else {
			mainGame.dispose();	
	        view.dispose();
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
			this.inMenu = false;

			
			// Construct the main game
			GameSystem tempSystem = new GameSystem(createDeerForestPlayer(), createDeerForestPlayer());
	        System.out.println("Game system constructed");
	        
	        //set and run game
			mainGame = new MainGame(tempSystem);
			mainGame.create();
	        System.out.println("Main Game created");

	        view = new MainGameScreen(mainGame);
	        System.out.println("View created (game)");
	        
			this.setScreen(view);


	        // Set the input processor to the main menu
			inputProcessor = new MainInputProcessor(mainGame, view);
			
			ArcadeInputMux.getInstance().addProcessor(inputProcessor);
			
		} else if (scene.equals("deck builder")) {
			this.inMenu = false;
			
			this.deckBuilder = new DeckBuilder(null);
			this.deckBuilder.create();
			
			this.deckBuilderView = new DeckBuilderScreen(deckBuilder);
			
			this.setScreen(deckBuilderView);
			
			this.deckInputProcessor = new DeckBuilderInputProcessor(deckBuilder, deckBuilderView);
			ArcadeInputMux.getInstance().addProcessor(deckInputProcessor);
			
			
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
		for(int i = 0; i < 5; i++) {
			SpellEffect spellEffect = null;
			String filePath = "DeerForestAssets/GeneralSpellShell.png";
			AbstractCard spell = new GeneralSpell(spellEffect, filePath);
			cardList.add(spell);
		}
		//add field Spells
		for(int i = 0; i < 5; i++) {
			SpellEffect spellEffect = null;
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
