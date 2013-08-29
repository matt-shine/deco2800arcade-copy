package deco2800.arcade.checkers;

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

import deco2800.arcade.model.Game;
import deco2800.arcade.model.Game.ArcadeGame;
import deco2800.arcade.model.Player;
import deco2800.arcade.protocol.game.GameStatusUpdate;
import deco2800.arcade.client.ArcadeSystem;
import deco2800.arcade.client.GameClient;
import deco2800.arcade.client.network.NetworkClient;

@ArcadeGame(id="Checkers")
public class Checkers extends GameClient {
	
	private OrthographicCamera camera;
	
	private enum GameState {
		READY,
		INPROGRESS,
		GAMEOVER
	}
	private GameState gameState;
	private int[] scores = new int[2];
	private String[] players = new String[2]; // The names of the players: the local player is always players[0]

	public static final int WINNINGSCORE = 3;
	public static final int SCREENHEIGHT = 480;
	public static final int SCREENWIDTH = 800;
	
	private ShapeRenderer shapeRenderer;
	private SpriteBatch batch;
	private BitmapFont font;

	private String statusMessage;
	
	//Network client for communicating with the server.
	//Should games reuse the client of the arcade somehow? Probably!
	private NetworkClient networkClient;

	/**
	 * Basic constructor for the Pong game
	 * @param player The name of the player
	 * @param networkClient The network client for sending/receiving messages to/from the server
	 */
	public Checkers(Player player, NetworkClient networkClient) {
		super(player, networkClient);
		players[0] = player.getUsername();
		players[1] = "Player 2"; //TODO eventually the server may send back the opponent's actual username
        this.networkClient = networkClient; //this is a bit of a hack
        

        
        
        
        
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
		
		//Initialise camera
		camera = new OrthographicCamera();
		camera.setToOrtho(false, SCREENWIDTH, SCREENHEIGHT);
		
		//Necessary for rendering
		shapeRenderer = new ShapeRenderer();
		font = new BitmapFont();
		font.setScale(2);
		batch = new SpriteBatch();
		
		//Initialise the scores and game state
		scores[0] = 0;
		scores[1] = 0;
		gameState = GameState.READY;
		statusMessage = "Click to start!";
		
	}

	@Override
	public void dispose() {
		super.dispose();
	}

	@Override
	public void pause() {
		super.pause();
	}

	/**
	 * Render the current state of the game and process updates
	 */
	@Override
	public void render() {
		
		//Black background
		Gdx.gl.glClearColor(0, 0, 0, 1);
	    Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

	    camera.update();
	    
	    shapeRenderer.setProjectionMatrix(camera.combined);
	    batch.setProjectionMatrix(camera.combined);
	    
	    //End drawing of shapes
	    shapeRenderer.end();
	    
	    //render score
	    batch.begin();
	    font.setColor(Color.YELLOW);
	    font.draw(batch, players[0], SCREENWIDTH/2 - 100, SCREENHEIGHT - 20);
	    font.draw(batch, players[1], SCREENWIDTH/2 + 50, SCREENHEIGHT - 20);
	    font.draw(batch, Integer.toString(scores[0]), SCREENWIDTH/2 - 50, SCREENHEIGHT-50);
	    font.draw(batch, Integer.toString(scores[1]), SCREENWIDTH/2 + 75, SCREENHEIGHT-50);
	    
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
		
	}
	

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
		game.id = "checkers";
		game.name = "Checkers";
        game.description = "CHEKKKAS";
	}
	
	public Game getGame() {
		return game;
	}
	
}
