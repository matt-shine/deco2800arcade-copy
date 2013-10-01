package deco2800.arcade.breakout;


import com.badlogic.gdx.Gdx;
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
import com.badlogic.gdx.math.Vector2;

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
	private int score;
	private int lives;
	

	// Cheat Code
	private int[] sequence = { 19, 19, 20, 20, 21, 22, 21, 22, 30, 29 };
	private int currentButton = 0;

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

	private String status = null;
	
	// Integer to determine what the current level is
	private int level = 1;

	// Keeps track of the number of bricks on screen.
	private int brickNum;

	// Screen Parameters
	public static final int SCREENHEIGHT = 720;
	public static final int SCREENWIDTH = 1280;

	// Game States
//	private enum GameState {
//		READY, INPROGRESS, GAMEOVER;
//	}

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
	
	GameScreen(final Breakout game) {
		this.levelSystem = new Level();
		score = 0;
		lives = 3;
		this.game = game;
		this.player = game.playerName();
		gamearea();
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

		// setting and playing the background music
		music.setLooping(true);
		music.setVolume(0.3f);
		music.play();

		// setting the ball and paddle
		setPaddle(new LocalPlayer(new Vector2(SCREENWIDTH / 2, 10)));
		setBall(new Ball());
		getBall().setColor(0.7f, 0.7f, 0.7f, 0.5f);
		
		

		int index;
		if (getLevel() == 1) {
			bricks = levelSystem.levelOne(bricks, this);
		} else if (getLevel() == 2) {
			bricks = levelSystem.levelTwo(bricks, this);
		} else if (getLevel() == 3) {
			bricks = levelSystem.levelThree(bricks, this);
		} else if (getLevel() == 4) {
			bricks = levelSystem.levelFour(bricks, this);
		} else if (getLevel() == 5) {
			bricks = levelSystem.levelFive(bricks, this);
		} else if (getLevel() == 6) {
			bricks = levelSystem.levelSix(bricks, this);
		} else if (getLevel() == 7) {
			bricks = levelSystem.levelSeven(bricks, this);
		} else if (getLevel() == 8) {
			bricks = levelSystem.levelEight(bricks, this);
		} else if (getLevel() == 9) {
			bricks = levelSystem.levelNine(bricks, this);
		} else if (getLevel() == 10) {
			bricks = levelSystem.levelTen(bricks,this);
		}
		setBrickNum(bricks.length);

		shapeRenderer = new ShapeRenderer();
		font = new BitmapFont();
		font.setScale(2);
		batch = new SpriteBatch();

		gameState = new ReadyState();
//		gameState = GameState.READY;
		setStatus("Click to start!");

	}
	/*
	 * Decrements bricks and increments score once ball hits brick. Gives time
	 * for processing
	 */
	public void updateGameState(int bounce) {
		if (bounce == 0) {
			getBall().bounceX();
		} else if (bounce == 1) {
			getBall().bounceY();
		} else {
			getBall().bounceX();
			getBall().bounceY();
		}
		setLastHitX(getBall().getX());
		setLastHitY(getBall().getY());
		breaking.play();
		brickBreak++;
		score += this.getLevel()*2;
		setBrickNum(getBrickNum() - 1);
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
			

			// Render the level
			levelSystem.render(bricks, outer, inner, this, shapeRenderer, batch);
			shapeRenderer.end();
			shapeRenderer.begin(ShapeType.FilledCircle);
			// Ball is a Circle
			getBall().render(shapeRenderer);
			shapeRenderer.end();
			
			
			batch.begin();
			font.setColor(Color.GREEN);
			font.draw(batch, "player " + player, SCREENWIDTH / 4, SCREENHEIGHT - 20);
			font.draw(batch, "Life " + Integer.toString(lives), SCREENWIDTH / 2,
					SCREENHEIGHT - 20);

			font.draw(batch, "Score " + Integer.toString(score),
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
		score += lives * 5;

		if (highScore < score){
			highScore = score;
			gameoverstatus = "Congratulations " + player + " you have a new HighScore: "
					+ score;
			
		}else{
			gameoverstatus = "Congratulations " + player + " your final score is: "
					+ score;
		}
		
		System.out.println("Congratulations " + player
				+ " your final score is: " + score);
		gameState = new GameOverState();
		//if (lives == 3) {
		//	incrementAchievement("breakout.prefect");
	//	} else if (lives == 0) {
	//		incrementAchievement("breakout.closeOne");
	//	} else if (score < 0) {
	//		incrementAchievement("breakout.noob");
	}
		
	//	incrementAchievement("breakout.winGame");
	//	gameState = GameState.GAMEOVER;
//	}
	
		
	/**
	 * Rewards the Player 2 extra lives and makes the sequence null, so that the
	 * Player can not re-use the cheat
	 */
	void bonusLives(int bonus) {
		lives = lives + bonus;
		setSequence(null);
	}

	/**
	 * Resets games area or sets the gameState to GAMEOVER
	 */
	void roundOver() {
		/*
		 * Checks whether the lives have fallen below 0 If true then the
		 * gameState is set to GAMEOVER, Otherwise a lives is decremented and
		 * gameState is set to READY
		 */
		if (lives > 0) {
			getBall().reset(new Vector2(getPaddle().getPaddleX(), getPaddle().getPaddleY()));
			lives--;
			score -= 5;
			gameState = new ReadyState();
		} else {

			gameoverstatus = "Bad luck " + player + " your final score is: "
					+ score;
			System.out.println("Bad luck " + player + " your final score is: "
					+ score);
			gameState = new GameOverState();
		}

	}
	 
	 
	 
	
	@Override
	public void dispose() {
		breaking.dispose();
		music.dispose();
		bump.dispose();
		
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
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

}
