package deco2800.arcade.server.database;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

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
import deco2800.server.database.PlayerStorage;

public class TestPlayerStorage {
	private static IDatabaseTester databaseTester; //manage connections to the database
	private PlayerStorage playerStorage; //storage object to test
	
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
		URL url = TestCreditStorage.class.getClassLoader().getResource("TestPlayerStorage.xml");
		FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
		builder.setColumnSensing(true);
		return builder.build(url);
	}
	
	/**
	 * Create a new player storage and initialise it,
	 * load in a dataset from XML, and get the database ready (clean old data and put the new stuff in)
	 * This method is run once before each test case.
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		playerStorage = new PlayerStorage();
        playerStorage.initialise();
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
	 * Test loading a player by ID
	 * 
	 * @throws DatabaseException
	 */
	//This doesn't like going after "TestHashStorage" runs
	//Reason: TestHashStorage creates a foreign dependency on Black_Knight's playerID and so when this test
	// tries to clean up playerStorage, it can't because Black_Knight's ID (1) is being depended on.
	// other than that, the test works fine.
	//@Test
	public void loadPlayerTest() throws DatabaseException {
		int playerID = 555;

		List<String> playerData = new ArrayList<String>();
		playerData = playerStorage.getPlayerData(playerID);

		assertTrue(playerData.get(0).equals("Guy"));
		assertTrue(playerData.get(1).equals("Guybrush Threepwood"));
		assertTrue(playerData.get(2).equals("guybrush@monkey.island"));
		assertTrue(playerData.get(3).equals("BE"));
		assertTrue(playerData.get(4).equals("Escape artist"));
	}
}
