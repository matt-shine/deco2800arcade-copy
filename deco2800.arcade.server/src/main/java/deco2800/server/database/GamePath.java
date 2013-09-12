package deco2800.server.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class GamePath {
	
	private boolean initialised = false;

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
		initialised = true;
	}
	/**
	 * Searches for the path of the game with a particular gameID
	 * @param gameID
	 *          ID for the requested game
	 * @return
	 *          Returns the path of the requested game
	 * @throws DatabaseException
	 *          If SQLException occurs
	 */
	public String getPath(int gameID) throws DatabaseException{
		if(!initialised){
			initialise();
		}
		Connection connection = Database.getConnection();

		Statement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery("SELECT path FROM GAMEPATH WHERE"
					+ " gameID=" + gameID);
			String result = resultSet.getString(1);

			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException("Unable to get game path from database",
					e);
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
	 * adds a game to the table with the specified ID and Path
	 * @param gameID
	 *          ID of the game to be added
	 * @param gamePath
	 *          Path of the game to be added
	 * @throws DatabaseException
	 *          If SQLException occurs
	 */
	public void insertGame(int gameID,String gamePath) throws DatabaseException{
		Connection connection = Database.getConnection();
		
		Statement statement = null;
		String query = "INSERT INTO GAMEPATH VALUES ('"+gameID+"','"+
		gamePath+"')";
		try {
			statement = connection.createStatement();
			statement.executeUpdate(query);
		} catch(SQLException e) {
			throw new DatabaseException("unable to add game to database",e);
		} finally {
			try {
				if (statement != null){
					statement.close();
				}
				if (connection != null){
					connection.close();
				}
			} catch (SQLException e){
				e.printStackTrace();
			}
		}
	}

}
