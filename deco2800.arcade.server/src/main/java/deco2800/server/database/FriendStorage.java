package deco2800.server.database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * FriendStorage handles database access for Friends List data
 * 
 * @author iamwinrar (Andrew Cleland: andrew.cleland3@gmail.com)
 *
 */

public class FriendStorage {
	
	private boolean initialised = false;
	
	/**
	 * Creates the FRIENDS table and sets initialised to true upon completion.
	 * 
	 * @throws DatabaseException 
	 * 		If SQLException occurs
	 */
	public void initialise() throws DatabaseException {
		
		//Get a connection to the database
		Connection connection = Database.getConnection();

		try {
			ResultSet tableData = connection.getMetaData().getTables(null, null, "FRIENDS", null);
			if (!tableData.next()){
				Statement statement = connection.createStatement();
				statement.execute("CREATE TABLE FRIENDS(U1 INT NOT NULL,"
						+ "U2 INT NOT NULL," 
						+ "PRIMARY KEY(U1, U2),"
						+ "FOREIGN KEY U1 REFERENCES FRIENDS(playerID),"
						+ "FOREIGN KEY U2 REFERENCES FRIENDS(playerID)");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException("Unable to create friends table", e);
		}
		initialised = true;
	}
	
	
}
