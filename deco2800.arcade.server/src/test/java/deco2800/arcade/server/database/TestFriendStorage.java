package deco2800.arcade.server.database;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.URL;
import java.util.*;

import org.dbunit.DBTestCase;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.Column;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.dataset.filter.DefaultColumnFilter;
import org.dbunit.dataset.filter.IColumnFilter;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import deco2800.arcade.model.Player;
import deco2800.server.database.DatabaseException;
import deco2800.server.database.FriendStorage;


public class TestFriendStorage extends DBTestCase {
	private static IDatabaseTester databaseTester; //manage connections to the database
	private FriendStorage friendStorage; //storage object to test
	private Map<String, List<String>> tablePrimaryKeyMap = new HashMap<String, List<String>>();
	
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
	
	@Override
	/**
	 * Retrieve the dataset from an XML file
	 * @return
	 * @throws DataSetException
	 * @throws IOException
	 */
	protected IDataSet getDataSet() throws DataSetException, IOException {
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
		IDatabaseConnection connection = databaseTester.getConnection();
		DatabaseConfig config = connection.getConfig();
		setUpDatabaseConfig(config);
	}
	
	/**
	 * Allow DBUnit to clean up after the test case (restore the database to its pre-testing state)
	 * @throws Exception
	 */
	@After
	public void  tearDown() throws Exception {
		databaseTester.onTearDown();
	}
	
	@Override
	protected void setUpDatabaseConfig(DatabaseConfig config) {
		config.setProperty(DatabaseConfig.PROPERTY_PRIMARY_KEY_FILTER, new ColumnFilter());
		Map<String, List<String>> tablePrimaryKeyMap = new HashMap<String, List<String>>();
		tablePrimaryKeyMap.put("FRIENDS", Arrays.asList(new String[] {"U1", "U2"}));
	}
	
	public Map<String, List<String>> getTablePrimaryKeyMap(){
		return tablePrimaryKeyMap;
	}
	
	@Test
	public void testAcceptFriendRequest() throws DatabaseException {
		friendStorage.acceptFriendRequest(1, 2);
		assertTrue(friendStorage.isFriends(1, 2));
	}
	
	private class ColumnFilter extends DefaultColumnFilter {
		
		TestFriendStorage testFriendStorage = new TestFriendStorage();
		Map<String, List<String>> tablePrimaryKeyMap = testFriendStorage.getTablePrimaryKeyMap();
		
		@Override
		public boolean accept(String tableName, Column column) {
			if (tablePrimaryKeyMap.containsKey(tableName)) {
				return tablePrimaryKeyMap.get(tableName).contains(column.getColumnName());
			} else {
				return column.getColumnName().equalsIgnoreCase("U1");
			}
		}
	}
	
	
}
