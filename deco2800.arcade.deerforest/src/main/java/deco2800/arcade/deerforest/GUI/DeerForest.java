package deco2800.arcade.deerforest.GUI;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.badlogic.gdx.Gdx;
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
 * @author uqjstee8
 *
 */
@ArcadeGame(id="deerforest")
public class DeerForest extends GameClient {
	
	MainGameScreen view;
	MainGame mainGame;
	MainInputProcessor inputProcessor;
	
	public DeerForest(Player player, NetworkClient networkClient){
		super(player, networkClient);
	}
	
	@Override
	public void create() {
		
		super.create();
		
		//start up main game
		GameSystem tempSystem = new GameSystem(createDeerForestPlayer(), createDeerForestPlayer());
		
		//set and run game
		mainGame = new MainGame(tempSystem);
		mainGame.create();
		view = new MainGameScreen(mainGame);
		this.setScreen(view);
		
		//set up input processor
		inputProcessor = new MainInputProcessor(mainGame, view);
		ArcadeInputMux.getInstance().addProcessor(inputProcessor);
	}

	@Override
	public void dispose() {
		super.dispose();
		ArcadeInputMux.getInstance().removeProcessor(inputProcessor);
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
		Gdx.input.setInputProcessor(inputProcessor);
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
	
	private DeerForestPlayer createDeerForestPlayer() {
		ArrayList<AbstractCard> cardList = new ArrayList<AbstractCard>();
		//add monsters
		for(int i = 0; i < 15; i++) {
			Attack a;
			try {
				Set<String> typeEffects = new HashSet<String>();
				typeEffects.add("Water");
				a = new Attack(100, "Fire", typeEffects, null, null);
			} catch (IncorrectEffectException e) {
				a = null;
			}
			List<Attack> atkList = new ArrayList<Attack>();
			atkList.add(a);
			double rand = Math.random();
			AbstractCard card;
			if(rand > 0.8) {
				card = new WaterMonster(100, atkList, "DeerForestAssets/WaterMonsterShell.png");
			} else if(rand > 0.6) {
				card = new FireMonster(100, atkList, "DeerForestAssets/FireMonsterShell.png");
			} else if(rand > 0.4) {
				card = new NatureMonster(100, atkList, "DeerForestAssets/NatureMonsterShell.png");
			} else if(rand > 0.2) {
				card = new DarkMonster(100, atkList, "DeerForestAssets/DarkMonsterShell.png");
			} else {
				card = new LightMonster(100, atkList, "DeerForestAssets/LightMonsterShell.png");
			}
			cardList.add(card);
		}
		//Add general spells
		for(int i = 0; i < 6; i++) {
			SpellEffect spellEffect = null;
			String filePath = "DeerForestAssets/GeneralSpellShell.png";
			AbstractCard spell = new GeneralSpell(spellEffect, filePath);
			cardList.add(spell);
		}
		//add field Spells
		for(int i = 0; i < 6; i++) {
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
