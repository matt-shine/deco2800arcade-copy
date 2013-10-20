package deco2800.arcade.mixmaze;

import deco2800.arcade.client.GameClient;
import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.model.Game;
import deco2800.arcade.model.Game.ArcadeGame;
import deco2800.arcade.model.Player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ArcadeGame(id = "Mixmaze")
public final class MixMaze extends GameClient {

	final Logger logger = LoggerFactory.getLogger(MixMaze.class);

	Skin skin;
	Screen splashScreen;
	Screen menuScreen;
	Screen settingsScreen;
	GameScreen localScreen;
	GameScreen hostScreen;
	GameScreen clientScreen;

	/**
	 * Constructor. Note this constructor will be called before any
	 * gdx.Application exists, and therefore all initialisation should be done
	 * in the method create.
	 * 
	 * @param player
	 *            TODO
	 * @param networkClient
	 *            TODO
	 */
	public MixMaze(Player player, NetworkClient networkClient) {
		super(player, networkClient);
		Achievements.initializeAchievements(this);
	}

	@Override
	public void setScreen(Screen screen) {
		super.setScreen(screen);
		if(screen instanceof GameScreen) {
			Sounds.playAction();
		} else {
			Sounds.playMenu();
		}
	}
	
	@Override
	public void create() {
		super.create();

        //add the overlay listeners
        this.getOverlay().setListeners(new Screen() {

            @Override
            public void render(float arg0) {
            }

            @Override
            public void resize(int width, int height) {
            }

            @Override
            public void show() {
            }

            @Override
            public void hide() {
            }

            @Override
            public void pause() {
            }

            @Override
            public void resume() {
            }

            @Override
            public void dispose() {
            }

        });
		
		/* overlay */
		this.getOverlay().setListeners(new Screen() {

			@Override
			public void dispose() {				
			}

			@Override
			public void hide() {	
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
			public void show() {
			}
		});

		skin = new Skin(Gdx.files.internal("uiskin.json"));
		splashScreen = new SplashScreen(this);
		menuScreen = new MenuScreen(this);
		settingsScreen = new SettingsScreen(this);
		localScreen = new LocalScreen(this);
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
		Sounds.stop();
		splashScreen.dispose();
		menuScreen.dispose();
		localScreen.dispose();
		hostScreen.dispose();
		clientScreen.dispose();
		super.dispose();
		logger.debug("disposed");
	}

	/**
	 * This method is called just before the application is destroyed.
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
		game.description = "Build as much box as you can, "
				+ "stop your opponent from building theirs.";
	}

	@Override
	public Game getGame() {
		return game;
	}
}
