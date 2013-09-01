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
/**
 * A Checkers game for testing replay feature
 * @author shewwiii
 *
 */
@ArcadeGame(id="Checkers")
public class Checkers extends GameClient {
	
	private OrthographicCamera camera;
	
	private Square[][] squares;
	private Pieces[] myPieces;
	private Pieces[] theirPieces;
	
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
	 * Basic constructor for the game
	 * @param player The name of the player
	 * @param networkClient The network client for sending/receiving messages to/from the server
	 */
	public Checkers(Player player, NetworkClient networkClient) {
		super(player, networkClient);
		players[0] = player.getUsername();
		players[1] = "Computer"; //TODO eventually the server may send back the opponent's actual username
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
		
		//Initialise camera
		camera = new OrthographicCamera();
		camera.setToOrtho(false, SCREENWIDTH, SCREENHEIGHT);
		
		//making grids
		
		squares = new Square[3][4];
		
		int height = SCREENHEIGHT - 80;
		int width = 20;
		for (int j=0; j<4; j++) {
			width += 50;
			if (width%20 == 0){
				height = SCREENHEIGHT - 80;
			} else {
				height = SCREENHEIGHT - 30;
			}
			for (int i=0; i<3; i++) {
				height = height - 100;
				squares[i][j] = new Square(new Vector2(width, height));
				squares[i][j].setColor(1, 1, 1, 1); //white
			}
		}
		
		//Create the pieces
		myPieces = new UserPieces[4];
		theirPieces = new AIPieces[4];
		for (int i=0; i<4; i++) {
			myPieces[i] = new UserPieces(new Vector2(squares[2][i].bounds.x + 10, squares[2][i].bounds.y +10));
			theirPieces[i] = new AIPieces(new Vector2(squares[0][i].bounds.x + 10, squares[0][i].bounds.y +10));
			myPieces[i].setColor(1, 0, 0, 1); //red
			theirPieces[i].setColor(0, 0, 1, 1); //

		}
		//ball = new Ball();
		//ball.setColor(1, 1, 1, 1);
		
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
	    
	    //Begin drawing of shapes
	    shapeRenderer.begin(ShapeType.FilledRectangle);
	    
	    for (int j=0; j<4; j++) {
	    	for (int i=0; i<3; i++) {
	    		squares[i][j].render(shapeRenderer);
	    	}
		}
	    for (int i=0; i<4; i++) {
	   		myPieces[i].render(shapeRenderer);
	   		theirPieces[i].render(shapeRenderer);
		}
	    
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
	    
	    // Respond to user input and move the ball depending on the game state
	    switch(gameState) {
	    
	    case READY: //Ready to start a new point
	    	if (Gdx.input.isTouched()) {
	    		startPoint();
	    	}
	    	break;
	    	
	    case INPROGRESS: //Point is underway, ball is moving
	    
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

	/**
	 * The point has ended: update scores, reset the ball, check for a winner and move the game state to ready or gameover
	 * @param winner 0 for player 1, 1 for player 2
	 */
	private void endPoint(int winner) {
		//ball.reset();
		scores[winner]++;
		// If we've reached the victory point then update the display
		if (scores[winner] == WINNINGSCORE) {	
		    int loser = winner==1?0:1; //The loser is the player who didn't win!
		    statusMessage = players[winner] + " Wins " + scores[winner] + " - " + scores[loser] + "!";
		    gameState = GameState.GAMEOVER;
		    //Update the game state to the server
		    networkClient.sendNetworkObject(createScoreUpdate());
		    //If the local player has won, send an achievement
		    if (winner == 0) {
                incrementAchievement("pong.winGame");
		    	//TODO Should have more detail in the achievement message
		    }
		} else {
			// No winner yet, get ready for another point
			gameState = GameState.READY;
			statusMessage = "Click to start!";
		}
	}
	
	/**
	 * Create an update object to send to the server notifying of a score change or game outcome
	 * @return The Game Status Update.
	 */
	private GameStatusUpdate createScoreUpdate() {
		GameStatusUpdate update = new GameStatusUpdate();
		update.gameId = game.id;
		update.username = players[0];
		//TODO Should also send the score!
		return update;
	}
	
	/**
	 * Start a new point: start the ball moving and change the game state
	 */
	private void startPoint() {
		//ball.randomizeVelocity();
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

	
	private static final Game game;
	static {
		game = new Game();
		game.id = "checkers";
		game.name = "Checkers";
	}
	
	public Game getGame() {
		return game;
	}
	
}
