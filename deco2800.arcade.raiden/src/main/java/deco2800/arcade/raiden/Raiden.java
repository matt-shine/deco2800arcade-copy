package deco2800.arcade.raiden;


import deco2800.arcade.model.Game;
import deco2800.arcade.model.Game.ArcadeGame;
import deco2800.arcade.model.Player;
import deco2800.arcade.client.GameClient;
import deco2800.arcade.client.network.NetworkClient;

/**
 * A raiden game for use in the Arcade
 * @author Team lion
 *
 */
@ArcadeGame(id="Raiden")
public class Raiden extends GameClient {

	/**
	 * The constructor for game raiden.
	 * @param player
	 * @param networkClient
	 */
	//private String statusMessage;
	
	public Raiden(Player player, NetworkClient networkClient) {
		super(player, networkClient);
		GameFrame raiden = new GameFrame();
	}
	
	public void resume() {
		//super.resume();
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