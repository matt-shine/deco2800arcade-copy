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
import deco2800.arcade.client.network.listener.ReplayListener;
import deco2800.arcade.client.replay.ReplayEventListener;
import deco2800.arcade.client.replay.ReplayHandler;
import deco2800.arcade.client.replay.ReplayNode;
import deco2800.arcade.client.replay.ReplayNodeFactory;
/**
 * A Checkers game for testing replay feature
 * @author shewwiii
 *
 */
@ArcadeGame(id="Checkers")
public class Checkers extends GameClient {
	
	private OrthographicCamera camera;

	private int chosen = 50;
	private float chosenx = 0;
	private float choseny = 0;
	private static Square[][] squares;
	private static Pieces[] myPieces;
	private static Pieces[] theirPieces;
	ClickListener pushed;
	
	private enum GameState {
		READY,
		AIGO,
		USERGO,
		REPLAY,
		GAMEOVER
	}
	private static GameState gameState;
	private int[] scores = new int[2];
	private String[] players = new String[2]; // The names of the players: the local player is always players[0]
	private static boolean isReplaying = false;

	public static final int WINNINGSCORE = 4;
	public static final int SCREENHEIGHT = 480;
	public static final int SCREENWIDTH = 800;
	
	private ShapeRenderer shapeRenderer;
	private SpriteBatch batch;
	private BitmapFont font;

	private String statusMessage;
	
	//start code for replay API
	private static ReplayHandler replayHandler;
	
	//Network client for communicating with the server.
	//Should games reuse the client of the arcade somehow? Probably!
	private NetworkClient networkClient;

	private final int MOUSE_UP = 0;
	private final int MOUSE_DOWN = 1;
	private int mouseState = MOUSE_UP;
	
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
        
        //start code for replay API
        replayHandler = new ReplayHandler( this.networkClient );
        
        //Set up our listener on this end
        ReplayListener replayListener = new ReplayListener(replayHandler);

        //Our client needs to know about this listener
        this.networkClient.addListener(replayListener);

        //Declare an event to be registered in the factory, we can pass arrays.
        ReplayNodeFactory.registerEvent("piece_move", new String[]{"piece", "move_x_val", "move_y_val"});
        ReplayNodeFactory.registerEvent("select_piece", new String[]{"chosen", "array_i", "array_j"});
        
      //Start session - tell the server we will be recording soon
      		replayHandler.startSession( getGame().id, player.getUsername() );
        //end code for replay API
	}
	
	//start code for replay API
	private static ReplayEventListener initReplayEventListener() {
	    return new ReplayEventListener() {
	        public void replayEventReceived( String eType, ReplayNode eData ) {
	            //Built in event types
	            if ( eType.equals( "node_pushed" ) ) {
	                System.out.println( eType );
	                System.out.println( eData );
	            }
	            if ( eType.equals( "event_pushed" ) ) {
	                System.out.println( eType );
	            }
	            //If you request a list of session from the server, this event will return with the list
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

	            //Custom events
	            if ( eType.equals( "piece_move" ) ) {
                	if ( gameState == GameState.REPLAY ) {
                		movePiece(eData.getItemForString("piece").intVal(), 
                				eData.getItemForString("move_x_val").floatVal(), 
                				eData.getItemForString("move_y_val").floatVal());
                		System.out.println( "Move: " + eData );
                	}
                }
	            
	            if ( eType.equals( "select_piece" ) ) {
                	if ( gameState == GameState.REPLAY ) {
                		selectPiece(eData.getItemForString("chosen").intVal(), 
                				eData.getItemForString("array_i").intVal(), 
                				eData.getItemForString("array_j").intVal());
                		System.out.println( "Move: " + eData );
                	}
                }
	        }
	    }; 
	}
	//end code for replay API
	
	/**
	 * Creates the game
	 */
	@Override
	public void create() {
		//FIXME big method
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
		pushed = new ClickListener();
		
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
				squares[i][j].addActionListener(pushed);
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
		//FIXME big method
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
	    		//start code for replay API
	    		//Actually begin recording
	    		replayHandler.startRecording();
	    		//end code for replay API
	    		gameState = GameState.AIGO;
	    	}
	    	break;
	    	
	    case AIGO: //AI go
	    	startPoint();
	    	if (scores[1] == WINNINGSCORE) {
				statusMessage = players[1] + " Wins " + scores[1] + " - " + scores[0] + "!";
				gameState = GameState.GAMEOVER;
				//start code for replay API
				replayHandler.endCurrentSession();
				//end code for replay API
			} else if (scores[0] == WINNINGSCORE) {
				statusMessage = players[0] + " Wins " + scores[0] + " - " + scores[1] + "!";
				gameState = GameState.GAMEOVER;
				//start code for replay API
				replayHandler.endCurrentSession();
				//end code for replay API
			}
	    	break;
	    	
	    case USERGO: //User go
			if (Gdx.input.isTouched()) {
				if ( this.mouseState == MOUSE_UP ) {
					// gone from up to down, click event here
					this.mouseState = MOUSE_DOWN;
				} else {
					// still down
				}
			} else {
				if ( this.mouseState == MOUSE_DOWN ) {
					// gone from down to up
					mouseReleased();
					this.mouseState = MOUSE_UP;
				} else {
					// mouse still up
				}
			}
			
			//follows mouse if clicked on
			if (chosen != 50) {
				myPieces[chosen].bounds.set(clickToScreenX(Gdx.input.getX()) - 15, clickToScreenY(Gdx.input.getY()) - 15, 30, 30);
			}
			
			if (scores[1] == WINNINGSCORE) {
				statusMessage = players[1] + " Wins " + scores[1] + " - " + scores[0] + "!";
				gameState = GameState.GAMEOVER;
				//start code for replay API
				replayHandler.endCurrentSession();
				//end code for replay API
			} else if (scores[0] == WINNINGSCORE) {
				statusMessage = players[0] + " Wins " + scores[0] + " - " + scores[1] + "!";
				gameState = GameState.GAMEOVER;
				//start code for replay API
				replayHandler.endCurrentSession();
				//end code for replay API
			}
			
	    	break;
	    	
	    case GAMEOVER: //The game has been won, wait to exit
	    	if (Gdx.input.isTouched()) {
	    		//start code for replay API
	    		create();
	    		gameState = GameState.REPLAY;
		    	//Start replay of last session
		    	replayHandler.playbackLastSession();
		    	
		    	isReplaying = true;
		    	//end code for replay API
	    		//gameOver();
	    		//ArcadeSystem.goToGame(ArcadeSystem.UI);
	    	}
	    	break;
	    
	    case REPLAY:
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
                incrementAchievement("checkers.winGame");
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
		//FIXME deprecated
		update.username = players[0];
		//TODO Should also send the score!
		return update;
	}
	
	/**
	 * AI makes a move, and change game state to player making move
	 */
	private void startPoint() {
		//FIXME gigantic method
	System.out.println("startPoint function");
		//Pieces toMove = theirPieces[toMoveNum];
		
		outerloop:
		for (int toMoveNum = 0; toMoveNum < 4; toMoveNum++) {
			Pieces toMove = theirPieces[toMoveNum];
			System.out.println("looped through as " +toMoveNum);
		
			//already eaten
			if (toMove.bounds.y == -90) {
				// toMoveNum += 1;
				toMove = theirPieces[toMoveNum];
				continue outerloop;
			}
			
			//no moves
			if (toMove.bounds.x == 80) { // is far left
				for (int p = 0; p < 4; p++) { 
					if (toMove.bounds.y - 50 == theirPieces[p].bounds.y && toMove.bounds.x + 50 == theirPieces[p].bounds.x) {
						// and has own piece blocking advancing right space
						System.out.println("detected own piece at advancing right space");
						// toMoveNum += 1;
						toMove = theirPieces[toMoveNum];
						continue outerloop;
					} else if (toMove.bounds.y - 50 == myPieces[p].bounds.y && toMove.bounds.x + 50 == myPieces[p].bounds.x) {
						// and has 2 edible pieces blocking advancing right spaces
						for (int r = 0; r < 4; r++) {
							if (toMove.bounds.y - 100 == myPieces[r].bounds.y && toMove.bounds.x + 100 == myPieces[r].bounds.x) {
								// toMoveNum += 1;
								toMove = theirPieces[toMoveNum];
								continue outerloop;
							}
						}
						movePiece(toMoveNum, 100f, -100f);
						myPieces[p].bounds.y = -90;
						System.out.println("moved 465");

						if (toMove.bounds.y == 110) { //last row
							scores[1] += 1;
							toMove.bounds.y = -90;
						}
						scores[1] += 1;
						gameState = GameState.USERGO;
						statusMessage = null;
						toMoveNum = 5;
						break outerloop;
					}
				}
			} else if (toMove.bounds.x == 230) { // is far right
				for (int p = 0; p < 4; p++) { 
					// and has own piece blocking advancing left space
					if (toMove.bounds.y - 50 == theirPieces[p].bounds.y && toMove.bounds.x - 50 == theirPieces[p].bounds.x) {
						// toMoveNum += 1;
						toMove = theirPieces[toMoveNum];
						continue outerloop;
					} else if (toMove.bounds.y - 50 == myPieces[p].bounds.y && toMove.bounds.x - 50 == myPieces[p].bounds.x) {
						// and has 2 edible pieces blocking advancing left spaces
						for (int r = 0; r < 4; r++) {
							if (toMove.bounds.y - 100 == myPieces[r].bounds.y && toMove.bounds.x - 100 == myPieces[r].bounds.x) {
								// toMoveNum += 1;
								toMove = theirPieces[toMoveNum];
								continue outerloop;
							}
						}
						movePiece(toMoveNum, 100f, -100f);
						myPieces[p].bounds.y = -90;
						System.out.println("moved 465");

						if (toMove.bounds.y == 110) { //last row
							scores[1] += 1;
							toMove.bounds.y = -90;
						}
						scores[1] += 1;
						gameState = GameState.USERGO;
						statusMessage = null;
						toMoveNum = 5;
						break outerloop;
					}
				}
			} else {
				for (int p = 0; p < 4; p++) { // has own piece blocking advancing right space
					if (toMove.bounds.y - 50 == theirPieces[p].bounds.y && toMove.bounds.x + 50 == theirPieces[p].bounds.x) {
						for (int r = 0; r < 4; r++) {
							if (toMove.bounds.y - 50 == theirPieces[r].bounds.y && toMove.bounds.x - 50 == theirPieces[r].bounds.x) {
								// and has own piece blocking advancing left space
								// toMoveNum += 1;
								toMove = theirPieces[toMoveNum];
								continue outerloop;
							} else if (toMove.bounds.y - 50 == myPieces[r].bounds.y && toMove.bounds.x - 50 == myPieces[r].bounds.x) {
								// edible piece is at corner
								if (toMove.bounds.x - 100 < 80) {
									// toMoveNum += 1;
									toMove = theirPieces[toMoveNum];
									continue outerloop;
								}
								// has 2 edible pieces blocking left spaces
								for (int s = 0; s < 4; s++) {
									if (toMove.bounds.y - 100 == myPieces[s].bounds.y && toMove.bounds.x - 100 == myPieces[s].bounds.x) {
										// toMoveNum += 1;
										toMove = theirPieces[toMoveNum];
										continue outerloop;
									}
								}
								// edible, has free space after
								movePiece(toMoveNum, -100f, -100f);
								myPieces[r].bounds.y = -90;
								System.out.println("moved 457");
								if (toMove.bounds.y == 110) { //last row
									scores[1] += 1;
									toMove.bounds.y = -90;
								}
								scores[1] += 1;
								gameState = GameState.USERGO;
								statusMessage = null;
								toMoveNum = 5;
								break outerloop;
							}
						}
					} else if (toMove.bounds.y - 50 == myPieces[p].bounds.y && toMove.bounds.x + 50 == myPieces[p].bounds.x) {
						// edible piece is at far right
						if (toMove.bounds.x + 100 > 230) {
							for (int t = 0; t < 4; t++) {
								if (toMove.bounds.y - 50 == theirPieces[t].bounds.y && toMove.bounds.x - 50 == theirPieces[t].bounds.x) {
									// and has own piece blocking advancing left space
									// toMoveNum += 1;
									toMove = theirPieces[toMoveNum];
									continue outerloop;
								} else if (toMove.bounds.y - 50 == myPieces[t].bounds.y && toMove.bounds.x - 50 == myPieces[t].bounds.x) {
									// edible piece is at corner
									if (toMove.bounds.x - 100 < 80) {
										// toMoveNum += 1;
										toMove = theirPieces[toMoveNum];
										continue outerloop;
									}
									// has 2 edible pieces blocking left spaces
									for (int s = 0; s < 4; s++) {
										if (toMove.bounds.y - 100 == myPieces[s].bounds.y && toMove.bounds.x - 100 == myPieces[s].bounds.x) {
											// toMoveNum += 1;
											toMove = theirPieces[toMoveNum];
											continue outerloop;
										} else if (toMove.bounds.y - 100 == theirPieces[s].bounds.y && toMove.bounds.x - 100 == theirPieces[s].bounds.x) {
											// toMoveNum += 1;
											toMove = theirPieces[toMoveNum];
											continue outerloop;
										}
									}
									// edible, has free space after
									movePiece(toMoveNum, -100f, -100f);
									myPieces[t].bounds.y = -90;
									System.out.println("moved 417");
									scores[1] += 1;
									if (toMove.bounds.y == 110) { //last row
										scores[1] += 1;
										toMove.bounds.y = -90;
									}
									gameState = GameState.USERGO;
									statusMessage = null;
									toMoveNum = 5;
									break outerloop;
								}
							}
						}
						// has 2 edible pieces blocking right spaces
						for (int r = 0; r < 4; r++) {
							if ((toMove.bounds.y - 100 == myPieces[r].bounds.y && toMove.bounds.x + 100 == myPieces[r].bounds.x) 
									|| (toMove.bounds.y - 100 == theirPieces[r].bounds.y && toMove.bounds.x + 100 == theirPieces[r].bounds.x)) {
								for (int t = 0; t < 4; t++) {
									if (toMove.bounds.y - 50 == theirPieces[t].bounds.y && toMove.bounds.x - 50 == theirPieces[t].bounds.x) {
										// and has own piece blocking advancing left space
										// toMoveNum += 1;
										toMove = theirPieces[toMoveNum];
										continue outerloop;
									} else if (toMove.bounds.y - 50 == myPieces[t].bounds.y && toMove.bounds.x - 50 == myPieces[t].bounds.x) {
										// edible piece is at far left
										if (toMove.bounds.x - 100 < 80) {
											// toMoveNum += 1;
											toMove = theirPieces[toMoveNum];
											continue outerloop;
										}
										// has 2 pieces blocking left spaces after edible
										for (int s = 0; s < 4; s++) {
											if (toMove.bounds.y - 100 == myPieces[s].bounds.y && toMove.bounds.x - 100 == myPieces[s].bounds.x) {
												// toMoveNum += 1;
												toMove = theirPieces[toMoveNum];
												continue outerloop;
											} else if (toMove.bounds.y - 100 == theirPieces[s].bounds.y && toMove.bounds.x - 100 == theirPieces[s].bounds.x) {
												// toMoveNum += 1;
												toMove = theirPieces[toMoveNum];
												continue outerloop;
											}
										}
										// edible, has free space after
										movePiece(toMoveNum, -100f, -100f);
										myPieces[t].bounds.y = -90;
										System.out.println("moved 457");
										if (toMove.bounds.y == 110) { //last row
											scores[1] += 1;
											toMove.bounds.y = -90;
										}
										scores[1] += 1;
										gameState = GameState.USERGO;
										statusMessage = null;
										toMoveNum = 5;
										break outerloop;
									}
								}
							} else {
								movePiece(toMoveNum, 100f, -100f);
								myPieces[r].bounds.y = -90;
								System.out.println("moved 465");

								if (toMove.bounds.y == 110) { //last row
									scores[1] += 1;
									toMove.bounds.y = -90;
								}
								scores[1] += 1;
								gameState = GameState.USERGO;
								statusMessage = null;
								toMoveNum = 5;
								break outerloop;
							}
						}
					}
				}
			}
			
			// move
			if (toMove.bounds.x == 80) { // is far left
				// advance right
				movePiece(toMoveNum, 50f, -50f);
				System.out.println("moved 4" + toMoveNum);

				if (toMove.bounds.y == 110) { //last row
					scores[1] += 1;
					toMove.bounds.y = -90;
				}
				gameState = GameState.USERGO;
				statusMessage = null;
				toMoveNum = 5;
				break outerloop;
			} else if (toMove.bounds.x == 230) {
				// advance left
				movePiece(toMoveNum, -50f, -50f);
				System.out.println("moved 5");

				if (toMove.bounds.y == 110) { //last row
					scores[1] += 1;
					toMove.bounds.y = -90;
				}
				gameState = GameState.USERGO;
				statusMessage = null;
				toMoveNum = 5;
				break outerloop;
			} else {
				for (int q = 0; q < 4; q++) { 
					if (toMove.bounds.y - 50 == myPieces[q].bounds.y && toMove.bounds.x + 50 == myPieces[q].bounds.x) {
						movePiece(toMoveNum, -50f, -50f);
						System.out.println("moved 6");

						if (toMove.bounds.y == 110) { //last row
							scores[1] += 1;
							toMove.bounds.y = -90;
						}
						gameState = GameState.USERGO;
						statusMessage = null;
						toMoveNum = 5;
						break outerloop;
					} else if (toMove.bounds.y - 50 == theirPieces[q].bounds.y && toMove.bounds.x + 50 == theirPieces[q].bounds.x) {
						movePiece(toMoveNum, -50f, -50f);
						System.out.println("moved 6");

						if (toMove.bounds.y == 110) { //last row
							scores[1] += 1;
							toMove.bounds.y = -90;
						}
						gameState = GameState.USERGO;
						statusMessage = null;
						toMoveNum = 5;
						break outerloop;
					}
				}
				movePiece(toMoveNum, 50f, -50f);
				System.out.println("moved 7");

				if (toMove.bounds.y == 110) { //last row
					scores[1] += 1;
					toMove.bounds.y = -90;
				}
				gameState = GameState.USERGO;
				statusMessage = null;
				toMoveNum = 5;
				break outerloop;
			}
		}
	}
	
	private void mouseReleased() {
		//FIXME big method
		int x = Gdx.input.getX();
		int y = Gdx.input.getY();
		int realX = clickToScreenX(x);
		int realY = clickToScreenY(y);
		
		if (chosen == 50) { // no piece selected
			System.out.println(theirPieces[2].bounds.y);
			for (int i = 0; i<4; i++) {
				if (myPieces[i].bounds.contains(realX, realY)) {
					chosen = i;
					chosenx = myPieces[i].bounds.x;
					choseny = myPieces[i].bounds.y;
				}
			}
		} else { // piece already selected
			for (int j=0; j<4; j++) {
		    	for (int i=0; i<3; i++) {
		    		if (squares[i][j].bounds.contains(realX, realY)) { //is a square
		    			if (squares[i][j].bounds.contains(chosenx+50, choseny+50) || squares[i][j].bounds.contains(chosenx-50, choseny+50)) { //diagonal square
		    					selectPiece(chosen, i, j);
		    					replayHandler.pushEvent(ReplayNodeFactory.createReplayNode("select_piece", chosen, i, j));
		    					if (squares[i][j].bounds.y == 350) {//reached end
		    						try{
		    				    	    Thread.sleep(1500);
		    				    	}catch(InterruptedException e){
		    				    	    System.out.println("got interrupted!");
		    				    	}
		    						scores[0] += 1;
		    						myPieces[chosen].bounds.y = -90;
		    					}
				    			chosen = 50;
				    			chosenx = choseny = 0;
				    			gameState = GameState.AIGO;
		    			} else if (squares[i][j].bounds.contains(chosenx+100, choseny+100)) { //diagonal square
	    					for (int k=0; k<4; k++) {
	    						if (squares[i][j].bounds.contains(theirPieces[k].bounds.x+50, theirPieces[k].bounds.y+50)) {
	    							//noms
	    							scores[0] += 1;
	    							theirPieces[k].bounds.y = -90;
	    							selectPiece(chosen, i, j);
	    							replayHandler.pushEvent(ReplayNodeFactory.createReplayNode("select_piece", chosen, i, j));
	    							if (squares[i][j].bounds.y == 350) {//reached end
			    						try{
			    				    	    Thread.sleep(1500);
			    				    	}catch(InterruptedException e){
			    				    	    System.out.println("got interrupted!");
			    				    	}
			    						scores[0] += 1;
			    						myPieces[chosen].bounds.y = -90;
			    					}
	    			    			chosen = 50;
	    			    			chosenx = choseny = 0;
	    			    			gameState = GameState.AIGO;
	    						}
	    					}
		    			} else if (squares[i][j].bounds.contains(chosenx-100, choseny+100)) { //diagonal square
	    					for (int k=0; k<4; k++) {
	    						if (squares[i][j].bounds.contains(theirPieces[k].bounds.x-50, theirPieces[k].bounds.y+50)) {
	    							//noms
	    							scores[0] += 1;
	    							theirPieces[k].bounds.y = -90;
	    							selectPiece(chosen, i, j);
	    							replayHandler.pushEvent(ReplayNodeFactory.createReplayNode("select_piece", chosen, i, j));
	    							if (squares[i][j].bounds.y == 350) {//reached end
			    						try{
			    				    	    Thread.sleep(1500);
			    				    	}catch(InterruptedException e){
			    				    	    System.out.println("got interrupted!");
			    				    	}
			    						scores[0] += 1;
			    						myPieces[chosen].bounds.y = -90;
			    					}
	    			    			chosen = 50;
	    			    			chosenx = choseny = 0;
	    			    			gameState = GameState.AIGO;
	    						}
	    					}
		    				
		    			} else if (squares[i][j].bounds.contains(chosenx, choseny)) { //put back
		    				selectPiece(chosen, i, j);
		    				replayHandler.pushEvent(ReplayNodeFactory.createReplayNode("select_piece", chosen, i, j));
			    			chosen = 50;
			    			chosenx = choseny = 0;
		    			}
		    		}
		    	}
			}
		}
	}
	
	public static void movePiece(int toMoveNum, float moveValX, float moveValY) { //created for the replay API
		//Pieces toMove = theirPieces[toMoveNum];
		theirPieces[toMoveNum].move(moveValX, moveValY);
		replayHandler.pushEvent(ReplayNodeFactory.createReplayNode("move_piece", toMoveNum, moveValX, moveValY));
	}
	
	public static void selectPiece(int chosen, int i, int j) { //created for the replay API
		//Pieces toMove = myPiece[chosen];
		//selectPiece(myPieces[chosen], squares, i, j);
		myPieces[chosen].bounds.set(squares[i][j].bounds.x + 10,
				squares[i][j].bounds.y + 10, 30, 30);
	}
	
	private int clickToScreenX(int clickX) {
		return clickX * SCREENWIDTH / Gdx.graphics.getWidth();
	}

	private int clickToScreenY(int clickY) {
		return SCREENHEIGHT - (clickY * SCREENHEIGHT / Gdx.graphics.getHeight());
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
		game.id = "Checkers";
		game.name = "Checkers";
		game.description = "A classical game, passed down through the ages, and"
				+ "now digitalized, for competitive, but original strategy.";
	}
	
	public Game getGame() {
		return game;
	}
}
