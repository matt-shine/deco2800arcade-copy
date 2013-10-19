package deco2800.arcade.server.database;

import java.sql.SQLException;
import deco2800.server.database.DatabaseException;
import deco2800.server.database.GameStorage;
import org.junit.Assert;
import org.junit.Test;


//This should work in the arcade build, else it is incorrect.
/**
 * JUnit tests for gamestorage
 * If these tests fail it probably means your database has been cached
 *  and isn't initialising with the proper columns.
 *      - Try Ending any server resources/processes,
 *      - Restarting your machine
 * @author Steven Sheriff
 * @see deco2800.arcade.server.database.GameStorage
 */
public class TestGameStorage {

	//@Test
	public void testBoundaries() throws DatabaseException {
		GameStorage gs = new GameStorage();
		gs.initialise();
		Assert.assertEquals("Tennis, without that annoying 3rd dimension!",gs.getGameDescription(0));
		Assert.assertEquals("Pong",gs.getGameName(0));
		Assert.assertEquals(0,gs.getGamePrice(0));
		Assert.assertEquals("Pong",gs.getGameID(0));
		Assert.assertEquals("",gs.getIconPath(0));
		Assert.assertEquals("N/A",gs.getGameDescription(15));
		Assert.assertEquals("Jungle Jump",gs.getGameName(15));
		Assert.assertEquals(0,gs.getGamePrice(15));
		Assert.assertEquals("junglejump",gs.getGameID(15));
		Assert.assertEquals("",gs.getIconPath(15));
	}
	
	@Test
	public void testOutOfBound() throws SQLException, DatabaseException {
		GameStorage gs = new GameStorage();
		gs.initialise();
		Assert.assertEquals(null,gs.getGameDescription(21));
		Assert.assertEquals(null,gs.getGameName(21));
		Assert.assertEquals(0,gs.getGamePrice(21));
		Assert.assertEquals(null,gs.getGameID(21));
		Assert.assertEquals(null,gs.getIconPath(21));
		
	}
}
