package deco2800.arcade.accolades;


import deco2800.arcade.model.Game;
import deco2800.arcade.model.Game.ArcadeGame;
import deco2800.arcade.model.Player;
import deco2800.arcade.client.GameClient;
import deco2800.arcade.client.network.NetworkClient;

/**
 * Statistics for use in the Arcade
 * @author Team Kangaroo Fans
 *
 */
@ArcadeGame(id="Statistics")
public class Statistics extends GameClient {

	
	
	public Statistics(Player player, NetworkClient networkClient) {
		super(player, networkClient);
		MainWindow m = new MainWindow();
	}
	
	public void resume() {
		super.resume();
	}
	
	private static final Game game;
	static {
		game = new Game();
		game.id = "Statistics";
		game.name = "Statistics";
        game.description = "Statistics";
	}
	
	public Game getGame() {
		return game;
	}
}