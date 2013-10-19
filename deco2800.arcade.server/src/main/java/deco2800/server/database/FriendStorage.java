package deco2800.server.database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * FriendStorage handles database access for Friends and Friend Request data
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
	 * @throws SQLException 
	 */
	public void initialise() throws DatabaseException {
		
		//Get a connection to the database
		Connection connection = Database.getConnection();

		try {
			ResultSet tableData = connection.getMetaData().getTables(null, null, "FRIENDS", null);
			
			if (!tableData.next()){
				Statement statement = connection.createStatement();
				String sql = "CREATE TABLE FRIENDS "
						+ "(U1 INT NOT NULL,"
						+ "U2 INT NOT NULL,"
						+ "STATUS INT NOT NULL,"
						+ "BLOCKED INT NOT NULL,"
						+ "PRIMARY KEY(U1, U2),"
						+ "FOREIGN KEY (U1) REFERENCES PLAYERS(playerID),"
						+ "FOREIGN KEY (U2) REFERENCES PLAYERS(playerID))";
				statement.executeUpdate(sql);
			}
			
		} catch (SQLException e) {
			//e.printStackTrace();
			throw new DatabaseException("Unable to create friends table", e);
		}
		initialised = true;
	}
	
	/**
	 * Returns a list of friends for the given playerID
	 * 
	 * @param playerID
	 * @return 
	 * 		Returns a list of playerIDs which represent the player's friends. 
	 * @throws DatabaseException
	 */
	public List<Integer> getFriendsList(int playerID) throws DatabaseException {
		if (!initialised) {
			initialise();
		}

		// Get a connection to the database
		Connection connection = Database.getConnection();

		Statement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery("SELECT * FROM FRIENDS"
					+ " WHERE U1=" + playerID
					+ " AND STATUS=1");
			return findPlayers(playerID, resultSet);
		} catch (SQLException e) {
			 Logger logger = LoggerFactory.getLogger(FriendStorage.class);
			 logger.error(e.getStackTrace().toString());
			throw new DatabaseException(
					"Unable to get to get friends list from database", e);
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
				 Logger logger = LoggerFactory.getLogger(FriendStorage.class);
				 logger.error(e.getStackTrace().toString());
			}
		}
	}
	
	/**
	 * Returns a list of friend invites for the given playerID.
	 * 
	 * @param playerID
	 * 			The playerID of the player whose friend invite list is being returned.
	 * @return 
	 * 		Returns a list of playerIDs of players who have sent friend invites
	 * 		which the player has not yet responded to.
	 * @throws DatabaseException
	 */
	public List<Integer> getFriendInviteList(int playerID) throws DatabaseException {
		if (!initialised) {
			initialise();
		}

		// Get a connection to the database
		Connection connection = Database.getConnection();

		Statement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery("SELECT * FROM FRIENDS" + 
					" WHERE U1=" + playerID + 
					" AND STATUS=0" +
					" AND BLOCKED=0");
			return findPlayers(playerID, resultSet);
		} catch (SQLException e) {
			 Logger logger = LoggerFactory.getLogger(FriendStorage.class);
			 logger.error(e.getStackTrace().toString());
			throw new DatabaseException(
					"Unable to get friend invite information from database", e);
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
				 Logger logger = LoggerFactory.getLogger(FriendStorage.class);
				 logger.error(e.getStackTrace().toString());
			}
		}
	}
	
	/**
	 * Returns a list of of players that the current Player has blocked.
	 * 
	 * @param playerID
	 * 			The current player who blocked list is being returned.
	 * @return
	 * 			The list of blocked players of the player represented by the playerID.
	 * @throws DatabaseException
	 */
	public List<Integer> getBlockedList(int playerID) throws DatabaseException {
		if (!initialised) {
			initialise();
		}

		// Get a connection to the database
		Connection connection = Database.getConnection();

		Statement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery("SELECT * FROM FRIENDS" 
											+" WHERE U1=" + playerID 
											+ " AND BLOCKED=1");
			return findPlayers(playerID, resultSet);
		} catch (SQLException e) {
			 Logger logger = LoggerFactory.getLogger(FriendStorage.class);
			 logger.error(e.getStackTrace().toString());
			throw new DatabaseException(
					"Unable to get player's blocked list from database", e);
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
				 Logger logger = LoggerFactory.getLogger(FriendStorage.class);
				 logger.error(e.getStackTrace().toString());
			}
		}
	}
	
	/**
	 * Searches through a ResultSet for a player's friend or 
	 * blocked player information.
	 * 
	 * @param playerID
	 *            The playerID of the player
	 * @param results
	 *            The query result set
	 * @return Returns the player's friends.
	 * @throws SQLException
	 */
	private List<Integer> findPlayers(int playerID, ResultSet results)
			throws SQLException {
		ArrayList<Integer> players = new ArrayList<Integer>();
		while (results.next()) {
			int user1 = results.getInt("U1");
			int user2 = results.getInt("U2");
			if (user1 == playerID) {
				players.add(user2);
			}
		}
		return players;
	}
	
	/**
	 * Blocks U2(player) from U1(playerID).
	 * 
	 * @param playerID
	 * 			The player who is blocking another player.
	 * @param player
	 * 			The player being blocked.
	 * @throws DatabaseException
	 */
	public void blockPlayer(int playerID, int player) throws DatabaseException{
		Connection connection = Database.getConnection();
		Statement stmt = null;
		ResultSet resultSet = null;
		try {
			stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			resultSet = stmt.executeQuery("SELECT * FROM FRIENDS" 
										+ " WHERE U1=" + playerID
										+ " AND U2=" + player );
			// if player-friend relationship exists, block the player
			if (resultSet.next()) {
				resultSet.updateInt("BLOCKED", 1);
				resultSet.updateRow();
			} else { // else add new player-player relationships with playerID:player blocked
				resultSet.moveToInsertRow();
				resultSet.updateInt("U1", playerID);
				resultSet.updateInt("U2", player);
				resultSet.updateInt("STATUS", 0);
				resultSet.updateInt("BLOCKED", 1);
				resultSet.insertRow();
				
				resultSet.moveToInsertRow();
				resultSet.updateInt("U1", player);
				resultSet.updateInt("U2", playerID);
				resultSet.updateInt("STATUS", 0);
				resultSet.updateInt("BLOCKED", 0);
				resultSet.insertRow();
				resultSet.moveToCurrentRow();
			}
		} catch (SQLException e) {
			 Logger logger = LoggerFactory.getLogger(FriendStorage.class);
			 logger.error(e.getStackTrace().toString());
			throw new DatabaseException("Unable to block player in database.", e);
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
				 Logger logger = LoggerFactory.getLogger(FriendStorage.class);
				 logger.error(e.getStackTrace().toString());
			}
		}
	}
	
	/**
	 * Unblocks U2(player) from U1(playerID).
	 * 
	 * @param playerID
	 * 			The player who is blocking another player.
	 * @param player
	 * 			The player being blocked.
	 * @throws DatabaseException
	 */
	public void unblockPlayer(int playerID, int player) throws DatabaseException{
		Connection connection = Database.getConnection();
		Statement stmt = null;
		ResultSet resultSet = null;
		try {
			stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			resultSet = stmt.executeQuery("SELECT * FROM FRIENDS" 
										+ " WHERE U1=" + playerID
										+ " AND U2=" + player);
			// if player-friend relationship exists, unblock the player
			if (resultSet.first()) {
				resultSet.updateInt("BLOCKED", 0);
				resultSet.updateRow();
			} 
		} catch (SQLException e) {
			 Logger logger = LoggerFactory.getLogger(FriendStorage.class);
			 logger.error(e.getStackTrace().toString());
			throw new DatabaseException("Unable to unblock player in database.", e);
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
				 Logger logger = LoggerFactory.getLogger(FriendStorage.class);
				 logger.error(e.getStackTrace().toString());
			}
		}
	}
	
		
	/**
	 * Adds a player-friend relationship to the FRIENDS table with the status
	 * of 1
	 * 
	 * @param playerID
	 * 			The playerID of the player adding the friend
	 * @param friendID
	 * 			The playerID of the friend who is being added
	 * @throws DatabaseException
	 */
	public void addFriendRequest(int playerID, int friendID) throws DatabaseException {
		Connection connection = Database.getConnection();
		Statement stmt = null;
		ResultSet resultSet = null;
		try {
			stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			resultSet = stmt.executeQuery("SELECT * FROM FRIENDS");
			
			// TODO CHECK TO SEE IF PLAYER-PLAYER RELATIONSHIPS ALREADY EXISTS
			
			// add friendID as a friend for playerID
			resultSet.moveToInsertRow();
			resultSet.updateInt("U1", playerID);
			resultSet.updateInt("U2", friendID);
			resultSet.updateInt("STATUS", 1);
			resultSet.updateInt("BLOCKED", 0);
			resultSet.insertRow();
			
			//add playerID as a friend for friendID
			resultSet.moveToInsertRow();
			resultSet.updateInt("U1", friendID);
			resultSet.updateInt("U2", playerID);
			resultSet.updateInt("STATUS", 0);
			resultSet.updateInt("BLOCKED", 0);
			resultSet.insertRow();
			resultSet.moveToCurrentRow();
			
		} catch (SQLException e) {
			 Logger logger = LoggerFactory.getLogger(FriendStorage.class);
			 logger.error(e.getStackTrace().toString());
			throw new DatabaseException("Unable to add friend request to database.", e);
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
				 Logger logger = LoggerFactory.getLogger(FriendStorage.class);
				 logger.error(e.getStackTrace().toString());
			}
		}
	}
	
	/**
	 * Accept the friend request that friendID has sent playerID
	 * 
	 * @param playerID
	 * 			The playerID of the player accepting the friend request.
	 * @param friendID
	 * 			The playerID of the player who sent the friend request.
	 * @throws DatabaseException
	 */
	public void acceptFriendRequest(int playerID, int friendID) throws DatabaseException{
		Connection connection = Database.getConnection();
		Statement stmt = null;
		ResultSet resultSet = null;
		try {
			stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			// retrieve the unaccepted friend request from the database
			resultSet = stmt.executeQuery("SELECT * FROM FRIENDS" 
										+ " WHERE U1=" + playerID
										+ " AND U2=" + friendID
										+ " AND STATUS=0"
										+ " AND BLOCKED=0");
			// accept the friend request
			if (resultSet.next()) {
				resultSet.updateInt("STATUS", 1);
				resultSet.updateRow();
			}
		} catch (SQLException e) {
			 Logger logger = LoggerFactory.getLogger(FriendStorage.class);
			 logger.error(e.getStackTrace().toString());
			throw new DatabaseException(
					"Unable to accept friend request in database", e);
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
				 Logger logger = LoggerFactory.getLogger(FriendStorage.class);
				 logger.error(e.getStackTrace().toString());
			}
		}
	}
	
	/**
	 * Remove the specified player-friend relationship from the database.
	 * Can be used to remove either confirmed friends or pending invites.
	 * 
	 * @param playerID
	 * 			The playerID of the player who is removing their friend/friend request.
	 * @param friendID
	 * 			The playerID of the friend who is being removed by the player.
	 * @throws DatabaseException
	 */
	public void removeFriend(int playerID, int friendID) throws DatabaseException {
		Connection connection = Database.getConnection();
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			resultSet = statement.executeQuery("SELECT * FROM FRIENDS");
			while (resultSet.next()) {
				int user1 = resultSet.getInt("U1");
				int user2 = resultSet.getInt("U2");
				// delete the two way relationship between player and friend
				if (user1 == playerID && user2 == friendID) {
					resultSet.deleteRow();
				} if (user2 == playerID && user1 == friendID){
					resultSet.deleteRow();
				}
			}
		} catch (SQLException e) {
			 Logger logger = LoggerFactory.getLogger(FriendStorage.class);
			 logger.error(e.getStackTrace().toString());
		} finally {
			//clean up JDBC objects
			try {
				if (resultSet != null){
					resultSet.close();
				}
				if (statement != null){
					statement.close();
				}
				if (connection != null){
					connection.close();
				}
			} catch (SQLException e) {
				 Logger logger = LoggerFactory.getLogger(FriendStorage.class);
				 logger.error(e.getStackTrace().toString());
			}
		}
	}
	
	/**
	 * Checks if player1 has blocked player2.
	 * @param player1
	 * 			The playerID of the blocker.
	 * @param player2
	 * 			The playerID of the blockee.
	 * @throws DatabaseException
	 */
	public boolean isBlocked(int player1, int player2) throws DatabaseException {
		
		ArrayList<Integer> blocked = (ArrayList<Integer>) getBlockedList(player1);
		return blocked.contains(player2);
	}
	
	/**
	 * Checks if player1 is friends with player2.
	 * @param player1
	 * 			The playerID of the befriender.
	 * @param player2
	 * 			The playerID of the friend.
	 * @throws DatabaseException
	 */
	public boolean isFriends(int player1, int player2) throws DatabaseException {
		
		ArrayList<Integer> friends = (ArrayList<Integer>) getFriendsList(player1);
		return friends.contains(player2);
	}
}
