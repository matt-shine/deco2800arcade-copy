package deco2800.server.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;


public class PlayerGameStorage {
	private boolean initialised = false;
	
	/** 
	 * Creates the Games table and sets initialised to TRUE on completion
	 * 
	 * @throws DatabaseException
	 *				If SQLException occurs.
	 */
	public void initialise() throws DatabaseException {
		// Get a connection to the database
		Connection connection = Database.getConnection();
		
		try {
			ResultSet tableData = connection.getMetaData().getTables(null, null, "PLAYERGAMES", null);
			if (!tableData.next()) {
				Statement statement = connection.createStatement();
				statement.execute("CREATE TABLE PLAYERGAMES(playerID INT PRIMARY KEY," 
				+ "gameID INT PRIMARY KEY," 
				+ "Rating INT));");
				//Check if need anything for foreign keys
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException("Unable to create playergames table", e);
		}
	initialised = true;
	}
	
	/**
	 * Searches for all games belonging to a certain player
	 * @param playerID
	 * @return A Set containing the names of all gameIDs for a playerID
	 * @throws DatabaseException
	 */
	public Set<String> getPlayerGames(int playerID) throws DatabaseException {
		//Check whether or not the database has been intitialised
		if (!initialised){
			//Not initialised yet - initialise it
			initialise();
		}

		//Get a connection to the database
		Connection connection = Database.getConnection();

		Statement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery("SELECT * from PLAYERGAMES;");
			Set<String> result = findGames(playerID, resultSet);

			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException("Unable to get playergames from database", e);
		} finally {
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
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Searches through a ResultSet for a players games
	 * 
	 * @param playerID
	 * 				The playerID of the player
	 * @param results
	 * 				The query result set
	 * @return The players games
	 * @throws SQLException
	 */
	private Set<String> findGames(int playerID, ResultSet results) throws SQLException{
		Set<String> result = new HashSet<String>();
		while (results.next()){
			String user = results.getString("playerID");
			if (user.equals(playerID)){
				result.add(results.getString("gameID"));
			}
		}
		return result;
	}
	
	/**
	 * Removes a playergame row from the database
	 * @param playerID, gameID
	 * 				The ID for the player and game to be removed
	 * @throws DatabaseException
	 */
	public void removeGame(int playerID, int gameID) throws DatabaseException {
		Connection connection = Database.getConnection();
		Statement stmt = null;
		ResultSet resultSet = null;
		try {
			stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			resultSet = stmt.executeQuery("SELECT * FROM PLAYERGAMES");
			while (resultSet.next()) {
				int player = resultSet.getInt("playerID");
				int game = resultSet.getInt("gameID");
				if (player ==playerID && game ==gameID) {
					resultSet.deleteRow();
				}
			}
		} catch (SQLException e) {
			//TODO Auto-generated catch block
			e.printStackTrace();
			throw new DatabaseException("Unable to remove player game from database", e);
		} finally {
			//clean up JDBC objects
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (stmt != null){
					stmt.close();
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
	 * Adds a player game entry to the database with the given PlayerID and 
	 * gameID
	 * 
	 * @param playerID, gameID
	 * 				the IDs for the player and game to be added
	 * @throws DatabaseException
	 */
	public void addPlayerGames(int playerID, int gameID) throws DatabaseException {
		Connection connection = Database.getConnection();
		Statement stmt = null;
		ResultSet resultSet = null;
		try {
			stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			resultSet = stmt.executeQuery("SELECT * FROM PLAYERGAMES");
			resultSet.moveToInsertRow();
			resultSet.updateInt("playerID", playerID);
			resultSet.updateInt("gameID", gameID);
			resultSet.insertRow();
		} catch (SQLException e) {
			//TODO Auto-generated catch block
			e.printStackTrace();
			throw new DatabaseException("Unable to add player game to database", e);
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