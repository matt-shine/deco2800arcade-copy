package deco2800.arcade.raiden;

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
@ArcadeGame(id="raiden")
public class raiden extends GameClient {
	
	private String[] players = new String[2]; // The names of the players: the local player is always players[0]
	//Network client for communicating with the server.
	//Should games reuse the client of the arcade somehow? Probably!
	private NetworkClient networkClient;
	private enum GameState {
		READY,
		INPROGRESS,
		GAMEOVER
	}
	
	public raiden(Player player, NetworkClient networkClient) {
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
		game.id = "raiden";
		game.name = "raiden";
        game.description = "Flight Fighter";
	}
	
	public Game getGame() {
		return game;
	}
	
}