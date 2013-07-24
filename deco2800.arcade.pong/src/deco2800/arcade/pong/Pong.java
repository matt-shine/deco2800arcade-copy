package deco2800.arcade.pong;

import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
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
	public static final int KBPADDLESPEED = 200;
	
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
	 * Create the game
	 */
	@Override
	public void create() {
		
		//Initialise camera
		camera = new OrthographicCamera();
		camera.setToOrtho(false, SCREENWIDTH, SCREENHEIGHT);
		
		// Create the paddles
		leftPaddle = new Paddle(new Vector2(20,SCREENHEIGHT/2 - Paddle.INITHEIGHT/2));
		rightPaddle = new Paddle(new Vector2(SCREENWIDTH-Paddle.WIDTH-20,SCREENHEIGHT/2 - Paddle.INITHEIGHT/2));
		
		//Create the ball
		ball = new Ball();
		
		//Necessar for rendering
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

		//render left paddle
	    shapeRenderer.begin(ShapeType.FilledRectangle);
	    shapeRenderer.setColor(1, 0, 0, 1);
	    shapeRenderer.filledRect(leftPaddle.bounds.x, leftPaddle.bounds.y, leftPaddle.bounds.width, leftPaddle.bounds.height);
	    
	    //render right paddle
	    shapeRenderer.setColor(0, 0, 1, 1);
	    shapeRenderer.filledRect(rightPaddle.bounds.x, rightPaddle.bounds.y, rightPaddle.bounds.width, rightPaddle.bounds.height);
	    
	    //render ball
	    shapeRenderer.setColor(1,1,1,1);
	    shapeRenderer.filledRect(ball.bounds.x, ball.bounds.y, ball.bounds.width, ball.bounds.height);
	    
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
	    	if (Gdx.input.isTouched()) {
	    		Vector2 touchPos = new Vector2();
	    		touchPos.set(Gdx.input.getX(), Gdx.input.getY());
	    		//camera.unproject(touchPos);
	    		leftPaddle.bounds.y = touchPos.y - leftPaddle.bounds.height / 2;
	    	}

	    	//Move the left paddle (keyboard)
	    	if(Gdx.input.isKeyPressed(Keys.UP)) leftPaddle.move(KBPADDLESPEED * Gdx.graphics.getDeltaTime());//leftPaddle.bounds.y += KBPADDLESPEED * Gdx.graphics.getDeltaTime();
	    	if(Gdx.input.isKeyPressed(Keys.DOWN)) leftPaddle.move(-KBPADDLESPEED * Gdx.graphics.getDeltaTime());//leftPaddle.position.y -= KBPADDLESPEED * Gdx.graphics.getDeltaTime();

	    	//Move the right paddle (automatic)
	    	rightPaddle.bounds.y = ball.bounds.y;

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
	    	}
	    	
	    	//If the ball gets to the right edge then player 1 wins
	    	if (ball.bounds.x + Ball.WIDTH > SCREENWIDTH) {
	    		endPoint(0);
	    	}
	    	break;
	    case GAMEOVER: //The game has been won, wait to exit
	    	if (Gdx.input.isTouched()) {
	    		matchOver();
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
	 * The game has been won. Do something!
	 */
	private void matchOver() {
		//TODO
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
