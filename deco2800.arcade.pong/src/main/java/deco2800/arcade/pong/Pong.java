package deco2800.arcade.pong;

import java.util.HashSet;
import java.util.Set;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

import deco2800.arcade.model.Achievement;
import deco2800.arcade.model.Game;
import deco2800.arcade.model.Game.ArcadeGame;
import deco2800.arcade.model.Player;
import deco2800.arcade.protocol.achievement.AddAchievementRequest;
import deco2800.arcade.protocol.game.GameStatusUpdate;
import deco2800.arcade.client.GameClient;
import deco2800.arcade.client.network.NetworkClient;
/**
 * A Pong game for use in the Arcade
 * @author uqjstee8
 *
 */
@ArcadeGame(id="pong")
public class Pong extends GameClient {
	
	private OrthographicCamera camera;
	
	private Paddle leftPaddle;
	private Paddle rightPaddle;
	private Ball ball;
	private enum GameState {
		READY,
		INPROGRESS,
		GAMEOVER
	}
	private GameState gameState;
	private int[] scores = new int[2];
	private String[] players = new String[2]; // The names of the players: the local player is always players[0]
	
	public static final int WINNINGSCORE = 5;
	public static final int SCREENHEIGHT = 480;
	public static final int SCREENWIDTH = 800;
	
	private ShapeRenderer shapeRenderer;
	private SpriteBatch batch;
	private BitmapFont font;

	private String statusMessage;

	//Reusable list of achievements
	private static Set<Achievement> achievements = new HashSet<Achievement>();
	static {		
		Achievement winPong = new Achievement("Win a game of Pong");
		achievements.add(winPong);
	}
	
	//Network client for communicating with the server.
	//Should games reuse the client of the arcade somehow? Probably!
	private NetworkClient networkClient;

	/**
	 * Basic constructor for the Pong game
	 * @param userName The name of the player
	 * @param client The network client for sending/receiving messages to/from the server
	 */
	public Pong(Player player, NetworkClient networkClient) {
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
		
		//Initialise camera
		camera = new OrthographicCamera();
		camera.setToOrtho(false, SCREENWIDTH, SCREENHEIGHT);
		System.out.println("At init");
		
		// Create the paddles
		leftPaddle = new LocalUserPaddle(new Vector2(20,SCREENHEIGHT/2 - Paddle.INITHEIGHT/2));
		leftPaddle.setColor(1, 0, 0, 1);
		
		rightPaddle = new AIPaddle(new Vector2(SCREENWIDTH-Paddle.WIDTH-20,SCREENHEIGHT/2 - Paddle.INITHEIGHT/2));
		rightPaddle.setColor(0, 0, 1, 1);
		
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
		ball = new Ball();
		ball.setColor(1, 1, 1, 1);
		
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
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
	    
	    leftPaddle.render(shapeRenderer);
	    
	    rightPaddle.render(shapeRenderer);
	    
	    ball.render(shapeRenderer);
	    
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
	    	break;
	    case GAMEOVER: //The game has been won, wait to exit
	    	if (Gdx.input.isTouched()) {
	    		gameOver();
	    	}
	    	break;
	    }
	    
	}

	/**
	 * The point has ended: update scores, reset the ball, check for a winner and move the game state to ready or gameover
	 * @param winner 0 for player 1, 1 for player 2
	 */
	private void endPoint(int winner) {
		ball.reset();
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
		    	AddAchievementRequest ach = new AddAchievementRequest();
		    	ach.username = players[0];
		    	networkClient.sendNetworkObject(ach);
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
	 * @return
	 */
	private GameStatusUpdate createScoreUpdate() {
		GameStatusUpdate update = new GameStatusUpdate();
		update.gameId = game.gameId;
		update.username = players[0];
		//TODO Should also send the score!
		return update;
	}
	
	/**
	 * Start a new point: start the ball moving and change the game state
	 */
	private void startPoint() {
		ball.randomizeVelocity();
		gameState = GameState.INPROGRESS;
		statusMessage = null;
	}

	@Override
	public void resize(int arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	
	private static final Game game;
	static {
		game = new Game();
		game.gameId = "pong";
		game.name = "Pong";
		game.availableAchievements = achievements;
	}
	
	public Game getGame() {
		return game;
	}
	
}
