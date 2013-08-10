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

	// TODO: Autonumbering of playerIDs

	// TODO: Prepared statements for queries

	// TODO: Username update
	// TODO: name update
	// TODO: Email update

	// TODO: Program update

	// TODO: Bio update
	
	
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
						.execute("CREATE TABLE PLAYERS(playerID INT PRIMARY KEY,"
								+ "USERNAME VARCHAR(30) NOT NULL,"
								+ "NAME VARCHAR(30),"
								+ "EMAIL VARCHAR(30),"
								+ "PROGRAM VARCHAR(30)," + "BIO VARCHAR(200));");
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
		
		if(!initialised){
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
			throw new DatabaseException("Unable to get player informtion from database",
					e);
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
				break;
			}
		}

		return result;
	}
	
//	STILL WORKING ON THIS
//	public boolean updateUsername(int playerID, String newValue){ 
//		if(!initialised){
//			initialise();
//		}
//
//		// Get a connection to the database
//		Connection connection = Database.getConnection();
//
//		Statement statement = null;
//		ResultSet resultSet = null;
//		try {
//			statement = connection.createStatement();
//			resultSet = statement.executeQuery("UPDATE PLAYER SET username * from PLAYERS;");
//			data.add(findPlayerInfo(playerID, resultSet, "username"));
//			data.add(findPlayerInfo(playerID, resultSet, "name"));
//			data.add(findPlayerInfo(playerID, resultSet, "email"));
//			data.add(findPlayerInfo(playerID, resultSet, "program"));
//			data.add(findPlayerInfo(playerID, resultSet, "bio"));
//
//			return data;
//		} catch (SQLException e) {
//			e.printStackTrace();
//			throw new DatabaseException("Unable to get player informtion from database",
//					e);
//		} finally {
//			try {
//				if (resultSet != null) {
//					resultSet.close();
//				}
//				if (statement != null) {
//					statement.close();
//				}
//				if (connection != null) {
//					connection.close();
//				}
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//	}

}
