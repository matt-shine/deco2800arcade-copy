package deco2800.arcade.pacman;


import static org.junit.Assert.fail;

import java.util.ArrayList;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.backends.openal.OpenALAudio;

import deco2800.arcade.client.UIOverlay;
import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.model.Player;

public class GameMapTest {

	private static LwjglApplication app;
	private static PacModel model;
	private static GameMap gameMap;
	
	@BeforeClass
	public static void setUpBeforeClass() {
		//necessary stuff to initialise libGdx
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "Pacman Test Window";
		cfg.useGL20 = false;
		cfg.width = 480;
		cfg.height = 320; 
		Pacman pacGame;
		app = new LwjglApplication(pacGame = new Pacman(Mockito.mock(Player.class), Mockito.mock(NetworkClient.class)), cfg);
		pacGame.addOverlayBridge(Mockito.mock(UIOverlay.class));
		model = new PacModel(1280, 720);
		gameMap = model.getGameMap();
	}
	
	/** Disposes of things. AL library still seems to cause occasional errors. */
	@AfterClass
	public static void tearDown() {
		//dispose of audio properly
		((OpenALAudio) app.getAudio()).dispose();
	}
	
	@Test
	/** Tests initial form of gameMap */
	public void testInit() {
		GameMap map = new GameMap(1280, 720);
		Assert.assertEquals(new ArrayList<WallTile>(), map.getGhostDoors());
		Assert.assertEquals(1280, map.SCREEN_WIDTH);
		Assert.assertEquals(720, map.SCREEN_HEIGHT);
	}
	
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
		contents[2] = Gdx.files.internal("testMap.txt").readString(); //non-vsym map
		for (int i=0; i < contents.length; i++) {
			files[i] = contents[i].split(System.getProperty("line.separator"));		
		}
		return files;
	}
	
	@Test
	/** Tests the createTiles() method */
	public void testGridSize() {
		GameMap map = new GameMap(1280, 720);
		map.createTiles(map.readMap("levelMap.txt"));
		Tile[][] grid = map.getGrid();
		for (int i = 0; i < grid.length; i++) {
			Assert.assertEquals(31, grid[0].length);
		}
		Assert.assertEquals(28, grid.length);
		Assert.assertEquals(416, map.getHOffset());
		Assert.assertEquals(112, map.getVOffset());
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				Assert.assertNotNull(grid[i][j]);
			}
		}
	}

}
