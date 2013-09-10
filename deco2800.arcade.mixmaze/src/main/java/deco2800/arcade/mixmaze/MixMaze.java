/*
 * MixMaze
 */
package deco2800.arcade.mixmaze;

import deco2800.arcade.model.Game;
import deco2800.arcade.model.Game.ArcadeGame;
import deco2800.arcade.model.Player;
import deco2800.arcade.client.GameClient;
import deco2800.arcade.client.network.NetworkClient;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

@ArcadeGame(id="mixmaze")
public final class MixMaze extends GameClient {
	private static final String LOG = MixMaze.class.getSimpleName();

	Screen splashScreen;
	Screen menuScreen;
	Screen gameScreen;
	Screen settingsScreen;
	Skin skin;

	public MixMaze(Player player, NetworkClient networkClient) {
		/*
		 * Note the constructor is called before a gdx.Application
		 * exists, and therefore all initialisation should be done
		 * under create().
		 */
		super(player, networkClient);
	}

	@Override
	public void create() {
		/* Use Application.LOG_NONE to mute all logging. */
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		Gdx.app.debug(LOG, "creating");

		super.create();

		skin = new Skin(Gdx.files.internal("uiskin.json"));
		splashScreen = new SplashScreen(this);
		menuScreen = new MenuScreen(this);
		gameScreen = new GameScreen(this);
		settingsScreen = new SettingsScreen(this);
		
		setScreen(splashScreen);

		Gdx.app.debug(LOG, "Default key bindings");
		Gdx.app.debug(LOG, "Player 1: W, S, A, D to move, "
				+ "G switch action, H use action");
		Gdx.app.debug(LOG, "Player 2: arrow keys to move"
				+ "NUM_5 switch action, NUM_6 use action");
	}

	@Override
	public void resize(int width, int height) {
		Gdx.app.debug(LOG, "resizing to "
				+ width + "x" + height);
		super.resize(width, height);
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void dispose() {
		Gdx.app.debug(LOG, "disposing");
		splashScreen.dispose();
		menuScreen.dispose();
		gameScreen.dispose();
		super.dispose();
	}

	@Override
	public void pause() {
		/*
		 * This method is called just before the application
		 * is destroyed.
		 */
		Gdx.app.debug(LOG, "pausing");
		super.pause();
	}

	@Override
	public void resume() {
		/* This method will never be called on the desktop. */
		Gdx.app.debug(LOG, "resuming");
		super.resume();
	}

	private static final Game game;
	static {
		game = new Game();
		game.id = "mixmaze";
		game.name = "Mix Maze";
	}

	@Override
	public Game getGame() {
		return game;
	}
}
