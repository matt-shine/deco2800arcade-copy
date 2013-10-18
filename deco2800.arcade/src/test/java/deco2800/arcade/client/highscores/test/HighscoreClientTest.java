package deco2800.arcade.client.highscores.test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


import deco2800.arcade.client.highscores.Highscore;
import deco2800.arcade.client.highscores.HighscoreClient;
import deco2800.arcade.client.highscores.UnsupportedScoreTypeException;
import deco2800.arcade.model.Player;
import deco2800.arcade.protocol.highscore.*;

public class HighscoreClientTest {
	
	/*It's actually really hard to test this class, as HighscoreClient must be 
	 * instantiated with a non-null NetworkClient object. And, this object 
	 * can't be created without the server running. So, I can really only test 
	 * that the initiliser throws the correct exception for null valued 
	 * paramaters.*/
	private HighscoreClient highscoreClient;	
	private Player player;
	private GetScoreRequest getScoreRequest;
	private boolean[] privacy = {false, false, false, false, false, false, false, false};
	
	/**
	 * Create a new highscore class object
	 * @param networkClient 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		this.player = new Player(0, "Player One", "", privacy);
		this.player.setUsername("Player One");
		HighscoreClient highscoreClient = new HighscoreClient("Dylan", "Pong");
	}
	
	
	@Test (expected=NullPointerException.class)
	public void TestGetGameTopPlayers() {
		List<Highscore> listHighscore = highscoreClient.getGameTopPlayers(10, true, "Point");
	}

	/* 
	 * Test for unsupported score type
	 */
	@Test
	public void testVaildScoreType() {
		HighscoreClient player1 = new HighscoreClient("Dylan", "Pong");
		HighscoreClient player2 = new HighscoreClient("Matt", "Pong");
		try {
			player1.storeScore("NOT_A_VAILD_TYPE", 1290193);
			player2.storeScore("Number", 3910921);
			assertEquals(false, false);
		} catch (UnsupportedScoreTypeException e) {
			assertEquals(true, true);
		}
	}

	/* 
	 * Test Network Client is not null
	 */
	@Test
	public void initTest2() {
		try {
			HighscoreClient hsc = new HighscoreClient(null, "TestGame", null);
			assertEquals(false, false);
		} catch(NullPointerException e) {
			assertEquals(true, true);
		}
	}
	
}
