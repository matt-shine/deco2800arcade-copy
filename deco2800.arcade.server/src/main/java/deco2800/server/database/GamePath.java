package deco2800.server.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class GamePath {

	public void initialise() throws DatabaseException {

		// Get a connection to the database
		Connection connection = Database.getConnection();

		try {
			ResultSet tableData = connection.getMetaData().getTables(null,
					null, "GAMEPATH", null);
			if (!tableData.next()) {
				Statement statement = connection.createStatement();
				statement
						.execute("CREATE TABLE GAMEPATH(gameID INT PRIMARY KEY,"
								+ "path VARCHAR(200));");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException("Unable to create game path table", e);
		}
	}
	
	public String getPath(int gameID) throws DatabaseException{
		Connection connection = Database.getConnection();

		Statement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery("SELECT path FROM GAMEPATH WHERE gameID=" + gameID);
			String result = resultSet.getString(1);

			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException("Unable to get game path from database", e);
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

}
