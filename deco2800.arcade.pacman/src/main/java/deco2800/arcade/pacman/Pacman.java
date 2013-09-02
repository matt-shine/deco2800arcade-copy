package deco2800.arcade.pacman;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

import deco2800.arcade.client.ArcadeSystem;
import deco2800.arcade.client.GameClient;
import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.model.Game;
import deco2800.arcade.model.Game.ArcadeGame;
import deco2800.arcade.model.Player;





@ArcadeGame(id="pacman")
public class Pacman extends GameClient {

	private OrthographicCamera camera;
	
	private enum GameState {
		READY,
		INPROGRESS,
		GAMEOVER
	}
	
	private GameState gameState;
	public static final int SCREENHEIGHT = 480;
	public static final int SCREENWIDTH = 800;
	
	private ShapeRenderer shapeRenderer;
	private SpriteBatch batch;
	private PacChar player;
	
	//not used yet
	//private NetworkClient networkClient;
	
	
	
	public Pacman(Player player, NetworkClient networkClient) {
		super(player, networkClient);
		// TODO Auto-generated constructor stub
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
				
		//Necessary for rendering
//		shapeRenderer = new ShapeRenderer();
//		font = new BitmapFont();
//		font.setScale(2);
		batch = new SpriteBatch();
		
		//Initialise game state
		gameState = GameState.READY;		
	}
	
	@Override
	public void dispose() {
		super.dispose();
	}

	@Override
	public void pause() {
		super.pause();
	}
	
	@Override
	public void render() {
		
		//Black background
		Gdx.gl.glClearColor(0, 0, 0, 1);
	    Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

	    // updating camera is something we should do once per frame
	    camera.update();
	    //tell these things to use the coordinate system of the camera
	    shapeRenderer.setProjectionMatrix(camera.combined);
	    batch.setProjectionMatrix(camera.combined);
	    
	    //Begin drawing of shapes- first shape should be first argument
	    shapeRenderer.begin(ShapeType.FilledRectangle);
	    	       
	    //End drawing of shapes
	    shapeRenderer.end();
	    
	    //render sprites- put between begin and end
	    batch.begin();
	    player = new PacChar();
	    batch.end();
	    
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
