//package deco2800.arcade.breakout.screens;
//
//import static org.junit.Assert.*;
//
//import org.junit.AfterClass;
//import org.junit.Before;
//import org.junit.BeforeClass;
//import org.junit.Test;
//import static org.mockito.Mockito.*;
//
//import org.junit.*;
//import org.lwjgl.util.vector.Vector;
//import org.mockito.Mockito;
//import org.mockito.internal.configuration.ClassPathLoader;
//
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
//import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
//import com.badlogic.gdx.backends.openal.OpenALAudio;
//import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.graphics.g2d.Sprite;
//import com.badlogic.gdx.math.Rectangle;
//import com.badlogic.gdx.math.Vector2;
//
//import deco2800.arcade.breakout.Ball;
//import deco2800.arcade.breakout.Breakout;
//import deco2800.arcade.breakout.Brick;
//import deco2800.arcade.breakout.LocalPlayer;
//import deco2800.arcade.breakout.Paddle;
//import deco2800.arcade.breakout.powerup.DecreasePaddle;
//import deco2800.arcade.breakout.powerup.IncreaseBallNo;
//import deco2800.arcade.breakout.powerup.IncreasePaddle;
//import deco2800.arcade.breakout.powerup.LifePowerup;
//import deco2800.arcade.breakout.powerup.Powerup;
//import deco2800.arcade.breakout.powerup.PowerupManager;
//import deco2800.arcade.breakout.powerup.SlowBall;
//import deco2800.arcade.client.UIOverlay;
//import deco2800.arcade.client.network.NetworkClient;
//import deco2800.arcade.model.Player;
//
///**
// * @author Carlie Smits
// * 
// * For this test package to run the following lines must be commented out:
// * line 38 - Brick.java (initialiseBrickImgs())
// * line 56 - Brick.java (initialiseBrickImgs())
// * line 26 - DecreasePaddle.java (setSprite())
// * line 28 - IncreasePaddle.java (setSprite())
// * line 25 - LifePowerup.java (setSprite())
// * line 23 - SlowBall.java (setSprite())
// * line 27 - IncreaseBallNo.java (setSprite())
// * REMEMBER TO UNCOMMENT THE LINES ONCE TESTING IS DONE.
// */
//public class PowerUpTest {
//	
//	private static LwjglApplication app;
//	private static Breakout breakout;
//	private static GameScreen context;
//	Paddle mockPaddle = mock(Paddle.class);
//	
//	@BeforeClass
//	public static void setup() {
//		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
//		cfg.title = "breakout test";
//		cfg.useGL20 = false;
//		cfg.width = 1280;
//		cfg.height = 720;
//		breakout = new Breakout(Mockito.mock(Player.class), Mockito.mock
//				(NetworkClient.class));
//		app = new LwjglApplication(breakout, cfg);
//		breakout.addOverlayBridge(Mockito.mock(UIOverlay.class));
//		context = new GameScreen(breakout);
//		context.setBall(new Ball());
//		context.switchGameMode(true);
//	}
//	
//	
//	@Test
//	public void powerupBallBrickCollision() {
//		context.createNewBall(new Vector2(100,100));
//		assertEquals(2, context.getNumBalls());
//		assertNotNull(context.getPowerupBall());
//		Brick[] bricks = new Brick[2];
//		context.setBrickNum(bricks.length);
//		bricks[0] = new Brick(100, 100);
//		bricks[1] = new Brick(200, 200);
//		assertEquals(2, context.getBrickNum());
//		assertEquals(0, context.getScore());
//		context.updateGameState(0, bricks[0], true);
//		assertEquals(2, context.getScore());
//		context.updateGameState(1, bricks[1], true);
//		assertEquals(4, context.getScore());
//		assertEquals(0, context.getBrickNum());
//		assertEquals(context.getPowerupBall().getX(), 
//				context.getLastHitX(), 0.01f);
//		assertEquals(context.getPowerupBall().getY(), 
//				context.getLastHitY(), 0.01f);
//	}
//	
//	@Test
//	public void dropAndCheckBelow() {
//		context.getPowerupManager().dispose();
//		context.getPowerupManager().handlePowerup(1, 1);
//		// Need to wait until the top of the powerup is below the screen
//		for (int i = 0; i < 26; i++) {
//			assertEquals(1, context.getPowerupManager().getPowerupArrayLength());
//			// move down 2 pixels at a time
//			context.getPowerupManager().moveAll();
//		}
//		context.getPowerupManager().checkBelowScreen();
//		assertEquals(0, context.getPowerupManager().getPowerupArrayLength());
//	}
//	
//	@Test
//	public void checkPaddleCollision() {
//		// setup the paddle
//		context.getPowerupManager().dispose();
//		when(mockPaddle.getWidth()).thenReturn(128f);
//		when(mockPaddle.getPaddleX()).thenReturn(20f);
//		when(mockPaddle.getPaddleY()).thenReturn(10f);
//		context.setPaddle(mockPaddle);
//		Rectangle paddleShape = new Rectangle();
//		paddleShape.x = context.getPaddle().getPaddleX();
//		paddleShape.y = context.getPaddle().getPaddleY();
//		paddleShape.width = context.getPaddle().getWidth();
//		paddleShape.height = 20f;
//		// drop a powerup right above the paddle
//		context.getPowerupManager().handlePowerup(31, 31);
//		context.getPowerupManager().checkCollision(paddleShape);
//		assertEquals(1, context.getPowerupManager().getPowerupArrayLength());
//		context.getPowerupManager().moveAll();
//		context.getPowerupManager().checkCollision(paddleShape);
//		assertEquals(0, context.getPowerupManager().getPowerupArrayLength());
//	}
//	
//	@Test
//	public void checkNumberArrayAndDispose() {
//		int count = 0;
//		for (Integer i : context.getPowerupManager().getNumArray()) {
//			count += i;
//		}
//		assertEquals(0, count);
//		context.getPowerupManager().handlePowerup(31, 31);
//		for (Integer i : context.getPowerupManager().getNumArray()) {
//			count += i;
//		}
//		assertEquals(1, count);
//		context.getPowerupManager().dispose();
//		count = 0;
//		for (Integer i : context.getPowerupManager().getNumArray()) {
//			count += i;
//		}
//		assertEquals(0, context.getPowerupManager().getPowerupArrayLength());
//		assertEquals(0, count);
//	}
//	
//	@Test
//	public void slowBallPowerup() {
//		context.resetScore();
//		Powerup p = new SlowBall(context);
//		Powerup q = new SlowBall(context);
//		context.getBall().randomizeVelocity();
//		float yVel = context.getBall().getYVelocity();
//		int score = context.getScore();
//		p.applyPowerup();
//		assertEquals(yVel * 0.7, context.getBall().getYVelocity(), 0.01f);
//		// Second powerup should just increase the score by 20 * level
//		q.applyPowerup();
//		assertEquals(yVel * 0.7, context.getBall().getYVelocity(), 0.01f);
//		assertEquals(score + 20, context.getScore());
//	}
//	
//	@Test
//	public void increaseBallNoPowerup() {
//		context.destroyPowerupBall();
//		context.resetScore();
//		when(mockPaddle.getPaddleX()).thenReturn(20f);
//		when(mockPaddle.getPaddleY()).thenReturn(10f);
//		context.setPaddle(mockPaddle);
//		Powerup p = new IncreaseBallNo(context);
//		Powerup q = new IncreaseBallNo(context);
//		context.getBall().randomizeVelocity();
//		int score = context.getScore();
//		p.applyPowerup();
//		context.getPowerupBall().randomizeVelocity();
//		assertNotNull(context.getBall());
//		assertNotNull(context.getPowerupBall());
//		assertEquals(2, context.getNumBalls());
//		q.applyPowerup();
//		assertEquals(score + 20, context.getScore());
//		context.getBall().bounceY(0);
//		if(context.getBall().getY() <= 0) {
//			context.destroyBall();
//		}
//		assertEquals(2, context.getNumBalls());
//		context.getBall().move(1);
//		if(context.getBall().getY() <= 0) {
//			context.destroyBall();
//			context.setNumBalls(context.getNumBalls() - 1);
//		}
//		assertEquals(1, context.getNumBalls());
//		Powerup r = new IncreaseBallNo(context);
//		r.applyPowerup();
//		assertEquals(2, context.getNumBalls());
//		
//	}
//	
//	@Test
//	public void increaseLifePowerup() {
//		Powerup p = new LifePowerup(context);
//		int lives = context.getLives();
//		p.applyPowerup();
//		assertEquals(lives + 1, context.getLives());
//	}
//	
//	@Test 
//	public void decreaseAndIncreasePaddle() {
//		context.resetScore();
//		context.setPaddle(new LocalPlayer(new Vector2(1280 / 2, 10)));
//		Powerup p = new IncreasePaddle(context);
//		Powerup q = new DecreasePaddle(context);
//		int score = context.getScore();
//		// increases paddle size up to max
//		p.applyPowerup();
//		assertEquals(context.getPaddle().getStandardWidth()*2, context.getPaddle().getPaddleShapeWidth(), 0.01f);
//		// Can't increase past max, so increase score
//		p.applyPowerup();
//		assertEquals(context.getPaddle().getStandardWidth()*2, context.getPaddle().getPaddleShapeWidth(), 0.01f);
//		assertEquals(score + 20, context.getScore());
//		// decrease back to normal size
//		q.applyPowerup();
//		assertEquals(context.getPaddle().getStandardWidth(), context.getPaddle().getPaddleShapeWidth(), 0.01f);
//		// decrease down to min size
//		q.applyPowerup();
//		assertEquals(context.getPaddle().getStandardWidth()/2, context.getPaddle().getPaddleShapeWidth(), 0.01f);
//		// can't decrease past min size, so increase score
//		q.applyPowerup();
//		assertEquals(context.getPaddle().getStandardWidth()/2, context.getPaddle().getPaddleShapeWidth(), 0.01f);
//		assertEquals(score + 40, context.getScore());
//		// increase back to normal size
//		p.applyPowerup();
//		assertEquals(context.getPaddle().getStandardWidth(), context.getPaddle().getPaddleShapeWidth(), 0.01f);
//	}
//	@AfterClass
//	public static void cleanUp() {
//		((OpenALAudio) app.getAudio()).dispose();
//	}
//}
