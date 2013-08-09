package deco2800.server.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PlayerStorage {

	private static boolean initialised = false;

	/**
	 * Creates the Credits table and sets initialised to TRUE on completion
	 * 
	 * @throws DatabaseException
	 *             If SQLException occurs.
	 */
	public static void initialise() throws DatabaseException {

		// Get a connection to the database
		Connection connection = Database.getConnection();

		try {
			ResultSet tableData = connection.getMetaData().getTables(null,
					null, "PLAYERS", null);
			if (!tableData.next()) {
				Statement statement = connection.createStatement();
				statement.execute("CREATE TABLE PLAYERS(playerID INT PRIMARY KEY,"
						+ "USERNAME VARCHAR(30) NOT NULL,"
						+ "NAME VARCHAR(30),"
						+ "EMAIL VARCHAR(30),"
						+ "PROGRAM VARCHAR(30),"
						+ "BIO VARCHAR(200))");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException("Unable to create players table", e);
		}
		initialised = true;
	}

}
