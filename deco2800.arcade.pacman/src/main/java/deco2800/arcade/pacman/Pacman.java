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
	    
	    // Outer Exterior West Walls
	    Wall westWall1 = new Wall(1, 300, 50, 200);
	    Wall westWall2 = new Wall(3, 300, 250, 125);
	    Wall westWall3 = new Wall(1, 425, 250, 75);
	    Wall westWall4 = new Wall(3, 300, 325, 125);
	    Wall westWall5 = new Wall(3, 300, 375, 125);
	    Wall westWall6 = new Wall(1, 425, 375, 75);
	    Wall westWall7 = new Wall(3, 300, 450, 125);
	    Wall westWall8 = new Wall(1, 300, 450, 200);
	    
	    // Inner Exterior West Walls
	    Wall westWall9 = new Wall(1, 305, 55, 190);
	    Wall westWall10 = new Wall(3, 305, 245, 125);
	    Wall westWall11 = new Wall(1, 430, 245, 85);
	    Wall westWall12 = new Wall(3, 300, 330, 130);
	    Wall westWall13 = new Wall(3, 300, 370, 130);
	    Wall westWall14 = new Wall(1, 430, 370, 85);
	    Wall westWall15 = new Wall(3, 305, 455, 125);
	    Wall westWall16 = new Wall(1, 305, 455, 190);
	    
	    // North and South Walls
	    Wall southWall1 = new Wall(3, 300, 50, 600);
	    Wall southWall2 = new Wall(3, 305, 55, 590);
	    Wall northWall1 = new Wall(3, 300, 650, 600);
	    Wall northWall2 = new Wall(3, 305, 645, 590);
	    
	    // Outer Exterior East Walls
	    Wall eastWall1 = new Wall(1, 900, 50, 200);
	    Wall eastWall2 = new Wall(3, 775, 250, 125);
	    Wall eastWall3 = new Wall(1, 775, 250, 75);
	    Wall eastWall4 = new Wall(3, 775, 325, 125);
	    Wall eastWall5 = new Wall(3, 775, 375, 125);
	    Wall eastWall6 = new Wall(1, 775, 375, 75);
	    Wall eastWall7 = new Wall(3, 775, 450, 125);
	    Wall eastWall8 = new Wall(1, 900, 450, 200);
	    
	    // Inner Exterior East Walls
	    Wall eastWall9 = new Wall(1, 895, 55, 190);
	    Wall eastWall10 = new Wall(3, 770, 245, 125);
	    Wall eastWall11 = new Wall(1, 770, 245, 85);
	    Wall eastWall12 = new Wall(3, 770, 330, 130);
	    Wall eastWall13 = new Wall(3, 770, 370, 130);
	    Wall eastWall14 = new Wall(1, 770, 370, 85);
	    Wall eastWall15 = new Wall(3, 770, 455, 125);
	    Wall eastWall16 = new Wall(1, 895, 455, 190);
	    
	    //Wall test2 = new Wall(2, 200, 200, 30);
	    //Wall test3 = new Wall(3, 200, 200, 30);
	    //Wall test4 = new Wall(4, 200, 200, 30);
	    shaper.begin(ShapeType.Line);
	    westWall1.render(shaper);
	    westWall2.render(shaper);
	    westWall3.render(shaper);
	    westWall4.render(shaper);
	    westWall5.render(shaper);
	    westWall6.render(shaper);
	    westWall7.render(shaper);
	    westWall8.render(shaper);
	    westWall9.render(shaper);
	    westWall10.render(shaper);
	    westWall11.render(shaper);
	    westWall12.render(shaper);
	    westWall13.render(shaper);
	    westWall14.render(shaper);
	    westWall15.render(shaper);
	    westWall16.render(shaper);
	    
	    southWall1.render(shaper);
	    southWall2.render(shaper);
	    northWall1.render(shaper);
	    northWall2.render(shaper);
	    
	    eastWall1.render(shaper);
	    eastWall2.render(shaper);
	    eastWall3.render(shaper);
	    eastWall4.render(shaper);
	    eastWall5.render(shaper);
	    eastWall6.render(shaper);
	    eastWall7.render(shaper);
	    eastWall8.render(shaper);
	    eastWall9.render(shaper);
	    eastWall10.render(shaper);
	    eastWall11.render(shaper);
	    eastWall12.render(shaper);
	    eastWall13.render(shaper);
	    eastWall14.render(shaper);
	    eastWall15.render(shaper);
	    eastWall16.render(shaper);
	    
	    //test2.render(shaper);
	   // test3.render(shaper);
	    //test4.render(shaper);
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
