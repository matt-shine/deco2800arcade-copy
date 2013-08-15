package deco2800.server.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Implements credits' storage of arcade games on web database server.
 *
 */
public class CreditStorage {


	/**
	 * Creates the Credits table and sets initialised to TRUE on completion
	 * 
	 * @throws	DatabaseException	If SQLException occurs. 
	 */
	public  void initialise() throws DatabaseException{

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
	}
	
	/**
	 * Check Users Credits
	 * 
	 * @param	username	String, username of arcade games
	 * @throws	DatabaseException	If SQLException occurs. 
	 */
	public Integer getUserCredits(String username) throws DatabaseException{
		
		// TODO: refactor to use playerID not username

		//Get a connection to the database
		Connection connection = Database.getConnection();

		Statement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery("SELECT * FROM CREDITS WHERE USERNAME='" + username + "'");
//			statement = connection.prepareStatement("SELECT * from CREDITS WHERE username=?");
//			statement.setString(1, username);
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
	private Integer findCreditsForUser(String username, ResultSet results) throws SQLException{
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
	
	/**
	 * Deduct a number of credits from the user's account
	 * @param username The user from whose account the credits should be deducted
	 * @param numCredits The number of credits to deduct
	 */
	public void deductUserCredits(String username, int numCredits) {
		//TODO implement me!
		throw new UnsupportedOperationException("Not yet implemented");
	}
	
	/**
	 * Add a number of credits to the user's account
	 * @param username The user to whose account the credits should be added
	 * @param numCredits The number of credits to add
	 * @throws DatabaseException 
	 */
	public void addUserCredits(String username, int numCredits) throws DatabaseException {
		Connection connection = Database.getConnection();
		Statement stmt = null;
		ResultSet resultSet = null;
		
		try {
			stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			resultSet = stmt.executeQuery("SELECT * FROM CREDITS WHERE USERNAME='" + username + "'");
			if (resultSet.next()) {
				int oldBalance = resultSet.getInt("CREDITS");
				resultSet.updateInt("CREDITS", oldBalance + numCredits);
				resultSet.updateRow();
			} else {
				throw new DatabaseException("Username has no balance");
			}
			if (resultSet.next()) {
				throw new DatabaseException("Two entries for username!");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
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
