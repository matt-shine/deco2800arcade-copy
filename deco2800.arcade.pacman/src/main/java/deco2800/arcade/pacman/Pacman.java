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
	private BitmapFont font;
	
	private NetworkClient networkClient;
	private String statusMessage;
	
	
	
	public Pacman(Player player, NetworkClient networkClient) {
		super(player, networkClient);
		// TODO Auto-generated constructor stub
	}

	// Game variable for Pacman- used by the arcade
	private static final Game game;
	static {
		game = new Game();
		game.id = "pacman";
		game.name = "Pac man";
		game.description = "An implementation of the classic arcade game Pac "
		+ "man." + System.lineSeparator() + "Still in progress- additional " + 
		"features may be added later. Note: will not currently run.";
		// game.icon- to be added later once the icon part is fully implemented
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
				//TODO: unpause pong
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
				//TODO: unpause pong
			}
			
        });
        
        
		
		super.create();
		
		//Initialize camera
		camera = new OrthographicCamera();
		camera.setToOrtho(false, SCREENWIDTH, SCREENHEIGHT);
		
		// This area is used for creating objects (sprites and user controlled thingies)
		
		
		
		//Necessary for rendering
		shapeRenderer = new ShapeRenderer();
		font = new BitmapFont();
		font.setScale(2);
		batch = new SpriteBatch();
		
		//Initialise the scores and game state
		
		
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

	    camera.update();
	    
	    shapeRenderer.setProjectionMatrix(camera.combined);
	    batch.setProjectionMatrix(camera.combined);
	    
	    //Begin drawing of shapes
	    shapeRenderer.begin(ShapeType.FilledRectangle);
	    
	    
	    
	    //End drawing of shapes
	    shapeRenderer.end();
	    
	    //render score
	    batch.begin();
	    font.setColor(Color.YELLOW);
	    //font.draw(batch, players[0], SCREENWIDTH/2 - 100, SCREENHEIGHT - 20);
	    //font.draw(batch, players[1], SCREENWIDTH/2 + 50, SCREENHEIGHT - 20);
	    //font.draw(batch, Integer.toString(scores[0]), SCREENWIDTH/2 - 50, SCREENHEIGHT-50);
	    //font.draw(batch, Integer.toString(scores[1]), SCREENWIDTH/2 + 75, SCREENHEIGHT-50);
	    
	    //If there is a current status message (i.e. if the game is in the ready or gameover state)
	    // then show it in the middle of the screen
	    if (statusMessage != null) {
	    	font.setColor(Color.WHITE);
	    	font.draw(batch, statusMessage, SCREENWIDTH/2 - 100, SCREENHEIGHT-100);
	    	if (gameState == GameState.GAMEOVER) {
	    		font.draw(batch, "Click to exit", SCREENWIDTH/2 - 100, SCREENHEIGHT - 200);
	    	}
	    }
	    batch.end();
	    
	    // Respond to user input and move the ball depending on the game state
	    switch(gameState) {
	    
	    case READY: //Ready to start a new point
	    	if (Gdx.input.isTouched()) {
	    		startPoint();
	    	}
	    	break;
	    	
	    /** case INPROGRESS: //Point is underway, ball is moving
	    	//Move the left paddle (mouse)
	    	leftPaddle.update(ball);
	    	
	    	//Move the right paddle (automatic)
	    	rightPaddle.update(ball);
	    	
	    	//Move the ball
	    	//ball.bounds.x -= ball.velocity.x * Gdx.graphics.getDeltaTime();
	    	ball.move(Gdx.graphics.getDeltaTime());
	    	//If the ball hits a paddle then bounce it
	    	if (ball.bounds.overlaps(leftPaddle.bounds) || ball.bounds.overlaps(rightPaddle.bounds)) {
	    		ball.bounceX();
	    	}
	    	//Bounce off the top or bottom of the screen
	    	if (ball.bounds.y <= 0 || ball.bounds.y >= SCREENHEIGHT-Ball.WIDTH) {
	    		ball.bounceY();
	    	}
	    	
	    	//If the ball gets to the left edge then player 2 wins
	    	if (ball.bounds.x <= 0) {
	    		endPoint(1);
	    	} else if (ball.bounds.x + Ball.WIDTH > SCREENWIDTH) { 
	    		//If the ball gets to the right edge then player 1 wins
	    		endPoint(0);
	    	}
	    	break; */
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
		statusMessage = null;
	}
	
	@Override
	public void resize(int arg0, int arg1) {
		super.resize(arg0, arg1);
	}

	@Override
	public void resume() {
		super.resume();
	}
	
	
	public Game getGame() {
		return game;
	}
}
