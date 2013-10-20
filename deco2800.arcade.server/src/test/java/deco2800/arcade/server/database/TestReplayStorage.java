package deco2800.arcade.server.database;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import deco2800.server.database.DatabaseException;
import deco2800.server.database.ReplayStorage;

public class TestReplayStorage {
	private static IDatabaseTester databaseTester; //manage connections to the database
	private ReplayStorage replayStorage; //storage object to test
	
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
	 * Retrieve the specified from an XML file
	 * @return an initialised IDataSet
	 * @throws DataSetException if the dataset could not be created
	 * @throws IOException if the file could not be loaded
	 */
	private IDataSet getDataSet() throws DataSetException, IOException {
		URL url = TestReplayStorage.class.getClassLoader().getResource("TestReplayStorage.xml");
		FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
		builder.setColumnSensing(true);
		return builder.build(url);
	}
	
	/**
	 * Create a new replay storage and initialise it, load the data set and
	 * initialise it. 
	 * This is set to be called before each test
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		replayStorage = new ReplayStorage();
		replayStorage.initialise();
        
		IDataSet ds = getDataSet();
        databaseTester.setDataSet(ds);
        databaseTester.onSetup();
	}
	
	/**
	 * Clean up the database tester. Called after every test to
	 * reset the test database.
	 * @throws Exception
	 */
	@After
	public void  tearDown() throws Exception {
		databaseTester.onTearDown();
	}
	
	/**
	 * Test getting the list of game IDs from the database.
	 * Creates an expected list of games given the initial
	 * dataset, and compares this to the list of game IDs returned
	 * by the getGameIds() method. Results are sorted as no
	 * order is explicitly required.
	 * 
	 * @throws DatabaseException if the query could not be executed
	 */
//	@Test
//	public void testGetGameIds() throws DatabaseException {
//		List<String> actualGames = new LinkedList<String>();
//		actualGames.add( "tetris" );
//		actualGames.add( "pong" );
//		Collections.sort( actualGames );
//		
//		List<String> dbGames = replayStorage.getGameIds();
//		Collections.sort( dbGames );
//		
//		Assert.assertEquals( actualGames, dbGames );
//	}
	
	/**
	 * Tests getting the current list of sessions for a game.
	 * Compares the expected list of sessions given the default
	 * dataset to that returned by getSessionsForGame( id ),
	 * once again sorting as order is not explicitly required.
	 * 
	 * @throws DatabaseException if the query could not be executed
	 */
//	@Test
//	public void testGetSessionsForGame() throws DatabaseException {
//		List<String> actualSessions = new LinkedList<String>();
//		actualSessions.add( "1, true, max, 12407120487, Comment" );
//		actualSessions.add( "2, false, ben, 124071204879, Another comment" );
//		Collections.sort( actualSessions );
//		
//		List<String> dbSessions = replayStorage.getSessionsForGame( "pong" );
//		Collections.sort( dbSessions );
//		
//		Assert.assertEquals( actualSessions, dbSessions );
//	}
//	
	/**
	 * Tests the ending of a session that is currently recording.
	 * Uses a session that is known to be recording, checks that 
	 * this is indeed the case, attempts to endRecording( id ) 
	 * and then checks that the session is now ended.
	 * 
	 * @require ReplayStorage.getSessionsForGame( id ) works, defined
	 * 			as passing the testGetSessionsForGame() test
	 * @throws DatabaseException if the query could not be executed
	 */
//	@Test
//	public void testEndRecording() throws DatabaseException {
//		List<String> initialSessions = new LinkedList<String>();
//		initialSessions.add( "3, true, max, 1240712879, DECO is fun" );
//		
//		Assert.assertEquals( initialSessions,  replayStorage.getSessionsForGame( "tetris" ) );
//		
//		replayStorage.endRecording( 3 );
//		
//		List<String> afterSessions = new LinkedList<String>();
//		afterSessions.add( "3, false, max, 1240712879, DECO is fun" );
//
//		Assert.assertEquals( afterSessions,  replayStorage.getSessionsForGame( "tetris" ) );
//	}

	/**
	 * Tests inserting a session for a given game.
	 * Adds a new session using insertSession( session info... ) and
	 * then checks the sessions returned is equal to the initial
	 * set of sessions returned plus the session just added.
	 * 
	 * @require ReplayStorage.getSessionsForGame( id ) works, defined
	 * 			as passing the testGetSessionsForGame() test
	 * @throws DatabaseException if the query could not be executed
	 */
//	@Test 
//	public void testInsertSession() throws DatabaseException {
//		List<String> expectedSessions = new LinkedList<String>();
//		expectedSessions.add( "3, true, max, 1240712879, DECO is fun" );
//		expectedSessions.add( "4, true, ben, 19823798, I <3 DECO" );
//		Collections.sort( expectedSessions );
//		
//		replayStorage.insertSession( "tetris", "ben", 19823798, "I <3 DECO" );
//		
//		List<String> dbSessions = replayStorage.getSessionsForGame( "tetris" );
//		Collections.sort( dbSessions );
//		
//		Assert.assertEquals( expectedSessions,  dbSessions );
//	}

	/**
	 * Tests removing a session from the database..
	 * Calls remove( session id ) and asserts that the list of 
	 * sessions is now the original list of sessions less the session
	 * that was just removed.
	 * 
	 * @require ReplayStorage.getSessionsForGame( id ) works, defined
	 * 			as passing the testGetSessionsForGame() test
	 * @throws DatabaseException if the query could not be executed
	 */
//	@Test 
//	public void testRemoveSession() throws DatabaseException {
//		List<String> expectedSessions = new LinkedList<String>();
//		expectedSessions.add( "1, true, max, 12407120487, Comment" );
//		
//		replayStorage.remove( 2 );
//
//		Assert.assertEquals( expectedSessions,  replayStorage.getSessionsForGame( "pong" ) );
//	}
//	
	/**
	 * Tests getting a list of the events for a replay by the replay ID.
	 * Creates an expected list of events given the known initial dataset,
	 * then compares this to the results from getReplay( id ). In this case,
	 * order is required in the output, so no sorting is performed.
	 * 
	 * @throws DatabaseException if the query could not be executed
	 */
//	@Test
//	public void testGetReplay() throws DatabaseException {
//		List<String> expectedEvents = new LinkedList<String>();
//		expectedEvents.add( "{\"nodeTime\":1750,\"type\":\"bat_move\",\"items\":{\"y_coord\":{\"type\":1,\"data\":193}}}" );
//		expectedEvents.add( "{\"nodeTime\":1795,\"type\":\"bat_move\",\"items\":{\"y_coord\":{\"type\":1,\"data\":205}}}" );
//		
//		Assert.assertEquals( expectedEvents,  replayStorage.getReplay( 1 ) );
//	}
	
	/**
	 * Tests inserting an event for a session.
	 * Constructs a list of events for the session from the known dataset,
	 * then creates a new event and both adds it to the list and inserts
	 * it into the database using insertEvent( eventID, eventIndex, 
	 * eventString ). Then asserts that the expected list is equal to the 
	 * replay fetched with getReplay( 1 ). Note that the results are not
	 * sorted, as ordering is important in this case.
	 * 
	 * @require ReplayStorage.getReplay( id ) works, defined
	 * 			as passing the testGetReplay() test
	 * @throws DatabaseException if the query could not be executed
	 */
//	@Test
//	public void testInsertEvent() throws DatabaseException {
//		List<String> expectedEvents = new LinkedList<String>();
//		expectedEvents.add( "{\"nodeTime\":1750,\"type\":\"bat_move\",\"items\":{\"y_coord\":{\"type\":1,\"data\":193}}}" );
//		expectedEvents.add( "{\"nodeTime\":1795,\"type\":\"bat_move\",\"items\":{\"y_coord\":{\"type\":1,\"data\":205}}}" );
//		
//		String toAdd = "{\"nodeTime\":1820,\"type\":\"ball_move\",\"items\":{\"x_coord\":{\"type\":1,\"data\":125}, \"y_coord\":{\"type\":1,\"data\":209}}}";
//		expectedEvents.add( toAdd );
//		
//		replayStorage.insertEvent( 1,  2,  toAdd );
//		
//		Assert.assertEquals( expectedEvents,  replayStorage.getReplay( 1 ) );
//	}
	
	/**
	 * Similar to testInsertEvent(), but inserts two events out of order.
	 * This simulates the situation where the network transmission
	 * introduces anomalies and the events reach the server out of order,
	 * and the server is required to perform the reordering itself.
	 * 
	 * @require ReplayStorage.getReplay( id ) works, defined
	 * 			as passing the testGetReplay() test
	 * @throws DatabaseException if the query could not be executed
	 */
//	@Test
//	public void testInsertEventOutOfOrder() throws DatabaseException {
//		List<String> expectedEvents = new LinkedList<String>();
//		expectedEvents.add( "{\"nodeTime\":1750,\"type\":\"bat_move\",\"items\":{\"y_coord\":{\"type\":1,\"data\":193}}}" );
//		expectedEvents.add( "{\"nodeTime\":1795,\"type\":\"bat_move\",\"items\":{\"y_coord\":{\"type\":1,\"data\":205}}}" );
//		
//		String toAddIndex2 = "{\"nodeTime\":1820,\"type\":\"ball_move\",\"items\":{\"x_coord\":{\"type\":1,\"data\":125}, \"y_coord\":{\"type\":1,\"data\":209}}}";
//		expectedEvents.add( toAddIndex2 );
//		
//		String toAddIndex3 = "{\"nodeTime\":1900,\"type\":\"ball_move\",\"items\":{\"x_coord\":{\"type\":1,\"data\":150}, \"y_coord\":{\"type\":1,\"data\":180}}}";
//		expectedEvents.add( toAddIndex3 );
//		
//		replayStorage.insertEvent( 1,  3,  toAddIndex3 );
//		replayStorage.insertEvent( 1,  2,  toAddIndex2 );
//		
//		Assert.assertEquals( expectedEvents,  replayStorage.getReplay( 1 ) );
//	}
}
