package deco2800.arcade.breakout;

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
import deco2800.arcade.breakout.PongBall;
import deco2800.arcade.protocol.achievement.AddAchievementRequest;
import deco2800.arcade.protocol.game.GameStatusUpdate;
import deco2800.arcade.client.GameClient;
import deco2800.arcade.client.network.NetworkClient;

/**
 * 
 * 
 * 
 */
@ArcadeGame(id="Breakout")

public class Breakout extends GameClient{
	
	//Orthographic Camera is how the is displayed.
	private OrthographicCamera camera;
	
	
	private String player;
	private NetworkClient nc;
	private Paddle paddle;
	private PongBall ball;
	private int score;
	private int lives;
	private String status;
	
	//Screen Parameters
	public static final int SCREENHEIGHT = 480;
	public static final int SCREENWIDTH = 800;
	
	//Game States
	private enum GameState {
		READY,
		INPROGRESS,
		GAMEOVER
	}
	
	private GameState gameState;
	
	private ShapeRenderer shapeRenderer;
	private SpriteBatch batch;
	private BitmapFont font;
	
	
	public Breakout(Player player, NetworkClient networkClient) {
		super(player, networkClient);
		 this.player = player.getUsername();
		 this.nc = networkClient;
	}
	
	@Override
	public void create() {
		super.create();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, SCREENWIDTH, SCREENHEIGHT);
		
		paddle = new LocalPlayer(new Vector2(SCREENWIDTH/2, 10));
		ball = new PongBall();
		ball.setColor(1,1,1,1);
		
		
		shapeRenderer = new ShapeRenderer();
		font = new BitmapFont();
		font.setScale(2);
		batch = new SpriteBatch();
		
		score = 0;
		lives = 3;
		gameState = GameState.READY;
		status = "Start!";
		
	}
	
	@Override
	public void dispose(){
		super.dispose();
	}
	
	@Override
	public void pause(){
		super.pause();
	}
	
	@Override
	public void resume(){
		super.resume();
	}
	
	public void render(){
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
	    Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
	    
	    camera.update();
	    
	    shapeRenderer.setProjectionMatrix(camera.combined);
	    batch.setProjectionMatrix(camera.combined);
	    
	    shapeRenderer.begin(ShapeType.FilledRectangle);
	    
	    paddle.render(shapeRenderer);
	    ball.render(shapeRenderer);
	    
	    shapeRenderer.end();
	    
	    batch.begin();
	    font.setColor(Color.GREEN);
	    font.draw(batch, player, SCREENWIDTH/2, SCREENHEIGHT/2);
	    //font.draw(batch, Integer.toString(score), SCREENWIDTH/2 - 50, SCREENHEIGHT/2 - 50);
	    font.draw(batch,"Life " + Integer.toString(lives), SCREENWIDTH/2 + 50, SCREENHEIGHT/2 + 50);
	    batch.end();
	    
	    switch(gameState) {
	    
	    case READY:
	    	if (Gdx.input.isButtonPressed(Keys.SPACE) || Gdx.input.isTouched()){
	    		Start();
	    	}
	    	break;
	    	
	    case INPROGRESS:
	    	paddle.update(ball);
	    	
	    	ball.move(Gdx.graphics.getDeltaTime());
	    	
	    	if ( ball.bounds.overlaps(paddle.paddleShape) && ball.getYVelocity() < 0 ) {
	    		ball.bounceY();
	    	}
	    	
	    	if (ball.bounds.y >= SCREENHEIGHT-PongBall.WIDTH) {
	    		ball.bounceY();
	    	}
	    	
	    	
	    	if (ball.bounds.x <= 0 || ball.bounds.x + PongBall.WIDTH > SCREENWIDTH) {
	    		ball.bounceX();
	    	}
	    	
	    	if (ball.bounds.y <= 0) {
	    		roundOver();
	    	}
	    	
	    	break;
	    	
	    case GAMEOVER:
	    	if (Gdx.input.isTouched()){
	    		gameOver();
	    	}
	    	
	    	break;
	    	
	    
	    }
	    
	    
	}


	@Override
	public Game getGame() {
		return game;
	}
	
	private void Start() {
		ball.randomizeVelocity();
		gameState = GameState.INPROGRESS;
		
	}
	
	private void roundOver() {
		if(lives > 0){
			ball.reset();
			lives--;
			gameState = GameState.READY;
		} else {
			gameState = GameState.GAMEOVER;
		}
		
	}
	
	private static final Game game;
	static {
		game = new Game();
		game.gameId = "breakout";
		game.name = "Breakout";
		//game.availableAchievements = achievements;
	}
	

	
}


