package deco2800.arcade.server.database;

import static org.junit.Assert.*;

import java.net.URL;

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
import deco2800.server.database.HashStorage;
import deco2800.server.database.PlayerStorage;

/**
 * Test class for HashStorage
 * 
 * @author Team Mashup
 * @see deco2800.arcade.server.database.HashStorage
 * 
 */
public class TestHashStorage {

	private static IDatabaseTester databaseTester;
	private HashStorage hashStorage;
	private PlayerStorage playerStorage;

	/**
	 * This class is run once when the class is instantiated
	 * 
	 * @throws ClassNotFoundException
	 */
	@BeforeClass
	public static void setUpClass() throws ClassNotFoundException {
		databaseTester = new JdbcDatabaseTester(
				"org.apache.derby.jdbc.EmbeddedDriver",
				"jdbc:derby:Arcade;user=server;password=server;create=true");
	}

	/**
	 * Create and initialise a new hash and player storage. Load dataset from
	 * file, clean the database and prepare it with the new data.
	 * 
	 * This method is called before every test.
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		playerStorage = new PlayerStorage();
		playerStorage.initialise();

		hashStorage = new HashStorage();
		hashStorage.initialise();

		IDataSet ds = getDataSet();
		databaseTester.setDataSet(ds);
		databaseTester.onSetup();
	}

	/**
	 * Load the dataset from file.
	 * 
	 * @throws DataSetException
	 */
	private IDataSet getDataSet() throws DataSetException {
		// Read in PLAYERS database for testing
		URL url = TestHashStorage.class.getClassLoader().getResource(
				"TestHashStorage.xml");
		FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
		builder.setColumnSensing(true);
		return builder.build(url);
	}

	/**
	 * Clean up and restore the database to its state prior to the test.
	 * 
	 * This method is called after every test.
	 * 
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {
		databaseTester.onTearDown();
	}

	/**
	 * Register a password for a player that already exists in the PLAYERS
	 * database and test that the registration was successful.
	 * 
	 * @throws DatabaseException
	 */
	@Test
	public void registerPasswordTest() throws DatabaseException {
		String password = "It's just a flesh wound.";
		String username = "Black_Knight";

		hashStorage.registerPassword(username, password);

		// Check that we successfully registered
		assertTrue(hashStorage.checkPassword(username, password));
	}
	
	@Test
	public void updatePasswordTest() throws DatabaseException {
		String password = "It's just a flesh wound.";
		String newPassword = "No it isn't!";
		String username = "Black_Knight";

		hashStorage.registerPassword(username, password);
		hashStorage.updatePassword(username, newPassword);

		// Check that we successfully registered
		assertTrue(hashStorage.checkPassword(username, newPassword));
	}
}
