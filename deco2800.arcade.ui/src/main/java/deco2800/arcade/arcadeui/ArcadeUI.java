package deco2800.arcade.arcadeui;

import deco2800.arcade.arcadeui.store.GameStore;
import deco2800.arcade.arcadeui.store.StoreScreen;
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
	
	LoginScreen login = null;
	StoreScreen store = null;
	HomeScreen home = null;
    FrontPage main = null;
    RegisterScreen register = null;
    MultiplayerLobby lobby = null;
    BettingWindow betting = null;

	public ArcadeUI(Player player, NetworkClient networkClient) {
		super(player, networkClient);
	}

    @Override
    public void create() {
        // TODO Move this to somewhere more appropriate.
        // FIXME This really needs to be fixed.
        // The connection should be attempted to be opened after a user has pressed login on the loginScreen
        // But I don't know the best way or place to do this - abbjohn
        ArcadeSystem.openConnection();

        // Initialise the different screens.
        login = new LoginScreen(this);
        home = new HomeScreen(this);
        store = new GameStore();
        main = new FrontPage();
        register = new RegisterScreen(this);
        lobby = new MultiplayerLobby(this);
        betting = new BettingWindow(this);

        // Check to see if a user is logged in.
        if (ArcadeSystem.isLoggedIn()) {
            this.setScreen(home);
        } else {
            this.setScreen(login);
        }
        
        if (ArcadeSystem.isMultiplayerEnabled()) {
        	this.setScreen(lobby);
        }

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
	
	public HomeScreen getHome() {
		return home;
	}
	
	public MultiplayerLobby getLobby() {
		return lobby;
	}
	public BettingWindow getBetting() {
		return betting;
	}
}
