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
 * 
 */
@ArcadeGame(id = "Breakout")
public class Breakout extends GameClient {
	SplashScreen splashScreen;
	GameScreen gamescreen;

	/*
	 * Creates private instance variables for each element of The
	 */
	private String player;
	
	// Screen Parameters
	public static final int SCREENHEIGHT = 720;
	public static final int SCREENWIDTH = 1280;


	public Breakout(Player player, NetworkClient networkClient) {
		super(player, networkClient);
		this.player = player.getUsername();
		
		// this.nc = networkClient;
	}

	/**
	 * change to the screen to the splashscreen.
	 */
	@Override
	public void create() {
		super.create();
		
		splashScreen = new SplashScreen(this);
		
		gamescreen = new GameScreen(this);
		setScreen(splashScreen);
	}

	@Override
	public void dispose() {
		
		splashScreen.dispose();
		gamescreen.dispose();
		
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
<<<<<<< HEAD
		 super.render();

		
=======
		//FIXME gigantic method
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
		font.draw(batch, "player " + player, SCREENWIDTH/4, SCREENHEIGHT - 20);
		font.draw(batch, "Life " + Integer.toString(lives), SCREENWIDTH / 2,
				SCREENHEIGHT - 20);
		font.draw(batch, "Score " + Integer.toString(score), SCREENWIDTH*3/4, SCREENHEIGHT-20);
		batch.end();

		switch (gameState) {

		case READY:
			if (Gdx.input.isKeyPressed(Keys.SPACE) || Gdx.input.isTouched()) {
				Start();
			} else if(sequence != null) {
				if(Gdx.input.isKeyPressed(sequence[currentButton])){
					currentButton++;
					System.out.println("WORKED!!!"+currentButton);
				}
				if(sequence.length == currentButton){
					currentButton = 0;
					bonusLives();
					System.out.println("SCORE UPDATE!");
				}
			}
			break;

		case INPROGRESS:
			paddle.update(ball);
			ball.move(Gdx.graphics.getDeltaTime());
			// int index = 0;
			// TODO: if it hits left/right side, only bounceX. if it hits
			// top/bottom, only bounceY
			for (Brick b : bricks) {
				if (b.getState()) {
					if (b.checkLeftCollision(ball.bounds)) {
						b.setState(false);
						score++;
						brickNum--;
						//breaking.play();
						//breaking.dispose();
						ball.bounceX();
						break;
					}
					if (b.checkRightCollision(ball.bounds)) {
						b.setState(false);
						score++;
						brickNum--;
						//breaking.play();
						//breaking.dispose();
						ball.bounceX();
						break;
					}
					if (b.checkTopCollision(ball.bounds)) {
						b.setState(false);
						score++;
						brickNum--;
						//breaking.play();
						//breaking.dispose();
						ball.bounceY();
						break;
					}
					if (b.checkBottomCollision(ball.bounds)) {
						b.setState(false);
						score++;
						brickNum--;
						//breaking.play();
						//breaking.dispose();
						ball.bounceY();
						break;
					}
//					if (ball.bounds.overlaps(b.getShape())) {
//						b.setState(false);
//						score++;
//						brickNum--;
//						ball.bounceY();
//						//breaking.play();
//						//breaking.dispose();
//					}
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
		
		super.render();
>>>>>>> master

	}

	@Override
	public Game getGame() {
		return game;
	}

	

	/**
	 * Provides details about the game to the Arcade system.
	 */
	private static final Game game;
	static {
		game = new Game();
		game.id = "breakout";
		game.name = "Breakout";
		game.description = "Bounce the ball off your paddle to keep it from falling off the bottom of the screen.";
	}
	
}
