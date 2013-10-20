package deco2800.arcade.pacman;

import java.util.ArrayList;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.Logger;

import deco2800.arcade.client.AchievementClient;
import deco2800.arcade.client.ArcadeInputMux;
import deco2800.arcade.client.ArcadeSystem;
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
	private boolean gamePaused;
	
	
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
		this.incrementAchievement("pacman.startgame");
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
		AchievementClient achievementClient = new AchievementClient(networkClient);
		AsyncFuture<ArrayList<Achievement>> achievements = achievementClient.getAchievementsForGame(game);
		AsyncFuture<AchievementProgress> playerProgress = achievementClient.getProgressForPlayer(player);

		for(Achievement ach : achievements.get()) {
		    int achProgress = playerProgress.get().progressForAchievement(ach);
		    double percentage = 100 * (achProgress / (double)ach.awardThreshold);
		}

		// getting a list of the achievements a player has been awarded
//		ArrayList<String> awardedIDs = playerProgress.awardedAchievementIDs();
//		achievements = achievementClient.getAchievementsForIDs(awardedIDs);

		for(Achievement achh : achievements.get()) {
		    System.out.println(achh.name);
		}
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
	
	/* Commenting this out because it's not being used and is really old
	private void makeChanges() {
		
		 // Respond to user input depending on the game state
	    switch(gameState) {	    
	    //TODO BLARH apparently this commented bit of code isn't even being reached
	    // (certainly none of my log statements are coming up, not sure if that's 
	    // cause I set it up wrongly- can't get them to show up anywhere. But yeah, so
	    // i can't get it to change gamestate
	    case READY: //Ready to initialise the game
//	    	if (controller.keyDown(Keys.ANY_KEY)) {
//	    		startGame();
//	    	}
	    	break;	    	
	    case RUNNING: 
	    	logger.debug("Still running");	
	    	if (controller.keyDown(Keys.ESCAPE)) {
	    		gameState = GameState.GAMEOVER;
	    	}
	    	break;	    	
	    case GAMEOVER: //The game has been won, wait to exit
	    	logger.debug("Game over man, game over!");	
	    	if (controller.keyDown(Keys.ANY_KEY)) {
	    		gameOver();
	    		ArcadeSystem.goToGame(ArcadeSystem.UI);
	    	}
	    	break;
	    }	    
	}*/
	
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
		
		// make changes in the model to prepare for rendering if overlay
		// not active
		if (!gamePaused) {
			model.prepareDraw();
		}
		
		view.render();
		super.render();		
	}
	
	private void startGame() {	
		logger.debug("Game is now running");		
		//gameState = GameState.RUNNING;	
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
