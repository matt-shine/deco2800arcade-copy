package deco2800.arcade.arcadeui;

import com.badlogic.gdx.Screen;

import deco2800.arcade.arcadeui.store.StoreHome;
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
	StoreHome store = null;
	HomeScreen home = null;
    FrontPage main = null;
    RegisterScreen register = null;
    MultiplayerLobby lobby = null;
    BettingWindow betting = null;
	MultiGamelist multigame = null;
	Gamewaiting wait = null;
	MultiGamelist2 multigame2 = null;
	BettingLobby bettingLobby = null;



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
        store = new StoreHome(this);
        main = new FrontPage(this);

        register = new RegisterScreen(this);
        lobby = new MultiplayerLobby(this);
        betting = new BettingWindow(this);
		multigame = new MultiGamelist(this);
		wait = new Gamewaiting(this);
		multigame2 = new MultiGamelist2(this);
		bettingLobby = new BettingLobby(this);

        // Check to see if a user is logged in.
        if (ArcadeSystem.isLoggedIn()) {
            this.setScreen(main);
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
	
	public StoreHome getStore() {
		return store;
	}
	
	public MultiplayerLobby getLobby() {
		return lobby;
	}
	public BettingWindow getBetting() {
		return betting;
	}


	public MultiGamelist getMultigame() {
		return multigame;
	}
	
	public Gamewaiting getWait() {
		return wait;
	}
	
	public MultiGamelist2 getMultigame2() {
		return multigame2;
	}

	public BettingLobby getBettingLobby() {
		return bettingLobby;
	}
	
}
