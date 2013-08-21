package deco2800.server.database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
						+ "FOREIGN KEY U1 REFERENCES PLAYER(playerID),"
						+ "FOREIGN KEY U2 REFERENCES PLAYER(playerID)");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException("Unable to create friends table", e);
		}
		initialised = true;
	}
	
	
	/**
	 * Returns a list of friends for the given playerID
	 * 
	 * @param playerID
	 * @return 
	 * 		Returns List of friends
	 * @throws DatabaseException
	 */
	public List<String> getFriendsList(int playerID) throws DatabaseException {
		if (!initialised) {
			initialise();
		}

		// Get a connection to the database
		Connection connection = Database.getConnection();

		Statement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery("SELECT * from PLAYERS;");
			return findFriends(playerID, resultSet);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException(
					"Unable to get player informtion from database", e);
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	

	/**
	 * Searches through a ResultSet for a player's friends.
	 * 
	 * @param playerID
	 *            The playerID of the player
	 * @param results
	 *            The query result set
	 * @return Returns the player's friends.
	 * @throws SQLException
	 */
	private ArrayList<String> findFriends(int playerID, ResultSet results)
			throws SQLException {
		ArrayList<String> result = new ArrayList<String>();
		while (results.next()) {
			String user1 = results.getString("U1");
			String user2 = results.getString("U2");
			if (user1.equals(playerID)) {
				result.add(user2);
			}
		}
		return result;
	}
}
