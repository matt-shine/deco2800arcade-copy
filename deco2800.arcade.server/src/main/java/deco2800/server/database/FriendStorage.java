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
	
	/**
	 * Adds a player-friend relationship to the FRIENDS table
	 * 
	 * @param playerID
	 * 			The playerID of the player adding the friend
	 * @param friendID
	 * 			The playerID of the friend who is being added
	 * @throws DatabaseException
	 */
	public void addFriend(int playerID, int friendID) throws DatabaseException {
		Connection connection = Database.getConnection();
		Statement stmt = null;
		ResultSet resultSet = null;
		try {
			stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			resultSet = stmt.executeQuery("SELECT * FROM FRIENDS");
			// add friendID as a friend for playerID
			resultSet.moveToInsertRow();
			resultSet.updateInt("U1", playerID);
			resultSet.updateInt("U2", friendID);
			resultSet.insertRow();
			
			//add playerID as a friend for friendID
			resultSet.moveToInsertRow();
			resultSet.updateInt("U1", friendID);
			resultSet.updateInt("U2", playerID);
			resultSet.insertRow();
			resultSet.moveToCurrentRow();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			//clean up JDBC objects
			try {
				if (resultSet != null){
					resultSet.close();
				}
				if (stmt != null){
					stmt.close();
				}
				if (connection != null){
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void removeFriend(int playerID, int friendID) throws DatabaseException {
		Connection connection = Database.getConnection();
		Statement stmt = null;
		ResultSet resultSet = null;
		try {
			stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			resultSet = stmt.executeQuery("SELECT * FROM FRIENDS");
			while (resultSet.next()) {
				int player = resultSet.getInt("U1");
				int friend = resultSet.getInt("U2");
				// delete the two way relationship between player and friend
				if (player == playerID && friend == friendID) {
					resultSet.deleteRow();
				} if (friend == playerID && player == friendID){
					resultSet.deleteRow();
				}
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			//clean up JDBC objects
			try {
				if (resultSet != null){
					resultSet.close();
				}
				if (stmt != null){
					stmt.close();
				}
				if (connection != null){
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
}
