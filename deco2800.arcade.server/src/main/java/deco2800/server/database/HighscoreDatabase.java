package deco2800.server.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class HighscoreDatabase {
	private boolean initialised = false;
	private static final DateFormat dateFormat = new SimpleDateFormat(
			"yyyy/MM/dd HH:mm:ss");
	
	/* Create the highscore database if it does not exist */
	public void initialise() throws DatabaseException{
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
				+ "Rating INT, " 
				+ "PRIMARY KEY (HID));");
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
	public List<String> getGameTopPlayers(String Game_ID, int top, String type) throws DatabaseException{
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
			resultSet = statement.executeQuery("SELECT h.USERNAME from HIGHSCORES h INNER JOIN " +
					"SCORES s on h.HID = s.HID WHERE GameId='" + Game_ID + "' AND Score_Type='" + type +
					"' ORDER BY s.Score desc LIMIT " + top + ";");
			while(resultSet.next())
			{
				data.add(resultSet.getString("USERNAME"));
			}

			return data;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException(
					"Unable to get player information from database", e);
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
	 * @throws DatabaseException 
	 */
	public String getUserHighScore(String Username, String Game_ID, String type) throws DatabaseException{
		String data = null;

		if (!initialised) {
			initialise();
		}

		// Get a connection to the database
		Connection connection = Database.getConnection();

		Statement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery("SELECT s.SCORE from HIGHSCORES h INNER JOIN " +
					"SCORES s on h.HID = s.HID WHERE h.GameId='" + Game_ID + "' AND Score_type='" + 
					type + "' AND Username='" + Username + "';");
			while(resultSet.next())
			{
				data = resultSet.getString("SCORE");
			}

			return data;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException(
					"Unable to get player information from database", e);
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
	 * @throws DatabaseException 
	 */
	public String getUserRanking(String Username, String Game_ID, String type) throws DatabaseException{
		String data = null;

		if (!initialised) {
			initialise();
		}

		// Get a connection to the database
		Connection connection = Database.getConnection();

		Statement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery("SELECT s2.RANK FROM (SELECT h.Username, RANK() OVER (ORDER BY s.SCORE DESC) AS 'RANK' from HIGHSCORES h INNER JOIN " +
					"SCORES s on h.HID = s.HID WHERE h.GameId='" + Game_ID + "' AND Score_type='" + 
					type + "' ORDER BY s.SCORE desc) s2 WHERE s2.Username='" + Username + "';");
			while(resultSet.next())
			{
				data = resultSet.getString("RANK");
			}

			return data;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException(
					"Unable to get player information from database", e);
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
	
	public String getTopPlayers() throws DatabaseException, SQLException{
		String data = null;
		
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		
		String selectTableSQL = "SELECT * FROM HIGHSCORE";
		
		try {
			// Get a connection to the database
			connection = Database.getConnection();		
			resultSet = statement.executeQuery(selectTableSQL);
			
			while(resultSet.next())
			{
				data = data.concat(resultSet.getString("Username")+ ",");
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException(
					"Unable to add highscore information to database", e);
		} finally {
 
			if (statement != null) {
				statement.close();
			}
 
			if (connection != null) {
				connection.close();
			}
 
		}
		
		return data;
	}
	
	
	/* Game to Database Methods */
	
	private int addHighscore(String Game_ID, String Username) throws DatabaseException, SQLException {
		int hid = 0;
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		
		String insertTableSQL = "INSERT INTO HIGHSCORES"
				+ "(Username, GameID, Date, Rating) VALUES"
				+ "(" + Username + "," + Game_ID +  ", to_date('"
				+ getCurrentTimeStamp() + "', 'yyyy/mm/dd hh24:mi:ss'), 0)";
		
		try {
			// Get a connection to the database
			connection = Database.getConnection();
			
			statement.executeUpdate(insertTableSQL);
			
			resultSet = statement.getGeneratedKeys();
	        if (resultSet.next()) {
	            hid = resultSet.getInt(1);
	        }

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException(
					"Unable to add highscore information to database", e);
		} finally {
 
			if (statement != null) {
				statement.close();
			}
 
			if (connection != null) {
				connection.close();
			}
 
		}
		
		
		return hid;
	}
	
	
	/**
	 * 
	 * @param Game_ID
	 * @param User_ID
	 * @param type
	 * @param score - the players score to store in the database
	 * @throws DatabaseException 
	 * @throws SQLException 
	 */
	public void updateScore(String Game_ID, String Username, String type, float score) throws DatabaseException, SQLException{
		String data = null;
		int hid = addHighscore(Game_ID, Username);

		if (!initialised) {
			initialise();
		}

		// Get a connection to the database
		Connection connection = Database.getConnection();

		Statement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.createStatement();
			
			String insertTableSQL = "INSERT INTO SCORES"
					+ "(Score_Type, HID, Score) VALUES"
					+ "(" + type + "," + hid +  ", " + score + ")";
			
			statement.executeUpdate(insertTableSQL);
			
			/************
			 statement.executeQuery("UPDATE SCORES SET Score='" + score + "' WHERE ID="
			 
					+ "(SELECT h.ID FROM HIGHSCORES h INNER JOIN SCORES s on h.HID = s.HID"
					+ "WHERE h.GameId='" + Game_ID + "' AND h.Username='" + Username + "' AND s.Score_Type='" + type + "');");
			*/

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException(
					"Unable to get player information from database", e);
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
	
	private static String getCurrentTimeStamp() {
		 
		java.util.Date today = new java.util.Date();
		return dateFormat.format(today.getTime());
 
	}
	
	/**** 
	public void main() throws DatabaseException {
		 
		try {
 
			updateScore("Pong", "Haydn", "Win", 66);
			System.out.println("Highscore: " + getUserHighScore("Haydn", "Pong", "Win"));
 
		} catch (SQLException e) {
 
			
 
		}
 
	}
	**/
	
	
}
