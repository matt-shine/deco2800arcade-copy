package deco2800.arcade.connect4;

import deco2800.arcade.client.replay.*;
import deco2800.arcade.client.network.listener.ReplayListener;

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
 * A Connect4 game for testing of replay handler
 * 
 */
@ArcadeGame(id="Connect4")
public class Connect4 extends GameClient {
	private OrthographicCamera camera;
	
	private Disc cursorDisc;
	private Table table;
	private Buttons buttons;
	private enum GameState {
		READY,
		INPROGRESS,
		REPLAY,
		GAMEOVER
	}
	private GameState gameState;
	// The names of the players: the local player is always players[0]
	private String[] players = new String[2];
	private int playerTurn = 0;
	
	private int[] keyCodes = new int[3];
	private boolean isReplaying = false;
	
	public static final int WINNINGSCORE = 3;
	public static final int SCREENHEIGHT = 480;
	public static final int SCREENWIDTH = 800;
	
	private static final int AI_DELAY = 200;
	
	private static final int KEY_LEFT = 0;
	private static final int KEY_RIGHT = 1;
	private static final int KEY_ENTER = 2;
	
	private ShapeRenderer shapeRenderer;
	private SpriteBatch batch;
	private BitmapFont font;

	private String statusMessage;
	
	private ReplayHandler replayHandler;
	private ReplayListener replayListener;
	
	//Network client for communicating with the server.
	private NetworkClient networkClient;
	
	private long nextComputerMove;
	private int nextComputerCol = -1;

	/**
	 * Basic constructor for the Connect4 game
	 * @param player The name of the player
	 * @param networkClient The network client for sending/receiving messages to/from the server
	 */
	public Connect4(Player player, NetworkClient networkClient) {
		super(player, networkClient);
		players[0] = player.getUsername();
		players[1] = "Computer"; 
		
		keyCodes[KEY_LEFT] = 0; //Left Key
		keyCodes[KEY_RIGHT] = 0; //Right Key
		keyCodes[KEY_ENTER] = 0; //Enter Key
		
        this.networkClient = networkClient;
        
        //Setup the replay handler
        replayHandler = new ReplayHandler( this.networkClient );
        
        //Set up the listener on this end
		replayListener = new ReplayListener(replayHandler);
		
		//Let the client know about this listener
		this.networkClient.addListener(replayListener);
		
		//Add an event listener for callbacks
		replayHandler.addReplayEventListener(initReplayEventListener());
		
		//Declare an event to be registered in the factory, we can pass arrays.
		//The do_move event is for recording a players move
		ReplayNodeFactory.registerEvent("do_move",
				                          	new String[]{"player_id",
				                                             "col"}
				                               );
		//Declare an event to be registered in the factory, we can pass arrays.
		//The cursor_move event is for recording the movement of the cursor
		ReplayNodeFactory.registerEvent("cursor_move",
						                    new String[]{"col"}
						                       );
		
		//Get the list of sessions for connect4
		replayHandler.requestSessionList(getGame().id);
		
		//Start session - tell the server we will be recording soon
		replayHandler.startSession( getGame().id, players[ 0 ] );
	}

	private ReplayEventListener initReplayEventListener() {
	    return new ReplayEventListener() {
            public void replayEventReceived( String eType, ReplayNode eData ) {
                /* 
                 * Here you can add actions to occur when each of the replay events is received.
                 * These are currently used for printing testing data and could
                 * be used for running custom code relative to any game that will be
                 * triggered when each replay event occurs.
            	*/
            	if ( eType.equals( "node_pushed" ) ) {
                    System.out.println( eType );
                    System.out.println( eData );
                }
                if ( eType.equals( "event_pushed" ) ) {
                    System.out.println( eType );
                }
                if ( eType.equals( "session_list" ) ) {
                    System.out.println( eData );
                }
                if ( eType.equals( "replay_reset" ) ) {
                    System.out.println( "replay reset" );
                }
                if ( eType.equals( "playback_complete" ) ) {
                    System.out.println( "playback finished" );
                    isReplaying = false;
                }
                
                if ( eType.equals( "do_move" ) ) {
                	/* When a do_move event is received, if the gameState is in replay mode
                	 * it will get the data required to pass into the doMove function and run that
                	 * function.
                	 */
               
                	if ( gameState == GameState.REPLAY ) {
                		int player = eData.getItemForString( "player_id" ).intVal();
                		doMove(player, eData.getItemForString( "col" ).intVal() );   

		    			playerTurn = ( player == 1 ) ? 0 : 1;
                		System.out.println( "Move: " + eData );
                	}
                }
                
                if ( eType.equals( "cursor_move" ) ) {
                	/* The same occurs when a cursor_move event is received. If the gameState
                	 * is in replay mode, it will get the data required and set the currentPos value.
                	 */
                	if ( gameState == GameState.REPLAY ) {
                		cursorDisc.currentPos = eData.getItemForString( "col" ).intVal();  
                        System.out.println( "Cursor move: " + cursorDisc.currentPos );
                	}
                }
            }
        }; 
	}
	
	/**
	 * Returns random integer for computer cursor
	 */
	public static int randInt(int min, int max) {

	    Random rand = new Random();

	    int randomNum = rand.nextInt((max - min) + 1) + min;

	    return randomNum;
	}
	
	/**
	 * Checks if any keys have been pressed and alters keyCodes where required.
	 * 
	 * The keyPressed and keyReleased states are used to ensure the related functions
	 * for the left, right and enters keys only occur once the key has been released.
	 * 
	 * 0 => key up, 1 => key pressed, 2 => key released
	 */
	public void checkKeysPressed(){
		if (Gdx.input.isKeyPressed(Keys.LEFT) && keyCodes[KEY_LEFT] == 0) {
			keyCodes[0] = 1;
		} else if (Gdx.input.isKeyPressed(Keys.RIGHT) && keyCodes[KEY_RIGHT] == 0) {
			keyCodes[1] = 1;
		} else if (Gdx.input.isKeyPressed(Keys.ENTER) && keyCodes[KEY_ENTER] == 0) {
			keyCodes[2] = 1;
		}
	}
	
	/**
	 * Checks if any keys have been released and alters keyCodes where required.
	 */
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
		
		init();
	}
	
	public void init() {
		ArrayList<String> ButtonList =  new ArrayList<String>();
		
		//Setup the button list
		ButtonList.add("Quit");
		ButtonList.add("Replay");
		
		//Set the current player's turn
		playerTurn = 0;
		
		//Create the table
		table = new Table();
		table.getBounds().x = SCREENWIDTH/2 - Table.WIDTH/2;
		table.setupDiscs();
		
		//Create the buttons and setup
		buttons = new Buttons();
		buttons.setX(Gdx.graphics.getWidth());
		buttons.setY(Gdx.graphics.getHeight() - 20);
		buttons.AddButtonsFromList(ButtonList);
		buttons.hide();
		
		//Create the cursor disc
		cursorDisc = new Disc();
		cursorDisc.setState( Disc.PLAYER1 );
		cursorDisc.setPosition((table.getBounds().x + cursorDisc.bounds.width + 5), (table.getBounds().y + table.getBounds().height + 25));
		
		//Necessary for rendering
		shapeRenderer = new ShapeRenderer();
		font = new BitmapFont();
		font.setScale(2);
		batch = new SpriteBatch();
		
		//Initialise the scores and game state
		gameState = GameState.READY;
		statusMessage = "Click to start!";
		
	}
	
	public void reset() {
		//Reset the discs and playerTurn
		table.resetDiscs();
		this.playerTurn = 0;
		cursorDisc.setState( Disc.PLAYER1 );
		cursorDisc.currentPos = 0;
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
	 * Render the cursor disc.
	 * 
	 * Determines the current player and renders the appropriate
	 * cursor(disc) colour.
	 */
	public void renderCursorDisc() {
		//Set cursor position
		cursorDisc.setPosition(
				(table.getBounds().x + cursorDisc.bounds.width + cursorDisc.currentPos * ( 5 + 2 * Table.DISCRADIUS ) + 5 ), 
				(table.getBounds().y + table.getBounds().height + 25));
		
		//Determine current player and set respective cursor colour.
		if (playerTurn == 0) {
			cursorDisc.setState( Disc.PLAYER1 );
		} else if (playerTurn == 1) {
			cursorDisc.setState( Disc.PLAYER2 );
		}
		
		//Render the cursor Disc
	    shapeRenderer.begin(ShapeType.FilledCircle);
	    cursorDisc.render(shapeRenderer);
	    shapeRenderer.end();
	}
	
	/**
	 * Moves the cursor disc to the right.
	 * 
	 * Calls moveRight on the cursor disc and registers
	 * a cursor_move with the replay handler.
	 */
	public void moveCursorRight() {
		//Attempt right move
		int rightMove = cursorDisc.moveRight();
		
		//If right move was successful, register a cursor_move with the replay handler
		if ( rightMove >= 0 ) {
			replayHandler.pushEvent(
			        ReplayNodeFactory.createReplayNode(
			                "cursor_move",
			                rightMove
			        )
			);
		}
	}
	
	/**
	 * Moves the cursor disc to the left.
	 * 
	 * Calls moveLeft on the cursor disc and registers
	 * a cursor_move with the replay handler.
	 */
	public void moveCursorLeft() {
		//Attempt left move
		int leftMove = cursorDisc.moveLeft();
		
		//If left move was successful, register a cursor_move with the replay handler
		if ( leftMove >= 0 ) {
			replayHandler.pushEvent(
			        ReplayNodeFactory.createReplayNode(
			                "cursor_move",
			                leftMove
			        )
			);
		}
	}

	/**
	 * Renders all required text on the screen.
	 * 
	 */
	public void renderScreenText() {
		//render player names
	    batch.begin();
	    font.setColor(Color.RED);
	    font.draw(batch, "Red: " + ( players[0].equals( "" ) ? "User" : players[0] ), 10, SCREENHEIGHT - 20);
	    font.setColor(Color.BLUE);
	    font.draw(batch, "Blue: " + players[1], 10, SCREENHEIGHT - 50);
	    
	    //render connect4 text
	    font.setColor(Color.YELLOW);
	    font.draw(batch, "Connect 4", SCREENWIDTH/2 - 80, SCREENHEIGHT - 20);
	    
	    //If there is a current status message (i.e. if the game is in the ready or gameover state)
	    // then show it in the middle of the screen
	    if (statusMessage != null) {
	    	font.setColor(Color.WHITE);
	    	font.draw(batch, statusMessage, SCREENWIDTH/2 - 100, SCREENHEIGHT-100);
	    }
	    batch.end();
	}
	
	/**
	 * Render the current state of the game and process updates
	 */
	@Override
	public void render() {
		int mouseX;
		int mouseY;
		
		//Black background
		Gdx.gl.glClearColor(0, 0, 0, 1);
	    Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

	    camera.update();
	    
	    shapeRenderer.setProjectionMatrix(camera.combined);
	    batch.setProjectionMatrix(camera.combined);
	    
	    //Render everything
	    renderCursorDisc();
	    table.render(shapeRenderer);
	    renderScreenText();
	    buttons.render();

	    // Respond to user input and move the ball depending on the game state
	    switch(gameState) {
		    
		    case READY: //Ready to start a new game
		    	if (Gdx.input.isTouched()) {
		    		reset();
		    		//Begin recording
		    		replayHandler.startRecording();
		    		startGame();
		    	}
		    	break;
		    	
		    case INPROGRESS: //Game is underway, players can make their moves
		    	if (playerTurn == 0) {
		    		
		    		checkKeysPressed();
		    		checkKeysReleased();
		    		
		    		//If a key has been released, do required movement and reset key codes to 0.
		    		if(keyCodes[KEY_RIGHT] == 2) {
		    			moveCursorRight();
		    			keyCodes[1] = 0;
		    		} else if(keyCodes[KEY_LEFT] == 2) {
		    			moveCursorLeft();
		    			keyCodes[0] = 0;
		    		} else if(keyCodes[KEY_ENTER] == 2) {
		    			//move the disc the lowest position and render table discs
		    			
		    			if ( doMove(playerTurn, cursorDisc.currentPos) ) {
		    				
		    				replayHandler.pushEvent(
			    			        ReplayNodeFactory.createReplayNode(
			    			                "do_move",
			    			                playerTurn, cursorDisc.currentPos
			    			        )
			    			);
		    				
		    				checkForWinner( playerTurn );

			    			playerTurn = ( playerTurn == 1 ) ? 0 : 1;
		    			}
		    			
		    			nextComputerMove = System.currentTimeMillis() + AI_DELAY + randInt( -AI_DELAY/2, AI_DELAY/2 );
		    			
		    			keyCodes[KEY_ENTER] = 0;
		    		}
		    	} else if (playerTurn == 1) {
		    		// Let the computer make a move

		    		if ( System.currentTimeMillis() < nextComputerMove ) {
		    			break;
		    		}
		    		
		    		if ( nextComputerCol < 0 ) {
		    			nextComputerCol = randInt(0,Table.TABLECOLS - 1);
		    		} 
		    		
		    		if ( cursorDisc.currentPos > nextComputerCol ) {
		    			moveCursorLeft();
		    			nextComputerMove = System.currentTimeMillis() + AI_DELAY + randInt( -AI_DELAY/2, AI_DELAY/2 );
		    			break;
		    		} else if ( cursorDisc.currentPos < nextComputerCol ) {
		    			moveCursorRight();
		    			nextComputerMove = System.currentTimeMillis() + AI_DELAY + randInt( -AI_DELAY/2, AI_DELAY/2 );
		    			break;
		    		}
		    		
		    		//Do computer move and register a do_move event with the replay handler
		    		if ( doMove(playerTurn, cursorDisc.currentPos) ) {
		    			
			    		replayHandler.pushEvent(
		    			        ReplayNodeFactory.createReplayNode(
		    			                "do_move",
		    			                playerTurn, cursorDisc.currentPos
		    			        )
		    			);
			    		
		    			checkForWinner( playerTurn );

		    			playerTurn = ( playerTurn == 1 ) ? 0 : 1;
		    		}
		    		
		    		nextComputerCol = -1;
		    	}
		    	break;
		    case GAMEOVER: //The game has been won, wait to exit
		    	statusMessage = "Game Over!";
		    	buttons.display();
		    	mouseX = (int)getMouseX();
		   		mouseY = (int)getMouseY();
		   		buttons.checkHovered(mouseX, mouseY);
			   	
			   	if (Gdx.input.isTouched()) {
			   		
			   		if (buttons.checkButtonsPressed(mouseX, mouseY) == 1) {
			   			//Quit button has been pressed
			   			gameOver();
				   		ArcadeSystem.goToGame(ArcadeSystem.UI);
				   		buttons.hide();
			   		} else if (buttons.checkButtonsPressed(mouseX, mouseY) == 2){
			   			//Replay button has been pressed
			   			//reset the board
			   			reset();
				    	gameState = GameState.REPLAY;
				    	//Start replay of last session
				    	replayHandler.playbackLastSession();
				    	
				    	isReplaying = true;
				    	buttons.hide();
			   		}
			   	}
			   	break;
		    case REPLAY: //Replaying last game
		    	if (isReplaying) {
		    		statusMessage = "REPLAYING";
		    	} else {
		    		statusMessage = "Replay over";
		    		gameState = GameState.GAMEOVER;
		    	}
		    	//Start running the replay
	    		replayHandler.runLoop();
		    	break;
	    }
	    
		super.render();
		
	}
	
	public void checkForWinner( int player ) {
		if (player == 0){
	    	if (table.checkFieldWinner( Disc.PLAYER1 )) {
	    		gameState = GameState.GAMEOVER;
	    		//End session with the server
		    	replayHandler.endCurrentSession();
	    		endGame( 0 );
	    	}
	    } else {
	    	if (table.checkFieldWinner( Disc.PLAYER2 )) {
	    		gameState = GameState.GAMEOVER;
	    		//End session with the server
		    	replayHandler.endCurrentSession();
	    		endGame( 1 );
	    	}
	    }
		
	}
	
	public boolean doMove(int player, int currentPosition) {
		return table.placeDisc(currentPosition, player);
	}

	private void endGame(int winner) {
		//Game is over - stop recording and set gameState
		replayHandler.finishRecording();
		gameState = GameState.GAMEOVER;
	}
	
	private int getMouseX(){
		int tempx = Gdx.input.getX();
		return clickToScreenX(tempx);
	}
	
	private int getMouseY(){
		int tempy = Gdx.input.getY();
		return clickToScreenY(tempy);
	}
	
	private int clickToScreenX(int clickX) {
		return clickX * SCREENWIDTH / Gdx.graphics.getWidth();
	}

	private int clickToScreenY(int clickY) {
		return SCREENHEIGHT - (clickY * SCREENHEIGHT / Gdx.graphics.getHeight());
	}
	
	/**
	 * Create an update object to send to the server notifying of a score change or game outcome
	 * @return The Game Status Update.
	 */
	private GameStatusUpdate createScoreUpdate() {
		GameStatusUpdate update = new GameStatusUpdate();
		update.gameId = game.id;
		update.username = players[0];
		return update;
	}
	
	private void startGame() {
		
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
        game.description = "Connect4 with replay integrated!";
	}
	
	public Game getGame() {
		return game;
	}
	
}
