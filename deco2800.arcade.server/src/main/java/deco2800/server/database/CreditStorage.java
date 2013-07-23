package deco2800.server.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CreditStorage {

	
	public static Integer getUserCredits(String username) throws DatabaseException{
		
		Connection connection;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost/arcade?" + "user=arcadeserver&password=arcadeserver");
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new DatabaseException("Unable to find MySQL driver", e);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException("Unable to connect to the MySQL database", e);
		}
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery("SELECT * from ARCADE.CREDITS");
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
