package deco2800.arcade.server.database;

import static org.junit.Assert.assertEquals;

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
import deco2800.server.database.PlayerPrivacy;

public class TestPlayerPrivacy {
	private static IDatabaseTester databaseTester; // manage connections to the
													// database
	private PlayerPrivacy playerPrivacy; // storage object to test

	/**
	 * This method is run once when this class is instantiated
	 * 
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
	 * 
	 * @return
	 * @throws DataSetException
	 * @throws IOException
	 */
	private IDataSet getDataSet() throws DataSetException, IOException {
		URL url = TestPlayerPrivacy.class.getClassLoader().getResource(
				"TestPlayerPrivacy.xml");
		FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
		builder.setColumnSensing(true);
		return builder.build(url);
	}

	/**
	 * Create a new achievement storage and initialise it, load in a dataset
	 * from XML, and get the database ready (clean old data and put the new
	 * stuff in) This method is run once before each test case.
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		playerPrivacy = new PlayerPrivacy();
		playerPrivacy.initialise();
		IDataSet ds = getDataSet();
		databaseTester.setDataSet(ds);
		databaseTester.onSetup();
	}

	/**
	 * Allow DBUnit to clean up after the test case (restore the database to its
	 * pre-testing state)
	 * 
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {
		databaseTester.onTearDown();
	}

	// @Test
	/**
	 * Tests getting the privacy data of a player.
	 */
	public void testGetPlayerPrivacyData() throws DatabaseException {

		List<Integer> expectedResults = new ArrayList<Integer>();
		for (int i = 0; i < 6; i++) {
			expectedResults.add(1);
		}
		List<Integer> actualResults = playerPrivacy.getPlayerData(1);
		assertEquals(expectedResults, actualResults);
	}

	// @Test
	/**
	 * Tests updating the privacy setting of a player's name.
	 */
	public void testUpdateNamePrivacy() throws DatabaseException {
		// update to friends only
		playerPrivacy.updateName(1, false);
		assertEquals(0, (int) playerPrivacy.getPlayerData(1).get(1));
		// update back to public
		playerPrivacy.updateName(1, true);
		assertEquals(1, (int) playerPrivacy.getPlayerData(1).get(1));
	}

	// @Test
	/**
	 * Tests updating the privacy setting of a player's email address.
	 */
	public void testUpdateEmailPrivacy() throws DatabaseException {
		// update to friends only
		playerPrivacy.updateEmail(1, false);
		assertEquals(0, (int) playerPrivacy.getPlayerData(1).get(2));
		// update back to public
		playerPrivacy.updateEmail(1, true);
		assertEquals(1, (int) playerPrivacy.getPlayerData(1).get(2));
	}

	// @Test
	/**
	 * Tests updating the privacy setting of a player's program.
	 */
	public void testUpdateProgramPrivacy() throws DatabaseException {
		// update to friends only
		playerPrivacy.updateProgram(1, false);
		assertEquals(0, (int) playerPrivacy.getPlayerData(1).get(3));
		// update back to public
		playerPrivacy.updateProgram(1, true);
		assertEquals(1, (int) playerPrivacy.getPlayerData(1).get(3));
	}

	// @Test
	/**
	 * Tests updating the privacy setting of a player's bio.
	 */
	public void testUpdateBioPrivacy() throws DatabaseException {
		// update to friends only
		playerPrivacy.updateBio(1, false);
		assertEquals(0, (int) playerPrivacy.getPlayerData(1).get(4));
		// update back to public
		playerPrivacy.updateBio(1, true);
		assertEquals(1, (int) playerPrivacy.getPlayerData(1).get(4));
	}

	// @Test
	/**
	 * Tests updating the privacy setting of a player's games.
	 */
	public void testUpdateGamesPrivacy() throws DatabaseException {
		// update to friends only
		playerPrivacy.updateGames(1, false);
		assertEquals(0, (int) playerPrivacy.getPlayerData(1).get(5));
		// update back to public
		playerPrivacy.updateGames(1, true);
		assertEquals(1, (int) playerPrivacy.getPlayerData(1).get(5));
	}

	// @Test
	/**
	 * Tests updating the privacy setting of a player's program.
	 */
	public void testUpdateAchievementsPrivacy() throws DatabaseException {
		// update to friends only
		playerPrivacy.updateAchievements(1, false);
		assertEquals(0, (int) playerPrivacy.getPlayerData(1).get(6));
		// update back to public
		playerPrivacy.updateAchievements(1, true);
		assertEquals(1, (int) playerPrivacy.getPlayerData(1).get(6));
	}
}
