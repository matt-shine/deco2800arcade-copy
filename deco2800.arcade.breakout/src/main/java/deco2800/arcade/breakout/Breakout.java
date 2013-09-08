package deco2800.arcade.breakout;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;

import deco2800.arcade.model.Game;
import deco2800.arcade.model.Game.ArcadeGame;
import deco2800.arcade.model.Player;
import deco2800.arcade.breakout.PongBall;
import deco2800.arcade.client.ArcadeSystem;
import deco2800.arcade.client.GameClient;
import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.client.ArcadeSystem;

/**
 * 
 * 
 * 
 */
@ArcadeGame(id = "Breakout")
public class Breakout extends GameClient {

	// Orthographic Camera is how the is displayed.
	private OrthographicCamera camera;

	/*
	 * Creates private instance variables for each element of The
	 */
	private String player;
	// private NetworkClient nc;
	private Paddle paddle;
	private Ball ball;
	private int score;
	private int lives;

	private int[] sequence = { 19, 19, 20, 20, 21, 22, 21, 22, 30, 29 };
	private int currentButton = 0;
	public int bumpCount = 0;
	public int brickBreak = 0;

	private String gameoverstatus;


	private Texture background;

	public Sound breaking;
//	public Music music;
//	public Sound bump;

	private String status = null;
	
	// Integer to determine what the current level is
	private int level = 1;

	// Keeps track of the number of bricks on screen.
	private int brickNum;

	// Screen Parameters
	public static final int SCREENHEIGHT = 720;
	public static final int SCREENWIDTH = 1280;

	// Game States
	private enum GameState {
		READY, INPROGRESS, GAMEOVER
	}

	private GameState gameState;

	// Rendering Requirements for LibGDX
	private ShapeRenderer shapeRenderer;
	private SpriteBatch batch;
	private BitmapFont font;
	
	private float lastHitX;
	private float lastHitY;

	// Array of Brick
	Brick bricks[];

	public Breakout(Player player, NetworkClient networkClient) {
		super(player, networkClient);
		this.player = player.getUsername();
		score = 0;
		lives = 3;
		// this.nc = networkClient;
	}

	/**
	 * Creates the game area.
	 */
	@Override
	public void create() {
		super.create();
		Texture.setEnforcePotImages(false);
		background = new Texture(Gdx.files.classpath("imgs/background.png"));
		// Sets the display size
		camera = new OrthographicCamera();
		camera.setToOrtho(false, SCREENWIDTH, SCREENHEIGHT);

		// access the file location of the sounds
		breaking = Gdx.audio.newSound(Gdx.files.classpath("sounds/break.wav"));
		// Gdx.audio.newMusic(Gdx.files.classpath("sounds/bgmusic.wav"));
		// bump = Gdx.audio.newSound(Gdx.files.classpath("sounds/bump.wav"));

		// setting and playing the background music
		// music.setLooping(true);
		// music.setVolume(0.3f);
		// music.play();

		// setting the ball and paddle
		paddle = new LocalPlayer(new Vector2(SCREENWIDTH / 2, 10));
		ball = new Ball();
		ball.setColor(0, 1, 0, 1);

		int index;
		if (level == 1) {
			// create 48 Bricks in a rectangle formation
			index = 0;
			bricks = new Brick[48];
			for (int i = 0; i < 6; i++) {
				for (int j = 0; j < 8; j++) {
					bricks[index] = new Brick(j * 125 + 120, SCREENHEIGHT - i
							* 45 - 110);
					index++;
				}
			}
		} else if (level == 2) {
			index = 0;
			bricks = new Brick[20];
			for (int i = 0; i < 4; i++) {
				for (int j = 0; j < 5; j++) {
					bricks[index] = new Brick(j * 125 + index * 10 + 120,
							SCREENHEIGHT - (4 * index) - (i * 40) - 110);
					index++;
				}
			}
		} else if (level == 3) {
			index = 0;
			bricks = new Brick[20];
			while (index < 20) {
				int shift = 0;
				double yPos = Math.sin(18 * index);
				if (yPos < 0) {
					shift = -40;
				} else if (yPos > 0) {
					shift = 40;
				}
				bricks[index] = new Brick((float)(640 + 225*Math.cos(18*index)),
						(float)(360 + shift + 225*yPos));
				index++;
			}
		}
		brickNum = bricks.length;

		shapeRenderer = new ShapeRenderer();
		font = new BitmapFont();
		font.setScale(2);
		batch = new SpriteBatch();

		gameState = GameState.READY;
		gameState = GameState.READY;
		status = "Click to start!";

	}

	/*
	 * Decrements bricks and increments score once ball hits brick. Gives time
	 * for processing
	 */
	public void updateGameState(int bounce) {
		System.out.println("ball's x velocity: " + ball.getXVelocity());
		if (bounce == 0) {
			ball.bounceX();
		} else if (bounce == 1) {
			ball.bounceY();
		} else {
			ball.bounceX();
			ball.bounceY();
		}
		lastHitX = ball.getX();
		lastHitY = ball.getY();
		breaking.play();
		brickBreak++;
		score += this.level*2;
		brickNum--;
		try {
			Thread.currentThread().sleep(35);
		} catch (Exception e) {
		}
	}

	/**
	 * Closes Application
	 */
	@Override
	public void dispose() {
		super.dispose();
		breaking.dispose();
//		music.dispose();
//		bump.dispose();
	}

	/**
	 * Pauses Application
	 */
	@Override
	public void pause() {
		super.pause();
	}

	/**
	 * Resumes Application from a Paused State
	 */
	@Override
	public void resume() {
		super.resume();
	}

	/**
	 * Renders game mechanics.
	 */
	public void render() {
		//super.render();
		
		// Clears Frame
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		camera.update();

		shapeRenderer.setProjectionMatrix(camera.combined);
		batch.setProjectionMatrix(camera.combined);

		// Draw a background
		batch.begin();
		batch.draw(background, 0, 0);
		batch.end();

		// renders the rectangles that are filled.
		shapeRenderer.begin(ShapeType.FilledRectangle);

		paddle.render(shapeRenderer);
		// TODO Make the Ball a Circle
		ball.render(shapeRenderer);
		
		

		// Writes in the text information
		/*
		 * Iterates through the array check whether brick's status is true,
		 * which will then render the individual brick.
		 */
		for (Brick b : bricks) {
			if (b.getState()) {
				// originally sent shapeRenderer
				b.render(shapeRenderer);
			}
		}
		shapeRenderer.end();
		batch.begin();
		font.setColor(Color.GREEN);
		font.draw(batch, "player " + player, SCREENWIDTH / 4, SCREENHEIGHT - 20);
		font.draw(batch, "Life " + Integer.toString(lives), SCREENWIDTH / 2,
				SCREENHEIGHT - 20);

		font.draw(batch, "Score " + Integer.toString(score), SCREENWIDTH*3/4, SCREENHEIGHT-20);
		if (gameoverstatus!= null) {
	    	font.setColor(Color.WHITE);
	    	
	    	font.draw(batch, gameoverstatus, SCREENWIDTH/2-250, SCREENHEIGHT/2);
		}
		
		
		
		if (status != null) {
	    	font.setColor(Color.WHITE);
	    	
	    	font.draw(batch, status, SCREENWIDTH/2-80, SCREENHEIGHT/2-60);
	    	}
	    	if (gameState == GameState.GAMEOVER) {
	    		font.draw(batch, "Click to exit", SCREENWIDTH/2 - 80, SCREENHEIGHT/2 - 60);
	    	}
	    
		batch.end();

		switch (gameState) {

		case READY:
			status = "Press Space or Touch the screen to Start!";
			if (Gdx.input.isKeyPressed(Keys.SPACE) || Gdx.input.isTouched()) {
				Start();
			} else if (sequence != null) {
				if (Gdx.input.isKeyPressed(sequence[currentButton])) {
					currentButton++;
					System.out.println("WORKED!!!" + currentButton);
				}
				if (sequence.length == currentButton) {
					currentButton = 0;
					bonusLives();
					System.out.println("SCORE UPDATE!");
				}
			}
			break;

		case INPROGRESS:
			paddle.update(ball);
			ball.move(Gdx.graphics.getDeltaTime());
			for (Brick b : bricks) {
				if (b.getState()) {
					if (b.checkLeftCollision(ball.bounds)) {
						b.setState(false);
						if (Math.abs(ball.getXVelocity()) < 60) {
							updateGameState(2);
							break;
						}
						updateGameState(0);
						break;
					}
					if (b.checkRightCollision(ball.bounds)) {
						b.setState(false);
						if (Math.abs(ball.getXVelocity()) < 60) {
							updateGameState(2);
							break;
						}
						updateGameState(0);
						break;
					}
					if (b.checkTopCollision(ball.bounds)) {
						b.setState(false);
						updateGameState(1);
						break;
					}
					if (b.checkBottomCollision(ball.bounds)) {
						b.setState(false);
						updateGameState(1);
						break;
					}
				}
			}

			if (brickNum == 0) {
				level++;
				if (level > 3) {
					win();
				} else {
					create();
				}
			}

			if (ball.bounds.overlaps(paddle.paddleShape)
					&& ball.getYVelocity() < 0) {
//				bump.play();
				ball.updateVelocity(lastHitX, lastHitY, paddle);
				ball.bounceY();

			}

			if (ball.bounds.y >= SCREENHEIGHT - Ball.WIDTH) {
				lastHitX = ball.getX();
				lastHitY = ball.getY();
				ball.bounceY();
			}

			if (ball.bounds.x <= 0
					|| ball.bounds.x + Ball.WIDTH > SCREENWIDTH) {
				lastHitX = ball.getX();
				lastHitY = ball.getY();
				ball.bounceX();
			}

			if (ball.bounds.y <= 0) {
				roundOver();
			}

			if (Gdx.input.isButtonPressed(Keys.ESCAPE)) {
				pause();
			}

			break;

		case GAMEOVER:
			if (Gdx.input.isTouched() || Gdx.input.isButtonPressed(Keys.SPACE)) {
				gameOver();
				// call dispose() method.
				breaking.dispose();
//				music.dispose();
//				bump.dispose();
				bumpCount++;
				ArcadeSystem.goToGame(ArcadeSystem.UI);
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
		status = null;
		gameState = GameState.INPROGRESS;
		status=null;

	}

	/**
	 * Adds the remaining lives as a score and checks whether any achievement
	 * criteria has been met. Also sets the gameState to GAMEOVER.
	 */
	private void win() {
		score += lives * 5;
		status = "Congratulations! " + player + " your final score is: "
				+ score;
		gameoverstatus="Congratulations " + player
				+ " your final score is: " + score;
		System.out.println("Congratulations " + player
				+ " your final score is: " + score);
		if (lives == 3) {
			incrementAchievement("breakout.prefect");
		} else if (lives == 0) {
			incrementAchievement("breakout.closeOne");
		} else if (score < 0) {
			incrementAchievement("breakout.noob");
		}
		incrementAchievement("breakout.winGame");
		gameState = GameState.GAMEOVER;
	}

	private void bonusLives() {
		lives = lives + 2;
		sequence = null;
	}

	/**
	 * Resets games area or sets the gameState to GAMEOVER
	 */
	private void roundOver() {
		/*
		 * Checks whether the lives have fallen below 0 If true then the
		 * gameState is set to GAMEOVER, Otherwise a lives is decremented and
		 * gameState is set to READY
		 */
		if (lives > 0) {
			ball.reset(new Vector2(paddle.getPaddleX(), paddle.getPaddleY()));
			lives--;
			score -= 5;
			gameState = GameState.READY;
		} else {
			status = "Bad luck " + player + " your final score is: " + score;
			gameoverstatus="Bad luck " + player + " your final score is: "
					+ score;
			System.out.println("Bad luck " + player + " your final score is: "
					+ score);
			gameState = GameState.GAMEOVER;
		}

	}

	private static final Game game;
	static {
		game = new Game();
		game.id = "breakout";
		game.name = "Breakout";
		game.description = "Bounce the ball off your paddle to keep it from falling off the bottom of the screen.";
	}
}
