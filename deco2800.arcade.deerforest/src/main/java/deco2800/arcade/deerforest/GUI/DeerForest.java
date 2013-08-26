package deco2800.arcade.deerforest.GUI;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.badlogic.gdx.Screen;

import deco2800.arcade.model.Game;
import deco2800.arcade.model.Game.ArcadeGame;
import deco2800.arcade.model.Player;
import deco2800.arcade.client.ArcadeInputMux;
import deco2800.arcade.client.GameClient;
import deco2800.arcade.client.UIOverlay;
import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.deerforest.models.cardContainers.Deck;
import deco2800.arcade.deerforest.models.cards.*;
import deco2800.arcade.deerforest.models.effects.Attack;
import deco2800.arcade.deerforest.models.effects.IncorrectEffectException;
import deco2800.arcade.deerforest.models.gameControl.GameSystem;
import deco2800.arcade.deerforest.models.gameControl.DeerForestPlayer;
/**
 * A card game for use in the Arcade
 * @author uqjstee8
 *
 */
@ArcadeGame(id="deerforest")
public class DeerForest extends GameClient implements UIOverlay {
	
	public DeerForest(Player player, NetworkClient networkClient){
		super(player, networkClient);
	}
	
	MainInputProcessor inputProcessor;
	
	@Override
	public void create() {
		
		super.create();
		
		//start up main game
		GameSystem tempSystem = new GameSystem(createDeerForestPlayer(), createDeerForestPlayer());
		
		//set and run game
		MainGame gam = new MainGame(tempSystem);
		gam.create();
		MainGameScreen view = new MainGameScreen(gam);
		this.setScreen(view);
		
		//set up input processor
		inputProcessor = new MainInputProcessor(gam, view);
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
	
	@Override
	public void setListeners(Screen l) {
		;
	}

	@Override
	public void addPopup(String s) {
		System.out.println("Message Popup: " + s);
	}
	
	private DeerForestPlayer createDeerForestPlayer() {
		ArrayList<AbstractCard> cardList = new ArrayList<AbstractCard>();
		for(int i = 0; i < 15; i++) {
			Attack a;
			try {
				Set<String> effect = new HashSet<String>();
				effect.add("Water");
				a = new Attack(100, "Fire", effect, null, null);
			} catch (IncorrectEffectException e) {
				a = null;
			}
			List<Attack> atkList = new ArrayList<Attack>();
			atkList.add(a);
			double rand = Math.random();
			AbstractCard card;
			if(rand > 0.8) {
				card = new WaterMonster(100, atkList);
			} else if(rand > 0.6) {
				card = new FireMonster(100, atkList);
			} else if(rand > 0.4) {
				card = new NatureMonster(100, atkList);
			} else if(rand > 0.2) {
				card = new DarkMonster(100, atkList);
			} else {
				card = new LightMonster(100, atkList);
			}
			cardList.add(card);
		}
		Deck deck = new Deck(cardList);
		DeerForestPlayer p = new DeerForestPlayer(deck);
		return p;
	}
}
