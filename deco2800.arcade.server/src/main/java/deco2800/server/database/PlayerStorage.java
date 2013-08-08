package deco2800.server.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PlayerStorage {
	
	//TODO: Autonumbering of playerIDs
	
	//TODO: Prepared statements for queries
	
	//TODO: Username update
	//TODO: Email update
	
	//TODO: Program get
	//TODO: Program update
	
	//TODO: Bio get
	//TODO: Bio update
	
	
	
	/**
	 * Creates the Credits table and sets initialised to TRUE on completion
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
				statement.execute("CREATE TABLE PLAYERS(playerID INT PRIMARY KEY,"
						+ "USERNAME VARCHAR(30) NOT NULL,"
						+ "NAME VARCHAR(30),"
						+ "EMAIL VARCHAR(30),"
						+ "PROGRAM VARCHAR(30),"
						+ "BIO VARCHAR(200));");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException("Unable to create players table", e);
		}
	}
	
	/**
	 * Searches for a player's username
	 * @param playerID	The player's playerID
	 * @return	Returns the player's username
	 * @throws DatabaseException
	 */
	public String getUsername(int playerID) throws DatabaseException{

		//Get a connection to the database
		Connection connection = Database.getConnection();

		Statement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery("SELECT * from PLAYERS;");
			String result = findUsername(playerID, resultSet);

			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException("Unable to get username from database", e);
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
	 * Searches through a ResultSet for a player's username.
	 * @param playerID	The player's playerID
	 * @param results	The query result set
	 * @return	Returns the player's username
	 * @throws SQLException
	 */
	private String findUsername(int playerID, ResultSet results) throws SQLException{
		String result = null;
		while (results.next()){
			String user = results.getString("playerID");
			if (user.equals(playerID)){
				result = results.getString("username");
				break;
			}
		}

		return result;
	}
	
	
	
	/**
	 * Searches for a player's email
	 * @param playerID	The player's playerID
	 * @return	Returns the player's email
	 * @throws DatabaseException
	 */
	public String getEmail(int playerID) throws DatabaseException{

		//Get a connection to the database
		Connection connection = Database.getConnection();

		Statement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery("SELECT * from PLAYERS;");
			String result = findEmail(playerID, resultSet);

			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException("Unable to get email from database", e);
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
	 * Searches through a ResultSet for a player's email.
	 * @param playerID	The player's playerID
	 * @param results	The query result set
	 * @return	Returns the player's email
	 * @throws SQLException
	 */
	private String findEmail(int playerID, ResultSet results) throws SQLException{
		String result = null;
		while (results.next()){
			String user = results.getString("playerID");
			if (user.equals(playerID)){
				result = results.getString("email");
				break;
			}
		}

		return result;
	}

}
