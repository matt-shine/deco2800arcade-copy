package deco2800.arcade.pong;

import java.util.ArrayList;
import java.util.List;

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
import deco2800.arcade.client.highscores.Highscore;
import deco2800.arcade.client.highscores.HighscoreClient;
import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.client.AchievementClient;


import java.util.ArrayList;
import deco2800.arcade.model.Achievement;

/**
 * A Pong game for use in the Arcade
 * @author uqjstee8
 *
 */
@ArcadeGame(id="Pong")
public class Pong extends GameClient {
	
	private OrthographicCamera camera;
	
	private static final Game GAME;
	
	private Paddle leftPaddle;
	private Paddle rightPaddle;
	private Ball ball;
//	private enum GameState {
//		READY,
//		INPROGRESS,
//		GAMEOVER
//	}
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
	private AchievementClient achievementClient;

	/**
	 * Basic constructor for the Pong game
	 * @param player The name of the player
	 * @param networkClient The network client for sending/receiving messages to/from the server
	 */
	public Pong(Player player, NetworkClient networkClient) {
		super(player, networkClient);
		players[0] = player.getUsername();
		players[1] = "Player 2"; //TODO eventually the server may send back the opponent's actual username
        this.networkClient = networkClient; //this is a bit of a hack
        //this.achievementClient = new AchievementClient(networkClient);

        
        //These methods are just used for testing HighscoreClient 
        //Creating new HighscoreClient connection
        HighscoreClient hsd = new HighscoreClient(player.getUsername(), "Pong", networkClient);
        
        //Single scores
        //hsd.storeScore("Number", 1234567890);
        //hsd.storeScore("Number", 5211);
        
        //Getting top scores
        List<Highscore> topPlayers = hsd.getGameTopPlayers(10, false, "Number");
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
		camera.setToOrtho(false, SCREENWIDTH/2, SCREENHEIGHT/2);
		
		// Create the paddles
		setLeftPaddle(new LocalUserPaddle(
				new Vector2(20,SCREENHEIGHT/2 - Paddle.INITHEIGHT/2)));
		getLeftPaddle().setColor(1, 0, 0, 1);
		
		setRightPaddle(new AIPaddle(
				new Vector2(SCREENWIDTH-Paddle.WIDTH-20,SCREENHEIGHT/2 - 
						Paddle.INITHEIGHT/2)));
		getRightPaddle().setColor(0, 0, 1, 1);
		
		/**
		 * TODO Allow network games
		 * 1. Create local player (LOBBY)
		 * 2. "Waiting for other players. Press '1' to play local game against the computer"
		 * 3a. Receive game join request
		 * 3b. "Player 'Bob' wishes to join the game. Press 'Y' to accept"
		 * 3c1. ('Y') Create Network player, move to READY
		 * 3c2. ('N') Go to 2.
		 */
		
		//Create the ball
		setBall(new Ball());
		getBall().setColor(1, 1, 1, 1);
		
		//Necessary for rendering
		shapeRenderer = new ShapeRenderer();
		font = new BitmapFont();
		font.setScale(2);
		batch = new SpriteBatch();
		
		//Initialise the scores and game state
		scores[0] = 0;
		scores[1] = 0;
		//gameState = GameState.READY;
		gameState = new ReadyState();
		statusMessage = "Click to start!";
		
        // achievements demo
        AchievementClient achClient = getAchievementClient();
        ArrayList<Achievement> achievements = achClient.achievementsForGame(getGame());
        for(Achievement ach : achievements) {
            System.out.println(ach.toString());
        }
        
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
	    
	    getLeftPaddle().render(shapeRenderer);
	    
	    getRightPaddle().render(shapeRenderer);
	    
	    getBall().render(shapeRenderer);
	    
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
	    	if (gameState instanceof GameOverState) {
	    		font.draw(batch, "Click to exit", SCREENWIDTH/2 - 100, SCREENHEIGHT - 200);
	    	}
	    }
	    batch.end();
	    handleInput();
		super.render();
		
	}

	/**
	 * Handle user input from keyboard or mouse
	 */
	private void handleInput() {
		// Respond to user input and move the ball depending on the game state
	   gameState.handleInput(this);
	}

	/**
	 * The point has ended: update scores, reset the ball, check for a winner and move the game state to ready or gameover
	 * @param winner 0 for player 1, 1 for player 2
	 */
	void endPoint(int winner) {
		ball.reset();
		scores[winner]++;
		// If we've reached the victory point then update the display
		if (scores[winner] == WINNINGSCORE) {	
		    int loser = winner == 1 ? 0 : 1; //The loser is the player who didn't win!
		    statusMessage = players[winner] + " Wins " + scores[winner] + " - " + scores[loser] + "!";
		    gameState = new GameOverState();
		    //Update the game state to the server
		    networkClient.sendNetworkObject(createScoreUpdate());
		    //If the local player has won, send an achievement
		    if (winner == 0) {
		    	incrementAchievement("pong.winGame");
			incrementAchievement("pong.master");
		    }
		} else {
			// No winner yet, get ready for another point
			gameState = new ReadyState();
			statusMessage = "Click to start!";
		}
	}
	
	/**
	 * Create an update object to send to the server notifying of a score change or game outcome
	 * @return The Game Status Update.
	 */
	private GameStatusUpdate createScoreUpdate() {
		GameStatusUpdate update = new GameStatusUpdate();
		update.gameId = GAME.id;
		update.username = players[0];
		//TODO Should also send the score!
		return update;
	}
	
	/**
	 * Start a new point: start the ball moving and change the game state
	 */
	void startPoint() {
		getBall().randomizeVelocity();
		gameState = new InProgressState();
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

	static {
		GAME = new Game();
		GAME.id = "pong";
		GAME.name = "Pong";
		GAME.description = "Tennis, without that annoying 3rd dimension!";
	}
	
	public Game getGame() {
		return GAME;
	}

	public Paddle getLeftPaddle() {
		return leftPaddle;
	}

	public void setLeftPaddle(Paddle leftPaddle) {
		this.leftPaddle = leftPaddle;
	}

	public Ball getBall() {
		return ball;
	}

	public void setBall(Ball ball) {
		this.ball = ball;
	}

	public Paddle getRightPaddle() {
		return rightPaddle;
	}

	public void setRightPaddle(Paddle rightPaddle) {
		this.rightPaddle = rightPaddle;
	}
	
}
