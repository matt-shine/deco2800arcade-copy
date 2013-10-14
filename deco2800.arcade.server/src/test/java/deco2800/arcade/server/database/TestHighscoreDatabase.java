package deco2800.arcade.server.database;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import deco2800.server.database.DatabaseException;
import deco2800.server.database.HighscoreDatabase;

/**
 * Test class for HighscoreDatabase
 * @author Team A (credit uqjstee8)
 * @see deco2800.arcade.server.database.HighscoreDatabase
 */
public class TestHighscoreDatabase {

	private static IDatabaseTester databaseTester; //manage connections to the database
	private HighscoreDatabase highscoreDatabase; //storage object to test

	/**
	 * This method is run once when this class is instantiated
	 * @throws Exception
	 */
	@BeforeClass
	public static void setUpClass() throws Exception {
		databaseTester = new JdbcDatabaseTester(
                "org.apache.derby.jdbc.EmbeddedDriver",
                "jdbc:derby:Arcade;user=server;password=server;create=true");
	}

	/**
	 * Retrieve the dataset from an XML file
	 * @return
	 * @throws DataSetException
	 * @throws IOException
	 */
	private IDataSet getDataSet() throws DataSetException, IOException {
		URL url = TestHighscoreDatabase.class.getClassLoader().getResource("TestHighscoreDatabase.xml");
		FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
		builder.setColumnSensing(true);
		return builder.build(url);
	}
	
	/**
	 * Create a new highscore and initialise it,
	 * load in a dataset from XML, and get the database ready (clean old data and put the new stuff in)
	 * This method is run once before each test case.
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		highscoreDatabase = new HighscoreDatabase();
		highscoreDatabase.initialise();
		IDataSet ds = getDataSet();
        databaseTester.setDataSet(ds);
		databaseTester.onSetup();
	}
	
	/**
	 * Allow DBUnit to clean up after the test case (restore the database to its pre-testing state)
	 * @throws Exception
	 */
	@After
	public void  tearDown() throws Exception {
		databaseTester.onTearDown();
	}
	
	
	/**
	 * Test for adding a score using updateScore method
	 * @throws DatabaseException
	 * @throws SQLException 
	 */
	@Test
	public void testUpdateScore() throws DatabaseException, SQLException {
		LinkedList<Integer> p1Scores = new LinkedList<Integer>();
		LinkedList<String> p1Types = new LinkedList<String>(); 
		p1Scores.add(13579);
		p1Types.add("Points");		
		highscoreDatabase.updateScore("Pong", "Player One", p1Scores, p1Types);
		List<String> p1Return = highscoreDatabase.getUserHighScore("Player One", "Pong", "Points", true);
		
		LinkedList<Integer> p2Scores = new LinkedList<Integer>();
		LinkedList<String> p2Types = new LinkedList<String>(); 
		p2Scores.add(24680);
		p2Types.add("Points");
		highscoreDatabase.updateScore("Pong", "Player Two", p2Scores, p2Types);
		List<String> p2Return = highscoreDatabase.getUserHighScore("Player Two", "Pong", "Points", true);
		
		assertEquals("Player One score equals 13579", "13579", p1Return.get(1));
		assertEquals("Player Two score equals 24680", "24680", p2Return.get(1));
		
	}
	
	/**
	 * Test getGameTopPlayers method
	 * @throws DatabaseException
	 * @throws SQLException 
	 */
	@Test
	public void testGetGameTopPlayers() throws DatabaseException, SQLException {
		List<String> gameTopPlayers = highscoreDatabase.getGameTopPlayers("Pong", 1, "Points", true);
		assertEquals("Player Two has highest score", "24680", gameTopPlayers.get(1));
	}

	/**
	 * Test getUserHighScore method
	 * @throws DatabaseException
	 * @throws SQLException 
	 */
	@Test
	public void testGetUserHighScore() throws DatabaseException, SQLException {
		LinkedList<Integer> p3Scores = new LinkedList<Integer>();
		LinkedList<String> p3Types = new LinkedList<String>(); 
		p3Scores.add(12);
		p3Types.add("Points");
		p3Scores.add(89);
		p3Types.add("Points");
		p3Scores.add(34);
		p3Types.add("Points");
		p3Scores.add(67);
		p3Types.add("Points");
		p3Scores.add(45);
		p3Types.add("Points");
		highscoreDatabase.updateScore("Pong", "Player Three", p3Scores, p3Types);
		List<String> p3HighestScore = highscoreDatabase.getUserHighScore("Player Three", "Pong", "Points", true);
		assertEquals("Player Three highest score", "89", p3HighestScore.get(1));
	}
	
	/**
	 * Test getUserRanking method
	 * @throws DatabaseException
	 * @throws SQLException 
	 */
	@Test
	public void testGetUserRanking() throws DatabaseException, SQLException {
		//Implemented but now working
		//List<String> p3HighestScore = highscoreDatabase.getUserRanking("Player Three", "Pong", "Points", true);
		//System.out.println("p3 return: " + p3HighestScore.toString());
	}
	
	/**
	 * Test getAvgUserHighScore method
	 * @throws DatabaseException
	 * @throws SQLException 
	 */
	@Test
	public void testGetAvgUserHighScore() throws DatabaseException, SQLException {
		//Not yet implemented
	}
	
	/**
	 * Test getTopPlayers method
	 * @throws DatabaseException
	 * @throws SQLException 
	 */
	@Test
	public void testGetTopPlayers() throws DatabaseException, SQLException {
		//Not yet implemented
	}
	
}
