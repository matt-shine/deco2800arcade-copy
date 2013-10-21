package deco2800.arcade.pacman;

import java.util.ArrayList;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.Logger;

import deco2800.arcade.client.AchievementClient;
import deco2800.arcade.client.ArcadeInputMux;
import deco2800.arcade.client.GameClient;
import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.model.Achievement;
import deco2800.arcade.model.AchievementProgress;
import deco2800.arcade.model.Game;
import deco2800.arcade.model.Game.ArcadeGame;
import deco2800.arcade.model.Player;
import deco2800.arcade.utils.AsyncFuture;

/**
 * The main Pacman game class, which sets up the model, view and controller 
 * and forms the LibGdx backend stuff.
 *
 */
@ArcadeGame(id="Pacman")
public class Pacman extends GameClient {
	
	public final int SCREEN_HEIGHT = 720;
	public final int SCREEN_WIDTH = 1280;	
	private final int NUM_GHOSTS = 4;
	public boolean gamePaused;
	
	
	private PacModel model; // model for Pacman	
	private PacView view; // view for Pacman	
	private PacController controller; //takes keyboard input for Pacman

	/** Checks to make sure View isn't set up more than once
	 * View can't be set up normally until the rendering starts because
	 *  it causes NullPointers for the tests when it tries to load Textures */
	private boolean viewNotSetUp; 
	
    private NetworkClient networkClient;
    private AchievementClient achievementClient;
    
		
	//lets us log stuff, left package private so we don't have to make a new one in each class
	Logger logger = new Logger("Pacman");
		
	public Pacman(Player player1, NetworkClient networkClient) {
		super(player1, networkClient);		
		this.networkClient = networkClient; // Once we can merge with master, this will work with new Achievements.
		this.incrementAchievement("pacman.onegame");
	}	
		
	/**
	 * Creates the game
	 */
	@Override
	public void create() {
		
        //add the overlay listeners
        this.getOverlay().setListeners(new Screen() {

			@Override
			public void hide() {
				gamePaused = false;
			}
			 
			@Override
			public void show() {
				gamePaused = true;
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
		super.create();		
		viewNotSetUp = true;		
		model = new PacModel(SCREEN_WIDTH, SCREEN_HEIGHT, NUM_GHOSTS);		
		//initialise receiver for input- use the Arcade Multiplexer
		controller = new PacController(model);
		ArcadeInputMux.getInstance().addProcessor(controller);
		
		
		// Achievement stuff
		AchievementClient achievementClient = 
				new AchievementClient(networkClient);
		AsyncFuture<ArrayList<Achievement>> achievements = achievementClient.getAchievementsForGame(game);
		AsyncFuture<AchievementProgress> playerProgress = achievementClient.getProgressForPlayer(player);
	}
	
	/**
	 * Called when application is closed, helps tidy things up
	 */
	@Override
	public void dispose() {
		super.dispose();
		ArcadeInputMux.getInstance().removeProcessor(controller);
		
		//TODO dispose more stuff here? Perhaps the view things?
	}

	@Override
	public void pause() {
		super.pause();
	}
	
	/**
	 * Called continually to draw the screen unless specifically told not to be
	 */
	@Override
	public void render() {	
		// makes sure view is only set up once
		if (viewNotSetUp) {
			view = new PacView(model);
			viewNotSetUp = false;
		}		
		updateAchievements();		
		// make changes in the model to prepare for rendering if overlay
		// not active and game not over.
		if (!gamePaused && !getModel().getGameMap().isGameOver()) {
			model.prepareDraw();
		}
		
		view.render();
		super.render();			
	}
	
	private void updateAchievements() {
		// Update checks for achievements
		if (getModel().getGameMap().getDotsEaten() == 100){
			this.incrementAchievement("pacman.insatiable");
		}
		
		if (getModel().getGameMap().getDotsEaten() == 244){
			// TODO: some end of game stuff
			this.incrementAchievement("pacman.completionist");
		}
		
		if (getModel().getGameMap().getGhostsEaten() >= 1){
			this.incrementAchievement("pacman.ghostbuster");
		}			
	}
		
	@Override
	public void resize(int arg0, int arg1) {
		super.resize(arg0, arg1);
	}

	@Override
	public void resume() {
		super.resume();
	}
	
	// Game variable for Pacman- used by the arcade
	private static final Game game; 
	static {
			game = new Game();
			game.id = "Pacman";
			game.name = "Pacman";
			game.description = "An implementation of the classic arcade game Pac-man." + 
			System.getProperty("line.separator") + "Still in progress- additional " + 
			"features may be added later.";
			// game.icon- to be added later once the icon part is fully implemented
		} 
	
	public Game getGame() {
		return game;
	}

	public PacController getController() {
		return controller;
	}
	
	public PacModel getModel() {
		return model;
	}
}
