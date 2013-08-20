package deco2800.arcade.breakout;

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
@ArcadeGame(id = "Breakout")
public class Breakout extends GameClient {

	// Orthographic Camera is how the is displayed.
	private OrthographicCamera camera;

	/*
	 * Creates private instance variables for each element of The
	 */
	private String player;
	private NetworkClient nc;
	private Paddle paddle;
	private PongBall ball;
	private int score;
	private int lives;
	private String status;

	// Keeps track of the number of bricks on screen.
	private int brickNum;

	// Screen Parameters
	public static final int SCREENHEIGHT = 480;
	public static final int SCREENWIDTH = 800;

	// Game States
	private enum GameState {
		READY, INPROGRESS, GAMEOVER
	}

	private GameState gameState;

	// Rendering Requirements for LibGDX
	private ShapeRenderer shapeRenderer;
	private SpriteBatch batch;
	private BitmapFont font;

	// Array of Brick
	Brick bricks[];

	public Breakout(Player player, NetworkClient networkClient) {
		super(player, networkClient);
		this.player = player.getUsername();
		this.nc = networkClient;
		bricks = new Brick[40];
	}

	/**
	 * Creates the game area.
	 */
	@Override
	public void create() {
		super.create();
		// Sets the display sizw
		camera = new OrthographicCamera();
		camera.setToOrtho(false, SCREENWIDTH, SCREENHEIGHT);

		paddle = new LocalPlayer(new Vector2(SCREENWIDTH / 2, 10));
		ball = new PongBall();
		ball.setColor(1, 1, 1, 1);

		// created the 40 Bricks
		int index = 0;
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 8; j++) {
				bricks[index] = new Brick(j * 80 + 80, SCREENHEIGHT - i * 20
						- 40);
				index++;
			}
		}
		brickNum = bricks.length;

		shapeRenderer = new ShapeRenderer();
		font = new BitmapFont();
		font.setScale(2);
		batch = new SpriteBatch();

		score = 0;
		lives = 3;
		gameState = GameState.READY;
		status = "Start!";

	}

	/**
	 * Closes Application
	 */
	@Override
	public void dispose() {
		super.dispose();
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
		// Clears Frame
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		// TODO Find out what camera.update does.
		camera.update();

		shapeRenderer.setProjectionMatrix(camera.combined);
		batch.setProjectionMatrix(camera.combined);

		// renders the rectangles that are filled.
		shapeRenderer.begin(ShapeType.FilledRectangle);

		paddle.render(shapeRenderer);
		// TODO Make the Ball a Circle
		ball.render(shapeRenderer);

		/*
		 * Iterates through the array check whether brick's status is true,
		 * which will then render the individual brick.
		 */
		for (Brick b : bricks) {
			if (b.getState()) {
				b.render(shapeRenderer);
			}
		}

		shapeRenderer.end();

		// Writes in the text information
		batch.begin();
		font.setColor(Color.GREEN);
		font.draw(batch, player, SCREENWIDTH / 2, SCREENHEIGHT / 2);
		font.draw(batch, "Life " + Integer.toString(lives),
				SCREENWIDTH / 2 + 50, SCREENHEIGHT / 2 + 50);
		font.draw(batch, "Score " + Integer.toString(score),
				SCREENWIDTH / 2 - 50, SCREENHEIGHT / 2 - 50);
		batch.end();

		switch (gameState) {

		case READY:
			if (Gdx.input.isKeyPressed(Keys.SPACE) || Gdx.input.isTouched()) {
				Start();
			}
			break;

		case INPROGRESS:
			paddle.update(ball);
			ball.move(Gdx.graphics.getDeltaTime());
			// ?int index = 0;
			// TODO: if it hits left/right side, only bounceX. if it hits
			// top/bottom, only bounceY
			for (Brick b : bricks) {
				if (b.getState()) {
					if (ball.bounds.overlaps(b.getShape())) {
						b.setState(false);
						score++;
						brickNum--;
						// ball.bounceX();
						ball.bounceY();
					}
				}
			}

			if (brickNum == 0) {
				win();
			}

			if (ball.bounds.overlaps(paddle.paddleShape)
					&& ball.getYVelocity() < 0) {
				ball.bounceY();
			}

			if (ball.bounds.y >= SCREENHEIGHT - PongBall.WIDTH) {
				ball.bounceY();
			}

			if (ball.bounds.x <= 0
					|| ball.bounds.x + PongBall.WIDTH > SCREENWIDTH) {
				ball.bounceX();
			}

			if (ball.bounds.y <= 0) {
				roundOver();
			}

			break;

		case GAMEOVER:
			if (Gdx.input.isTouched()) {
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

	/**
	 * Adds the remaining lives as a score and checks whether any achievement
	 * criteria has been met. Also sets the gameState to GAMEOVER.
	 */
	private void win() {
		score += lives * 5;
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
			ball.reset();
			lives--;
			score -= 10;
			gameState = GameState.READY;
		} else {
			System.out.println("Bad luck " + player + " your final score is: "
					+ score);
			gameState = GameState.GAMEOVER;
		}

	}

	private static final Game game;
	static {
		game = new Game();
		// game.gameId = "breakout";
		game.name = "Breakout";
		// game.availableAchievements = achievements;
	}

}
