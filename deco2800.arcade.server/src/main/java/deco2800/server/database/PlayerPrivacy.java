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
 * PlayerPrivacy deals with database access for player privacy data.
 * 
 * @author Leggy (Lachlan Healey: lachlan.j.healey@gmail.com)
 * 
 */
public class PlayerPrivacy {

	public static final int FRIENDS_ONLY = 0;

	public static final int PUBLIC = 1;

	/*
	 * TODO: Contact Gabe's Disciples for TODO Information
	 */

	private boolean initialised = false;

	/**
	 * Creates the PLAYERPRIVACY table and sets initialised to TRUE on
	 * completion
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
					null, "PLAYERPRIVACY", null);
			if (!resultSet.next()) {
				statement = connection.createStatement();
				statement
						.execute("CREATE TABLE PLAYERPRIVACY( "
								+ "playerID INT NOT NULL, "
								+ "name INT NOT NULL, email INT, program INT, "
								+ "bio INT, "
								+ "games INT, achievements INT, "
								+ "age INT, "
								+ "PRIMARY KEY (playerID), "
								+ "FOREIGN KEY (playerID) REFERENCES PLAYER(playerID))");
			}
		} catch (SQLException e) {
			 Logger logger = LoggerFactory.getLogger(PlayerPrivacy.class);
			 logger.error(e.getStackTrace().toString());
			throw new DatabaseException(
					"Unable to create PLAYERPRIVACY table.", e);
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
				 Logger logger = LoggerFactory.getLogger(PlayerPrivacy.class);
				 logger.error(e.getStackTrace().toString());
			}
		}
		initialised = true;
	}

	/**
	 * Returns a list of player privacy data given a playerID
	 * 
	 * @param playerID
	 * @return Returns List of player data such that: List.get(0) -> name;
	 *         List.get(1) -> email; List.get(2) -> program; List.get(3) -> bio;
	 *         List.get(4) -> games; List.get(5) -> achievements;
	 * 
	 * @throws DatabaseException
	 */
	public List<Integer> getPlayerData(int playerID) throws DatabaseException {
		List<Integer> data = new ArrayList<Integer>();

		if (!initialised) {
			initialise();
		}

		// Get a connection to the database
		Connection connection = Database.getConnection();

		Statement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery("SELECT * from PLAYERPRIVACY");
			data.add(findPlayerInfo(playerID, resultSet, "name"));
			data.add(findPlayerInfo(playerID, resultSet, "email"));
			data.add(findPlayerInfo(playerID, resultSet, "program"));
			data.add(findPlayerInfo(playerID, resultSet, "bio"));
			data.add(findPlayerInfo(playerID, resultSet, "games"));
			data.add(findPlayerInfo(playerID, resultSet, "achievements"));
			data.add(findPlayerInfo(playerID, resultSet, "age"));

			return data;
		} catch (SQLException e) {
			 Logger logger = LoggerFactory.getLogger(PlayerPrivacy.class);
			 logger.error(e.getStackTrace().toString());
			throw new DatabaseException(
					"Unable to get player privacy informtion from database", e);
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
				 Logger logger = LoggerFactory.getLogger(PlayerPrivacy.class);
				 logger.error(e.getStackTrace().toString());
			}
		}
	}

	/**
	 * Searches through a ResultSet for a player's privacy information.
	 * 
	 * @param playerID
	 *            The player's playerID
	 * @param results
	 *            The query result set
	 * @param field
	 *            The field to search for (ie email, program, name, etc).
	 * @return Returns the player's privacy information.
	 * @throws SQLException
	 */
	private int findPlayerInfo(int playerID, ResultSet results, String field)
			throws SQLException {
		// Defaulting to friends only privacy setting
		int result = FRIENDS_ONLY;
		while (results.next()) {
			String user = results.getString("playerID");
			if (user.equals(playerID)) {
				result = results.getInt(field);
				return result;
			}
		}

		return result;
	}

	/**
	 * Sets a player's name privacy setting to the provided modes.
	 *  
	 * @param playerID
	 *            The player's playerID.
	 * @param privacySetting
	 *            True if information is public, false if information is for
	 *            friends only.
	 * @throws DatabaseException
	 */
	public void updateName(int playerID, boolean privacySetting)
			throws DatabaseException {


		if(privacySetting){
			updateField(playerID, PUBLIC, "name");
		} else {
			updateField(playerID, FRIENDS_ONLY, "name");
		}
	}


	/**
	 * Sets a player's email privacy setting to the provided modes.
	 *  
	 * @param playerID
	 *            The player's playerID.
	 * @param privacySetting
	 *            True if information is public, false if information is for
	 *            friends only.
	 * @throws DatabaseException
	 */
	public void updateEmail(int playerID, boolean privacySetting)
			throws DatabaseException {


		if(privacySetting){
			updateField(playerID, PUBLIC, "email");
		} else {
			updateField(playerID, FRIENDS_ONLY, "email");
		}
	}

	/**
	 * Sets a player's program privacy setting to the provided modes.
	 *  
	 * @param playerID
	 *            The player's playerID.
	 * @param privacySetting
	 *            True if information is public, false if information is for
	 *            friends only.
	 * @throws DatabaseException
	 */
	public void updateProgram(int playerID, boolean privacySetting)
			throws DatabaseException {


		if(privacySetting){
			updateField(playerID, PUBLIC, "program");
		} else {
			updateField(playerID, FRIENDS_ONLY, "program");
		}
	}

	/**
	 * Sets a player's bio privacy setting to the provided modes.
	 *  
	 * @param playerID
	 *            The player's playerID.
	 * @param privacySetting
	 *            True if information is public, false if information is for
	 *            friends only.
	 * @throws DatabaseException
	 */
	public void updateBio(int playerID, boolean privacySetting)
			throws DatabaseException {


		if(privacySetting){
			updateField(playerID, PUBLIC, "bio");
		} else {
			updateField(playerID, FRIENDS_ONLY, "bio");
		}
	}

	/**
	 * Sets a player's games privacy setting to the provided modes.
	 *  
	 * @param playerID
	 *            The player's playerID.
	 * @param privacySetting
	 *            True if information is public, false if information is for
	 *            friends only.
	 * @throws DatabaseException
	 */
	public void updateGames(int playerID, boolean privacySetting)
			throws DatabaseException {


		if(privacySetting){
			updateField(playerID, PUBLIC, "games");
		} else {
			updateField(playerID, FRIENDS_ONLY, "games");
		}
	}

	/**
	 * Sets a player's achievements privacy setting to the provided modes.
	 *  
	 * @param playerID
	 *            The player's playerID.
	 * @param privacySetting
	 *            True if information is public, false if information is for
	 *            friends only.
	 * @throws DatabaseException
	 */
	public void updateAchievements(int playerID, boolean privacySetting)
			throws DatabaseException {


		if(privacySetting){
			updateField(playerID, PUBLIC, "achievements");
		} else {
			updateField(playerID, FRIENDS_ONLY, "achievements");
		}
	}
	
	/**
	 * Sets a player's age privacy setting to the provided modes.
	 *  
	 * @param playerID
	 *            The player's playerID.
	 * @param privacySetting
	 *            True if information is public, false if information is for
	 *            friends only.
	 * @throws DatabaseException
	 */
	public void updateAge(int playerID, boolean privacySetting)
			throws DatabaseException {


		if(privacySetting){
			updateField(playerID, PUBLIC, "age");
		} else {
			updateField(playerID, FRIENDS_ONLY, "age");
		}
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
	private void updateField(int playerID, int newValue, String field)
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
			 Logger logger = LoggerFactory.getLogger(PlayerPrivacy.class);
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
				 Logger logger = LoggerFactory.getLogger(PlayerPrivacy.class);
				 logger.error(e.getStackTrace().toString());
			}
		}
	}
	
	/**
	 * Drops all tables from the database for clean testing.
	 */
	public void dropTables() throws DatabaseException {
		
		if (!initialised) {
			initialise();
		}
		
		Connection connection = Database.getConnection();
		
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery("DROP TABLE *;");

		} catch (SQLException e) {
			 Logger logger = LoggerFactory.getLogger(PlayerPrivacy.class);
			 logger.error(e.getStackTrace().toString());
			throw new DatabaseException(
					"Unable to drop tables from the database.", e);
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
				 Logger logger = LoggerFactory.getLogger(PlayerPrivacy.class);
				 logger.error(e.getStackTrace().toString());
			}
		}
	}
}