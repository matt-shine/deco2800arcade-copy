package deco2800.arcade.server.database;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import deco2800.server.database.DatabaseException;
import deco2800.server.database.PlayerGameStorage;

public class TestPlayerGameStorage {
	private static IDatabaseTester databaseTester; //manage connections to the database
	private PlayerGameStorage playerGameStorage; //storage object to test
	
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
		URL url = TestCreditStorage.class.getClassLoader().getResource("TestPlayerGameStorage.xml");
		FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
		builder.setColumnSensing(true);
		return builder.build(url);
	}
	
	/**
	 * Create a new achievement storage and initialise it,
	 * load in a dataset from XML, and get the database ready (clean old data and put the new stuff in)
	 * This method is run once before each test case.
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		playerGameStorage = new PlayerGameStorage();
        playerGameStorage.initialise();
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
	
	//@Test
	/**
	 * Tests retrieving a player's games.
	 */
	public void testGetPlayerGames() throws DatabaseException {
		Set<Integer> expectedResult = new HashSet<Integer>();
		expectedResult.add(Integer.parseInt("1"));
		expectedResult.add(Integer.parseInt("2"));
		Set<Integer> actualResult = playerGameStorage.getPlayerGames(1);
		assertEquals(expectedResult, actualResult);
	}
	
	//@Test
	/**
	 * Tests removing one of a player's games.
	 */
	public void testRemoveGame() throws DatabaseException {
		playerGameStorage.removeGame(1, 2);
		assertFalse(playerGameStorage.hasGame(1, 2));
	}
	
	//@Test
	/**
	 * Tests adding a game to a player's games. 
	 */
	public void testAddGame() throws DatabaseException {
		playerGameStorage.addPlayerGames(1, 3);
		assertTrue(playerGameStorage.hasGame(1, 3));
	}
	
	//@Test
	/**
	 * Tests retrieving a player's rating for one of their games.
	 */
	public void testGetRating() throws DatabaseException {
		int expectedResult = 9;
		int actualResult = playerGameStorage.getPlayerRating(1, 1);
		assertEquals(expectedResult, actualResult);
	}
	
	//@Test
	/**
	 * Tests updating a player's rating for one of their games. 
	 */
	public void testUpdateRating() throws DatabaseException {
		playerGameStorage.updatePlayerRating(1, 1, 8);
		assertEquals(8, playerGameStorage.getPlayerRating(1, 1));
	}
}
