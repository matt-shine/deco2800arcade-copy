package deco2800.arcade.server.database;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.URL;
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
		URL url = TestPlayerStorage.class.getClassLoader().getResource("TestPlayerStorage.xml");
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
	
	//@Test
	/**
	 * Get a player's data from the database.
	 */
	public void testGetPlayerData() throws DatabaseException {
		try {
			List<String> playerData = new ArrayList<String>();
			playerData.add("1");
			playerData.add("U1");
			playerData.add("bob");
			playerData.add("bob@gmail.com");
			playerData.add("BE");
			playerData.add("I am bob.");
			playerData.add("86");
			assertEquals(playerData, playerStorage.getPlayerData(1));
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//@Test
	/**
	 * Tests updating a player's username.
	 */
	public void testUpdateUsername() throws DatabaseException {
		playerStorage.updateUsername(1, "xXxMLGx420x1337x5n1pZ0rxXx");
		assertEquals("xXxMLGx420x1337x5n1pZ0rxXx", playerStorage.getPlayerData(1).get(1));
	}
	
	//@Test
	/**
	 * Tests updating a player's name.
	 */
	public void testUpdateName() throws DatabaseException {
		playerStorage.updateName(1, "Saul Goodman");
		assertEquals("Saul Goodman", playerStorage.getPlayerData(1).get(2));
	}
	
	//@Test
	/**
	 * Tests updating a player's email address.
	 */
	public void testUpdateEmail() throws DatabaseException {
		playerStorage.updateEmail(1, "cod4pro4life@hotmail.com");
		assertEquals("cod4pro4life@hotmail.com", playerStorage.getPlayerData(1).get(3));
	}
	
	//@Test
	/**
	 * Tests updating a player's program.
	 */
	public void testUpdateProgram() throws DatabaseException {
		playerStorage.updateProgram(1, "LLB");
		assertEquals("LLB", playerStorage.getPlayerData(1).get(4));
	}
	
	//@Test
	/**
	 * Tests updating a player's bio.
	 */
	public void testUpdateBio() throws DatabaseException {
		playerStorage.updateBio(1, "Better call Saul! Also Huell's head looks hilarious.");
		assertEquals("Better call Saul! Also Huell's head looks hilarious.", playerStorage.getPlayerData(1).get(5));
	}
	
	//@Test
	/**
	 * Tests updating a player's age.
	 */
	public void testUpdateAge() throws DatabaseException {
		playerStorage.updateAge(1, "9001");
		assertEquals("9001", playerStorage.getPlayerData(1).get(6));
	}
	
	//@Test
	/**
	 * Tests adding a player and then retrieving their results.
	 */
	public void testAddPlayer() throws DatabaseException {
		List<String> playerData = new ArrayList<String>();
		playerData.add("5");
		playerData.add("Space Cowboy");
		playerData.add("Spike Spiegel");
		playerData.add("spike@bebop.com");
		playerData.add("Bachelor of Bounty Hunting");
		playerData.add("BANG!");
		playerData.add("27");
		playerStorage.addPlayer(Integer.parseInt(playerData.get(0)), 
				playerData.get(1), playerData.get(2), playerData.get(3), 
				playerData.get(4), playerData.get(5), playerData.get(6));
		List<String> retrievedData = playerStorage.getPlayerData(5);
		assertEquals(playerData, retrievedData);
	}
}
