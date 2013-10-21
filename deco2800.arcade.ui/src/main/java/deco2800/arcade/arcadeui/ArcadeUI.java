package deco2800.arcade.arcadeui;

import deco2800.arcade.arcadeui.store.StoreHome;
import deco2800.arcade.arcadeui.store.StoreTransactions;
import deco2800.arcade.arcadeui.store.StoreWishlist;
import deco2800.arcade.client.ArcadeSystem;
import deco2800.arcade.client.GameClient;
import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.model.Game;
import deco2800.arcade.model.Game.ArcadeGame;
import deco2800.arcade.model.Game.InternalGame;
import deco2800.arcade.model.Player;
import deco2800.arcade.protocol.lobby.LobbyMessageResponse;

/**
 * This class is the main interface for the arcade.
 * 
 * @author Simon
 * 
 */
@InternalGame
@ArcadeGame(id = "arcadeui")
public class ArcadeUI extends GameClient {

	LoginScreen login = null;
	HomeScreen home = null;
	FrontPage main = null;
	RegisterScreen register = null;
	MultiplayerLobby lobby = null;
	BettingWindow betting = null;
	JoinMatchList multigame = null;
	CreateMatchList multigame2 = null;
	BettingLobby bettingLobby = null;
	private StoreHome storeHome = null;
	private StoreTransactions storeTransactions = null;
	private StoreWishlist storeWishlist = null;

	public ArcadeUI(Player player, NetworkClient networkClient) {
		super(player, networkClient);
	}

	@Override
	public void create() {
		// TODO Move this to somewhere more appropriate.
		// FIXME This really needs to be fixed.
		// The connection should be attempted to be opened after a user has
		// pressed login on the loginScreen
		// But I don't know the best way or place to do this - abbjohn
		ArcadeSystem.openConnection();

		// Initialise the different screens.
		login = new LoginScreen(this);
		register = new RegisterScreen(this);
		home = new HomeScreen(this);
		main = new FrontPage(this, player);
		register = new RegisterScreen(this);
		lobby = new MultiplayerLobby(this, player);
		betting = new BettingWindow(this);
		multigame = new JoinMatchList(this);
		multigame2 = new CreateMatchList(this);
		bettingLobby = new BettingLobby(this);
		
		storeHome = new StoreHome(this, player);
		storeTransactions = new StoreTransactions(this, player);
		storeWishlist = new StoreWishlist(this, player);

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
	public FrontPage getMain() {
		return main;
	}

	public StoreHome getStoreHome() {
		storeHome.show();
		return storeHome;
	}

	public StoreTransactions getStoreTransactions() {
		storeTransactions.show();
		return storeTransactions;
	}

	public StoreWishlist getStoreWishlist() {
		storeWishlist.show();
		return storeWishlist;
	}

	public MultiplayerLobby getLobby() {
		return lobby;
	}

	public BettingWindow getBetting() {
		return betting;
	}

	public JoinMatchList getMultigame() {
		return multigame;
	}

	public CreateMatchList getMultigame2() {
		return multigame2;
	}

	public BettingLobby getBettingLobby() {
		return bettingLobby;
	}
	
	public void displayChat(LobbyMessageResponse response) {
		lobby.displayChat(response);
	}
	
	public void setPlayer(Player player) {
		this.player = player;
		if (lobby != null) { 
			lobby.setPlayer(player);
		}
	}
	public Player getPlayer() {
		return this.player;
	}

}
