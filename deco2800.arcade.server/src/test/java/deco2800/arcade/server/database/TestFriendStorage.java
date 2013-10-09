package deco2800.arcade.server.database;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import deco2800.arcade.model.Player;
import deco2800.server.database.DatabaseException;
import deco2800.server.database.FriendStorage;


public class TestFriendStorage {
	private static IDatabaseTester databaseTester; //manage connections to the database
	private FriendStorage friendStorage; //storage object to test
	
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
		URL url = TestCreditStorage.class.getClassLoader().getResource("TestFriendStorage.xml");
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
		friendStorage = new FriendStorage();
        friendStorage.initialise();
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
	
	Player testPlayer0 = new Player(0, null, null, null, null, null, null, null);
	Player testPlayer1 = new Player(1, null, null, null, null, null, null, null);
	Player testPlayer2 = new Player(2, null, null, null, null, null, null, null);
	
/*	@Test
	public void testFriendRequest() throws DatabaseException {
		// create a test ArrayList<Integer> of playerIDs
		ArrayList<Integer> testFriendRequests = new ArrayList<Integer>();
		testFriendRequests.add(1);
		testFriendRequests.add(2);
		// create a test response from the database
		
		// compare playerIDs
		friendStorage.addFriendRequest(0, 1);
		friendStorage.addFriendRequest(0, 2);
		assertEquals(testFriendRequests, friendStorage.getFriendInviteList(0));
	}*/
	
}
