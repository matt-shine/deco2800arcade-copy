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

import deco2800.server.database.CreditStorage;
import deco2800.server.database.DatabaseException;

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
		URL url = TestCreditStorage.class.getClassLoader().getResource("TestCreditStorage.xml");
		FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
		builder.setColumnSensing(true);
		return builder.build(url);
	}

	/**
	 * Create a new credit storage and initialise it,
	 * load in a dataset from XML, and get the database ready (clean old data and put the new stuff in)
	 * This method is run once before each test case.
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		creditStorage = new CreditStorage();
		IDataSet ds = getDataSet();
        databaseTester.setDataSet(ds);
		databaseTester.onSetup();
	}

	/**
	 * Allow DBUnit to clean up after the test case (restore the database to its pre-testing state)
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {
		databaseTester.onTearDown();
	}
<<<<<<< HEAD
//
//	/**
//	 * Simple test case to make sure our XML loading is working, and that retrieving a user's balance is OK
//	 * @throws DatabaseException
//	 */
=======
	
	/**
	 * Simple test case to make sure our XML loading is working, and that retrieving a user's balance is OK
	 * @throws DatabaseException
	 */
>>>>>>> master
//	@Test
//	public void initialTotal() throws DatabaseException {
//		assertEquals(0, (int) creditStorage.getUserCredits(1));
//	}
<<<<<<< HEAD
//
=======
//	
>>>>>>> master
//	/**
//	 * Check that a simple addition to a zero balance works
//	 * @author Addison Gourluck
//	 * @throws DatabaseException
//	 */
//	@Test
//	public void addBaseCase() throws DatabaseException {
//		creditStorage.addUserCredits(1, 5);
//		assertEquals(new Integer(5), creditStorage.getUserCredits(1));
//	}
<<<<<<< HEAD
//
=======
//	
>>>>>>> master
//	/**
//	 * Check that several additions to 0 works
//	 * @author Addison Gourluck
//	 * @throws DatabaseException
//	 */
//	@Test
//	public void multipleAddCases() throws DatabaseException {
//		creditStorage.addUserCredits(1, 50);
//		creditStorage.addUserCredits(1, 20);
//		creditStorage.addUserCredits(1, 10);
//		creditStorage.addUserCredits(1, 1);
//		creditStorage.addUserCredits(1, 1000);
//		assertEquals(new Integer(1081), creditStorage.getUserCredits(1));
//	}
<<<<<<< HEAD
//
=======
//	
>>>>>>> master
//	/**
//	 * Check that adding 0 will throw an exception.
//	 * @author Addison Gourluck
//	 * @throws DatabaseException
//	 */
//	@Test(expected=DatabaseException.class)
//	public void addZeroCase() throws DatabaseException {
//		creditStorage.addUserCredits(1, 100);
//		creditStorage.addUserCredits(1, 0);
//	}
//
//	/**
//	 * Check that adding a negative number will throw an exception.
//	 * @author Addison Gourluck
//	 * @throws DatabaseException
//	 */
//	@Test(expected=DatabaseException.class)
//	public void addNegativeCase() throws DatabaseException {
//		creditStorage.addUserCredits(1, 100);
//		creditStorage.addUserCredits(1, -10);
//	}
//
//	/**
//	 * Check that a simple addition and then subtraction works
//	 * @author Addison Gourluck
//	 * @throws DatabaseException
//	 */
//	@Test
//	public void deductBaseCase() throws DatabaseException {
//		creditStorage.addUserCredits(1, 20);
//		creditStorage.deductUserCredits(1, 5);
//		assertEquals(new Integer(15), creditStorage.getUserCredits(1));
//	}
<<<<<<< HEAD
//
=======
//	
>>>>>>> master
//	/**
//	 * Check that several subtractions work with a final balance of 0.
//	 * @author Addison Gourluck
//	 * @throws DatabaseException
//	 */
//	@Test
//	public void multipleDeductCases() throws DatabaseException {
//		creditStorage.addUserCredits(1, 1000);
//		creditStorage.deductUserCredits(1, 900);
//		creditStorage.deductUserCredits(1, 50);
//		creditStorage.deductUserCredits(1, 50);
//		assertEquals(new Integer(0), creditStorage.getUserCredits(1));
//	}
<<<<<<< HEAD
//
=======
//	
>>>>>>> master
//	/**
//	 * Check that subtracting more than the user has will throw an exception.
//	 * @author Addison Gourluck
//	 * @throws DatabaseException
//	 */
//	@Test(expected=DatabaseException.class)
//	public void cantAffordCase() throws DatabaseException {
//		creditStorage.addUserCredits(1, 100);
//		creditStorage.deductUserCredits(1, 101);
//	}
<<<<<<< HEAD
//
=======
//	
>>>>>>> master
//	/**
//	 * Check that subtracting 0 will throw an exception.
//	 * @author Addison Gourluck
//	 * @throws DatabaseException
//	 */
//	@Test(expected=DatabaseException.class)
//	public void deductZeroCase() throws DatabaseException {
//		creditStorage.addUserCredits(1, 100);
//		creditStorage.deductUserCredits(1, 0);
//	}
//
//	/**
//	 * Check that subtracting a negative number will throw an exception.
//	 * @author Addison Gourluck
//	 * @throws DatabaseException
//	 */
//	@Test(expected=DatabaseException.class)
//	public void deductNegativeCase() throws DatabaseException {
//		creditStorage.addUserCredits(1, 100);
//		creditStorage.deductUserCredits(1, -10);
//	}
}
