package deco2800.arcade.breakout.screens;

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

import deco2800.arcade.breakout.Ball;
import deco2800.arcade.breakout.Breakout;
import deco2800.arcade.breakout.Brick;
import deco2800.arcade.breakout.GameOverState;
import deco2800.arcade.breakout.GameState;
import deco2800.arcade.breakout.InProgressState;
import deco2800.arcade.breakout.Level;
import deco2800.arcade.breakout.LocalPlayer;
import deco2800.arcade.breakout.Paddle;
import deco2800.arcade.breakout.PauseState;
import deco2800.arcade.breakout.ReadyState;
import deco2800.arcade.breakout.powerup.PowerupManager;
import deco2800.arcade.client.*;

/**
 * Handles the current game screen and manages the game
 * 
 * @author Carlie Smits and Naveen Kumar
 * 
 */
public class GameScreen implements Screen {
	// Orthographic Camera is how game is being displayed.
	private OrthographicCamera camera;
	private final Breakout game;

	/*
	 * Creates private instance variables of basic game parts
	 */
	public String player;
	private Paddle paddle;
	private Ball ball;
	private Ball powerupBall;
	private int score;
	private int lives;

	// Cheat Code
	private int[] sequence = { Keys.UP, Keys.UP, Keys.DOWN, Keys.DOWN,
			Keys.LEFT, Keys.RIGHT, Keys.LEFT, Keys.RIGHT, Keys.B, Keys.A };
	private int currentButton = 0;
	private int pressed = 0;

	// The counting of random statistics
	public int highScore = 0;
	public int bumpCount = 0;
	public int brickBreak = 0;

	// GameOver status message constructor
	private String gameoverstatus;

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

	// Screen Size Parameters
	public static final int SCREENHEIGHT = 720;
	public static final int SCREENWIDTH = 1280;

	// Constructors for gamestates and level system
	private GameState gameState;
	private Level levelSystem;

	// Rendering Requirements for LibGDX
	private ShapeRenderer shapeRenderer;
	private SpriteBatch batch;
	private BitmapFont font;

	// Constructors for the position of the ball last hiting the paddle
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

	/**
	 * Sets the Breakout game variables
	 * 
	 * @param game
	 */
	public GameScreen(final Breakout game) {
		this.levelSystem = new Level();
		this.game = game;
		this.player = game.playerName();
		this.powerupManager = new PowerupManager(this);
	}

	/**
	 * Creates an area where the game functions are presented. This includes
	 * Brick layouts, sounds and other images.
	 */
	public void gamearea() {
		Texture.setEnforcePotImages(false);
		// Sets the display size
		resetScore();
		setLives(3);
		// Clears the window and prepare a clear screen
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
		getBall().setColor(1f, 1f, 1f, 0.5f);

		/*
		 * checks whether achievement condition are met. If conditions are met
		 * the incrementAchievement is called.
		 */
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

		/*
		 * Tries to read the Brick layout file and sets the BrickNum value. If
		 * there is an issue the can will dispose all files and set the value of
		 * gameState to GameOverState.
		 */
		try {
			bricks = levelSystem.readFile("levels/level" + getLevel() + ".txt",
					bricks, this);
			setBrickNum(bricks.length);
		} catch (Exception e) {
			System.err.println("Error is: " + e);
			this.dispose();
			this.gameState = new GameOverState();
		}

		// Sets the font values and batch
		shapeRenderer = new ShapeRenderer();
		font = new BitmapFont();
		font.setScale(2);
		batch = new SpriteBatch();

		// Sets the gameState to ReadyState and presents a message
		gameState = new ReadyState();
		setStatus("Click to start!");

	}

	/**
	 * Checks what is game mode was selected. If PowerupOn is true then certain
	 * brick will contain power ups. The Power ups are govern by the
	 * getPowerupManager.
	 * 
	 * @param b
	 */
	private void powerupCheck(Brick b) {
		if (!isPowerupOn()) {
			return;
		}
		if (Math.random() < 0.4) {
			Rectangle r = b.getShape();
			getPowerupManager().handlePowerup(r.x + r.width / 2, r.y);
		}

	}

	/**
	 * Decrements bricks and increments score once ball hits brick. Gives time
	 * for processing
	 * 
	 * @param bounce
	 * @param b
	 * @param pBall
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
		incrementScore(this.getLevel() * 2);
		setBrickNum(getBrickNum() - 1);
		powerupCheck(b);
		try {
			Thread.currentThread().sleep(35);
		} catch (Exception e) {
		}
	}

	/**
	 * Constantly keeps rendering the window and update screen
	 */
	public void render(float delta) {
		// Clears Frame
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		camera.update();

		shapeRenderer.setProjectionMatrix(camera.combined);
		batch.setProjectionMatrix(camera.combined);

		// renders the rectangles that are filled.
		shapeRenderer.begin(ShapeType.FilledRectangle);

		// Renders the Paddle
		getPaddle().render(shapeRenderer);

		// Renders the level
		levelSystem.render(bricks, this, batch);
		getPowerupManager().renderAll(batch);
		shapeRenderer.end();
		shapeRenderer.begin(ShapeType.FilledCircle);

		// Creates a Ball
		if (ball != null) {
			getBall().render(shapeRenderer);
		}
		if (powerupBall != null) {
			powerupBall.render(shapeRenderer);
		}
		shapeRenderer.end();

		// Renders the top display
		batch.begin();
		font.setColor(Color.GREEN);
		font.draw(batch, "Player: " + player, SCREENWIDTH / 4,
				SCREENHEIGHT - 20);
		font.draw(batch, "Life: " + Integer.toString(getLives()),
				SCREENWIDTH / 2, SCREENHEIGHT - 20);

		font.draw(batch, "Score: " + Integer.toString(getScore()),
				SCREENWIDTH * 3 / 4, SCREENHEIGHT - 20);

		// Provide any status messages
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
	 * Initialises the handleState. This control what state the game currently
	 * is.
	 */
	private void handleState() {
		gameState.handleState(this);
	}

	/**
	 * Sets the gamestate to InProgressState removes any messages and starts the
	 * movement of the ball
	 */
	public void Start() {
		getBall().randomizeVelocity();
		setStatus(null);
		gameState = new InProgressState();
		setStatus(null);
	}

	/**
	 * Adds the remaining lives as a score and checks whether any achievement
	 * criteria has been met. Also sets the gameState to GAMEOVER.
	 */
	public void win() {
		incrementScore(getLives() * 200);

		// Checks and sets a new highscore.
		if (getHighScore() < getScore()) {
			setHighScore(getScore());
			gameoverstatus = "Congratulations " + player
					+ " you have a new HighScore: " + getScore();

		} else {
			gameoverstatus = "Congratulations " + player
					+ " your final score is: " + getScore();
		}

		// Increments Achievements
		if (getLives() > 3) {
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
		incrementScore(bonus * 10);
		setSequence();
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
			getBall().reset(
					new Vector2(getPaddle().getPaddleX(), getPaddle()
							.getPaddleY()));
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

	/**
	 * Mutes and Unmutes the background Music
	 */
	public void mute() {
		if (mute) {
			music.play();
			mute = false;
		} else {
			music.stop();
			mute = true;
		}
	}

	/**
	 * Removes all media files from memory
	 */
	@Override
	public void dispose() {
		if (breaking != null) {
			breaking.dispose();
		}
		if (music != null) {
			music.dispose();
		}
		if (bump != null) {
			bump.dispose();
		}
		if (achieve != null) {
			achieve.dispose();
		}
		gameoverstatus = null;
		powerupManager.dispose();

	}

	/**
	 * 
	 */
	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	/**
	 * Pauses the game
	 */
	@Override
	public void pause() {
		game.pause();

	}

	/**
	 * Sets the game to a Pause state in which the ball and powerup ball does
	 * not move. The velocity of the ball is also stored for the inGameUnpause
	 * state
	 */
	public void inGamePause() {
		Vector2 prevBallVelocity = new Vector2(0, 0);
		Vector2 prevPowerupBallVelocity = new Vector2(0, 0);
		if (getBall() != null) {
			prevBallVelocity = new Vector2(getBall().getVelocity());
			getBall().stopBall();
		}
		if (getPowerupBall() != null) {
			prevPowerupBallVelocity = new Vector2(getPowerupBall()
					.getVelocity());
			getPowerupBall().stopBall();
		}
		gameState = new PauseState(this, prevBallVelocity,
				prevPowerupBallVelocity);
	}

	/**
	 * Retrieves the Velocity of the ball and the powerup ball. Restores the
	 * game back to InProgessState
	 * 
	 * @param prevVelocity
	 * @param prevPowerupBallVelocity
	 */
	public void inGameUnpause(Vector2 prevVelocity,
			Vector2 prevPowerupBallVelocity) {
		setStatus(null);
		if (getBall() != null) {
			getBall().resumeBall(prevVelocity);
		}
		if (getPowerupBall() != null) {
			getPowerupBall().resumeBall(prevPowerupBallVelocity);
		}
		gameState = new InProgressState();
	}

	/**
	 * Changes the screen to the main menu screen.
	 */
	public void setMenuScreen() {
		game.setScreen(game.getMenuScreen());
	}

	/**
	 * libGDX Resize method
	 */
	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	/**
	 * 
	 */
	@Override
	public void resume() {
		game.resume();
	}

	/**
	 * 
	 */
	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	/**
	 * Returns the game status message
	 * 
	 * @return String status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Sets a game status message
	 * 
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Returns the sequence
	 * 
	 * @return int[] sequence
	 */
	public int[] getSequence() {
		return sequence;
	}

	/**
	 * Sets the sequence to be null
	 * 
	 * @param sequence
	 * 
	 */
	public void setSequence() {
		this.sequence = null;
	}

	/**
	 * Return the currentButton
	 * 
	 * @return int currentButton
	 */
	public int getCurrentButton() {
		return currentButton;
	}

	/**
	 * 
	 * Sets the currentButton
	 * 
	 * @param int currentButton
	 */
	public void setCurrentButton(int currentButton) {
		this.currentButton = currentButton;
	}

	/**
	 * Returns the pressed variable
	 * 
	 * @return int pressed
	 */
	public int getPressed() {
		return pressed;
	}

	/**
	 * Sets the pressed button
	 * 
	 * @param int pressed
	 */
	public void setPressed(int pressed) {
		this.pressed = pressed;

	}

	/**
	 * Returns the Paddle
	 * 
	 * @return Paddle paddle
	 */
	public Paddle getPaddle() {
		return paddle;
	}

	/**
	 * Sets the Paddle
	 * 
	 * @param Paddle
	 *            paddle
	 */
	public void setPaddle(Paddle paddle) {
		this.paddle = paddle;
	}

	/**
	 * Returns the ball
	 * 
	 * @return Ball ball
	 */
	public Ball getBall() {
		return ball;
	}

	/**
	 * Sets the Ball
	 * 
	 * @param Ball
	 *            ball
	 */
	public void setBall(Ball ball) {
		this.ball = ball;
	}

	/**
	 * Creates a new powerup ball and normal ball
	 * 
	 * @param position
	 */
	public void createNewBall(Vector2 position) {
		if (powerupBall == null) {
			this.powerupBall = new Ball();
			this.powerupBall.reset(position);
			setNumBalls(2);
			this.powerupBall.randomizeVelocity();
			this.powerupBall.setColor(1f, 1f, 1f, 0.5f);
		} else {
			this.ball = new Ball();
			this.ball.reset(position);
			setNumBalls(2);
			this.ball.randomizeVelocity();
			this.ball.setColor(1f, 1f, 1f, 0.5f);
		}

	}

	/**
	 * Returns the Number of Bricks
	 * 
	 * @return int brickNum
	 */
	public int getBrickNum() {
		return brickNum;
	}

	/**
	 * Sets the number of Bricks
	 * 
	 * @param int brickNum
	 */
	public void setBrickNum(int brickNum) {
		this.brickNum = brickNum;
	}

	/**
	 * Returns the Level number
	 * 
	 * @return int level
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * Sets the level number
	 * 
	 * @param int level
	 * 
	 */
	public void setLevel(int level) {
		this.level = level;
	}

	/**
	 * returns the last hit x variable of the ball
	 * 
	 * @return float lastHitX
	 */
	public float getLastHitX() {
		return lastHitX;
	}

	/**
	 * Sets the last hit x variable of the ball
	 * 
	 * @param int lastHitX
	 */
	public void setLastHitX(float lastHitX) {
		this.lastHitX = lastHitX;
	}

	/**
	 * returns the last hit y variable of the ball
	 * 
	 * @return float lastHitY
	 */
	public float getLastHitY() {
		return lastHitY;
	}

	/**
	 * Sets the last hit y variable of the ball
	 * 
	 * @param int lastHity
	 */
	public void setLastHitY(float lastHitY) {
		this.lastHitY = lastHitY;
	}

	/**
	 * Set the inner
	 * 
	 * @param inner
	 */
	public void setInner(int inner) {
		this.inner = inner;
	}

	/**
	 * Sets the outer
	 * 
	 * @param outer
	 */
	public void setOuter(int outer) {
		this.outer = outer;
	}

	/**
	 * Returns the number of ball that are currently in play
	 * 
	 * @return int numBalls
	 */
	public int getNumBalls() {
		return this.numBalls;
	}

	/**
	 * Sets the number of plays in play
	 * 
	 * @param int newNum
	 */
	public void setNumBalls(int newNum) {
		this.numBalls = newNum;
	}

	/**
	 * returns the powerupManager
	 * 
	 * @return PowerupManager powerupManager
	 */
	public PowerupManager getPowerupManager() {
		return powerupManager;
	}

	/**
	 * Returns true if powerups are turned on
	 * 
	 * @return boolean powerupsOn
	 */
	public boolean isPowerupOn() {
		return this.powerupsOn;
	}

	/**
	 * Sets the powerup mode. True means that enchanced mode is on
	 * 
	 * @param boolean mode
	 */
	public void switchGameMode(boolean mode) {
		this.powerupsOn = mode;
	}

	/**
	 * returns the PowerUpBall
	 * 
	 * @return Ball powerupBall
	 */
	public Ball getPowerupBall() {
		return this.powerupBall;
	}

	/**
	 * Destroys any existing powerup balls and sets the NumBalls
	 */
	public void destroyPowerupBall() {
		if (powerupBall != null) {
			setNumBalls(getNumBalls() - 1);
			powerupBall = null;
		}
	}

	/**
	 * Destorys the Ball
	 */
	public void destroyBall() {
		if (ball != null) {
			ball = null;
		}
	}

	/**
	 * Return the remaining number of lives
	 * 
	 * @return int lives
	 */
	public int getLives() {
		return lives;
	}

	/**
	 * Sets the number of Lives
	 * 
	 * @param int lives
	 */
	public void setLives(int lives) {

		this.lives = lives;
	}

	/**
	 * Increases the number of Lives by desired value
	 * 
	 * @param int value
	 */
	public void incrementLives(int value) {
		this.lives = this.lives + value;
	}

	/**
	 * Decreases the number of Lives by desired value
	 * 
	 * @param int value
	 */
	public void decrementLives(int value) {
		this.lives = this.lives - value;
	}

	/**
	 * Return the score
	 * 
	 * @return int score
	 */
	public int getScore() {
		return score;
	}

	/**
	 * reset the score variable to 0
	 */
	public void resetScore() {
		this.score = 0;
	}

	/**
	 * Increases the score by desired value
	 * 
	 * @param int value
	 */
	public void incrementScore(int value) {
		this.score = this.score + value;
	}

	/**
	 * Decreases the score by desired value
	 * 
	 * @param int value
	 */
	public void decrementScore(int value) {
		this.score = this.score - value;
	}

	/**
	 * Set the value of Highscore
	 * 
	 * @param int score
	 */
	public void setHighScore(int score) {
		if (score > 0) {
			this.highScore = score;
			game.getHighScoreClient().storeScore("Number", score);
		} else {
			this.highScore = 0;
		}
	}

	/**
	 * Returns the value of Highscore
	 * 
	 * @return int highScore
	 */
	public int getHighScore() {
		return highScore;
	}

	/**
	 * Plays the background Music
	 */
	public void playMusic() {
		music.play();
	}

	/**
	 * Stops the background Music
	 */
	public void stopMusic() {
		music.stop();
	}

	/**
	 * Increments the number of slow balls activated
	 */
	public void incrementNumSlowBallsActivated() {
		slowBallsActivated++;
	}

	/**
	 * Returns the number of slow balls activated
	 * 
	 * @return int slowBallsActivated
	 */
	public int getNumSlowBallsActivated() {
		return slowBallsActivated;
	}

	/**
	 * Increments the Bump Counter
	 */
	public void incrementBumpCount() {
		bumpCount++;
	}

	/**
	 * Returns the Bump Counter
	 * 
	 * @return int bumpCount
	 */
	public int getBumpCount() {
		return bumpCount;
	}

	/**
	 * Increments the Brick Break Counter
	 */
	public void incrementBrickBreak() {
		brickBreak++;
	}

	/**
	 * Returns the Brick break counter
	 * 
	 * @return int brickBreak
	 */
	public int getBrickBreak() {
		return brickBreak;
	}

	/**
	 * Return the Brick array
	 * 
	 * @return Brick[] bricks
	 */
	public Brick[] getBrickArray() {
		return this.bricks;
	}
}
