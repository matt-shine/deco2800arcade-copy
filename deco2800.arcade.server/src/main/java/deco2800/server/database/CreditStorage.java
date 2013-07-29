package deco2800.server.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CreditStorage {

	private static boolean initialised = false;
	
	/**
	 * Create Java Database connection 
	 * @ return connection (session) with deco2800.server.database. 
	 */
	private static Connection getDatabaseConnection() throws DatabaseException{
		Connection connection;
		try {
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
			connection = DriverManager.getConnection("jdbc:derby:Arcade;user=server;password=server;create=true");

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new DatabaseException("Unable to find Derby driver", e);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException("Unable to connect to the Derby database", e);
		}
		return connection;
	}
	
	/**
	 * Creates the Credits table and sets initialise to TRUE on completion
	 */
	public static void initialise() throws DatabaseException{

		//Get a connection to the database
		Connection connection = getDatabaseConnection();

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
	
	/*
	 * Check Users Credits
	 * @param String username
	 */
	public static Integer getUserCredits(String username) throws DatabaseException{

		//Check whether or not the database has been intitialised
		if (!initialised){
			//Not initialised yet - initialise it
			initialise();
		}

		//Get a connection to the database
		Connection connection = getDatabaseConnection();

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
	 * Returns the amount of credits a user has
	 * @param username
	 * @param results
	 * @return
	 * @throws SQLException
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
