package deco2800.arcade.pacman;

import static org.junit.Assert.fail;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.backends.openal.OpenALAudio;
import com.badlogic.gdx.utils.Array;

import deco2800.arcade.client.ArcadeInputMux;
import deco2800.arcade.client.UIOverlay;
import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.model.Player;

/* so Gdx.files itself is null, I can happily make a Gdx() which isn't null, 
 * but if I try to .files it, that's still null which is odd because it's static
 * just read that there's initialisation stuff which causes this problem (that Gdx.files is null)
 * 
 * Now I can access Gdx.files, but can't load any Textures as the OpenGL context doesn't exist in the current 
 * thread, or something like that. I've decided to separate out all the drawing stuff from everything else
 * So basically model view controller
 */		


/**
 * A main test class for Pacman
 * The render thread has a GdxRuntimeException, but it can test anything before that point
 */

public class MainTest {

	private static Pacman pacGame;
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
		UIOverlay overlayMock = Mockito.mock(UIOverlay.class);
		pacGame.addOverlayBridge(overlayMock);
		pacGame.create();
		gameMap = pacGame.getGameMap();
	}
	
	/** Disposes of things. AL library still seems to cause occasional errors. */
	@AfterClass
	public static void tearDown() {
		pacGame.dispose();
		app.exit();
		//dispose of audio properly
		((OpenALAudio) app.getAudio()).dispose();
	}
	
	@Test
	/** Checks if the map file exists in the directory */
	public void mapFileExists() {
		gameMap.readMap(pacGame.getMapName());
	}	
	
	//these next two should move to a GameMaptests class when i make that
	
	@Test
	/**
	 * Checks if the map file is formatted correctly
	 * TODO needs to be altered to manage characters that can't be vsymmed as well
	 */
	public void mapFormattedCorrectly() {
		String[] lineArray = getLines();
		int startPoint = lineArray[0].contains("VSYM") ? 1 : 0;
		char[] result;
		String allowedChars = "ACDEFHacdefh13456890RSXZWYxzwyJLKMrsBpbP gG2QT7";
		for (int i = startPoint; i < lineArray.length; i++) {
			result = lineArray[i].toCharArray();
			for (int j = 0; j < lineArray[i].length(); j++) {
				Character c = result[j];
				if (!allowedChars.contains(c.toString())) {
					System.out.println(c.toString());
					fail("Character in file which is not allowed");
				}
			}
		}				
	}
	
	@Test
	/**
	 * Checks if a line with vertical symmetry has its linelength doubled
	 * Note that this doesn't actually check if symbols were changed appropriately
	 */
	public void checkVsymNoticed() {
		String file = pacGame.getMapName();
		String[] lineArray = getLines();
		int startPoint = lineArray[0].contains("VSYM") ? 1 : 0;
		for (int i = startPoint; i < gameMap.readMap(file).size() && i < lineArray.length; i++) {
			Assert.assertEquals(gameMap.readMap(file).get(i).length, lineArray[i].length()*2);
		}
	}
	/** Helper method for map file tests */
	private String[] getLines() {
		String contents = Gdx.files.internal(pacGame.getMapName()).readString();
		return contents.split(System.getProperty("line.separator"));		
	}
	
	/** Checks to see if the multiplexer works properly */
	@Test
	public void checkMultiplexerExists() {
		ArcadeInputMux.getInstance().addProcessor(pacGame.getController());
		Array<InputProcessor> processors = ArcadeInputMux.getInstance().getProcessors();
		Assert.assertTrue(processors.contains(pacGame.getController(), false));
	}

}
