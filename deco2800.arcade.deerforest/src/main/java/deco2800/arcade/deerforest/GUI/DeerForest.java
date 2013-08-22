package deco2800.arcade.deerforest.GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

import deco2800.arcade.model.Game;
import deco2800.arcade.model.Game.ArcadeGame;
import deco2800.arcade.model.Player;
import deco2800.arcade.client.GameClient;
import deco2800.arcade.client.UIOverlay;
import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.deerforest.models.gameControl.GameSystem;
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
		GameSystem tempSystem = new GameSystem(null, null);
		
		//set and run game
		MainGame gam = new MainGame(tempSystem);
		gam.create();
		MainGameScreen view = new MainGameScreen(gam);
		this.setScreen(view);
		
		//set up input processor
		inputProcessor = new MainInputProcessor(gam, view);
<<<<<<< HEAD
		//ArcadeInputMux.getInstance().addProcessor(inputProcessor);
		Gdx.input.setInputProcessor(inputProcessor);
=======
>>>>>>> 0d3607842582c43bb7ecd76b1f820e11e864bd1f
	}

	@Override
	public void dispose() {
		super.dispose();
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
	
	@Override
	public void setListeners(Screen l) {
		;
	}

	@Override
	public void addPopup(String s) {
		System.out.println("Message Popup: " + s);
	}
}
