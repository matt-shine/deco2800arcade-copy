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
	 * Test for addHighscore method
	 * @throws DatabaseException
	 * @throws SQLException 
	 */
	@Test
	public void testUpdateScore() throws DatabaseException, SQLException {
		//int p1 = highscoreDatabase.addHighscore("Pong", "Player One");
		//int p2 = highscoreDatabase.addHighscore("Pong", "Player Two");
		
		LinkedList<Integer> p1Scores = new LinkedList<Integer>();
		LinkedList<String> p1Types = new LinkedList<String>(); 
		p1Scores.add(13579);
		p1Types.add("Points");		
		highscoreDatabase.updateScore("Pong", "Player One", p1Scores, p1Types);
		List<String> p1Return = highscoreDatabase.getUserHighScore("Player One", "Pong", "", true);
		String[] strarray = p1Return.toArray(new String[0]);
		System.out.println("return: " + Arrays.toString(strarray));
		
		LinkedList<Integer> p2Scores = new LinkedList<Integer>();
		LinkedList<String> p2Types = new LinkedList<String>(); 
		p1Scores.add(24680);
		p1Types.add("Points");
		highscoreDatabase.updateScore("Pong", "Player Two", p2Scores, p2Types);
		List<String> p2Return = highscoreDatabase.getUserHighScore("Player Two", "Pong", "", true);
		
		System.out.println("return: " + p2Return.toString());
		
		//assertEquals("", sql, s);
		
		//System.out.println("return: " + highscoreDatabase.addHighscore("Pong", "Player One"));
		
		
		//assertEquals("Player One must have ID 1", 1, highscoreDatabase.addHighscore("Pong", "Player One"));
		//assertEquals("Player Two must have ID 2", 2, highscoreDatabase.addHighscore("Pong", "Player Two"));
		//assertEquals("Player Three must have ID 3", 3, highscoreDatabase.addHighscore("Pong", "Player Thre"));
	}

}
