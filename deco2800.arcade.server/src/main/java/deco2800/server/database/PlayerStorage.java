package deco2800.server.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

		ResultSet resultSet = null;
		Statement statement = null;
		try {
			resultSet = connection.getMetaData().getTables(null,
					null, "PLAYERS", null);
			if (!resultSet.next()) {
				statement = connection.createStatement();
				statement
						.execute("CREATE TABLE PLAYERS(playerID INT PRIMARY KEY,"
								+ "username VARCHAR(30) NOT NULL,"
								+ "name VARCHAR(30),"
								+ "email VARCHAR(30),"
								+ "program VARCHAR(30),"
								+ "bio VARCHAR(200),"
								+ "age VARCHAR(30))");
			}
		} catch (SQLException e) {
			 Logger logger = LoggerFactory.getLogger(PlayerStorage.class);
			 logger.error(e.getStackTrace().toString());
			throw new DatabaseException("Unable to create PLAYERS table.", e);
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
				 Logger logger = LoggerFactory.getLogger(PlayerStorage.class);
				 logger.error(e.getStackTrace().toString());
			}
		}
		initialised = true;
	}

	
	public void addPlayer(int playerID, String username, String name,
			String email, String program, String bio, String age) throws DatabaseException {
		Connection connection = Database.getConnection();
		Statement stmt = null;
		ResultSet resultSet = null;
		try {
			stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			resultSet = stmt.executeQuery("SELECT * FROM PLAYERS");
			// add player to database
			resultSet.moveToInsertRow();
			resultSet.updateInt("playerID", playerID);
			resultSet.updateString("username", username);
			resultSet.updateString("name", name);
			resultSet.updateString("email", email);
			resultSet.updateString("program", program);
			resultSet.updateString("bio", bio);
			resultSet.updateString("age", age);
			resultSet.insertRow();
		} catch (SQLException e) {
			 Logger logger = LoggerFactory.getLogger(PlayerStorage.class);
			 logger.error(e.getStackTrace().toString());
			 throw new DatabaseException(
						"Unable to add player to database", e);
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
				 Logger logger = LoggerFactory.getLogger(PlayerStorage.class);
				 logger.error(e.getStackTrace().toString());
			}
		}
	}
	
	/**
	 * Returns a list of player data given a playerID
	 * 
	 * @param playerID
	 * @return Returns List of player data such that: List.get(0) -> username;
	 *         List.get(1) -> name; List.get(2) -> email; List.get(3) ->
	 *         program; List.get(4) -> bio; List.get(5) -> age;
	 * 
	 * @throws DatabaseException
	 */
	public List<String> getPlayerData(Integer playerID) throws DatabaseException {
		List<String> data = new ArrayList<String>();

		if (!initialised) {
			initialise();
		}

		// Get a connection to the database
		Connection connection = Database.getConnection();

		Statement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery("SELECT * FROM PLAYERS");
			List<String> details = findPlayerInfo(playerID, resultSet);
			data.add(playerID.toString());
			data.add(details.get(0));
			data.add(details.get(1));
			data.add(details.get(2));
			data.add(details.get(3));
			data.add(details.get(4));
			data.add(details.get(5));
			return data;
		} catch (SQLException e) {
			 Logger logger = LoggerFactory.getLogger(PlayerStorage.class);
			 logger.error(e.getStackTrace().toString());
			throw new DatabaseException(
					"Unable to get player information from database", e);
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
				 Logger logger = LoggerFactory.getLogger(PlayerStorage.class);
				 logger.error(e.getStackTrace().toString());
			}
		}
	}

	/**
	 * Searches through a ResultSet for a player's information.
	 * 
	 * @param playerID
	 *            The player's playerID
	 * @param results
	 *            The query result set
	 * @return Returns the player's information.
	 * @throws SQLException
	 */
	private List<String> findPlayerInfo(int playerID, ResultSet results)
			throws SQLException {
		List<String> details = new ArrayList<String>();
		while (results.next()) {
			Integer user = results.getInt("playerID");
			if (user.equals(playerID)) {
				details.add(results.getString("username"));
				details.add(results.getString("name"));
				details.add(results.getString("email"));
				details.add(results.getString("program"));
				details.add(results.getString("bio"));
				details.add(results.getString("age"));
				return details;
			}
		}

		return details;
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
	 * Sets a player's name to the provided name.
	 * 
	 * @param playerID
	 *            The player's playerID.
	 * @param newValue
	 *            The player's new name.
	 * @throws DatabaseException
	 */
	public void updateAge(int playerID, String newValue)
			throws DatabaseException {
		updateField(playerID, newValue, "age");
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
			 Logger logger = LoggerFactory.getLogger(PlayerStorage.class);
			 logger.error(e.getStackTrace().toString());
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
				 Logger logger = LoggerFactory.getLogger(PlayerStorage.class);
				 logger.error(e.getStackTrace().toString());
			}
		}
	}
}
