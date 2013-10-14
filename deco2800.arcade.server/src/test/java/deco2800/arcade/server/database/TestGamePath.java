package deco2800.arcade.server.database;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
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

import deco2800.arcade.packman.PackageUtils;
import deco2800.server.database.DatabaseException;
import deco2800.server.database.GamePath;


public class TestGamePath {

	private static IDatabaseTester databaseTester; //manage connections to the database
	private GamePath gamePath; //storage object to test
	
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
	
	private IDataSet getDataSet() throws DataSetException, IOException {
		URL url = TestGamePath.class.getClassLoader().getResource("TestGamePath.xml");
		FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
		builder.setColumnSensing(true);
		return builder.build(url);
	}
	
	/**
	 * Create a new GamePath and initialise it,
	 * load in a dataset from XML, and get the database ready (clean old data and put the new stuff in)
	 * This method is run once before each test case.
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		gamePath = new GamePath();
		gamePath.initialise();
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
	
	@Test
	public void testGetPath() throws DatabaseException {
		assertEquals("TestPath", gamePath.getPath(0));
		System.out.print("\nGame Path found correctly\n\n\n");
	}
	
	@Test 
	public void testGetMD5() throws DatabaseException {
		assertEquals("1001", gamePath.getMD5(0));
	}
	
	@Test
	public void testInsertGame() throws DatabaseException {
		gamePath.insertGame(1, "games.txt");
		assertEquals("games.txt", gamePath.getPath(1));
		assertEquals(PackageUtils.genMD5("games.txt"), gamePath.getMD5(1));
	}
	
	/**
	 * Test for AchievementsForIDs method
	 * @throws DatabaseException
	 */
	/*@Test
	public void testInsert() throws DatabaseException {
		
	}*/
	
}
