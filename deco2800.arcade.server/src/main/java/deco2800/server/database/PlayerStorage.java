package deco2800.server.database;

import java.sql.Connection;
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
			ResultSet tableData = connection.getMetaData().getTables(null,
					null, "PLAYERS", null);
			if (!tableData.next()) {
				Statement statement = connection.createStatement();
				statement
						.execute("CREATE TABLE PLAYERS(playerID INT NOT NULL PRIMARY KEY,"
								+ "username VARCHAR(30) NOT NULL,"
								+ "name VARCHAR(30),"
								+ "email VARCHAR(30),"
								+ "style INT,"
								+ "program VARCHAR(30)," + "bio VARCHAR(200))");
			}
		} catch (SQLException e) {
			e.printStackTrace();
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

		Statement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery("SELECT * from PLAYERS;");
			data.add(findPlayerInfo(playerID, resultSet, "username"));
			data.add(findPlayerInfo(playerID, resultSet, "name"));
			data.add(findPlayerInfo(playerID, resultSet, "email"));
			data.add(findPlayerInfo(playerID, resultSet, "program"));
			data.add(findPlayerInfo(playerID, resultSet, "bio"));

			return data;
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
	 * Sets a player's age to the provided name.
	 * 
	 * @param playerID
	 *            The player's playerID.
	 * @param newValue
	 *            The player's new age.
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
     * Update an integer Field
     * @param playerID The Player's ID
     * @param newValue New value
     * @param field Field Name
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

	public void addPlayer(int parseInt, String string, String string2,
			String string3, String string4, String string5, String string6) {
		// TODO Auto-generated method stub
		
	}

    /**
     * Get the Player's style
     * @param playerID playerID
     * @return library style
     * @throws DatabaseException
     */
    public int getStyle(int playerID) throws DatabaseException{
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
            return findPlayerStyleInfo(playerID, resultSet, "colour");
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
     * Find player Style info
     * @param playerID player ID
     * @param results results set
     * @param field column name
     * @return int of style
     * @throws SQLException
     */
    private int findPlayerStyleInfo(int playerID, ResultSet results, String field) throws SQLException {
        while (results.next()) {
            String user = results.getString("playerID");
            if (user.equals(playerID)) {
                return results.getInt(field);
            }
        }
        return 0;
    }

    /**
     * Update Player's library colour
     * @param playerID Player ID
     * @param style style
     */
    public void updateStyle(int playerID, int style) throws DatabaseException {
        updateField(playerID, style, "style");
    }
}