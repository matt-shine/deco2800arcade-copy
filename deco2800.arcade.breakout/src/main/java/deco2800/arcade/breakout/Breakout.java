package deco2800.arcade.breakout;

import com.badlogic.gdx.Screen;

import deco2800.arcade.breakout.screens.GameScreen;
import deco2800.arcade.breakout.screens.HelpScreen1;
import deco2800.arcade.breakout.screens.HelpScreen2;
import deco2800.arcade.breakout.screens.LevelScreen1;
import deco2800.arcade.breakout.screens.LevelScreen2;
import deco2800.arcade.breakout.screens.MenuScreen;
import deco2800.arcade.breakout.screens.ModelScreen;
import deco2800.arcade.breakout.screens.SplashScreen;
import deco2800.arcade.client.AccoladeSystem;
import deco2800.arcade.client.AchievementClient;
import deco2800.arcade.client.GameClient;
import deco2800.arcade.client.highscores.HighscoreClient;
import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.model.Game;
import deco2800.arcade.model.Game.ArcadeGame;
import deco2800.arcade.model.Player;

/**
 * The game client
 * 
 * @author Carlie Smits and Naveen Kumar
 * 
 */
@ArcadeGame(id = "Breakout")
public class Breakout extends GameClient {

	private SplashScreen splashScreen;
	private GameScreen gamescreen;
	private MenuScreen menuScreen;
	private LevelScreen1 levelScreen1;
	private LevelScreen2 levelScreen2;
	private HelpScreen1 helpscreen1;
	private ModelScreen modelscreen;
	private HelpScreen2 helpscreen2;

	// Creates private instance variables for each element of the game
	public String player;
	private NetworkClient networkClient;
	private AchievementClient achievementClient;
	private HighscoreClient highscoreUser;
	private AccoladeSystem accolades;
	private Double accoladeBumpCounter;
	private Double accoladeBrickBreak;

	// Screen Parameters
	public static final int SCREENHEIGHT = 720;
	public static final int SCREENWIDTH = 1280;

	/**
	 * Stores the player name and network client. Also creates instances of
	 * shared game mechanics ie achievements, highscore and accolades
	 * 
	 * @param player
	 * @param networkClient
	 */
	public Breakout(Player player, NetworkClient networkClient) {
		super(player, networkClient);
		this.player = player.getUsername();
		this.networkClient = networkClient;
		this.achievementClient = new AchievementClient(networkClient);
		this.highscoreUser = new HighscoreClient(this.player, "Breakout",
				networkClient);
		this.accolades = new AccoladeSystem();
		//accoladeBumpCounter = accolades.fetchID("bumpCount");
		//accoladeBrickBreak = accolades.fetchID("brickBreak");
	}

	/**
	 * Creates the game.
	 */
	@Override
	public void create() {
		super.create();

		// add the overlay listeners
		this.getOverlay().setListeners(new Screen() {

			@Override
			public void dispose() {
			}

			@Override
			public void hide() {
				resume();
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
				pause();
			}

		});
		splashScreen = new SplashScreen(this);
		menuScreen = new MenuScreen(this);
		gamescreen = new GameScreen(this);
		levelScreen1 = new LevelScreen1(this);
		levelScreen2 = new LevelScreen2(this);
		helpscreen1 = new HelpScreen1(this);
		helpscreen2 = new HelpScreen2(this);
		modelscreen = new ModelScreen(this);
		setScreen(splashScreen);
	}

	/**
	 * Properly disposes of game data from memory.
	 */
	@Override
	public void dispose() {

		splashScreen.dispose();
		gamescreen.dispose();
		levelScreen1.dispose();
		menuScreen.dispose();
		helpscreen1.dispose();

		super.dispose();
	}

	/**
	 * Pauses Application
	 */
	@Override
	public void pause() {
		super.pause();
	}

	/**
	 * Resumes Application from a Paused State
	 */
	@Override
	public void resume() {
		super.resume();
	}

	/**
	 * Renders game mechanics.
	 */
	public void render() {
		super.render();

	}

	/**
	 * Return game details
	 * 
	 * @return Game game
	 */
	@Override
	public Game getGame() {
		return game;
	}

	/**
	 * Returns the players name
	 * 
	 * @return String player
	 */
	public String playerName() {
		return player;
	}

	/**
	 * Provides details about the game to the Arcade system.
	 */
	private static final Game game;
	static {
		game = new Game();
		game.id = "breakout";
		game.name = "Breakout";
		game.description = "Bounce the ball off your paddle to keep it from" +
				" falling off the bottom of the screen.";
	}

	/**
	 * Returns the highscore for the user
	 * 
	 * @return HighscoreClient highscoreUser
	 */
	public HighscoreClient getHighScoreClient() {
		return this.highscoreUser;
	}

	/**
	 * Returns the accolades for the user
	 * 
	 * @return AccoladeSystem accolades
	 */
	public AccoladeSystem getAccolade() {
		return this.accolades;
	}

	/**
	 * Returns the accoladeBumpCounter for the user
	 * 
	 * @return int accolades
	 */
	public Double getAccoladeBumpCounter() {
		return accoladeBumpCounter;
	}

	/**
	 * Returns the accoladeBrickBreak for the user
	 * 
	 * @return int accolades
	 */
	public Double getAccoladeBrickBreak() {
		return accoladeBrickBreak;
	}

	/**
	 * Returns the splashscreen
	 * 
	 * @return SplashScreen splashScreen
	 */
	public SplashScreen getSplashScreen() {
		return splashScreen;
	}

	/**
	 * Returns the gamescreen
	 * 
	 * @return GameScreen gamescreen
	 */
	public GameScreen getGamescreen() {
		return gamescreen;
	}

	/**
	 * Returns the MenuScreen
	 * 
	 * @return MenuScreen menuScreen
	 */
	public MenuScreen getMenuScreen() {
		return menuScreen;
	}

	/**
	 * Returns the LevelScreen1
	 * 
	 * @return LevelScreen1 levelScreen1
	 */
	public LevelScreen1 getLevelScreen1() {
		return levelScreen1;
	}

	/**
	 * Returns the LevelScreen2
	 * 
	 * @return LevelScreen2 levelScreen2
	 */
	public LevelScreen2 getLevelScreen2() {
		return levelScreen2;
	}

	/**
	 * Returns the HelpScreen1
	 * 
	 * @return HelpScreen1 helpscreen1
	 */
	public HelpScreen1 getHelpscreen1() {
		return helpscreen1;
	}

	/**
	 * ModelScreen
	 * 
	 * @return ModelScreen modelscreen
	 */
	public ModelScreen getModelscreen() {
		return modelscreen;
	}

	/**
	 * HelpScreen2
	 * 
	 * @return HelpScreen2 helpscreen2
	 */
	public HelpScreen2 getHelpscreen2() {
		return helpscreen2;
	}

}
