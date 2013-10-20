package deco2800.arcade.server.database;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.DefaultTable;
import org.dbunit.dataset.DefaultDataSet;
import org.dbunit.dataset.datatype.DataType;
import org.dbunit.dataset.Column;

import deco2800.server.database.DatabaseException;
import deco2800.server.database.HighscoreDatabase;

/**
 * Test class for HighscoreDatabase
 * @author Team A
 * @see deco2800.arcade.server.database.HighscoreDatabase
 */
public class TestHighscoreDatabase {
   
	private static IDatabaseTester databaseTester;
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
	 * Create a new highscore class object
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		databaseTester.setDataSet(new DefaultDataSet());
		databaseTester.onSetup();
		highscoreDatabase = new HighscoreDatabase();		
		highscoreDatabase.initialise();
	}
	
	/**
	 * Clean up after tests.
	 * @throws Exception
	 */
	@After
	public void  tearDown() throws Exception {
		databaseTester.onTearDown();
	}
	
	
	/**
	 * Tests adding a scores using updateScore method
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
	 * Tests retrieving the highest scores in the database.
	 * @throws DatabaseException
	 * @throws SQLException 
	 */
	@Test
	public void testGetGameTopPlayers() throws DatabaseException, SQLException {
		List<String> gameTopPlayers = highscoreDatabase.getGameTopPlayers("Pong", 1, "Points", true);
		assertEquals("Player Two has highest score", "24680", gameTopPlayers.get(1));
	}

	/**
	 * Test retrieving a users top scores
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
	 * Test a users score ranking in a game
	 * @throws DatabaseException
	 * @throws SQLException 
	 */
	@Test
	public void testGetUserRanking() throws DatabaseException, SQLException {
		List<String> p3HighestScore = highscoreDatabase.getUserRanking("Player Three", "Pong", "Points", true);
		assertEquals("[Player Three, 3, , rank]", p3HighestScore.toString());
	}
	
	/**
	 * Test getAvgUserHighScore method !! Not yet implemented
	 * @throws DatabaseException
	 * @throws SQLException 
	@Test
	public void testGetAvgUserHighScore() throws DatabaseException, SQLException {
		
	}
	*/
	
}
