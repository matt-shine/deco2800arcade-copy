package deco2800.arcade.breakout;


import java.io.IOException;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import deco2800.arcade.breakout.powerup.PowerupManager;
import deco2800.arcade.client.*;


public class GameScreen implements Screen  {
	// Orthographic Camera is how the is displayed.
	
	private OrthographicCamera camera;
	private final Breakout game;
	/*
	 * Creates private instance variables for each element of The
	 */
	public String player;
	// private NetworkClient nc;
	private Paddle paddle;
	private Ball ball;
	private Ball powerupBall;
	private int score;
	private int lives;
	

	// Cheat Code
	private int[] sequence = {Keys.UP, Keys.UP, Keys.DOWN, Keys.DOWN, Keys.LEFT, Keys.RIGHT, Keys.LEFT, Keys.RIGHT, Keys.B, Keys.A};
	private int currentButton = 0;
	private int pressed = 0;

	// The counting of random statistics
	public int highScore = 0;
	public int bumpCount = 0;
	public int brickBreak = 0;

	private String gameoverstatus;

	private Texture background;

	// Sounds and Music constructors
	public Sound breaking;
	public Music music;
	public Sound bump;
	public Sound achieve;
	public boolean mute = false;
	
	private String status = null;
	
	// Integer to determine what the current level is
	private int level = 1;

	// Keeps track of the number of bricks on screen.
	private int brickNum;
	private int numBalls = 1;

	// Screen Parameters
	public static final int SCREENHEIGHT = 720;
	public static final int SCREENWIDTH = 1280;

	private GameState gameState;
	private Level levelSystem;

	// Rendering Requirements for LibGDX
	private ShapeRenderer shapeRenderer;
	private SpriteBatch batch;
	private BitmapFont font;
	
	private float lastHitX;
	private float lastHitY;

	// Array of Brick
	Brick bricks[];
	
	// variables for rendering colours
	private int outer = 0;
	private int inner = 0;
	
	// Power up manager and details
	private boolean powerupsOn = false;
	private PowerupManager powerupManager;
	private int slowBallsActivated = 0;
	
	
	GameScreen(final Breakout game) {
		this.levelSystem = new Level();
		resetScore();
		setLives(3);
		this.game = game;
		this.player = game.playerName();
		this.powerupManager = new PowerupManager(this);
		//gamearea();
	}
	
	public void gamearea() {
		Texture.setEnforcePotImages(false);
		//background = new Texture(Gdx.files.classpath("imgs/background.png"));
		// Sets the display size
		camera = new OrthographicCamera();
		camera.setToOrtho(false, SCREENWIDTH, SCREENHEIGHT);

		// access the file location of the sounds
		breaking = Gdx.audio.newSound(Gdx.files.classpath("sounds/break.wav"));
		music = Gdx.audio.newMusic(Gdx.files.classpath("sounds/bgmusic.ogg"));
		bump = Gdx.audio.newSound(Gdx.files.classpath("sounds/bump.wav"));
		achieve = Gdx.audio.newSound(Gdx.files.classpath("sounds/achieve.mp3"));

		// setting and playing the background music
		music.setLooping(true);
		music.setVolume(0.2f);
		playMusic();

		// setting the ball and paddle
		setPaddle(new LocalPlayer(new Vector2(SCREENWIDTH / 2, 10)));
		setBall(new Ball());
		getBall().setColor(0.7f, 0.7f, 0.7f, 0.5f);
		
		if (getLevel() == 4) {
			game.incrementAchievement("breakout.basic");
			achieve.play();
			gameState = new GameOverState();
		}
		
		if (getLevel() == 7) {
			game.incrementAchievement("breakout.intermediate");
			achieve.play();
			gameState = new GameOverState();
		}

		if (getLevel() == 10) {
			game.incrementAchievement("breakout.pro");
			achieve.play();
			gameState = new GameOverState();
		}
		try {
			bricks = levelSystem.readFile("levels/level" + getLevel() + ".txt",
				bricks, this);
			setBrickNum(bricks.length);
		} catch (Exception e) {
			System.err.println("Error is: " + e);
			this.dispose();
			this.gameState = new GameOverState();
		}
//		else if (getLevel() == 2) {
//			bricks = levelSystem.levelTwo(bricks, this);
//		} else if (getLevel() == 3) {
//			bricks = levelSystem.levelThree(bricks, this);
//		} else if (getLevel() == 4) {
//			bricks = levelSystem.levelFour(bricks, this);
//			game.incrementAchievement("breakout.basic");
//			achieve.play();
//		} else if (getLevel() == 5) {
//			bricks = levelSystem.levelFive(bricks, this);
//		} else if (getLevel() == 6) {
//			bricks = levelSystem.levelSix(bricks, this);
//		} else if (getLevel() == 7) {
//			bricks = levelSystem.levelSeven(bricks, this);
//			game.incrementAchievement("breakout.intermediate");
//			achieve.play();
//		} else if (getLevel() == 8) {
//			bricks = levelSystem.levelEight(bricks, this);
//		} else if (getLevel() == 9) {
//			bricks = levelSystem.levelNine(bricks, this);
//		} else if (getLevel() == 10) {
//			bricks = levelSystem.levelTen(bricks,this);

		shapeRenderer = new ShapeRenderer();
		font = new BitmapFont();
		font.setScale(2);
		batch = new SpriteBatch();

		gameState = new ReadyState();
		setStatus("Click to start!");

	}
	
	private void powerupCheck(Brick b) {
		if (!isPowerupOn()) {
			return;
		}
		if (Math.random() < 0.4) {
			Rectangle r = b.getShape();
			getPowerupManager().handlePowerup(r.x + r.width/2, r.y);
		}
		
	}
	
	/*
	 * Decrements bricks and increments score once ball hits brick. Gives time
	 * for processing
	 */
	public void updateGameState(int bounce, Brick b, boolean pBall) {
		if (pBall) {
			if (powerupBall != null) {
				if (bounce == 0) {
					getPowerupBall().bounceX(0);
				} else if (bounce == 1) {
					getPowerupBall().bounceY(0);
				} else {
					getPowerupBall().bounceX(0);
					getPowerupBall().bounceY(0);
				}
				setLastHitX(getPowerupBall().getX());
				setLastHitY(getPowerupBall().getY());
			}
		} else {
			if (ball != null) {
				if (bounce == 0) {
					getBall().bounceX(0);
				} else if (bounce == 1) {
					getBall().bounceY(0);
				} else {
					getBall().bounceX(0);
					getBall().bounceY(0);
				}
				
				setLastHitX(getBall().getX());
				setLastHitY(getBall().getY());
			}
		}
		breaking.play();
		brickBreak++;
		incrementScore(this.getLevel()*2);
		setBrickNum(getBrickNum() - 1);
		powerupCheck(b);
		try {
			Thread.currentThread().sleep(35);
		} catch (Exception e) {
		}
	}
	
	
	
	
	 public void render(float delta) {
		// Clears Frame
			Gdx.gl.glClearColor(0, 0, 0, 1);
			Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

			camera.update();

			shapeRenderer.setProjectionMatrix(camera.combined);
			batch.setProjectionMatrix(camera.combined);

			// Draw a background
			//batch.begin();
			//batch.draw(background, 0, 0);
			//batch.end();

			// renders the rectangles that are filled.
			shapeRenderer.begin(ShapeType.FilledRectangle);

			getPaddle().render(shapeRenderer);
			getPowerupManager().renderAll(batch);
			

			// Render the level
			levelSystem.render(bricks, outer, inner, this, shapeRenderer, batch);
			shapeRenderer.end();
			shapeRenderer.begin(ShapeType.FilledCircle);
			// Ball is a Circle
			if (ball != null) {
				getBall().render(shapeRenderer);
			}
			if (powerupBall != null) {
				powerupBall.render(shapeRenderer);
			}
			shapeRenderer.end();
			
			
			batch.begin();
			font.setColor(Color.GREEN);
			font.draw(batch, "Player: " + player, SCREENWIDTH / 4, SCREENHEIGHT - 20);
			font.draw(batch, "Life: " + Integer.toString(getLives()), SCREENWIDTH / 2,
					SCREENHEIGHT - 20);

			font.draw(batch, "Score: " + Integer.toString(getScore()),
					SCREENWIDTH * 3 / 4, SCREENHEIGHT - 20);
			if (gameoverstatus != null) {
				font.setColor(Color.WHITE);

				font.draw(batch, gameoverstatus, SCREENWIDTH / 2 - 250,
						SCREENHEIGHT / 2);
			}

			if (getStatus() != null) {
				font.setColor(Color.WHITE);

				font.draw(batch, getStatus(), SCREENWIDTH / 2 - 250,
						SCREENHEIGHT / 2 - 60);
			}
			if (gameState instanceof GameOverState) {
				font.draw(batch, "Click to exit", SCREENWIDTH / 2 - 80,
						SCREENHEIGHT / 2 - 60);
			}

			batch.end();
			handleState();
	 }
	
	 /**
	 * 
	 */
	private void handleState() {
		gameState.handleState(this);
	}

	
	 void Start() {
		getBall().randomizeVelocity();
		setStatus(null);
		gameState = new InProgressState();
		setStatus(null);

	}
	 /**
	 * Adds the remaining lives as a score and checks whether any achievement
	 * criteria has been met. Also sets the gameState to GAMEOVER.
	 */
	void win() {
		incrementScore(getLives() * 5);

		if (getHighScore() < getScore()){
			setHighScore(getScore());
			gameoverstatus = "Congratulations " + player + " you have a new HighScore: "
					+ getScore();
			
		}else{
			gameoverstatus = "Congratulations " + player + " your final score is: "
					+ getScore();
		}
		System.out.println("Congratulations " + player
				+ " your final score is: " + getScore());
		if (getLives() == 3) {
			game.incrementAchievement("breakout.prefect");
		} else if (getLives() == 0) {
			game.incrementAchievement("breakout.closeOne");
		} else if (getScore() < 0) {
			game.incrementAchievement("breakout.noob");
		gameState = new GameOverState();
		}	
		
		game.incrementAchievement("breakout.winGame");
		gameState = new GameOverState();
		
	}
	
		
	/**
	 * Rewards the Player extra lives and makes the sequence null, so that the
	 * Player can not re-use the cheat
	 */
	public void cheatBonus(int bonus) {
		incrementLives(bonus);
		incrementScore(bonus*10);
		setSequence(null);
		game.incrementAchievement("breakout.secret");
		achieve.play();
	}

	/**
	 * Resets games area or sets the gameState to GAMEOVER
	 */
	public void roundOver() {
		/*
		 * Checks whether the lives have fallen below 0 If true then the
		 * gameState is set to GAMEOVER, Otherwise a lives is decremented and
		 * gameState is set to READY
		 */
		if (getLives() > 0) {
			if (getBall() == null) {
				setBall(new Ball());
			}
			getBall().reset(new Vector2(getPaddle().getPaddleX(), getPaddle().getPaddleY()));
			setNumBalls(1);
			slowBallsActivated = 0;
			destroyPowerupBall();
			decrementLives(1);
			decrementScore(5);
			gameState = new ReadyState();
		} else {

			gameoverstatus = "Bad luck " + player + " your final score is: "
					+ getScore();
			gameState = new GameOverState();
		}

	}
	 
	 public void mute(){
		 if(mute){
			 music.play();
			 mute = false;
		 } else {
			 music.stop();
			 mute = true;
		 }
	 }
	 
	
	@Override
	public void dispose() {
		breaking.dispose();
		music.dispose();
		bump.dispose();
		achieve.dispose();
		powerupManager.dispose();
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void pause() {
		game.pause();
		if(Gdx.input.isKeyPressed(Keys.R)){
			resume();
		}
	}

	

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		game.resume();		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the sequence
	 */
	public int[] getSequence() {
		return sequence;
	}
	/**
	 * @param sequence the sequence to set
	 */
	public void setSequence(int[] sequence) {
		this.sequence = sequence;
	}
	/**
	 * @return the currentButton
	 */
	public int getCurrentButton() {
		return currentButton;
	}

	/**
	 * @param currentButton the currentButton to set
	 */
	public void setCurrentButton(int currentButton) {
		this.currentButton = currentButton;
	}
	
	/**
	 * 
	 * @return the check press int
	 */
	public int getPressed(){
		return pressed;
	}
	/**
	 * 
	 * @param pressed
	 */
	public void setPressed(int pressed){
		this.pressed = pressed;
		
	}
	
	/**
	 * @return the paddle
	 */
	public Paddle getPaddle() {
		return paddle;
	}
	/**
	 * @param paddle the paddle to set
	 */
	public void setPaddle(Paddle paddle) {
		this.paddle = paddle;
	}
	/**
	 * @return the ball
	 */
	public Ball getBall() {
		return ball;
	}
	/**
	 * @param ball the ball to set
	 */
	public void setBall(Ball ball) {
		this.ball = ball;
	}
	
	public void createNewBall(Vector2 position) {
		if (powerupBall == null) {
			this.powerupBall = new Ball();
			System.out.println("Runs");
			this.powerupBall.reset(position);
			setNumBalls(2);
			this.powerupBall.randomizeVelocity();
			this.powerupBall.setColor(0.7f, 0.7f, 0.7f, 0.5f);
		} else {
			this.ball = new Ball();
			System.out.println("Runs");
			this.ball.reset(position);
			setNumBalls(2);
			this.ball.randomizeVelocity();
			this.ball.setColor(0.7f, 0.7f, 0.7f, 0.5f);
		}
		
	}
	
	/**
	 * @return the brickNum
	 */
	public int getBrickNum() {
		return brickNum;
	}
	/**
	 * @param brickNum the brickNum to set
	 */
	public void setBrickNum(int brickNum) {
		this.brickNum = brickNum;
	}
	/**
	 * @return the level
	 */
	public int getLevel() {
		return level;
	}
	/**
	 * @param level the level to set
	 */
	public void setLevel(int level) {
		this.level = level;
	}
	/**
	 * @return the lastHitX
	 */
	public float getLastHitX() {
		return lastHitX;
	}
	/**
	 * @param lastHitX the lastHitX to set
	 */
	public void setLastHitX(float lastHitX) {
		this.lastHitX = lastHitX;
	}
	/**
	 * @return the lastHitY
	 */
	public float getLastHitY() {
		return lastHitY;
	}
	/**
	 * @param lastHitY the lastHitY to set
	 */
	public void setLastHitY(float lastHitY) {
		this.lastHitY = lastHitY;
	}
	
	public void setInner(int inner) {
		this.inner = inner;
	}
	
	public void setOuter(int outer) {
		this.outer = outer;
	}
	
	public int getNumBalls() {
		return this.numBalls;
	}
	
	public void setNumBalls(int newNum) {
		this.numBalls = newNum;
	}

	public PowerupManager getPowerupManager() {
		return powerupManager;
	}
	
	public boolean isPowerupOn() {
		return this.powerupsOn;
	}
	
	public void switchGameMode(boolean mode) {
		this.powerupsOn = mode;
	}
	
	public Ball getPowerupBall() {
		return this.powerupBall;
	}
	
	public void destroyPowerupBall() {
		if (powerupBall != null) {
			setNumBalls(getNumBalls() - 1);
			powerupBall = null;
		}
	}
	
	public void destroyBall() {
		if (ball != null) {
			ball = null;
		}
	}
	public int getLives(){
		return lives;
	}
	
	public void setLives(int lives){
		
		this.lives = lives;
	}
	
	public void incrementLives(int value){
		this.lives = this.lives + value;
	}
	
	public void decrementLives(int value){
		this.lives = this.lives - value;
	}
	
	public int getScore(){
		return score;
	}
	
	public void resetScore(){
		this.score = 0;
	}
	
	public void incrementScore(int value){
		this.score = this.score + value;
	}
	
	public void decrementScore(int value){
		this.score = this.score - value;
	}
	
	public void setHighScore(int score){
		if (score > 0){
			this.highScore = score;
			game.highscoreUser.storeScore("Score", getHighScore());
		} else {
			this.highScore = 0;
		}
	}
	
	public int getHighScore(){
		return highScore;
	}
	
	public void playMusic(){
		music.play();
	}
	
	public void stopMusic(){
		music.stop();
	}
	
	public void incrementNumSlowBallsActivated() {
		slowBallsActivated++;
	}
	
	public int getNumSlowBallsActivated() {
		return slowBallsActivated;
	}
	
}
