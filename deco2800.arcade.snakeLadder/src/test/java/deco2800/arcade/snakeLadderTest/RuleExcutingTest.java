package deco2800.arcade.snakeLadderTest;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

import com.badlogic.gdx.files.FileHandle;

import deco2800.arcade.snakeLadder.SnakeLadder;
import deco2800.arcade.snakeLadderGameState.*;
import deco2800.arcade.snakeLadderModel.*;
import deco2800.arcade.snakeLadderRulePlugin.ScoreRule;

public class RuleExcutingTest {

        private static final String fileDir = "src/test/java/deco2800/arcade/snakeLadderTest/";
        private SnakeLadder context;
        
        public void iniContext()
        {
                FileHandle mapFile = new FileHandle(new File(fileDir+"mapLoadingTest1.txt"));
                FileHandle xmlFile = new FileHandle(new File(fileDir+"ruleMappingTest.xml"));
                context = new SnakeLadder(null, null, "test", xmlFile, mapFile);
        }
        
        @Test
        public void ScoringRuleTest() {
                iniContext();
                ScoreRule scoringRule = new ScoreRule();
                scoringRule.processRule(0, "+10", context);
                //checking score is updated
                assertEquals(context.gamePlayers[0].getScore(),10);
                //proceed to next user's turn
                assertEquals(context.getturns(),1);
                //game state transit from ruleExcuting to WaitingState
                assertTrue(context.gameState instanceof WaitingState);
                
                iniContext();
                ScoreRule scoringRule1=new ScoreRule();
                scoringRule1.processRule(0,"-10",context);
                assertEquals(context.gamePlayers[0].getScore(),-10);
                assertEquals(context.getturns(),1);
                assertTrue(context.gameState instanceof WaitingState);
                
                iniContext();
                ScoreRule scoringRule2=new ScoreRule();
                scoringRule2.processRule(0, "+20", context);
                assertEquals(context.gamePlayers[0].getScore(),20);
                assertEquals(context.getturns(),1);
                assertTrue(context.gameState instanceof WaitingState);
                
                iniContext();
                ScoreRule scoringRule3=new ScoreRule();
                scoringRule3.processRule(0, "-20", context);
                assertEquals(context.gamePlayers[0].getScore(),-20);
                assertEquals(context.getturns(),1);
                assertTrue(context.gameState instanceof WaitingState);
                
                iniContext();
                ScoreRule scoringRule4=new ScoreRule();
                scoringRule4.processRule(0, "+50", context);
                assertEquals(context.gamePlayers[0].getScore(),50);
                assertEquals(context.getturns(),1);
                assertTrue(context.gameState instanceof WaitingState);
                
                iniContext();
                ScoreRule scoringRule5=new ScoreRule();
                scoringRule5.processRule(0, "-50", context);
                assertEquals(context.gamePlayers[0].getScore(),-50);
                assertEquals(context.getturns(),1);
                assertTrue(context.gameState instanceof WaitingState);
                
                iniContext();
                ScoreRule scoringRule6=new ScoreRule();
                scoringRule6.processRule(0, "+100", context);
                assertEquals(context.gamePlayers[0].getScore(),100);
                assertEquals(context.getturns(),1);
                assertTrue(context.gameState instanceof WaitingState);
                
                iniContext();
                ScoreRule scoringRule7=new ScoreRule();
                scoringRule7.processRule(0, "-100", context);
                assertEquals(context.gamePlayers[0].getScore(),-100);
                assertEquals(context.getturns(),1);
                assertTrue(context.gameState instanceof WaitingState);
                
        }
        
        @Test
        public void ladderSnakeRuleTest()
        {
                iniContext();
                LadderSnakeRule ladderSnakeRule = new LadderSnakeRule();
                ladderSnakeRule.excuteRules(0, "L20", context);
                //check destination position
                assertEquals(context.gamePlayers[0].newposition(),19);
                // Still within this player's turn
                assertEquals(context.getturns(),0);
                //transit to moving state
                assertTrue(context.gameState instanceof MovingState);
        }

}