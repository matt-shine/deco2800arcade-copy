package deco2800.arcade.raiden;

import com.badlogic.gdx.Screen;
import deco2800.arcade.model.Game;
import deco2800.arcade.model.Game.ArcadeGame;
import deco2800.arcade.model.Player;
import deco2800.arcade.client.GameClient;
import deco2800.arcade.client.network.NetworkClient;

/**
 * A raiden game for use in the Arcade
 * @author team lion
 *
 */
@ArcadeGame(id="Raiden")
public class Raiden extends GameClient {
	
	//private OrthographicCamera camera;
	// The names of the players: the local player is always players[0]
	private String[] players = new String[2]; 
	
	//Network client for communicating with the server.
	//Should games reuse the client of the arcade somehow? Probably!
	private NetworkClient networkClient;

	private GameFrame gameFrame;

	//public static final int SCREENHEIGHT = 480;
	//public static final int SCREENWIDTH = 800;
	//private enum GameState {
	//	READY,
	//	INPROGRESS,
	//}
	//private GameState gameState;
	/**
	 * The constructor for game raiden.
	 * @param player
	 * @param networkClient
	 */
	//private String statusMessage;
	
	public Raiden(Player player, NetworkClient networkClient) {
		super(player, networkClient);
		players[0] = player.getUsername();
		players[1] = "Player 2"; //TODO eventually the server may send back the opponent's actual username
        this.networkClient = networkClient; //this is a bit of a hack
	}
	
	/**
	 * Creates the game
	 */
	@Override
	public void create() {
		//add the overlay listeners
		
		
		this.getOverlay().setListeners(new Screen() {

			@Override
			public void dispose() {
			}

			@Override
			public void hide() {
				//TODO: unpause pong
			}

			@Override
			public void pause() {
			}

			@Override
			public void render(float arg0) {
			}

			@Override
			public void resize(int arg0, int arg1) {
			}

			@Override
			public void resume() {
			}

			@Override
			public void show() {
				//TODO: unpause pong
			}
			
        });
		super.create();
		this.gameFrame = new GameFrame();
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
		//new GameFrame();
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
		game.id = "Raiden";
		game.name = "Raiden";
        game.description = "Flight Fighter";
	}
	
	public Game getGame() {
		return game;
	}
}