package deco2800.server.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class HighscoreDatabase {
	private boolean initialised = false;
	
	/* Create the highscore database if it does not exist */
	public  void initialise() throws DatabaseException{
		// Get a connection to the database
		Connection connection = Database.getConnection();
		
		//Create high scores base table
		try {
			ResultSet tableData = connection.getMetaData().getTables(null, null, "HIGHSCORES", null);
			if (!tableData.next()) {
				Statement statement = connection.createStatement();
				statement.execute("CREATE TABLE HIGHSCORES(HID int NOT NULL AUTO_INCREMENT," 
				+ "Username VARCHAR(30) NOT NULL, "
				+ "GameID int NOT NULL, "
				+ "Date TIMESTAMP, "
				+ "PRIMARY KEY (HID)" 
				+ "Rating INT));");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException("Unable to create highscores table", e);
		}
		
		//Create game scores table 
		try {
			ResultSet tableData = connection.getMetaData().getTables(null, null, "SCORES", null);
			if (!tableData.next()) {
				Statement statement = connection.createStatement();
				statement.execute("CREATE TABLE IF NOT EXISTS SCORES(ID int NOT NULL AUTO_INCREMENT, "
				+ "Score_Type VARCHAR(255), "
				+ "HID int NOT NULL, "
				+ "Score Long NOT NULL, "
				+ "PRIMARY KEY (ID), "
				+ "FOREIGN KEY (HID) REFERENCES HIGHSCORES(HID));");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException("Unable to create scores table", e);
		}
		
		initialised = true;
	}
	
	/* User Interface to Database Methods */
	
	/** Displays an amount of top players for a specified game
	 * @param Game_ID
	 * @param top - number of top players to display
	 * @throws DatabaseException 
	 */
	public List<String> getGameTopPlayers(String Game_ID, int top) throws DatabaseException{
		List<String> data = new ArrayList<String>();

		if (!initialised) {
			initialise();
		}

		// Get a connection to the database
		Connection connection = Database.getConnection();

		Statement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery("SELECT USERNAME from HIGHSCORES WHERE GameId='" + Game_ID + "' ORDER BY Rating desc LIMIT " + top + ";");
			while(resultSet.next())
			{
				data.add(resultSet.getString("USERNAME"));
			}

			return data;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException(
					"Unable to get player informtion from database", e);
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 
	 * @param User_ID
	 * @param Game_ID
	 * @param type
	 */
	void getUserHighScore(String User_ID, String Game_ID, String type){
		
	}
	
	/**
	 * 
	 * @param User_ID
	 * @param Game_ID
	 * @param type
	 */
	void getUserRanking(String User_ID, String Game_ID, String type){
		
	}
	
	/* Game to Database Methods */
	/**
	 * 
	 * @param Game_ID
	 * @param User_ID
	 * @param type
	 * @param score - the players score to store in the database
	 */
	void updateScore(String Game_ID, String User_ID, String type, float score){
		
	}
	
	
}
