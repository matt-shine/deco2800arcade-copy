/*
 * MixMaze
 */
package deco2800.arcade.mixmaze;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

import deco2800.arcade.model.Game;
import deco2800.arcade.model.Game.ArcadeGame;
import deco2800.arcade.model.Player;
import deco2800.arcade.client.GameClient;
import deco2800.arcade.client.network.NetworkClient;

@ArcadeGame(id="mixmaze")
public final class MixMaze extends GameClient {
	Screen splashScreen;
	Screen menuScreen;
	Screen gameScreen;

	private static final String LOG = MixMaze.class.getSimpleName();

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

		splashScreen = new SplashScreen(this);
		menuScreen = new MenuScreen(this);
		gameScreen = new GameScreen(this);
		setScreen(splashScreen);
		Gdx.app.debug(LOG, "Use arrows to move");
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
