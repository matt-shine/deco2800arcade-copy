package deco2800.arcade.pong;

import java.util.HashSet;
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
import deco2800.arcade.protocol.NetworkObject;
import deco2800.arcade.protocol.achievement.AddAchievementRequest;
import deco2800.arcade.protocol.game.GameStatusUpdate;
import deco2800.arcade.client.network.NetworkClient;

@ArcadeGame(id="pong")
public class Pong extends Game {

	{
		gameId = "pong";
		name = "Pong";
	}
	
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
	private String[] players = new String[2];
	
	public static final int WINNINGSCORE = 5;
	public static final int SCREENHEIGHT = 480;
	public static final int SCREENWIDTH = 800;
	public static final int KBPADDLESPEED = 200;
	
	private ShapeRenderer shapeRenderer;
	private SpriteBatch batch;
	private BitmapFont font;

	private String statusMessage;
	private static Set<Achievement> achievements = new HashSet<Achievement>();
	static {		
		Achievement winPong = new Achievement("Win a game of Pong");
		achievements.add(winPong);
	}
	
	//Network client for communicating with the server.
	//Should games reuse the client of the arcade somehow? Probably!
	private NetworkClient networkClient;

	public Pong(String userName, NetworkClient client) {
		players[0] = userName;
		players[1] = "Player 2";
		this.networkClient = client;
	}
	
	
	@Override
	public void create() {
		//Achievements
		availableAchievements = achievements;
		
		//Initialise camera
		camera = new OrthographicCamera();
		camera.setToOrtho(false, SCREENWIDTH, SCREENHEIGHT);
		
		leftPaddle = new Paddle(new Vector2(20,SCREENHEIGHT/2 - Paddle.INITHEIGHT/2));
		rightPaddle = new Paddle(new Vector2(SCREENWIDTH-Paddle.WIDTH-20,SCREENHEIGHT/2 - Paddle.INITHEIGHT/2));
		
		ball = new Ball();
		
		shapeRenderer = new ShapeRenderer();
		font = new BitmapFont();
		font.setScale(2);
		batch = new SpriteBatch();
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
	    //shapeRenderer.end();
	    
	    //render right paddle
	    //shapeRenderer.begin(ShapeType.FilledRectangle);
	    shapeRenderer.setColor(0, 0, 1, 1);
	    shapeRenderer.filledRect(rightPaddle.bounds.x, rightPaddle.bounds.y, rightPaddle.bounds.width, rightPaddle.bounds.height);
	    //shapeRenderer.end();
	    
	    //render ball
	    //shapeRenderer.begin(ShapeType.FilledRectangle);
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
	    
	    if (statusMessage != null) {
	    	font.setColor(Color.WHITE);
	    	font.draw(batch, statusMessage, SCREENWIDTH/2 - 100, SCREENHEIGHT-100);
	    	if (gameState == GameState.GAMEOVER) {
	    		font.draw(batch, "Click to exit", SCREENWIDTH/2 - 100, SCREENHEIGHT - 200);
	    	}
	    }
	    batch.end();
		//TODO
	    
	    //shapeRenderer.end();
	    
	    switch(gameState) {
	    
	    case READY:
	    	if (Gdx.input.isTouched()) {
	    		startPoint();
	    	}
	    	break;
	    	
	    case INPROGRESS:
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
	    	if (ball.bounds.overlaps(leftPaddle.bounds) || ball.bounds.overlaps(rightPaddle.bounds)) {
	    		ball.bounceX();
	    	}
	    	if (ball.bounds.y <= 0 || ball.bounds.y >= SCREENHEIGHT-Ball.WIDTH) {
	    		ball.bounceY();
	    	}
	    	if (ball.bounds.x <= 0) {
	    		endPoint(1);
	    	}
	    	break;
	    case GAMEOVER:
	    	if (Gdx.input.isTouched()) {
	    		matchOver();
	    	}
	    	break;
	    }
	    
	}

	private void endPoint(int winner) {
		ball.setPosition(new Vector2(SCREENWIDTH/2 - Ball.WIDTH/2, SCREENHEIGHT/2 - Ball.WIDTH/2));
		scores[winner]++;
		if (scores[winner] == WINNINGSCORE) {	
		    int loser = winner==1?0:1;
		    statusMessage = players[winner] + " Wins " + scores[winner] + " - " + scores[loser] + "!";
		    gameState = GameState.GAMEOVER;
		    networkClient.sendNetworkObject(createScoreUpdate());
		    if (winner == 0) {
		    	AddAchievementRequest ach = new AddAchievementRequest();
		    	ach.username = players[0];
		    	networkClient.sendNetworkObject(ach);
		    	//TODO Should have more detail in the achievement message
		    }
		} else {
			gameState = GameState.READY;
			statusMessage = "Click to start!";
		}
	}
	
	private GameStatusUpdate createScoreUpdate() {
		GameStatusUpdate update = new GameStatusUpdate();
		update.gameId = gameId;
		update.username = players[0];
		//TODO Should also send the score!
		return update;
	}


	private void matchOver() {
		//TODO
	}

	private void startPoint() {
		ball.randomizeSpeed();
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
	
}
