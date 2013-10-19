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
import deco2800.arcade.model.AchievementProgress;
import deco2800.arcade.model.Player;
import deco2800.server.database.AchievementStorage;
import deco2800.server.database.DatabaseException;
import deco2800.server.database.ImageStorage;

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
		URL url = TestAchievementStorage.class.getClassLoader().getResource("TestAchievementStorage.xml");
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
        ImageStorage imageStorage = new ImageStorage();
		achievementStorage = new AchievementStorage(imageStorage);
        imageStorage.initialise();
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
	 * Test for numPlayerWithAchievement
	 * @throws DatabaseException
	 */
	@Test
	public void testNum() throws DatabaseException{
		// Tests for the number of players with a certain achievement.
		int num = achievementStorage.numPlayerWithAchievement("test.test1");
		assertEquals(3, num);
	}
	
	/**
	 * Test for AchievementsForIDs method
	 * @throws DatabaseException
	 */
	@Test
	public void testAchievementsForIDs() throws DatabaseException {
		// Create a mock response from database
				ArrayList<Achievement> achievementtest = new ArrayList<Achievement>();
				achievementtest.add(new Achievement("test.test1", "Test 1",
						"This is a test.", 5, null));
		
		// Create a mock ArrayList<String> of AchievementIDs
		ArrayList<String> test = new ArrayList<String>();
		test.add("test.test1");
		
		// Compare achievement's name
		assertEquals(achievementtest.get(0), achievementStorage.achievementsForIDs(test).get(0));
	}

	/**
	 * Test incrementing an achievement
	 * @throws DatabaseException
	 */
	@Test
	public void testIncrementAchievement() throws DatabaseException {
		int testProgress = achievementStorage.incrementProgress(1, "test.test1");
		assertEquals(2, testProgress);
	}

	/**
	 * Test progressForPlayer() function
	 * @throws DatabaseException
	 */
	@Test
	public void testProgressForPlayer() throws DatabaseException {
		
		ArrayList<String> mockProgress = new ArrayList<String>();
		mockProgress.add("test.test1");
		mockProgress.add("test.test2");
	
		AchievementProgress testResult = achievementStorage.progressForPlayer(1);
		ArrayList<String> testProgress = testResult.inProgressAchievementIDs();
		assertEquals(mockProgress, testProgress);
		
		ArrayList<String> mockAwarded = new ArrayList<String>();
		mockAwarded.add("test.test3");
		
		ArrayList<String> testAwarded = testResult.awardedAchievementIDs();
		assertEquals(mockAwarded, testAwarded);
		
	}
}

