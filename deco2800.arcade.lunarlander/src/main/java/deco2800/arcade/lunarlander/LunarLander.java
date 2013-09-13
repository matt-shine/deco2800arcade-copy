package deco2800.arcade.lunarlander;

import java.util.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

import deco2800.arcade.model.Game;
import deco2800.arcade.model.Game.ArcadeGame;
import deco2800.arcade.model.Player;
import deco2800.arcade.protocol.game.GameStatusUpdate;
import deco2800.arcade.client.ArcadeSystem;
import deco2800.arcade.client.GameClient;
import deco2800.arcade.client.network.NetworkClient;

@ArcadeGame(id="LunarLander")
public class LunarLander extends GameClient {
	
	private OrthographicCamera camera;
	//private GameState gameState;
	public static final int SCREENHEIGHT = 800;
	public static final int SCREENWIDTH = 1200; 
	
	private ShapeRenderer shapeRenderer;
	private SpriteBatch batch;
	private Texture texture;
	private BitmapFont font;
	
	private Texture lander;
	private int acceleration;
	private int initPosition;
	
	private int score;
	private int fuel;
	private int speed;
	private int time;
	private float pixelsPerSecond = 10.0f;
	
	Timer timer;
	
	/**
	 * Basic constructor for Lunar Lander 
	 * @param player The name of the player
	 * @param networkClient The network client for sending/receiving messages to/from the server
	 */
	
	public LunarLander(Player player, NetworkClient networkClient) {
		super(player, networkClient);
        this.networkClient = networkClient;  
	}
	
	/**
	 * Creates the game
	 */
	@Override
	public void create() {
				
		batch = new SpriteBatch();		
		texture = new Texture(Gdx.files.internal("lunarlanderassets/stars.png"));
		lander = new Texture(Gdx.files.internal("lunarlanderassets/lander.png"));
		font = new BitmapFont();
		shapeRenderer = new ShapeRenderer();

		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, SCREENWIDTH, SCREENHEIGHT);
		
		initPosition= 600;
		acceleration=0;
		score = 0;
		fuel = 1000;
		speed = 0;
		time = 0;
		timer = new Timer();
		
		//ball = new Ball();
		//ball.setColor(1, 1, 1, 1);
		
        /*//add the overlay listeners
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
			
        }*/
        
        
	}	
/*
	@Override
	public void dispose() {
		super.dispose();
	}

	@Override
	public void pause() {
		super.pause();
	}

	*//**
	 * Render the current state of the game and process updates
	 */
	@Override
	public void render() {
		 
		// clear screen, create background using texture
	    Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
	    batch.begin();
	    batch.draw(texture, 0, 0, 1200, 800);
	    batch.draw(lander, 550, (initPosition - acceleration), 50, 50);
	    
	    font.setColor(Color.WHITE);
	    font.draw(batch, "Score: " + Integer.toString(score), SCREENWIDTH - 200, SCREENHEIGHT - 40);
	    font.draw(batch, "Remaining fuel: " + Integer.toString(fuel), SCREENWIDTH - 200, SCREENHEIGHT - 60);
	    font.draw(batch, "Current speed: " + Integer.toString(speed), SCREENWIDTH - 200, SCREENHEIGHT - 80);
	    font.draw(batch, "Time spent: " + Integer.toString(time), SCREENWIDTH - 200, SCREENHEIGHT - 100);
	    
	    batch.end();

	    camera.update();
	    
	    shapeRenderer.setProjectionMatrix(camera.combined);
	    batch.setProjectionMatrix(camera.combined);
	    
	    //Begin drawing of shapes
	    shapeRenderer.begin(ShapeType.FilledRectangle);
	 
	    //ball.render(shapeRenderer);
	    
	    //End drawing of shapes
	    shapeRenderer.end();
	    
	    //ball.update(ball);

	    /*	    
	    shapeRenderer.setProjectionMatrix(camera.combined);
	    
	    
	    //End drawing of shapes
	    shapeRenderer.end();
	    */
	    
	   /* switch(gameState) {
	    
	    case READY: //Ready to start a new point
	    	if (Gdx.input.isTouched()) {
	    		startPoint();
	    	}
	    	break;
	    	
	    case INPROGRESS: //Point is underway, ball is moving
	    	
	    case GAMEOVER: //The game has been won, wait to exit
	    	if (Gdx.input.isTouched()) {
	    		gameOver();
	    		ArcadeSystem.goToGame(ArcadeSystem.UI);
	    	}
	    	break;
	    }*/
	    
	    //Simple gravity function, hopefully this will increase logarithmically
	    while(!(acceleration < 0)){
	    	acceleration = acceleration + 1;
	    	acceleration = (acceleration - (acceleration * 2));
	    }
	    
		super.render();
		
	}


	/**
	 * Create an update object to send to the server notifying of a score change or game outcome
	 * @return The Game Status Update.
	 *//*
	private GameStatusUpdate createScoreUpdate() {
		GameStatusUpdate update = new GameStatusUpdate();
		update.gameId = game.id;
		update.username = players[0];
		//TODO Should also send the score!
		return update;
	}

*/
	@Override
	public void resize(int arg0, int arg1) {
		super.resize(arg0, arg1);
	}

	@Override
	public void resume() {
		super.resume();
	}

	
	private static final Game game;
	static {
		game = new Game();
		game.id = "lunarlander";
		game.name = "LunarLander";
        game.description = "Can your Lunar Lander make it to the surface safely?";
	}
	
	public Game getGame() {
		return game;
	}
	
}
