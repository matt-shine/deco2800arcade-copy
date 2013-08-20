package deco2800.arcade.hunter;

import com.badlogic.gdx.scenes.scene2d.InputListener;

import deco2800.arcade.hunter.Screens.GameOverScreen;
import deco2800.arcade.hunter.Screens.GameScreen;
import deco2800.arcade.hunter.Screens.MenuScreen;
import deco2800.arcade.model.Game;
import deco2800.arcade.model.Game.ArcadeGame;
import deco2800.arcade.model.Player;
import deco2800.arcade.client.GameClient;
import deco2800.arcade.client.network.NetworkClient;
/**
 * A Hunter game for use in the Arcade
 * @author jsteel8, Nessex
 *
 */
@ArcadeGame(id="hunter")
public class Hunter extends GameClient {	
	private String username = new String();
	private MenuScreen menuScreen;
	private GameScreen gameScreen;
	private GameOverScreen gameOverScreen;
	
	public int screenWidth, screenHeight;
	
	/**
	 * Basic constructor for the Hunter game
	 * @param userName The name of the player
	 * @param client The network client for sending/receiving messages to/from the server
	 */
	public Hunter(Player player, NetworkClient networkClient) {
		super(player, networkClient);
		username = player.getUsername();
	}
	
	/**
	 * Creates the game
	 */
	@Override
	public void create() {
		screenWidth = 640;
		screenHeight = 480;
		
		menuScreen = new MenuScreen(this);
		gameScreen = new GameScreen(this);
		gameOverScreen = new GameOverScreen(this);
		
		
		setScreen(menuScreen);
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

	/**
	 * Render the current state of the game and process updates
	 */
	@Override
	public void render() {

		super.render();	
	}

	@Override
	public void resize(int arg0, int arg1) {
		super.resize(arg0, arg1);
	}

	@Override
	public void resume() {
		super.resume();
	}
	
	private static final Game game;
	static {
		game = new Game();
		game.id = "Hunter";
		game.name = "Hunter";
	}
	
	public Game getGame() {
		return game;
	}
	
}
