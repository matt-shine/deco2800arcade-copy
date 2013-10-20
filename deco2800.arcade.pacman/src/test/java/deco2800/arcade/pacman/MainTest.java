package deco2800.arcade.pacman;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.backends.openal.OpenALAudio;
import com.badlogic.gdx.utils.Array;

import deco2800.arcade.client.ArcadeInputMux;
import deco2800.arcade.client.UIOverlay;
import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.model.Player;
// TESTS DO NOT RUN ON JENKINS BUT WORK FINALLY OTHERWISE. DO NOT DELETE THEM, 
// THE OFFENDING ONES ARE COMMENTED OUT

/**
 * A main test class for Pacman, also testing the model. 
 * It should also be noted here that the View cannot 
 * be tested as JUnit causes errors if Textures are loaded
 */

public class MainTest {

	private static Pacman pacGame;
	private static PacModel model;
	private static GameMap gameMap;
	private static LwjglApplication app;
	
	@BeforeClass
	public static void init() {
		//necessary stuff to initialise libGdx
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "Pacman Test Window";
		cfg.useGL20 = false;
		cfg.width = 480;
		cfg.height = 320; 
		app = new LwjglApplication(pacGame = new Pacman(Mockito.mock(Player.class), Mockito.mock(NetworkClient.class)), cfg);
		pacGame.addOverlayBridge(Mockito.mock(UIOverlay.class));
		//try-catch because NetworkClient is being mocked and therefore achievements can't be accessed
		try {
			pacGame.create();
		} catch (NullPointerException e) {
			
		}
		model = pacGame.getModel();
		gameMap = model.getGameMap();
	}
	
	/** Disposes of things. AL library still seems to cause occasional errors. */
	@AfterClass
	public static void tearDown() {
		pacGame.dispose();
		app.exit();
		//dispose of audio properly- try catch necessary so it passes both in Eclipse and in a build
		try {
			((OpenALAudio) app.getAudio()).dispose();
		} catch (UnsatisfiedLinkError e) {
			e.printStackTrace();
		}
	}
	
	@Test
	/** Checks if the map file exists in the directory */
	public void mapFileExists() {
		gameMap.readMap(model.getMapName());
	}	
	
	@Test
	/** Checks to see if the multiplexer still exists properly */
	public void checkMultiplexerExists() {
		ArcadeInputMux.getInstance().addProcessor(pacGame.getController());
		Array<InputProcessor> processors = ArcadeInputMux.getInstance().getProcessors();
		Assert.assertTrue(processors.contains(pacGame.getController(), false));
	}
	
	@Test
	/** Tests the initialisation of the model */	
	public void modelInit() {
		PacModel pModel = new PacModel(1280, 720, 4);
		Assert.assertEquals("levelMap.txt", pModel.getMapName());
		Assert.assertEquals(720, pModel.getSCREENHEIGHT());
		Assert.assertEquals(1280, pModel.getSCREENWIDTH());
		Assert.assertNotNull(pModel.getGameMap());
		Assert.assertNotNull(pModel.getPlayer());
		Assert.assertNotNull(pModel.getBlinky());
		Assert.assertNotNull(pModel.getPinky());
	}
	
}
