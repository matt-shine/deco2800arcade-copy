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
	 * Test for AchievementsForIDs method
	 * @throws DatabaseException
	 */
	@Test
	public void testAchievementsForIDs() throws DatabaseException {
		System.out.print("Testing AchievementsForIDs method.\n================\n" +
				"Create a test ArrayList of AchievementIDs.\n");
		// Create a test ArrayList<String> of AchievementIDs
		ArrayList<String> test = new ArrayList<String>();
		test.add("pong.winthreegames");
		test.add("pong.winfivegames");
		System.out.print("Create a test example response from database " +
				"- ArrayList<Achievement>.\n");
		// Create a test response from database
		ArrayList<Achievement> achievementtest = new ArrayList<Achievement>();
		achievementtest.add(new Achievement("pong.winthreegames", "3 Times Down",
				"Win 3 games of Pong", 3, "master.png"));
		
		// Compare achievement's name
		System.out.print("Test ArrayList<Achievement>: ");
		System.out.print(achievementtest);
		System.out.print("\nFrom Method ArrayList<Achievement>: ");
		System.out.print(achievementStorage.achievementsForIDs(test));
		assertEquals(achievementtest.get(0), achievementStorage.achievementsForIDs(test).get(0));
		System.out.print("\nAchievementsForIDs method: PASSED\n\n\n");
	}
	
	
	/**
	 * Test incrementing an achievement
	 * @throws DatabaseException
	 */
	@Test
	public void testIncrementAchievement() throws DatabaseException {
		Player testplayer;
		Player testplayer2;
		System.out.print("Returning initial PLAYER_ACHIEVEMENT table." +
				"\n================\n");
		achievementStorage.returnPlayersAchievement();
		testplayer = new Player(1, "Bob", "default.png");
		testplayer2 = new Player(2, "Bobbie", "default.png");
		achievementStorage.incrementProgress(testplayer, "pong.winthreegames");
		achievementStorage.incrementProgress(testplayer2, "pong.winthreegames");
		achievementStorage.incrementProgress(testplayer2, "pong.winthreegames");
		achievementStorage.incrementProgress(testplayer, "pong.winfivegames");
		System.out.print("\n- Increment Player1 with achievement Pong Win3Games\n" +
				"- Increment x2 Player2 with achievement Pong Win3Games\n" +
				"- Increment Player1 with achievement Pong Win5Games\n================\n");
		achievementStorage.returnPlayersAchievement();
		
		System.out.print("TEST: Testing Over Increment\n");
		achievementStorage.incrementProgress(testplayer, "pong.winfivegames");
	}
	
}

