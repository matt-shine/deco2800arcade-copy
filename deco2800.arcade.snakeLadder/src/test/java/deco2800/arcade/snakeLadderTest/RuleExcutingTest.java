package deco2800.arcade.snakeLadderTest;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

import com.badlogic.gdx.files.FileHandle;

import deco2800.arcade.snakeLadder.SnakeLadder;
import deco2800.arcade.snakeLadderModel.*;

public class RuleExcutingTest {

	private static final String fileDir = "src/test/java/deco2800/arcade/snakeLadderTest/";
	private SnakeLadder context;
	
	public void iniContext()
	{
		FileHandle mapFile = new FileHandle(new File(fileDir+"mapLoadingTest1.txt"));
		FileHandle xmlFile = new FileHandle(new File(fileDir+"ruleMappingTest.xml"));
		context = new SnakeLadder(null, null, "test", xmlFile, mapFile);
	}
	
	//@Test
	public void ScoringRuleTest() {
		iniContext();
		ScoreRule scoringRule = new ScoreRule();
		scoringRule.processRule(0, "+10", context);
		assertEquals(context.gamePlayers[0].getScore(),10);
	}

}
