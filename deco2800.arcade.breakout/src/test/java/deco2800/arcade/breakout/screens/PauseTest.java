package deco2800.arcade.breakout.screens;

import static org.mockito.Mockito.mock;

import static org.junit.Assert.*;
import org.junit.*;
import org.mockito.Mockito;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.backends.openal.OpenALAudio;
import com.badlogic.gdx.math.Vector2;

import deco2800.arcade.breakout.Ball;
import deco2800.arcade.breakout.Breakout;
import deco2800.arcade.breakout.Paddle;
import deco2800.arcade.client.UIOverlay;
import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.model.Player;

/**
 * 
 * @author Carlie Smits
 * 
 * This testing package requires no modifications of any other files.
 *
 */
public class PauseTest {

	private static LwjglApplication app;
	private static Breakout breakout;
	private static GameScreen context;
	Paddle mockPaddle = mock(Paddle.class);
	
	@BeforeClass
	public static void setup() {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "breakout test";
		cfg.useGL20 = false;
		cfg.width = 1280;
		cfg.height = 720;
		breakout = new Breakout(Mockito.mock(Player.class), Mockito.mock
				(NetworkClient.class));
		app = new LwjglApplication(breakout, cfg);
		breakout.addOverlayBridge(Mockito.mock(UIOverlay.class));
		context = new GameScreen(breakout);
		context.setBall(new Ball());
		context.switchGameMode(true);
	}
	
	@Test
	public void pauseTest() {
		context.getBall().randomizeVelocity();
		assertNotEquals(0f, context.getBall().getXVelocity(), 0.01f);
		assertNotEquals(0f, context.getBall().getYVelocity(), 0.01f);
		float prevX = context.getBall().getXVelocity();
		float prevY = context.getBall().getYVelocity();
		context.inGamePause();
		assertEquals(0f, context.getBall().getXVelocity(), 0.01f);
		assertEquals(0f, context.getBall().getYVelocity(), 0.01f);
		context.inGameUnpause(new Vector2(prevX, prevY), null);
		assertEquals(prevX, context.getBall().getXVelocity(), 0.01f);
		assertEquals(prevY, context.getBall().getYVelocity(), 0.01f);
	}
	
	@AfterClass
	public static void cleanUp() {
		((OpenALAudio) app.getAudio()).dispose();
	}
}
