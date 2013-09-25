/*
 * MixMaze
 */
package deco2800.arcade.mixmaze;

import deco2800.arcade.client.GameClient;
import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.model.Game;
import deco2800.arcade.model.Game.ArcadeGame;
import deco2800.arcade.model.Player;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ArcadeGame(id="mixmaze")
public final class MixMaze extends GameClient {

	final Logger logger = LoggerFactory.getLogger(MixMaze.class);

	Skin skin;
	Screen splashScreen;
	Screen menuScreen;
	Screen settingsScreen;
	GameScreen singleScreen;
	GameScreen hostScreen;
	GameScreen clientScreen;

	/**
	 * Constructor. Note this constructor will be called before any
	 * gdx.Application exists, and therefore all initialisation should
	 * be done in the method create.
	 *
	 * @param player	TODO
	 * @param networkClient	TODO
	 */
	public MixMaze(Player player, NetworkClient networkClient) {
		super(player, networkClient);
	}

	@Override
	public void create() {
		super.create();

		skin = new Skin(Gdx.files.internal("uiskin.json"));
		splashScreen = new SplashScreen(this);
		menuScreen = new MenuScreen(this);
		settingsScreen = new SettingsScreen(this);
		singleScreen = new SingleScreen(this);
		hostScreen = new HostScreen(this);
		clientScreen = new ClientScreen(this);

		setScreen(splashScreen);
	}

	@Override
	public void resize(int width, int height) {
		logger.debug("window resized to {}x{}", width, height);
		super.resize(width, height);
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void dispose() {
		splashScreen.dispose();
		menuScreen.dispose();
		singleScreen.dispose();
		hostScreen.dispose();
		clientScreen.dispose();
		super.dispose();
		logger.debug("disposed");
	}

	/**
	 * This method is called just before the application
	 * is destroyed.
	 */
	@Override
	public void pause() {
		logger.debug("paused");
		super.pause();
	}

	/**
	 * This method will never be called on the desktop.
	 */
	@Override
	public void resume() {
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
