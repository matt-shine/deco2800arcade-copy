package deco2800.arcade.towerdefence.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Application;

import deco2800.arcade.client.GameClient;
import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.model.Game;
import deco2800.arcade.model.Player;
import deco2800.arcade.model.Game.ArcadeGame;
import deco2800.arcade.towerdefence.view.CreditsScreen;
import deco2800.arcade.towerdefence.view.GameScreen;
import deco2800.arcade.towerdefence.view.LoreScreen;
import deco2800.arcade.towerdefence.view.MenuScreen;
import deco2800.arcade.towerdefence.view.OptionsScreen;
import deco2800.arcade.towerdefence.view.SplashScreen;

@ArcadeGame(id = "towerdefence")
public class TowerDefence extends GameClient {
	Screen splashScreen;
	public Screen menuScreen, loreScreen, gameScreen, creditsScreen,
			optionsScreen;
	private boolean isPaused = false;
	private static final String LOG = TowerDefence.class.getSimpleName();

	public TowerDefence(Player player, NetworkClient networkClient) {
		super(player, networkClient);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void create() {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		Gdx.app.debug(LOG, "creating");

		super.create();

		splashScreen = new SplashScreen(this);
		menuScreen = new MenuScreen(this);
		gameScreen = new GameScreen(this);
		loreScreen = new LoreScreen(this);
		creditsScreen = new CreditsScreen(this);
		optionsScreen = new OptionsScreen(this);
		setScreen(splashScreen);
		this.getOverlay().setListeners(new Screen() {
			@Override
			public void hide() {
				// Unpause your game here
			}

			@Override
			public void show() {
				// Pause your game here
			}

			@Override
			public void pause() {
			}

			@Override
			public void render(float arg0) {
			}

			@Override
			public void resize(int arg0, int arg1) {
			}

			@Override
			public void resume() {
			}

			@Override
			public void dispose() {
			}
		});
	}

	@Override
	public void dispose() {
		Gdx.app.debug(LOG, "disposing");
		super.dispose();
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void resize(int width, int height) {
		Gdx.app.debug(LOG, "resizing to " + width + "x" + height);
		super.resize(width, height);
	}

	@Override
	public void pause() {
		Gdx.app.debug(LOG, "pausing");
		super.pause();
	}

	@Override
	public void resume() {
		Gdx.app.debug(LOG, "resuming");
		super.resume();
	}

	private static final Game game;
	static {
		game = new Game();
		game.id = "towerdefence";
		game.name = "Tower Defence";
		game.description = "Defend your towers with wit and strategy."
				+ "Endless, fun, fast paced gameplay!";
	}

	public Game getGame() {
		return game;
	}

	public boolean isPaused() {
		return isPaused;
	}

	// Call this when your own user pauses, so you can pop up a pause menu and
	// pause your game at once
	public void setPause(boolean pause) {
		isPaused = pause;
	}

}
