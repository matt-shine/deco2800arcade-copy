package deco2800.arcade.client.highscores.test;

import java.lang.reflect.Constructor;
import java.util.List;

import org.junit.*;

import deco2800.arcade.client.highscores.Highscore;
import deco2800.arcade.client.highscores.HighscoreClient;
import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.model.Player;
import deco2800.arcade.protocol.highscore.*;


public class HighscoreClientTest {
	
	/*It's actually really hard to test this class, as HighscoreClient must be 
	 * instantiated with a non-null NetworkClient object. And, this object 
	 * can't be created without the server running. So, I can really only test 
	 * that the initiliser throws the correct exception for null valued 
	 * paramaters.*/
	private HighscoreClient highscoreClient;	
	private NetworkClient networkClient;
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
	}
	
	
	@Test (expected=NullPointerException.class)
	public void TestGetGameTopPlayers() {
		HighscoreClient player1 = new HighscoreClient("Dylan", "Pong", networkClient);
		List<Highscore> listHighscore = player1.getGameTopPlayers(10, true, "Number");
		System.out.println("return: " + listHighscore.toString());
	}
	
	@Test (expected=NullPointerException.class)
	public void initTest1() {
		HighscoreClient player1 = new HighscoreClient("Dylan", "Pong", networkClient);
		HighscoreClient player2 = new HighscoreClient("Matt", "Pong", networkClient);
		player1.storeScore("points", 1290193);
		player2.storeScore("points", 3910921);
		
		List<Highscore> topPlayers = player1.getGameTopPlayers(10, true, "points");
		
		System.out.println("return: " + topPlayers.toString());
	}
	
	@Test (expected=NullPointerException.class)
	public void initTest2() {
		HighscoreClient hsc = new HighscoreClient(null, "TestGame", null);
	}
	
}
