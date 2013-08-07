package deco2800.server.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Implements credits' storage of arcade games on web database server.
 *
 */
public class CreditStorage {

	private static boolean initialised = false;
	
	/**
	 * Creates the Credits table and sets initialised to TRUE on completion
	 * 
	 * @throws	DatabaseException	If SQLException occurs. 
	 */
	public static void initialise() throws DatabaseException{

		//Get a connection to the database
		Connection connection = Database.getConnection();

		try {
			ResultSet tableData = connection.getMetaData().getTables(null, null, "CREDITS", null);
			if (!tableData.next()){
				Statement statement = connection.createStatement();
				statement.execute("CREATE TABLE CREDITS(id INT PRIMARY KEY," +
						"USERNAME VARCHAR(30) NOT NULL," +
						"CREDITS INT NOT NULL)");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException("Unable to create credits table", e);
		}
		initialised = true;
	}
	
	/**
	 * Check Users Credits
	 * 
	 * @param	username	String, username of arcade games
	 * @throws	DatabaseException	If SQLException occurs. 
	 */
	public static Integer getUserCredits(String username) throws DatabaseException{

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
			resultSet = statement.executeQuery("SELECT * from CREDITS");
			Integer result = findCreditsForUser(username, resultSet);
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException("Unable to get user credits from database", e);
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
	 * Returns credits where username matches String username given
	 * 
	 * @param	String username, ResultSet results
	 * @throws	SQLException
	 * @return	Integer result
	 */
	private static Integer findCreditsForUser(String username, ResultSet results) throws SQLException{
		Integer result = null;
		while (results.next()){
			String user = results.getString("username");
			if (user.equals(username)){
				result = results.getInt("credits");
				break;
			}
		}

		return result;
	}
}
