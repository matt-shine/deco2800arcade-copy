package deco2800.arcade.pacman;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

import deco2800.arcade.client.ArcadeSystem;
import deco2800.arcade.client.GameClient;
import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.model.Game;
import deco2800.arcade.model.Game.ArcadeGame;
import deco2800.arcade.model.Player;




//note: no 'implements ApplicationListener is relevant anywhere in our program,
// as GameClient extends Game which implements it. As far as I can tell
@ArcadeGame(id="pacman")
public class Pacman extends GameClient {
	
	private enum GameState {
		READY,
		INPROGRESS,
		GAMEOVER
	}
	
	private OrthographicCamera camera;			
	private GameState gameState;
	public static final int SCREENHEIGHT = 480;
	public static final int SCREENWIDTH = 800;	
	private SpriteBatch batch;
	private ShapeRenderer shaper;
	private PacChar player;
	
	//not used yet
	//private NetworkClient networkClient;
	
	
	
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
		//Initialize camera
		camera = new OrthographicCamera();
		// set resolution
		camera.setToOrtho(false, SCREENWIDTH, SCREENHEIGHT);
		// initialise spriteBatch for drawing things
		batch = new SpriteBatch();		
		shaper = new ShapeRenderer();
		//Initialise game state
		gameState = GameState.READY;		
	}
	
	/**
	 * Called when application is closed, helps tidy things up
	 */
	@Override
	public void dispose() {
		super.dispose();
		batch.dispose();
		shaper.dispose();
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
		//Black background
		Gdx.gl.glClearColor(0, 0, 0, 1);
	    Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
	    // updating camera is something we should do once per frame
	    camera.update();
	    //tell the spritebatch to use the coordinate system of the camera
	    batch.setProjectionMatrix(camera.combined);
	    //initialise and render player pacman (should we just give the constructor 
	    // the sprite batch rather than sending it in the render method everytime?
	    player = new PacChar();
	    // start the drawing
	    batch.begin();
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
	    
	    // Respond to user input depending on the game state
	    switch(gameState) {	    
	    case READY: //Ready to initialise the game
	    	if (Gdx.input.isTouched()) {
	    		startPoint();
	    	}
	    	break;	    	
	    case INPROGRESS: 
	    	break;	    	
	    case GAMEOVER: //The game has been won, wait to exit
	    	if (Gdx.input.isTouched()) {
	    		gameOver();
	    		ArcadeSystem.goToGame(ArcadeSystem.UI);
	    	}
	    	break;
	    }	    
	    //do any stuff the superclass normally does for rendering
		super.render();
		
	}
	
	private void startPoint() {		
		gameState = GameState.INPROGRESS;
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
}
