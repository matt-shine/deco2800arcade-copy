package deco2800.arcade.arcadeui;

import com.badlogic.gdx.Screen;

import deco2800.arcade.client.GameClient;
import deco2800.arcade.client.UIOverlay;
import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.model.Game;
import deco2800.arcade.model.Game.InternalGame;
import deco2800.arcade.model.Player;
import deco2800.arcade.model.Game.ArcadeGame;

/**
 * This class is the main interface for the arcade. It can be run as a game,
 * but normally it will be run in parallel with another game as an overlay.
 * @author Simon
 *
 */
@InternalGame
@ArcadeGame(id="arcadeui")
public class ArcadeUI extends GameClient implements UIOverlay {
	   
	private boolean isOverlay = false;

	private Overlay overlay = null;
	@SuppressWarnings("unused")
	private LoginScreen login = null;
	@SuppressWarnings("unused")
	private HomeScreen home = null;
	private Screen current = null;
	
	public ArcadeUI(Player player, NetworkClient networkClient, Boolean isOverlay){
		super(player, networkClient);
		this.isOverlay = isOverlay;
	}
	
	public ArcadeUI(Player player, NetworkClient networkClient){
		this(player, networkClient, false);
	}

	@Override
	public void create() {
		
		if (isOverlay) {
			current = overlay = new Overlay();
		} else if (player == null) {
			current = login = new LoginScreen();
		} else {
			current = home = new HomeScreen();
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


	@Override
	public void setListeners(Screen l) {
		if (this.overlay != null) {
			this.overlay.setCallbacks(l);
		}
	}

	@Override
	public void addPopup(String s) {
		System.out.println("UI Popup: " + s);
	}
		
}
