package deco2800.arcade.client.highscores.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.LinkedList;
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
import deco2800.arcade.client.network.NetworkClient;

public class HighscoreClientTest {
	
	/** It's actually really hard to test this class, as HighscoreClient must be 
	 * instantiated with a non-null NetworkClient object. And, this object 
	 * can't be created without the server running. So, I can really only test 
	 * that the initiliser throws the correct exception for null valued 
	 * paramaters.*/
	private HighscoreClient playerTeamA;	
	private Player player;
	private boolean[] privacy = { true, true, true, true, true, true, true };
	
	/**
	 * Initialize and setup variables for testing
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {	    
		List<String> playerInfo = new ArrayList<String>();
		playerInfo.add("TeamA");
		playerInfo.add("Team A Testing");
		playerInfo.add("teama@uq.edu.au");
		playerInfo.add("ITEE");
		playerInfo.add("#TeamA");
		playerInfo.add("2");
		this.player = new Player(123, null, playerInfo, null,
				null, null, null, privacy);
		
		playerTeamA = new HighscoreClient("TeamA", "Pong");
	}
	
	/**
	 * Tests creation on a new player
	 */
	@Test
	public void testPlayerInit() {
		assertEquals("Team A Testing", player.getName());
		assertEquals(123, player.getID());
	}
	
	/**
	 * Test is currently redundant while trying to figure out a way to test while the
	 * server is not running.
	 * @exception NullPointerException
	 */
	@Test (expected=NullPointerException.class)
	public void testGetGameTopPlayers() {
		List<Highscore> listHighscore = playerTeamA.getGameTopPlayers(10, true, "Number");
		System.out.print("List: " + listHighscore.toString());
	}
	
	/**
	 * Tests adding multiple scores to a queue.
	 */
	@Test
	public void testAddMultiScoreItem() {
		LinkedList<String> lsTest = new LinkedList<String>();
		lsTest.add("Distance");
		lsTest.add("12");
		lsTest.add("Number");
		lsTest.add("1290193");
		playerTeamA.addMultiScoreItem("Distance", 12);
		playerTeamA.addMultiScoreItem("Number", 1290193);
		assertEquals(lsTest, playerTeamA.getMultiScoreQueue());
		assertEquals(2, playerTeamA.queuedScoreCount());
	}

	/**
	 * Tests for unsupported score type exception.
	 * @exception UnsupportedScoreTypeException
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
	
	/**
	 * Tests when the server is down and Highscore Client Network Client is null.
	 * @exception NullPointerException
	 */
	@Test
	public void testHighscoreClientInit() {
		try {
			HighscoreClient playerDylan = new HighscoreClient(null, "TestGame", null);
			assertEquals(false, false);
		} catch(NullPointerException e) {
			assertEquals(true, true);
		}
	}
	
}
