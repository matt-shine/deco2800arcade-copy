package deco2800.server.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
	
	
	private static String username = "server";
	private static String password = "server";
	
	/**
	 * Create Java Database connection 
	 * 
	 * @return	Connection, connection (session) with deco2800.server.database. 
	 */
	public static Connection getConnection() throws DatabaseException{
		Connection connection;
		String connectionAddress = "jdbc:derby:Arcade;user=" + username + ";password=" + password + ";create=true";
		try {
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
			connection = DriverManager.getConnection(connectionAddress);

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new DatabaseException("Unable to find Derby driver", e);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException("Unable to connect to the Derby database", e);
		}
		return connection;
	}

}
