//package deco2800.arcade.breakout;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNotEquals;
//
//import java.io.IOException;
//
//import org.junit.AfterClass;
//import org.junit.BeforeClass;
//import org.junit.Test;
//import org.mockito.Mockito;
//
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
//import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
//import com.badlogic.gdx.backends.openal.OpenALAudio;
//
//import deco2800.arcade.breakout.screens.GameScreen;
//import deco2800.arcade.client.UIOverlay;
//import deco2800.arcade.client.network.NetworkClient;
//import deco2800.arcade.model.Player;
//
///**
// * @author Carlie Smits
// * 
// * For this test package to run the following lines must be commented out:
// * line 38 - Brick.java (initialiseBrickImgs())
// * line 61 - Brick.java (initialiseBrickImgs())
// * REMEMBER TO UNCOMMENT THE LINES ONCE TESTING IS DONE.
// * REMEMBER TO COMMENT THIS FILE OUT AS WELL.
// */
//public class LevelTest1 {
//
//	private static LwjglApplication app;
//	private static Breakout breakout;
//	private static GameScreen context;
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
//	}
//	
//	
//	@Test
//	public void testLevel1() {
//		String fileContents = Gdx.files.classpath("levels/level1.txt").
//				readString();
//		Brick[] bricks;
//		try {
//			bricks = context.getLevelSystem().initialiseBrickArray
//					(fileContents);
//		} catch (IOException e) {
//			e.printStackTrace();
//			return;
//		}
//		// check bricks isn't null and contains 48 bricks
//		assertNotEquals(null, bricks);
//		assertEquals(48, bricks.length);
//	}
//	
//	@Test
//	public void testLevel5() {
//		String fileContents = Gdx.files.classpath("levels/level5.txt").
//				readString();
//		Brick[] bricks;
//		try {
//			bricks = context.getLevelSystem().initialiseBrickArray
//					(fileContents);
//		} catch (IOException e) {
//			e.printStackTrace();
//			return;
//		}
//		// check bricks isn't null and contains 48 bricks
//		assertNotEquals(null, bricks);
//		assertEquals(34, bricks.length);
//	}
//	
//	@Test
//	public void testLevel10() {
//		String fileContents = Gdx.files.classpath("levels/level10.txt").
//				readString();
//		Brick[] bricks;
//		try {
//			bricks = context.getLevelSystem().initialiseBrickArray
//					(fileContents);
//		} catch (IOException e) {
//			e.printStackTrace();
//			return;
//		}
//		// check bricks isn't null and contains 48 bricks
//		assertNotEquals(null, bricks);
//		assertEquals(189, bricks.length);
//	}
//	
//	@Test
//	public void testFinalBrick() {
//		Brick[] bricks = new Brick[1];
//		bricks[0] = new Brick(100, 100);
//	}
//	
//	@Test
//	public void testSizeBricks() {
//		Brick[] bricks = new Brick[2];
//		bricks[0] = new Brick(100, 100, 10, 20);
//		bricks[1] = new Brick(200, 200, 140, 60);
//		assertNotEquals(120f, bricks[0].getWidth());
//		assertEquals(20f, bricks[0].getHeight(), 0.01f);
//		assertNotEquals(40f, bricks[1].getHeight());
//		assertEquals(140f, bricks[1].getWidth(), 0.01f);
//	}
//	
//	@Test
//	public void ballBrickCollision() {
//		Brick[] bricks = new Brick[2];
//		context.setBrickNum(bricks.length);
//		bricks[0] = new Brick(100, 100);
//		bricks[1] = new Brick(200, 200);
//		assertEquals(2, context.getBrickNum());
//		assertEquals(0, context.getScore());
//		context.updateGameState(0, bricks[0], false);
//		assertEquals(2, context.getScore());
//		context.updateGameState(1, bricks[1], false);
//		assertEquals(4, context.getScore());
//		assertEquals(0, context.getBrickNum());
//		assertEquals(0, context.getPowerupManager().getPowerupArrayLength());
//		assertEquals(context.getBall().getX(), context.getLastHitX(), 0.01f);
//		assertEquals(context.getBall().getY(), context.getLastHitY(), 0.01f);
//	}
//	
//	@AfterClass
//	public static void cleanUp() {
//		((OpenALAudio) app.getAudio()).dispose();
//	}
//}