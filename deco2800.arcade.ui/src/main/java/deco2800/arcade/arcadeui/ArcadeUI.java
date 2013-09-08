package deco2800.arcade.arcadeui;

import com.badlogic.gdx.Screen;

import deco2800.arcade.client.ArcadeSystem;
import deco2800.arcade.client.GameClient;
import deco2800.arcade.client.MultiplayerTest;
import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.model.Game;
import deco2800.arcade.model.Game.InternalGame;
import deco2800.arcade.model.Player;
import deco2800.arcade.model.Game.ArcadeGame;

/**
 * This class is the main interface for the arcade.
 * @author Simon
 *
 */
@InternalGame
@ArcadeGame(id="arcadeui")
public class ArcadeUI extends GameClient {
	   
	@SuppressWarnings("unused")
	private LoginScreen login = null;
	@SuppressWarnings("unused")
	private HomeScreen home = null;
	@SuppressWarnings("unused")
	private MultiplayerLobby Lobby = null;
	private Screen current = null;

	public ArcadeUI(Player player, NetworkClient networkClient) {
		super(player, networkClient);
	}
	
	@Override
	public void create() {
		
		ArcadeSystem.openConnection();
		
		if (player == null) {
			current = login = new LoginScreen();
		} else {
			if(ArcadeSystem.isMultiplayerEnabled()){
				current = Lobby = new MultiplayerLobby();
				new MultiplayerTest(networkClient);
			}else{
				current = home = new HomeScreen();
			}

		}
		
		this.setScreen(current);
		
		
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
	public void render() {
		super.render();
	}

	@Override
	public void resume() {
		super.resume();
	}

	private static final Game game;
	static {
		game = new Game();
		game.id = "arcadeui";
		game.name = "Arcade UI";
	}

	public Game getGame() {
		return game;
	}
		
}
