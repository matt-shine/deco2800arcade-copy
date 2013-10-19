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


/**
 * A main test class for Pacman
 * OCCASIONALLY causes Unsatisfied Link Exceptions in the tearDown() code. But 
 * usually doesn't. It's very confusing. Everything still runs fine either way.
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
		UIOverlay overlayMock = Mockito.mock(UIOverlay.class);
		pacGame.addOverlayBridge(overlayMock);
		pacGame.create();
		model = pacGame.getModel();
		gameMap = model.getGameMap();
	}
	
	/** Disposes of things. AL library still seems to cause occasional errors. */
	@AfterClass
	public static void tearDown() {
		pacGame.dispose();
		//dispose of audio properly
		((OpenALAudio) app.getAudio()).dispose();
	}
	
	@Test
	/** Checks if the map file exists in the directory */
	public void mapFileExists() {
		gameMap.readMap(model.getMapName());
	}	
	
	//these next two should move to a GameMaptests class when i make that
	
	@Test
	/**
	 * Checks if the map file is formatted correctly
	 */
	public void mapFormattedCorrectly() {
		String[][] lineArrays = getLines();
		for (int x = 0; x < lineArrays.length; x++) {
			int startPoint = lineArrays[x][0].contains("VSYM") ? 1 : 0;
			char[] result;
			String allowedChars = "ACDEFHacdefh13456890RSXZWYxzwyJLKMrsBpbP gG2QT7";
			for (int i = startPoint; i < lineArrays[x].length; i++) {
				result = lineArrays[x][i].toCharArray();
				for (int j = 0; j < lineArrays[x][i].length(); j++) {
					Character c = result[j];
					if (!allowedChars.contains(c.toString())) {
						System.out.println(c.toString());
						fail("Character in file which is not allowed");
					}
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
		String file; 
		String[][] lineArrays = getLines();
		for (int x = 0; x < lineArrays.length; x++) {
			int startPoint = lineArrays[x][0].contains("VSYM") ? 1 : 0;
			switch(x) {			
			case 1: file = "levelMap.txt";
			case 2: file = "testMap.txt";
			default: file = model.getMapName();
			}
			for (int i = startPoint; i < gameMap.readMap(file).size() && i < lineArrays[x].length; i++) {
				int multiplier = startPoint == 1 ? 2:1;
				Assert.assertEquals(gameMap.readMap(file).get(i).length, lineArrays[x][i].length()* multiplier);
			}
		}		
	}
	/** Helper method for map file tests */
	private String[][] getLines() {
		String[][] files = new String[3][];
		String[] contents = new String[3];
		contents[0] = Gdx.files.internal(model.getMapName()).readString(); //current map
		contents[1] = Gdx.files.internal("levelMap.txt").readString(); //vsym map
		contents[2] = Gdx.files.internal("testMap.txt").readString(); //nonvsym map
		for (int i=0; i < contents.length; i++) {
			files[i] = contents[i].split(System.getProperty("line.separator"));		
		}
		return files;
	}
	
	/** Checks to see if the multiplexer works properly */
	@Test
	public void checkMultiplexerExists() {
		ArcadeInputMux.getInstance().addProcessor(pacGame.getController());
		Array<InputProcessor> processors = ArcadeInputMux.getInstance().getProcessors();
		Assert.assertTrue(processors.contains(pacGame.getController(), false));
	}

}
