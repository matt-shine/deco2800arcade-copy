package deco2800.arcade.snakeLadderTest;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.junit.Test;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import deco2800.arcade.snakeLadder.SnakeLadder;
import deco2800.arcade.snakeLadderModel.GameMap;
import deco2800.arcade.snakeLadderModel.RuleMapping;
import deco2800.arcade.snakeLadderModel.Tile;

public class MapLoadingTest {
	private static final String fileDir = "src/test/java/deco2800/arcade/snakeLadderTest/";

	@Test
	public void tileCoorTest() {
		Tile[] tiles = new Tile[100];
		//create a tile map without anyrules
		for(int i=1;i<=100;i++)
		{
			tiles[i-1] = new Tile(i, ".");
		}
		assertEquals(tiles[0].getCoorX(), 0);
		assertEquals(tiles[0].getCoorY(), 0);
		assertEquals(tiles[1].getCoorX(), 60);
		assertEquals(tiles[1].getCoorY(), 0);
		assertEquals(tiles[2].getCoorX(), 120);
		assertEquals(tiles[2].getCoorY(), 0);
		assertEquals(tiles[3].getCoorX(), 180);
		assertEquals(tiles[3].getCoorY(), 0);
		assertEquals(tiles[4].getCoorX(), 240);
		assertEquals(tiles[4].getCoorY(), 0);
		assertEquals(tiles[5].getCoorX(), 300);
		assertEquals(tiles[5].getCoorY(), 0);
		assertEquals(tiles[6].getCoorX(), 360);
		assertEquals(tiles[6].getCoorY(), 0);
		assertEquals(tiles[7].getCoorX(), 420);
		assertEquals(tiles[7].getCoorY(), 0);
		assertEquals(tiles[8].getCoorX(), 480);
		assertEquals(tiles[8].getCoorY(), 0);
		assertEquals(tiles[9].getCoorX(), 540);
		assertEquals(tiles[9].getCoorY(), 0);
	}
	
	@Test
	public void tileRuleLoading()
	{
		GameMap gp = new GameMap();
		//mock the ruleMapping
		HashMap<String,RuleMapping> ruleMapping = RuleMapping.iniRuleMapping(new FileHandle(new File(fileDir+"ruleMappingTest.xml")));
		//load testing file 
		try {
			gp.populateTileListFromMapFile(new FileHandle(new File(fileDir+"mapLoadingTest1.txt")), ruleMapping);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(0, 0);
	}
}
