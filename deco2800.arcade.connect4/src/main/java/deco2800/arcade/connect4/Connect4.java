package deco2800.arcade.connect4;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

import deco2800.arcade.model.Game;
import deco2800.arcade.model.Game.ArcadeGame;
import deco2800.arcade.model.Player;
import deco2800.arcade.protocol.game.GameStatusUpdate;
import deco2800.arcade.client.ArcadeSystem;
import deco2800.arcade.client.GameClient;
import deco2800.arcade.client.network.NetworkClient;
import java.util.*;
/**
 * A Connect4 game for use in the Arcade
 * 
 *@author-
 */
@ArcadeGame(id="Connect4")
public class Connect4 extends GameClient {
	
	private OrthographicCamera camera;
	
	private Disc cursorDisc;
	private Table table;
	private enum GameState {
		READY,
		INPROGRESS,
		REPLAY,
		GAMEOVER
	}
	private GameState gameState;
	private int[] scores = new int[2];
	private String[] players = new String[2]; // The names of the players: the local player is always players[0]
	private int playerTurn = 0;
	
	private int[] keyCodes = new int[3];
	
	public static final int WINNINGSCORE = 3;
	public static final int SCREENHEIGHT = 480;
	public static final int SCREENWIDTH = 800;
	
	private final int KEY_LEFT = 0;
	private final int KEY_RIGHT = 1;
	private final int KEY_ENTER = 2;
	
	private ShapeRenderer shapeRenderer;
	private SpriteBatch batch;
	private BitmapFont font;

	private String statusMessage;
	
	//Network client for communicating with the server.
	//Should games reuse the client of the arcade somehow? Probably!
	private NetworkClient networkClient;

	/**
	 * Basic constructor for the Checkers game
	 * @param player The name of the player
	 * @param networkClient The network client for sending/receiving messages to/from the server
	 */
	public Connect4(Player player, NetworkClient networkClient) {
		super(player, networkClient);
		players[0] = player.getUsername();
		players[1] = "Player 2"; 
		
		keyCodes[KEY_LEFT] = 0; //Left Key
		keyCodes[KEY_RIGHT] = 0; //Right Key
		keyCodes[KEY_ENTER] = 0; //Enter Key
		
        this.networkClient = networkClient; //this is a bit of a hack   
	}
	
	/**
	 * Returns random integer
	 */
	
	public static int randInt(int min, int max) {

	    // Usually this can be a field rather than a method variable
	    Random rand = new Random();

	    // nextInt is normally exclusive of the top value,
	    // so add 1 to make it inclusive
	    int randomNum = rand.nextInt((max - min) + 1) + min;

	    return randomNum;
	}
	
	public void checkKeysPressed(){
		if (Gdx.input.isKeyPressed(Keys.LEFT) && keyCodes[KEY_LEFT] == 0) {
			keyCodes[0] = 1;
		} else if (Gdx.input.isKeyPressed(Keys.RIGHT) && keyCodes[KEY_RIGHT] == 0) {
			keyCodes[1] = 1;
		} else if (Gdx.input.isKeyPressed(Keys.ENTER) && keyCodes[KEY_ENTER] == 0) {
			keyCodes[2] = 1;
		}
	}
	
	public void checkKeysReleased(){
		if (!Gdx.input.isKeyPressed(Keys.LEFT) && keyCodes[KEY_LEFT] == 1) {
			keyCodes[0] = 2;
		} else if (!Gdx.input.isKeyPressed(Keys.RIGHT) && keyCodes[KEY_RIGHT] == 1) {
			keyCodes[1] = 2;
		} else if (!Gdx.input.isKeyPressed(Keys.ENTER) && keyCodes[KEY_ENTER] == 1) {
			keyCodes[2] = 2;
		}
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
				//TODO: unpause connect4
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
				//TODO: unpause connect4
			}
			
        });
		
		super.create();
		
		//Initialise camera
		camera = new OrthographicCamera();
		camera.setToOrtho(false, SCREENWIDTH, SCREENHEIGHT);
		
		init();
	}
	
	public void init() {

		//Set the current player's turn
		playerTurn = 0;
		
		//Create the table
		table = new Table();
		table.bounds.x = SCREENWIDTH/2 - Table.WIDTH/2;
		table.SetupDiscs();
		
		//Create the cursor disc
		cursorDisc = new Disc();
		cursorDisc.setState( Disc.PLAYER1 );
		cursorDisc.setPosition((table.bounds.x + cursorDisc.bounds.width + 5), (table.bounds.y + table.bounds.height + 25));
		
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
	
	public void reset() {
		table.resetDiscs();
		this.playerTurn = 0;
		cursorDisc.setState( Disc.PLAYER1 );
		
		//Need to somehow clear the display
	    shapeRenderer.begin(ShapeType.FilledRectangle);
	    table.render(shapeRenderer);
	    shapeRenderer.end();
		/*
		shapeRenderer.begin(ShapeType.FilledCircle);
	    table.renderDiscs(shapeRenderer);
	    shapeRenderer.end();
	    */
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
	 * Render the cursor disc - takes current player
	 * and renders the appropriate colour.
	 */
	
	public void renderCursorDisc(int currentPlayer) {
		cursorDisc.setPosition((table.bounds.x + cursorDisc.bounds.width + 5), (table.bounds.y + table.bounds.height + 25));
		cursorDisc.currentPos = 0;
		if (currentPlayer == 0) {
			cursorDisc.setState( Disc.PLAYER1 );
			
		} else if (currentPlayer == 1) {
			cursorDisc.setState( Disc.PLAYER2 );
		}
		
		//Render the cursor Disc for player 1
	    shapeRenderer.begin(ShapeType.FilledCircle);
	    cursorDisc.render(shapeRenderer);
	    shapeRenderer.end();
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
	    
	    //Render the cursor Disc
	    shapeRenderer.begin(ShapeType.FilledCircle);
	    cursorDisc.render(shapeRenderer);
	    shapeRenderer.end();
	    
	    //Render the table
	    shapeRenderer.begin(ShapeType.FilledRectangle);
	    table.render(shapeRenderer);
	    shapeRenderer.end();
	    
	    //Render the tablediscs
	    shapeRenderer.begin(ShapeType.FilledCircle);
	    table.renderDiscs(shapeRenderer);
	    shapeRenderer.end();
	    
	    //render score
	    batch.begin();
	    font.setColor(Color.YELLOW);
	    font.draw(batch, "Connect 4!", 10, SCREENHEIGHT - 20);
	    font.draw(batch, players[0], SCREENWIDTH/2 - 100, SCREENHEIGHT - 20);
	    font.draw(batch, players[1], SCREENWIDTH/2 + 50, SCREENHEIGHT - 20);
	    font.draw(batch, Integer.toString(scores[0]), SCREENWIDTH/2, SCREENHEIGHT-50);
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
		    
		    case READY: //Ready to start a new game
		    	if (Gdx.input.isTouched()) {
		    		reset();
		    		startPoint();
		    	}
		    	break;
		    	
		    case INPROGRESS: //Game is underway, players can make their moves
		    	if (playerTurn == 0) {
		    		
		    		checkKeysPressed();
		    		checkKeysReleased();
		    		
		    		if(keyCodes[KEY_RIGHT] == 2) {
		    			cursorDisc.moveRight(0);
		    			keyCodes[1] = 0;
		    		} else if(keyCodes[KEY_LEFT] == 2) {
		    			cursorDisc.moveLeft(0);
		    			keyCodes[0] = 0;
		    		} else if(keyCodes[KEY_ENTER] == 2) {
		    			//move the disc the lowest position and render table discs
		    			doMove(playerTurn, cursorDisc.currentPos);
		    			keyCodes[KEY_ENTER] = 0;
		    		}
		    	} else if (playerTurn == 1) {
		    		// Let the computer make a move
		    		
		    		cursorDisc.currentPos = randInt(0,Table.TABLECOLS - 1);
		    		doMove(playerTurn, cursorDisc.currentPos);
		    		
		    	}
		    	break;
		    case REPLAY: //Replaying last game
		    	System.out.println("replay mode");
		    	break;
		    case GAMEOVER: //The game has been won, wait to exit
		    	if (Gdx.input.isTouched()) {
		    		gameOver();
		    		ArcadeSystem.goToGame(ArcadeSystem.UI);
		    	}
		    	if (Gdx.input.isKeyPressed(Keys.ENTER)) {
		    		//Replay the last played game.
		    		reset();
		    		gameState = GameState.REPLAY;
		    	}
		    	break;
	    }
	    
		super.render();
		
	}
	
	private void doMove(int player, int currentPosition) {
		if (table.placeDisc(currentPosition, player)) {
			
			//render the table discs
			shapeRenderer.begin(ShapeType.FilledCircle);
		    table.renderDiscs(shapeRenderer);
		    shapeRenderer.end();
		   
		    if (player == 0){
		    	if (table.checkFieldWinner( Disc.PLAYER1 )) {
		    		gameState = GameState.GAMEOVER;
		    		endPoint( 0 );
		    	}
		    	playerTurn = 1;
		    	renderCursorDisc(1);
		    } else {
		    	if (table.checkFieldWinner( Disc.PLAYER2 )) {
		    		gameState = GameState.GAMEOVER;
		    		endPoint( 1 );
		    	}
		    	playerTurn = 0;
		    	renderCursorDisc(0);
		    }
			
		} else {
			//can't place a disc in desired position - do nothing
		}
	}

	/**
	 * The point has ended: update scores, reset the ball, check for a winner and move the game state to ready or gameover
	 * @param winner 0 for player 1, 1 for player 2
	 */
	private void endPoint(int winner) {
		cursorDisc.reset();
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
		game.id = "connect4";
		game.name = "Connect4";
        game.description = "Fun old connect 4!";
	}
	
	public Game getGame() {
		return game;
	}
	
}
