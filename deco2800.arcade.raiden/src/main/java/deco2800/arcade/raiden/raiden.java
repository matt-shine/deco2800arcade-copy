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
	
	public raiden(Player player, NetworkClient networkClient) {
		super(player, networkClient);
		// TODO Auto-generated constructor stub
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