package deco2800.arcade.server.database;

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
import deco2800.server.database.CreditStorage;

/**
 * Test class for CreditStorage
 * @author uqjstee8
 * @see deco2800.arcade.server.database.CreditStorage
 */
public class TestCreditStorage {

	private static IDatabaseTester databaseTester; //manage connections to the database
	private CreditStorage creditStorage; //storage object to test

	/**
	 * This method is run once when this class is instantiated
	 * @throws Exception
	 */
	@BeforeClass
	public static void setUpClass() throws Exception {
	//	databaseTester = new JdbcDatabaseTester(
    //            "org.apache.derby.jdbc.EmbeddedDriver",
    //            "jdbc:derby:Arcade;user=server;password=server;create=true");
	}

	/**
	 * Retrieve the dataset from an XML file
	 * @return
	 * @throws DataSetException
	 * @throws IOException
	 */
	private IDataSet getDataSet() throws DataSetException, IOException {
	//	URL url = TestCreditStorage.class.getClassLoader().getResource("TestCreditStorage.xml");
	//	FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
	//	builder.setColumnSensing(true);
	//	return builder.build(url);
		return null;
	}
	
	/**
	 * Create a new credit storage and initialise it,
	 * load in a dataset from XML, and get the database ready (clean old data and put the new stuff in)
	 * This method is run once before each test case.
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
	//	creditStorage = new CreditStorage();
	//	creditStorage.initialise();
	//	IDataSet ds = getDataSet();
    //   databaseTester.setDataSet(ds);
	//	databaseTester.onSetup();
	}
	
	/**
	 * Allow DBUnit to clean up after the test case (restore the database to its pre-testing state)
	 * @throws Exception
	 */
	@After
	public void  tearDown() throws Exception {
	//	databaseTester.onTearDown();
	}
	
	/**
	 * Simple test case to make sure our XML loading is working, and that retrieving a user's balance is OK
	 * @throws DatabaseException
	 */

	@Test
	public void initialTotal() throws DatabaseException {
	//	assertEquals(0, (int) creditStorage.getUserCredits(1));
	}
	
	/**
	 * Check that a simple addition to a zero balance works
	 * @throws DatabaseException
	 */
	@Test
	public void basecase() throws DatabaseException {
	//	creditStorage.addUserCredits(1, 5);
	//	assertEquals(new Integer(5), creditStorage.getUserCredits(1));
	}
	

//	@Test
//	public void initialTotal() throws DatabaseException {
//		assertEquals(0, (int) creditStorage.getUserCredits(1));
//	}
//	
//	/**
//	 * Check that a simple addition to a zero balance works
//	 * @throws DatabaseException
//	 */
//	@Test
//	public void basecase() throws DatabaseException {
//		creditStorage.addUserCredits(1, 5);
//		assertEquals(new Integer(5), creditStorage.getUserCredits(1));
//	}
//	

}
