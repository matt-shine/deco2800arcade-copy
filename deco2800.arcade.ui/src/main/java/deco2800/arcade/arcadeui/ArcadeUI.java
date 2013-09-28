package deco2800.arcade.arcadeui;

import deco2800.arcade.client.ArcadeSystem;
import deco2800.arcade.client.GameClient;
import deco2800.arcade.client.MultiplayerTest;
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
	
<<<<<<< HEAD
	@SuppressWarnings("unused")
	private LoginScreen login = null;
	@SuppressWarnings("unused")
	private StoreScreen store = null;
	@SuppressWarnings("unused")
	private HomeScreen home = null;

	@SuppressWarnings("unused")
	private MultiplayerLobby Lobby = null;

    @SuppressWarnings("unused")
    private AccMgtScreen accMgt = null;
	private Screen current = null;
=======
	LoginScreen login = null;
	StoreScreen store = null;
	HomeScreen home = null;
    FrontPage main = null;
    RegisterScreen register = null;
>>>>>>> origin/master

	public ArcadeUI(Player player, NetworkClient networkClient) {
		super(player, networkClient);
	}
<<<<<<< HEAD
	
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

			//current = home = new HomeScreen();
			//current = store = new StoreScreen();
		}
		
		this.setScreen(current);
		
		
		super.create();
	}
=======

    @Override
    public void create() {
        // TODO Move this to somewhere more appropriate.
        // FIXME This really needs to be fixed.
        // The connection should be attempted to be opened after a user has pressed login on the loginScreen
        // But I don't know the best way or place to do this - abbjohn
        ArcadeSystem.openConnection();

        // Initialise the different screens.
        login = new LoginScreen(this);
        home = new HomeScreen();
        store = new StoreScreen();
        main = new FrontPage();
        register = new RegisterScreen(this);

        // Check to see if a user is logged in.
        if (ArcadeSystem.isLoggedIn()) {
            this.setScreen(home);
        } else {
            this.setScreen(login);
        }

        super.create();
    }
>>>>>>> origin/master

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
