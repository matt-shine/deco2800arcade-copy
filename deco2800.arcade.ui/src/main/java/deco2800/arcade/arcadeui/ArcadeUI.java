package deco2800.arcade.arcadeui;

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
    private RegisterScreen register = null;
	
	private Screen current = null;

	public ArcadeUI(Player player, NetworkClient networkClient) {
		super(player, networkClient);
	}

    /*
     * ArcadeUI called when starting the arcade, or exiting a game
     * Checks to see whether a user is logged in.
     * If not, changes screen to login screen
     * If a user is logged in, go to the home screen.
     * If register is called from the login screen, go to the register screen
     * If store is called from the home screen, go to the store screen.
     * Whenever you wish to go bck to the home screen, just call arcadeUI and it will sort it out
     */

    @Override
    public void create() {
        ArcadeSystem.openConnection(); // Move this to somewhere more appropriate.

        // Initialise the different screens.
        login = new LoginScreen(this);
        home = new HomeScreen();
        store = new StoreScreen();
        register = new RegisterScreen(this);

        // Check to see if a user is logged in.
        if (player == null) {
            current = login; // No user, go to login screen
        } else {
            current = home;  // There is a user, go to home screen
        }
        this.setScreen(current);

        super.create();
    }

    public void requestScreen(String screen) {
        if (screen.equals("register")) {
            current = register;
        } else if (screen.equals("store")) {
            current = store;
        }
        this.setScreen(current);
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
