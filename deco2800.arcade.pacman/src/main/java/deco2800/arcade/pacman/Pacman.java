package deco2800.arcade.pacman;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Logger;

import deco2800.arcade.client.ArcadeInputMux;
import deco2800.arcade.client.ArcadeSystem;
import deco2800.arcade.client.GameClient;
import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.model.Game;
import deco2800.arcade.model.Game.ArcadeGame;
import deco2800.arcade.model.Player;
import deco2800.arcade.pacman.PacChar.PacState;




//note: no 'implements ApplicationListener is relevant anywhere in our program,
// as GameClient extends Game which implements it. As far as I can tell
@ArcadeGame(id="pacman")
public class Pacman extends GameClient {
	
	private enum GameState {
		READY,
		RUNNING,
		GAMEOVER
	}
	
	private OrthographicCamera camera;			
	private GameState gameState;
	public static final int SCREENHEIGHT = 480;
	public static final int SCREENWIDTH = 800;	
	private SpriteBatch batch;
	private ShapeRenderer shaper;
	private PacChar player;
	//takes keyboard input
	private InputProcessor controller;
	
	//not used yet
	//private NetworkClient networkClient;
	
	//lets us log stuff, doesn't seem to work yet
	private Logger logger = new Logger("Pacman");
	
	
	public Pacman(Player player, NetworkClient networkClient) {
		super(player, networkClient);
		// TODO is there stuff we need to happen here?
	}	
	
	/**
	 * Creates the game
	 */
	@Override
	public void create() {
		
        //add the overlay listeners
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
        
		super.create();	
		// this guy doesn't show up either. 
		logger.info("Hey, I'm a log message");
		//Initialize camera
		camera = new OrthographicCamera();
		// set resolution
		camera.setToOrtho(false, SCREENWIDTH, SCREENHEIGHT);
		// initialise spriteBatch for drawing things
		batch = new SpriteBatch();		
		shaper = new ShapeRenderer();
		//initialise pacman
		player = new PacChar();
		//initialise receiver for input- use the multiplexer from Arcade
		// because overlay group said to in log messages
		controller = new PacController(player);
		ArcadeInputMux.getInstance().addProcessor(controller);
		//Initialise game state
		gameState = GameState.READY;		
	}
	
	/**
	 * Called when application is closed, helps tidy things up
	 */
	@Override
	public void dispose() {
		super.dispose();
		ArcadeInputMux.getInstance().removeProcessor(controller);
	}

	@Override
	public void pause() {
		super.pause();
	}
	
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
	}
	
	/**
	 * Called continually to draw the screen unless specifically told not to be
	 */
	@Override
	public void render() {		
		//make changes to location of object etc, then draw
		makeChanges();
		
		//Black background
		Gdx.gl.glClearColor(0, 0, 0, 1);
	    Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
	    // updating camera is something we should do once per frame
	    camera.update();
	    //tell the spritebatch to use the coordinate system of the camera
	    batch.setProjectionMatrix(camera.combined);	    
	    // start the drawing
	    batch.begin();
	    // render player pacman 
	    player.render(batch);
	    //end the drawing
	    batch.end();
	    //initialise walls and draw them 
	    // note, this method only allows single pixel width lines, as far as I can tell.
	    // shouldn't be super difficult to make them thicker, but will need a different approach 
	    // (filled shapes probably)
	    // just testing walls at the moment, haven't arranged any
	    Wall test1 = new Wall(1, 200, 200, 30);
	    Wall test2 = new Wall(2, 200, 200, 30);
	    Wall test3 = new Wall(3, 200, 200, 30);
	    Wall test4 = new Wall(4, 200, 200, 30);
	    shaper.begin(ShapeType.Line);
	    test1.render(shaper);
	    test2.render(shaper);
	    test3.render(shaper);
	    test4.render(shaper);
	    shaper.end();	    
	    //do any stuff the superclass normally does for rendering
		super.render();
		
	}
	
	private void startGame() {	
		logger.debug("Game is now running");		
		gameState = GameState.RUNNING;	
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
			game.id = "pacman";
			game.name = "Pac man";
			game.description = "An implementation of the classic arcade game Pac "
			+ "man." + System.lineSeparator() + "Still in progress- additional " + 
			"features may be added later. Note: currently only displays blank screen";
			// game.icon- to be added later once the icon part is fully implemented
		}
	
	public Game getGame() {
		return game;
	}

	public GameState getGameState() {
		return gameState;
	}

	public void setGameState(GameState gameState) {
		this.gameState = gameState;
	}
}
