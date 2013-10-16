package deco2800.server.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * PlayerStorage deals with database access for player data.
 * 
 * @author Leggy (Lachlan Healey: lachlan.j.healey@gmail.com)
 * 
 */
public class PlayerStorage {

	/*
	 * TODO: Autonumbering of playerIDs
	 * 
	 * TODO: Prepared statements for queries
	 * 
	 * TODO: Unit Tests.
	 */

	private boolean initialised = false;

	/**
	 * Creates the PLAYERS table and sets initialised to TRUE on completion
	 * 
	 * @throws DatabaseException
	 *             If SQLException occurs.
	 */
	public void initialise() throws DatabaseException {

		// Get a connection to the database
		Connection connection = Database.getConnection();
		
		try {
			ResultSet tableData = connection.getMetaData().getTables(null, null, "PLAYERS", null);
						
			if (!tableData.next()) {
				Statement statement = connection.createStatement();
				statement.execute("CREATE TABLE PLAYERS(playerID INT PRIMARY KEY,"
								+ "username VARCHAR(30) NOT NULL,"
								+ "name VARCHAR(30),"
								+ "email VARCHAR(30),"
								+ "program VARCHAR(30)," + "bio VARCHAR(200))");
			}
			
		} catch (SQLException e) {
			//e.printStackTrace();
			throw new DatabaseException("Unable to create players table", e);
		}
		initialised = true;
	}

	/**
	 * Returns a list of player data given a playerID
	 * 
	 * @param playerID
	 * @return Returns List of player data such that: List.get(0) -> username;
	 *         List.get(1) -> name; List.get(2) -> email; List.get(3) ->
	 *         program; List.get(4) -> bio;
	 * 
	 * @throws DatabaseException
	 */
	public List<String> getPlayerData(int playerID) throws DatabaseException {
		List<String> data = new ArrayList<String>();

		if (!initialised) {
			initialise();
		}

		// Get a connection to the database
		Connection connection = Database.getConnection();

		//Statement statement = null;
		PreparedStatement ps = null;
		ResultSet resultSet = null;
		try {
			//statement = connection.createStatement();
			/*
			resultSet = statement.executeQuery("SELECT * FROM PLAYERS");
			data.add(findPlayerInfo(playerID, resultSet, "username"));
			data.add(findPlayerInfo(playerID, resultSet, "name"));
			data.add(findPlayerInfo(playerID, resultSet, "email"));
			data.add(findPlayerInfo(playerID, resultSet, "program"));
			data.add(findPlayerInfo(playerID, resultSet, "bio"));
			*/
			//resultSet = statement.executeQuery("SELECT * FROM PLAYERS WHERE playerID ="+playerID);
			
			/**
			 * What happened here? Excellent question! Read on:
			 * 
			 * The default statement was upgraded to a preparedStatement and the method below
			 * can effectively replace the findPlayerInfo(...) method:
			 * 
			 * The things that were changed have been left in //comments
			 */
			
			ps = connection.prepareStatement("SELECT * FROM PLAYERS WHERE playerID = ?");
			ps.setInt(1, playerID);
			resultSet = ps.executeQuery();
			while (resultSet.next()){
				data.add(resultSet.getString("username"));
				data.add(resultSet.getString("name"));
				data.add(resultSet.getString("email"));
				data.add(resultSet.getString("program"));
				data.add(resultSet.getString("bio"));
			}

			return data;
		} catch (SQLException e) {
			//e.printStackTrace();
			throw new DatabaseException(
					"Unable to get player informtion from database", e);
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				//if (statement != null) {
				//	statement.close();
				//}
				if (ps != null) {
					ps.close();
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
	 * Searches through a ResultSet for for a player's information.
	 * 
	 * @param playerID
	 *            The player's playerID
	 * @param results
	 *            The query result set
	 * @param field
	 *            The field to search for (ie email, program, username, bio,
	 *            program).
	 * @return Returns the player's information.
	 * @throws SQLException
	 */
	private String findPlayerInfo(int playerID, ResultSet results, String field)
			throws SQLException {
		String result = null;
		
		while (results.next()) {
			String user = results.getString("playerID");
			if (user.equals(playerID)) {
				result = results.getString(field);
				return result;
			}
		}

		return result;
	}

	/**
	 * Sets a player's username to the provided name.
	 * 
	 * @param playerID
	 *            The player's playerID.
	 * @param newValue
	 *            The player's new username.
	 * @throws DatabaseException
	 */
	public void updateUsername(int playerID, String newValue)
			throws DatabaseException {
		updateField(playerID, newValue, "username");
	}

	/**
	 * Sets a player's email to the provided address.
	 * 
	 * @param playerID
	 *            The player's playerID.
	 * @param newValue
	 *            The player's new email.
	 * @throws DatabaseException
	 */
	public void updateEmail(int playerID, String newValue)
			throws DatabaseException {
		updateField(playerID, newValue, "email");
	}

	/**
	 * Sets a player's program to the provided string.
	 * 
	 * @param playerID
	 *            The player's playerID.
	 * @param newValue
	 *            The player's new program.
	 * @throws DatabaseException
	 */
	public void updateProgram(int playerID, String newValue)
			throws DatabaseException {
		updateField(playerID, newValue, "program");
	}

	/**
	 * Sets a player's bio to the provided string.
	 * 
	 * @param playerID
	 *            The player's playerID.
	 * @param newValue
	 *            The player's new bio.
	 * @throws DatabaseException
	 */
	public void updateBio(int playerID, String newValue)
			throws DatabaseException {
		updateField(playerID, newValue, "bio");
	}

	/**
	 * Sets a player's name to the provided name.
	 * 
	 * @param playerID
	 *            The player's playerID.
	 * @param newValue
	 *            The player's new name.
	 * @throws DatabaseException
	 */
	public void updateName(int playerID, String newValue)
			throws DatabaseException {
		updateField(playerID, newValue, "name");
	}

	/**
	 * Updates a database field, given a playerID, the field to be updated and
	 * the new value for that field.
	 * 
	 * @param playerID
	 *            The player's playerID.
	 * @param newValue
	 *            The field's new value.
	 * @param field
	 *            The player's field to be updated.
	 * @throws DatabaseException
	 */
	private void updateField(int playerID, String newValue, String field)
			throws DatabaseException {
		if (!initialised) {
			initialise();
		}

		// Get a connection to the database
		Connection connection = Database.getConnection();

		Statement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery("UPDATE PLAYER SET " + field
					+ " = " + newValue + " WHERE playerID = " + playerID + ";");

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException(
					"Unable to update player username in database", e);
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
	 * TEMPORARY: Add's a player to the database
	 * @param playerID
	 * @param username
	 * @throws DatabaseException
	 */
	public void addPlayer(int playerID, String username, String name, String email, String program, String bio) throws DatabaseException {
		// Get a connection to the database
		Connection connection = Database.getConnection();
		
		try{
			String insert = "INSERT INTO PLAYERS (playerID, username, name, email, program, bio) VALUES (?, ?, ?, ?, ?, ?)";
			PreparedStatement statement = connection.prepareStatement(insert);
			statement.setInt(1, playerID);
			statement.setString(2, username);
			statement.setString(3, name);
			statement.setString(4, email);
			statement.setString(5, program);
			statement.setString(6, bio);
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new DatabaseException("There was an error adding the player to the database", e);
		}
	}
}
