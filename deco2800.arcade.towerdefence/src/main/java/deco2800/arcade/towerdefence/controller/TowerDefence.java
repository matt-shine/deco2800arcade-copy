package deco2800.arcade.towerdefence.controller;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.math.Vector2;

import deco2800.arcade.client.GameClient;
import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.model.Game;
import deco2800.arcade.model.Player;
import deco2800.arcade.model.Game.ArcadeGame;
import deco2800.arcade.towerdefence.model.AnimationsList;
import deco2800.arcade.towerdefence.model.Grid;
import deco2800.arcade.towerdefence.model.Ship;
import deco2800.arcade.towerdefence.model.Team;
import deco2800.arcade.towerdefence.model.TowerType;
import deco2800.arcade.towerdefence.model.creationclasses.Enemy;
import deco2800.arcade.towerdefence.model.creationclasses.Projectile;
import deco2800.arcade.towerdefence.model.creationclasses.Tower;
import deco2800.arcade.towerdefence.view.CreditsScreen;
import deco2800.arcade.towerdefence.view.GameScreen;
import deco2800.arcade.towerdefence.view.LoreScreen;
import deco2800.arcade.towerdefence.view.MenuScreen;
import deco2800.arcade.towerdefence.view.OptionsScreen;
import deco2800.arcade.towerdefence.view.SplashScreen;

@ArcadeGame(id = "towerdefence")
public class TowerDefence extends GameClient {
	// Fields
	// The rendering animations list static
	public static AnimationsList toRender = new AnimationsList();
	// All Tower Defence screens
	private Screen splashScreen, menuScreen, loreScreen, gameScreen,
			creditsScreen, optionsScreen;
	// Boolean to store if game is paused
	private boolean isPaused = false;
	// Create log file name
	private static final String LOG = TowerDefence.class.getSimpleName();

	// Constructor
	public TowerDefence(Player player, NetworkClient networkClient) {
		super(player, networkClient);
	}

	@Override
	public void create() {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		Gdx.app.debug(LOG, "creating");

		super.create();

		// construct screens
		splashScreen = new SplashScreen(this);
		menuScreen = new MenuScreen(this);
		gameScreen = new GameScreen(this);
		loreScreen = new LoreScreen(this);
		creditsScreen = new CreditsScreen(this);
		optionsScreen = new OptionsScreen(this);
		// Set initial screen
		setScreen(splashScreen);

		// Dan insert game creation code here.
		// Create the Portal to Earth
		Vector2 portalPosition = new Vector2();
		// Create a Ship
		Ship ship = new Ship(gameScreen, portalPosition);
		// Create a Grid
		Grid grid = new Grid(4166, 4166, "Main Grid", 25, ship, portalPosition,
				"src/main/resources/testGrid");
		// Create objects on the Grid
		// Create a Standard Tower
		// Initialise upgradeCosts
		List<Integer> upgradeCosts = new ArrayList<Integer>(3);
		upgradeCosts.add(0);
		upgradeCosts.add(100);
		upgradeCosts.add(100);
		// Create a blank Vector2
		Vector2 blank = new Vector2();
		// Create the projectile to load into the tower
		Projectile projStand = new Projectile(0, 0, grid, blank, 1000,
				Team.Player, null, 10, 0, 0);
		// Create animation filepaths
		List<String> fileStanding = new ArrayList<String>();
		fileStanding
				.add("frames\\TowerStandard\\Standing\\StandardStanding.png");
		List<String> fileDying = new ArrayList<String>();
		for (int i = 0; i < 9; i++) {
			fileDying.add("frames\\TowerStandard\\Death\\StandardDeath000" + i
					+ ".png");
		}
		List<String> fileDeath = new ArrayList<String>();
		for (int i = 0; i < 9; i++) {
			fileDeath.add("frames\\TowerStandard\\Dead\\StandardDead000" + i
					+ ".png");
		}

		// StandardI
		Tower standardI = new Tower(100, 2, 2472, 2083, grid, Team.Player,
				TowerType.STANDARD, 2.0, 9.0, projStand, 100, upgradeCosts,
				fileStanding, fileDying, fileDeath);

		// Draw
		standardI.start();

		// Create a wave of enemies
		// Create animation filepaths
		List<String> fileStanding1 = new ArrayList<String>();
		fileStanding1.add("frames\\SmallBug\\Walk\\SmBugWalk0001.png");
		List<String> fileMoving1 = new ArrayList<String>();
		for (int i = 0; i < 9; i++) {
			fileMoving1.add("frames\\SmallBug\\Walk\\SmBugWalk000" + i
					+ ".png");
		}
		List<String> fileDying1 = new ArrayList<String>();
		for (int i = 0; i < 9; i++) {
			fileStanding.add("frames\\SmallBug\\Death\\SmBugDeath0001" + i
					+ ".png");
		}
		List<String> fileDeath1 = new ArrayList<String>();
		for (int i = 0; i < 9; i++) {
			fileStanding.add("frames\\SmallBug\\Dead\\SmBugDead000" + i
					+ ".png");
		}
		List<String> fileAttacking = new ArrayList<String>();
		for (int i = 0; i < 9; i++) {
			fileStanding.add("frames\\SmallBug\\Attack\\SmBugAttack000" + i
					+ ".png");
		}
		// Enemy constructor
		for (int i = 0; i < 20; i++) {
			Enemy OrdinaryI = new Enemy(15, 2600, 2200, 0, 25, grid, Team.Computer,
					0, 0, 0, 0, 10, fileStanding1, fileMoving1, fileDying1, fileDeath1, fileAttacking);
			//Draw
			OrdinaryI.start();
		}

		// Listener controls
		this.getOverlay().setListeners(new Screen() {
			@Override
			public void hide() {
				// Unpause here
			}

			@Override
			public void show() {
				// Pause here
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
		game.description = "Defend the Starship Arcadia and the people of "
				+ "Earth in the Year 2800 with tower building strategy and"
				+ "fast paced, tactical gameplay!";
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
	
	//Screen getters
	public Screen splashScreen(){
		return splashScreen;
	}
	
	public Screen menuScreen(){
		return menuScreen;
	} 
	
	public Screen loreScreen(){
		return loreScreen;
	}
	
	public Screen gameScreen(){
		return gameScreen;
	}
	
	public Screen creditsScreen(){
		return creditsScreen;
	}

	public Screen optionsScreen(){
		return optionsScreen;
	}

}
