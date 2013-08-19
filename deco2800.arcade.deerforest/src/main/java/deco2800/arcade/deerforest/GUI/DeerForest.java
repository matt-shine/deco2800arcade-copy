package deco2800.arcade.deerforest.GUI;

import com.badlogic.gdx.Gdx;

import deco2800.arcade.model.Game;
import deco2800.arcade.model.Game.ArcadeGame;
import deco2800.arcade.model.Player;
import deco2800.arcade.client.GameClient;
import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.deerforest.models.gameControl.GameSystem;
/**
 * A card game for use in the Arcade
 * @author uqjstee8
 *
 */
@ArcadeGame(id="deerforest")
public class DeerForest extends GameClient {
	
	public DeerForest(Player player, NetworkClient networkClient){
		super(player, networkClient);
	}

	@Override
	public void create() {
		
		//start up main game
		GameSystem tempSystem = new GameSystem(null, null);
		
		//set and run game
		MainGame gam = new MainGame(tempSystem);
		gam.create();
		MainGameScreen view = new MainGameScreen(gam);
		this.setScreen(view);
		
		//set up input processor
		MainInputProcessor inputProcessor = new MainInputProcessor(gam, view);
		Gdx.input.setInputProcessor(inputProcessor);
		
		super.create();
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

	private static final Game game;
	static {
		game = new Game();
		game.id = "deerforest";
		game.name = "Deer Forest";
	}

	public Game getGame() {
		return game;
	}
	
}
