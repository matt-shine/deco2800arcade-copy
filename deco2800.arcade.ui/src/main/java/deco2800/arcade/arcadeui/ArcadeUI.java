package deco2800.arcade.arcadeui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import deco2800.arcade.client.ArcadeSystem;
import deco2800.arcade.client.GameClient;
import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.model.Game;
import deco2800.arcade.model.Game.ArcadeGame;
import deco2800.arcade.model.Game.InternalGame;
import deco2800.arcade.model.Player;


/**
 * This class is the main interface for the arcade.
 * @author Simon
 *
 */
@InternalGame
@ArcadeGame(id="arcadeui")
public class ArcadeUI extends GameClient {
	
	private LoginScreen login = null;
	private StoreScreen store = null;
	private HomeScreen home = null;
	private FrontPage main = null;
	
    @SuppressWarnings("unused")
    private RegisterScreen register = null;
	
	private Screen current = null;

	public ArcadeUI(Player player, NetworkClient networkClient) {
		super(player, networkClient);
	}

    private void chooseScreen() {
		if (player == null) {
			current = login;
		} else if (player.getUsername() == "store") {
			current = store;
		} else if (player.getUsername() == "home") {
			current = main;
		} else {
			current = home;
		}
    }
	
	@Override
	public void create() {
		ArcadeSystem.openConnection();
        login = new LoginScreen();
        home = new HomeScreen();
        store = new StoreScreen();
        main = new FrontPage();
        register = new RegisterScreen();

        chooseScreen();
		this.setScreen(current);
		super.create();
	}

	@Override
	public void dispose() {
		super.dispose();
		current.dispose();
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