package deco2800.arcade.server.database;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.URL;
import java.util.*;

import org.dbunit.DBTestCase;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.database.*;
import org.dbunit.dataset.*;
import org.dbunit.dataset.filter.DefaultColumnFilter;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.mysql.MySqlDataTypeFactory;
import org.dbunit.ext.mysql.MySqlMetadataHandler;
import org.dbunit.operation.DatabaseOperation;
import org.junit.*;

import deco2800.arcade.model.Player;
import deco2800.server.database.DatabaseException;
import deco2800.server.database.FriendStorage;
import deco2800.server.database.PlayerStorage;


public class TestFriendStorage {
	private static IDatabaseTester databaseTester; //manage connections to the database
	private FriendStorage friendStorage; //storage object to test
	private PlayerStorage playerStorage;
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
	
	/**
	 * Retrieve the dataset from an XML file
	 * @return
	 * @throws DataSetException
	 * @throws IOException
	 */
	private IDataSet getDataSet() throws DataSetException, IOException {
		URL url = TestFriendStorage.class.getClassLoader().getResource("TestFriendStorage.xml");
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
		friendStorage = new FriendStorage();
        friendStorage.initialise();
		IDataSet ds = getDataSet();
		// databaseTester is null here, need to fix

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
	
	private void setUpDatabaseConfig(DatabaseConfig config) {
		config.setProperty(DatabaseConfig.PROPERTY_PRIMARY_KEY_FILTER, new ColumnFilter());
		Map<String, List<String>> tablePrimaryKeyMap = new HashMap<String, List<String>>();
		tablePrimaryKeyMap.put("FRIENDS", Arrays.asList(new String[] {"U1", "U2"}));
		tablePrimaryKeyMap.put("PLAYERS", Arrays.asList(new String[] {"playerID"}));
	}
	
	public Map<String, List<String>> getTablePrimaryKeyMap(){
		return tablePrimaryKeyMap;
	}
		
	@Test
	/**
	 * Tests getting the list of friends.
	 * @throws DatabaseException
	 */
	public void testGetFriendList() throws DatabaseException {
		List<Integer> friends = new ArrayList<Integer>();
		friends.add(2);
		assertEquals(friends, friendStorage.getFriendsList(1));
	}

	@Test
	/**
	 * Tests getting the list of friend invites.
	 * @throws DatabaseException
	 */
	public void testGetFriendInviteList() throws DatabaseException {
		List<Integer> friendInvites = new ArrayList<Integer>();
		friendInvites.add(1);
		assertEquals(friendInvites, friendStorage.getFriendInviteList(2));
	}
	
	@Test
	/**
	 * Tests getting the list of blocked players.
	 * @throws DatabaseException
	 */
	public void testGetBlockedList() throws DatabaseException {
		List<Integer> blockedList = new ArrayList<Integer>();
		blockedList.add(4);
		assertEquals(blockedList, friendStorage.getBlockedList(2));
	}
	
	@Test
	/**
	 * Tests blocking an existing player:player relationship.
	 * @throws DatabaseException
	 */
	public void testBlockExistingPlayerRelationship() throws DatabaseException {
		friendStorage.blockPlayer(1, 2);
		assertTrue(friendStorage.isBlocked(1, 2));
		assertFalse(friendStorage.isBlocked(2, 1));
	}
		
	@Test
	/**
	 * Tests blocking a non-existing player-player relationship.
	 * @throws DatabaseException
	 */
	public void testBlockNonExistingPlayerRelationship() throws DatabaseException {
		friendStorage.blockPlayer(2, 3);
		assertTrue(friendStorage.isBlocked(2, 3));
	}
	
	@Test
	/**
	 * Tests blocking and unblocking a player.
	 * @throws DatabaseException
	 */
	public void testUnblockPlayer() throws DatabaseException {
		friendStorage.blockPlayer(1, 2);
		assertTrue(friendStorage.isBlocked(1,2));
		friendStorage.unblockPlayer(1, 2);
		assertFalse(friendStorage.isBlocked(1, 2));
	}
	
	@Test
	/**
	 * Tests two players blocking each other, then unblocking each other.
	 * @throws DatabaseException
	 */
	public void testTwoPlayersBlockingThenUnblockingEachOther() throws DatabaseException {
		friendStorage.blockPlayer(1, 2);
		friendStorage.blockPlayer(2, 1);
		assertTrue(friendStorage.isBlocked(1, 2));
		assertTrue(friendStorage.isBlocked(2, 1));
		friendStorage.unblockPlayer(1, 2);
		assertFalse(friendStorage.isBlocked(1, 2));
		friendStorage.unblockPlayer(2, 1);
		assertFalse(friendStorage.isBlocked(2, 1));
	}
	
	@Test
	/**
	 * Tests adding a friend request.
	 * @throws DatabaseException
	 */
	public void testAddFriendRequest() throws DatabaseException {
		friendStorage.addFriendRequest(2, 3);
		assertTrue(friendStorage.isFriends(2, 3));
		assertFalse(friendStorage.isFriends(3,2));
	}
	
	@Test
	/**
	 * Tests accepting a friend request.
	 * @throws DatabaseException
	 */
	public void testAcceptFriendRequest() throws DatabaseException {
		friendStorage.acceptFriendRequest(2, 1);
		assertTrue(friendStorage.isFriends(1, 2));
		assertTrue(friendStorage.isFriends(2, 1));
	}
	
	@Test
	/** Tests remove friend.
	 * 
	 * @throws DatabaseException
	 */
	public void testRemoveFriend() throws DatabaseException {
		assertTrue(friendStorage.isFriends(2, 4));
		assertTrue(friendStorage.isFriends(4, 2));
		friendStorage.removeFriend(2, 4);	
		assertFalse(friendStorage.isFriends(2, 4));
		assertFalse(friendStorage.isFriends(4, 2));
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
