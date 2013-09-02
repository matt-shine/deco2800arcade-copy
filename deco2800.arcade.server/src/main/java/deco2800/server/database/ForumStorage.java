package deco2800.server.database;

import java.sql.Connection;

/**
 * This models the server storage for a forum.
 * 
 * @author Forum
 *
 */
public class ForumStorage {
	/**
	 * TODO 1. Create Initialization with test
	 */
	/* Fields */
	private boolean initialized = false;
	
	/**
	 * Initialise the DB with tables.
	 * 
	 * @throws	DatabaseException for any exceptions
	 */
	public static void initialise() throws DatabaseException {
		Connection conn;
		/* Create database connection */
		try {
			conn = Database.getConnection();
		} catch (Exception e) {
			throw new DatabaseException(e);
		}
		/* Create tables */
		
	}
	
	
}
