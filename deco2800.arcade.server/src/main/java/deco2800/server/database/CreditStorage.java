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
	
	private boolean initialised = false;


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
		initialised = true;
	}
	
	/**
	 * Check Users Credits
	 * 
	 * @param	playerID	Int, playerID of player
	 * @throws	DatabaseException	If SQLException occurs. 
	 */
	public Integer getUserCredits(int playerID) throws DatabaseException{
		
		if(!initialised){
			initialise();
		}

		//Get a connection to the database
		Connection connection = Database.getConnection();

		Statement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery("SELECT * FROM CREDITS WHERE id=" + playerID + "");
//			statement = connection.prepareStatement("SELECT * from CREDITS WHERE username=?");
//			statement.setString(1, username);
			Integer result = findCreditsForUser(playerID, resultSet);

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
	 * Returns credits where id matches int playerID given
	 * 
	 * @param	int playerID, ResultSet results
	 * @throws	SQLException
	 * @return	Integer result
	 */
	private Integer findCreditsForUser(int playerID, ResultSet results) throws SQLException{
		Integer result = null;
		while (results.next()){
			int id = results.getInt("id");
			if (id == playerID){
				result = results.getInt("credits");
				break;
			}
		}

		return result;
	}
	
	/**
	 * Deduct a number of credits from the user's account
	 * @param playerID The user from whose account the credits should be deducted
	 * @param numCredits The number of credits to deduct
	 */
	public void deductUserCredits(int playerID, int numCredits) {
		//TODO implement me!
		throw new UnsupportedOperationException("Not yet implemented");
	}
	
	/**
	 * Add a number of credits to the user's account
	 * @param playerID The user to whose account the credits should be added
	 * @param numCredits The number of credits to add
	 * @throws DatabaseException 
	 */
	public void addUserCredits(int playerID, int numCredits) throws DatabaseException {
		Connection connection = Database.getConnection();
		Statement stmt = null;
		ResultSet resultSet = null;
		
		try {
			stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			// first retrieve the current users's current balance
			resultSet = stmt.executeQuery("SELECT * FROM CREDITS WHERE id=" + playerID + "");
			if (resultSet.next()) {
				int oldBalance = resultSet.getInt("CREDITS");
				// then increment it and set it
				resultSet.updateInt("CREDITS", oldBalance + numCredits);
				resultSet.updateRow();
			} else {
				throw new DatabaseException("playerID has no balance");
			}
			if (resultSet.next()) {
				throw new DatabaseException("Two entries for username!");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
