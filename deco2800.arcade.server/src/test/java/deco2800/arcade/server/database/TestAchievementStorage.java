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

import deco2800.arcade.model.Achievement;
import deco2800.server.database.AchievementStorage;
import deco2800.server.database.DatabaseException;

/**
 * Test class for AchievementStorage
 * @author PeterHsieh via uqjstee8(CreditStorage)
 * @see deco2800.arcade.server.database.AchievementStorage
 */
public class TestAchievementStorage {

	private static IDatabaseTester databaseTester; //manage connections to the database
	private AchievementStorage achievementStorage; //storage object to test
	
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
		URL url = TestCreditStorage.class.getClassLoader().getResource("TestAchievementStorage.xml");
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
		achievementStorage = new AchievementStorage();
		achievementStorage.initialise();
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
	 * Simple test case to make sure our XML loading is working, and that retrieving a achievement's
	 * name is OK.
	 * @throws DatabaseException
	 */
	@Test
	public void initialTotal() throws DatabaseException {
		
		// Create a test ArrayList<String> of AchievementIDs
		ArrayList<String> test = new ArrayList<String>();
		test.add("pong.winthreegames");
		test.add("pong.winfivegames");
		
		// Create a test response from database
		ArrayList<Achievement> achievementtest = new ArrayList<Achievement>();
		achievementtest.add(new Achievement("pong.winthreegames", "3 Times Down",
				"Win 3 games of Pong", 3, "master.png"));
		
		// Compare achievement's name
		System.out.println(achievementStorage.achievementsForIDs(test).get(0).getName());
		assertEquals(achievementtest.get(0).getName(), achievementStorage.achievementsForIDs(test).get(0).getName());
	}
//	
//	/**
//	 * Check that a simple addition to a zero balance works
//	 * @throws DatabaseException
//	 */
//	@Test
//	public void basecase() throws DatabaseException {
//		achievementStorage.addUserCredits("Bob", 5);
//		assertEquals(new Integer(5), achievementStorage.getUserCredits("Bob"));
//	}
	
}

