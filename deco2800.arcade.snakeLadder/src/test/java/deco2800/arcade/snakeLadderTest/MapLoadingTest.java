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
		
		assertEquals(tiles[10].getCoorX(), 540);
		assertEquals(tiles[10].getCoorY(), 60);
		assertEquals(tiles[11].getCoorX(), 480);
		assertEquals(tiles[11].getCoorY(), 60);
		assertEquals(tiles[12].getCoorX(), 420);
		assertEquals(tiles[12].getCoorY(), 60);
		assertEquals(tiles[13].getCoorX(), 360);
		assertEquals(tiles[13].getCoorY(), 60);
		assertEquals(tiles[14].getCoorX(), 300);
		assertEquals(tiles[14].getCoorY(), 60);
		assertEquals(tiles[15].getCoorX(), 240);
		assertEquals(tiles[15].getCoorY(), 60);
		assertEquals(tiles[16].getCoorX(), 180);
		assertEquals(tiles[16].getCoorY(), 60);
		assertEquals(tiles[17].getCoorX(), 120);
		assertEquals(tiles[17].getCoorY(), 60);
		assertEquals(tiles[18].getCoorX(), 60);
		assertEquals(tiles[18].getCoorY(), 60);
		assertEquals(tiles[19].getCoorX(), 0);
		assertEquals(tiles[19].getCoorY(), 60);
		
		assertEquals(tiles[20].getCoorX(), 0);
		assertEquals(tiles[20].getCoorY(), 120);
		assertEquals(tiles[21].getCoorX(), 60);
		assertEquals(tiles[21].getCoorY(), 120);
		assertEquals(tiles[22].getCoorX(), 120);
		assertEquals(tiles[22].getCoorY(), 120);
		assertEquals(tiles[23].getCoorX(), 180);
		assertEquals(tiles[23].getCoorY(), 120);
		assertEquals(tiles[24].getCoorX(), 240);
		assertEquals(tiles[24].getCoorY(), 120);
		assertEquals(tiles[25].getCoorX(), 300);
		assertEquals(tiles[25].getCoorY(), 120);
		assertEquals(tiles[26].getCoorX(), 360);
		assertEquals(tiles[26].getCoorY(), 120);
		assertEquals(tiles[27].getCoorX(), 420);
		assertEquals(tiles[27].getCoorY(), 120);
		assertEquals(tiles[28].getCoorX(), 480);
		assertEquals(tiles[28].getCoorY(), 120);
		assertEquals(tiles[29].getCoorX(), 540);
		assertEquals(tiles[29].getCoorY(), 120);
		
		assertEquals(tiles[30].getCoorX(), 540);
		assertEquals(tiles[30].getCoorY(), 180);
		assertEquals(tiles[31].getCoorX(), 480);
		assertEquals(tiles[31].getCoorY(), 180);
		assertEquals(tiles[32].getCoorX(), 420);
		assertEquals(tiles[32].getCoorY(), 180);
		assertEquals(tiles[33].getCoorX(), 360);
		assertEquals(tiles[33].getCoorY(), 180);
		assertEquals(tiles[34].getCoorX(), 300);
		assertEquals(tiles[34].getCoorY(), 180);
		assertEquals(tiles[35].getCoorX(), 240);
		assertEquals(tiles[35].getCoorY(), 180);
		assertEquals(tiles[36].getCoorX(), 180);
		assertEquals(tiles[36].getCoorY(), 180);
		assertEquals(tiles[37].getCoorX(), 120);
		assertEquals(tiles[37].getCoorY(), 180);
		assertEquals(tiles[38].getCoorX(), 60);
		assertEquals(tiles[38].getCoorY(), 180);
		assertEquals(tiles[39].getCoorX(), 0);
		assertEquals(tiles[39].getCoorY(), 180);
		
		assertEquals(tiles[40].getCoorX(), 0);
		assertEquals(tiles[40].getCoorY(), 240);
		assertEquals(tiles[41].getCoorX(), 60);
		assertEquals(tiles[41].getCoorY(), 240);
		assertEquals(tiles[42].getCoorX(), 120);
		assertEquals(tiles[42].getCoorY(), 240);
		assertEquals(tiles[43].getCoorX(), 180);
		assertEquals(tiles[43].getCoorY(), 240);
		assertEquals(tiles[44].getCoorX(), 240);
		assertEquals(tiles[44].getCoorY(), 240);
		assertEquals(tiles[45].getCoorX(), 300);
		assertEquals(tiles[45].getCoorY(), 240);
		assertEquals(tiles[46].getCoorX(), 360);
		assertEquals(tiles[46].getCoorY(), 240);
		assertEquals(tiles[47].getCoorX(), 420);
		assertEquals(tiles[47].getCoorY(), 240);
		assertEquals(tiles[48].getCoorX(), 480);
		assertEquals(tiles[48].getCoorY(), 240);
		assertEquals(tiles[49].getCoorX(), 540);
		assertEquals(tiles[49].getCoorY(), 240);
		
		assertEquals(tiles[50].getCoorX(), 540);
		assertEquals(tiles[50].getCoorY(), 300);
		assertEquals(tiles[51].getCoorX(), 480);
		assertEquals(tiles[51].getCoorY(), 300);
		assertEquals(tiles[52].getCoorX(), 420);
		assertEquals(tiles[52].getCoorY(), 300);
		assertEquals(tiles[53].getCoorX(), 360);
		assertEquals(tiles[53].getCoorY(), 300);
		assertEquals(tiles[54].getCoorX(), 300);
		assertEquals(tiles[54].getCoorY(), 300);
		assertEquals(tiles[55].getCoorX(), 240);
		assertEquals(tiles[55].getCoorY(), 300);
		assertEquals(tiles[56].getCoorX(), 180);
		assertEquals(tiles[56].getCoorY(), 300);
		assertEquals(tiles[57].getCoorX(), 120);
		assertEquals(tiles[57].getCoorY(), 300);
		assertEquals(tiles[58].getCoorX(), 60);
		assertEquals(tiles[58].getCoorY(), 300);
		assertEquals(tiles[59].getCoorX(), 0);
		assertEquals(tiles[59].getCoorY(), 300);
		
		assertEquals(tiles[60].getCoorX(), 0);
		assertEquals(tiles[60].getCoorY(), 360);
		assertEquals(tiles[61].getCoorX(), 60);
		assertEquals(tiles[61].getCoorY(), 360);
		assertEquals(tiles[62].getCoorX(), 120);
		assertEquals(tiles[62].getCoorY(), 360);
		assertEquals(tiles[63].getCoorX(), 180);
		assertEquals(tiles[63].getCoorY(), 360);
		assertEquals(tiles[64].getCoorX(), 240);
		assertEquals(tiles[64].getCoorY(), 360);
		assertEquals(tiles[65].getCoorX(), 300);
		assertEquals(tiles[65].getCoorY(), 360);
		assertEquals(tiles[66].getCoorX(), 360);
		assertEquals(tiles[66].getCoorY(), 360);
		assertEquals(tiles[67].getCoorX(), 420);
		assertEquals(tiles[67].getCoorY(), 360);
		assertEquals(tiles[68].getCoorX(), 480);
		assertEquals(tiles[68].getCoorY(), 360);
		assertEquals(tiles[69].getCoorX(), 540);
		assertEquals(tiles[69].getCoorY(), 360);
		
		assertEquals(tiles[70].getCoorX(), 540);
		assertEquals(tiles[70].getCoorY(), 420);
		assertEquals(tiles[71].getCoorX(), 480);
		assertEquals(tiles[71].getCoorY(), 420);
		assertEquals(tiles[72].getCoorX(), 420);
		assertEquals(tiles[72].getCoorY(), 420);
		assertEquals(tiles[73].getCoorX(), 360);
		assertEquals(tiles[73].getCoorY(), 420);
		assertEquals(tiles[74].getCoorX(), 300);
		assertEquals(tiles[74].getCoorY(), 420);
		assertEquals(tiles[75].getCoorX(), 240);
		assertEquals(tiles[75].getCoorY(), 420);
		assertEquals(tiles[76].getCoorX(), 180);
		assertEquals(tiles[76].getCoorY(), 420);
		assertEquals(tiles[77].getCoorX(), 120);
		assertEquals(tiles[77].getCoorY(), 420);
		assertEquals(tiles[78].getCoorX(), 60);
		assertEquals(tiles[78].getCoorY(), 420);
		assertEquals(tiles[79].getCoorX(), 0);
		assertEquals(tiles[79].getCoorY(), 420);
		
		assertEquals(tiles[80].getCoorX(), 0);
		assertEquals(tiles[80].getCoorY(), 480);
		assertEquals(tiles[81].getCoorX(), 60);
		assertEquals(tiles[81].getCoorY(), 480);
		assertEquals(tiles[82].getCoorX(), 120);
		assertEquals(tiles[82].getCoorY(), 480);
		assertEquals(tiles[83].getCoorX(), 180);
		assertEquals(tiles[83].getCoorY(), 480);
		assertEquals(tiles[84].getCoorX(), 240);
		assertEquals(tiles[84].getCoorY(), 480);
		assertEquals(tiles[85].getCoorX(), 300);
		assertEquals(tiles[85].getCoorY(), 480);
		assertEquals(tiles[86].getCoorX(), 360);
		assertEquals(tiles[86].getCoorY(), 480);
		assertEquals(tiles[87].getCoorX(), 420);
		assertEquals(tiles[87].getCoorY(), 480);
		assertEquals(tiles[88].getCoorX(), 480);
		assertEquals(tiles[88].getCoorY(), 480);
		assertEquals(tiles[89].getCoorX(), 540);
		assertEquals(tiles[89].getCoorY(), 480);
		
		assertEquals(tiles[90].getCoorX(), 540);
		assertEquals(tiles[90].getCoorY(), 540);
		assertEquals(tiles[91].getCoorX(), 480);
		assertEquals(tiles[91].getCoorY(), 540);
		assertEquals(tiles[92].getCoorX(), 420);
		assertEquals(tiles[92].getCoorY(), 540);
		assertEquals(tiles[93].getCoorX(), 360);
		assertEquals(tiles[93].getCoorY(), 540);
		assertEquals(tiles[94].getCoorX(), 300);
		assertEquals(tiles[94].getCoorY(), 540);
		assertEquals(tiles[95].getCoorX(), 240);
		assertEquals(tiles[95].getCoorY(), 540);
		assertEquals(tiles[96].getCoorX(), 180);
		assertEquals(tiles[96].getCoorY(), 540);
		assertEquals(tiles[97].getCoorX(), 120);
		assertEquals(tiles[97].getCoorY(), 540);
		assertEquals(tiles[98].getCoorX(), 60);
		assertEquals(tiles[98].getCoorY(), 540);
		assertEquals(tiles[99].getCoorX(), 0);
		assertEquals(tiles[99].getCoorY(), 540);
	}
	
	@Test
	public void ruleXMLLoading()
	{
		HashMap<String,RuleMapping> ruleMapping = RuleMapping.iniRuleMapping(new FileHandle(new File(fileDir+"ruleMappingTest.xml")));
		assertEquals(ruleMapping.get("+10").getImplementationClass(),"ScoreRule");
		assertEquals(ruleMapping.get("-10").getImplementationClass(),"ScoreRule");
		assertEquals(ruleMapping.get("+20").getImplementationClass(),"ScoreRule");
		assertEquals(ruleMapping.get("-20").getImplementationClass(),"ScoreRule");
		assertEquals(ruleMapping.get("+50").getImplementationClass(),"ScoreRule");
		assertEquals(ruleMapping.get("-50").getImplementationClass(),"ScoreRule");
		assertEquals(ruleMapping.get("+100").getImplementationClass(),"ScoreRule");
		assertEquals(ruleMapping.get("-100").getImplementationClass(),"ScoreRule");
		
		assertEquals(ruleMapping.get("+10").getIcon(),"plus_10.png");
		assertEquals(ruleMapping.get("-10").getIcon(),"minus_10.png");
		assertEquals(ruleMapping.get("+20").getIcon(),"plus_20.png");
		assertEquals(ruleMapping.get("-20").getIcon(),"minus_20.png");
		assertEquals(ruleMapping.get("+50").getIcon(),"plus_50.png");
		assertEquals(ruleMapping.get("-50").getIcon(),"minus_50.png");
		assertEquals(ruleMapping.get("+100").getIcon(),"plus_100.png");
		assertEquals(ruleMapping.get("-100").getIcon(),"minus_100.png");
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
		assertEquals(gp.getTileList()[0].getRule(), "+10");
		assertEquals(gp.getTileList()[1].getRule(), "-10");
		assertEquals(gp.getTileList()[2].getRule(), "+20");
		assertEquals(gp.getTileList()[3].getRule(), "-20");
		assertEquals(gp.getTileList()[4].getRule(), "+50");
		assertEquals(gp.getTileList()[5].getRule(), "-50");
		assertEquals(gp.getTileList()[6].getRule(), "+100");
		assertEquals(gp.getTileList()[7].getRule(), "-100");
		assertEquals(gp.getTileList()[29].getRule(), "L50");
		assertEquals(gp.getTileList()[40].getRule(), "S21");
	}
	
	@Test
	public void emptyRuleLoading()
	{
		GameMap gp = new GameMap();
		//mock the ruleMapping
		HashMap<String,RuleMapping> ruleMapping = RuleMapping.iniRuleMapping(new FileHandle(new File(fileDir+"ruleMappingTest.xml")));
		//load testing file 
		try {
			gp.populateTileListFromMapFile(new FileHandle(new File(fileDir+"mapLoadingTest2.txt")), ruleMapping);
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
		for(Tile t:gp.getTileList())
		{
			assertEquals(t.getRule(), ".");
		}
	}
}
