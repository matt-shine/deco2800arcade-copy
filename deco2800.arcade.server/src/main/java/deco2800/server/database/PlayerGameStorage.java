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
			ResultSet tableData = connection.getMetaData().getTables(null, null, "USERGAMES", null);
			if (!tableData.next()) {
				Statement statement = connection.createStatement();
				statement.execute("CREATE TABLE USERGAMES(playerID INT PRIMARY KEY," 
				+ "gameID INT PRIMARY KEY," 
				+ "Rating INT));");
				//Check if need anything for foreign keys
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException("Unable to create usergames table", e);
		}
	initialised = true;
	}
	
	/**
	 * Searches for all games belonging to a certain player
	 * @param playerID
	 * @return
	 * @throws DatabaseException
	 */
	public Set<String> getUserGames(int playerID) throws DatabaseException {
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
			resultSet = statement.executeQuery("SELECT * from USERGAMES;");
			Set<String> result = findGames(playerID, resultSet);

			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException("Unable to get user games from database", e);
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
}