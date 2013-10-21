package deco2800.arcade.pacman;


import static org.junit.Assert.fail;

import java.util.ArrayList;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.lwjgl.util.Point;
import org.mockito.Mockito;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.backends.openal.OpenALAudio;

import deco2800.arcade.client.UIOverlay;
import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.model.Player;

/*TESTS DO NOT RUN ON JENKINS BUT WORK FINALLY OTHERWISE. DO NOT DELETE THEM, 
 * THE OFFENDING ONES ARE COMMENTED OUT
 * NOTE ALSO that a GdxRuntimeException: NullPointerException will appear in the console 
 * from the "LWJGL Application" thread, as the achievements need access to the server.
 * All tests still run despite this
 */


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
		model = new PacModel(1280, 720, 4);
		gameMap = model.getGameMap();
	}
	
	/** Disposes of things. Apparently no longer necessary */
	@AfterClass
	public static void tearDown() {
		app.exit();
		//dispose of audio properly. try-catch necessary to build successfully on some machines
		try {
			((OpenALAudio) app.getAudio()).dispose();
		} catch (UnsatisfiedLinkError e) {
		} 
	}
	
	@Test
	/** Tests initial form of gameMap */
	public void testInit() {
		GameMap map = new GameMap(1280, 720, 4);
		Assert.assertEquals(new ArrayList<WallTile>(), map.getGhostDoors());
		Assert.assertEquals(16, map.getTileSideLength());
		Assert.assertEquals(1280, map.SCREEN_WIDTH);
		Assert.assertEquals(720, map.SCREEN_HEIGHT);
		Assert.assertArrayEquals(new Tile[4], map.getGhostStarts());
		Assert.assertEquals(new ArrayList<Tile>(), map.getAfterTeleports());
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
			String allowedChars = "ACDEFHacdefh13456890RSXZWYxzwyJLKMrsBpbP gG2QT7!@#$";
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
	/** Tests the grid */
	public void testGrid() {
		GameMap map = new GameMap(1280, 720, 4);
		map.createTiles(map.readMap("levelMap.txt"));
		Tile[][] grid = map.getGrid();
		for (int i = 0; i < grid.length; i++) {
			Assert.assertEquals(39, grid[0].length);
		}
		Assert.assertEquals(36, grid.length);
		Assert.assertEquals(352, map.getHOffset());
		Assert.assertEquals(48, map.getVOffset());
		Assert.assertEquals(grid[17][17], map.getFruitRight());
		Assert.assertEquals(grid[17][11], map.getPacStart());
		Assert.assertEquals(grid[16][21], map.getGhostStarts()[0]);
		Assert.assertEquals(grid[19][21], map.getGhostStarts()[1]);
		Assert.assertEquals(grid[16][20], map.getGhostStarts()[2]);
		Assert.assertEquals(grid[19][20], map.getGhostStarts()[3]);
		ArrayList<WallTile> ghostDoors = new ArrayList<WallTile>();
		ghostDoors.add((WallTile) grid[17][22]);
		ghostDoors.add((WallTile) grid[18][22]);
		Assert.assertEquals(ghostDoors, map.getGhostDoors());
		ArrayList<Tile> afterTeleports = new ArrayList<Tile>();
		afterTeleports.add(grid[3][20]);
		afterTeleports.add(grid[32][20]);
		Assert.assertEquals(afterTeleports, map.getAfterTeleports());
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				Assert.assertNotNull(grid[i][j]);				
				Assert.assertEquals(new Point(i,j), map.getTilePos(grid[i][j]));
				int s = map.getTileSideLength();
				Assert.assertEquals(new Point(i*s + 352, j*s + 48), map.getTileCoords(grid[i][j]));
			}
		}
	}
	
	@Test
	/** Tests finding of Mover current tiles */
	public void testMoverTileFinding() {
		GameMap map = new GameMap(1280, 720, 4);
		map.createTiles(map.readMap("levelMap.txt"));
		Tile[][] grid = map.getGrid();
		Assert.assertEquals(grid[18][11], map.findMoverTile(model.getPlayer()));
		// these commented out while we're testing. TODO MAKE SURE THEY GET ADDED IN AGAIN!
		//Assert.assertEquals(grid[12][17], map.findMoverTile(model.getBlinky()));
		//Assert.assertEquals(grid[15][17], map.findMoverTile(model.getPinky()));
	}
	
}
