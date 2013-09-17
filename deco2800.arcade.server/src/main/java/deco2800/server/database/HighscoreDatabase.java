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
	
	
	//======================
	//Database Setup Methods
	//======================
	
	/** 
	 * Create the highscore database if it does not exist 
	 */
	public void initialise() throws DatabaseException{
		// Get a connection to the database
		Connection connection = Database.getConnection();
		
		
		
		try {
			Statement statement = connection.createStatement();
			
			//Create high scores base table
			ResultSet tableData = connection.getMetaData().getTables(null, null, "HIGHSCORES_PLAYER", null);
			
			
			if (!tableData.next()) {
				statement.execute("CREATE TABLE HIGHSCORES_PLAYER(HID INT GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1)," + 
							"Username VARCHAR(30) NOT NULL," +
							"GameID INT NOT NULL," +
							"Date TIMESTAMP, " +
							"CONSTRAINT primary_key PRIMARY KEY (HID))");
			}
			//Create game scores table 
			tableData = connection.getMetaData().getTables(null, null, "HIGHSCORES_DATA", null);
			if (!tableData.next()) {
				statement.execute("CREATE TABLE HIGHSCORES_DATA(ID INT PRIMARY KEY," +
							"Score_Type VARCHAR(255)," +
							"HID INT," +
							"Score INT," +
							"FOREIGN KEY(HID) REFERENCES HIGHSCORES_PLAYER(HID))");
			}
			
		} catch (SQLException e) {
			//e.printStackTrace();
			throw new DatabaseException("Unable to create highscores tables\n", e);
		}
		initialised = true;
	}
	
	
	//======================
	//Fetching Score Methods
	//======================	
	
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
			resultSet = statement.executeQuery("SELECT h.USERNAME from HIGHSCORES_PLAYER h INNER JOIN " +
					"HIGHSCORES_DATA s on h.HID = s.HID WHERE GameId='" + Game_ID + "' AND Score_Type='" + type +
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
			resultSet = statement.executeQuery("SELECT s.SCORE from HIGHSCORES_PLAYER h INNER JOIN " +
					"HIGHSCORES_DATA s on h.HID = s.HID WHERE h.GameId='" + Game_ID + "' AND Score_type='" + 
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
			resultSet = statement.executeQuery("SELECT s2.RANK FROM (SELECT h.Username, RANK() OVER (ORDER BY s.SCORE DESC) AS 'RANK' from HIGHSCORES_PLAYER h INNER JOIN " +
					"HIGHSCORES_DATA s on h.HID = s.HID WHERE h.GameId='" + Game_ID + "' AND Score_type='" + 
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
		
		String selectTableSQL = "SELECT * FROM HIGHSCORES_PLAYER";
		
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
			connectionCleanup(connection, statement, resultSet);
		}
		
		return data;
	}
	
	
	//======================
	//Adding Score Methods
	//======================
	
	private int addHighscore(String Game_ID, String Username) throws DatabaseException, SQLException {
		int hid = 0;
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		
		
		String insertTableSQL = "INSERT INTO HIGHSCORES_PLAYER"
				+ "(Username, GameID, Date, Rating) VALUES"
				+ "(" + Username + "," + Game_ID +  ", to_date('"
				+ getCurrentTimeStamp() + "', 'yyyy/mm/dd hh24:mi:ss'), 0)";
		
		try {
			// Get a connection to the database
			connection = Database.getConnection();
			
			//Do we need a statement = connection.createStatement() here?
			
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
			connectionCleanup(connection, statement, resultSet);
		}
		
		
		return hid;
	}
	
	
	/** Used for games 
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
			
			String insertTableSQL = "INSERT INTO HIGHSCORES_DATA"
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
			connectionCleanup(connection, statement, resultSet);
		}
	}
	
	
	
	//======================
	//General Utility Methods
	//======================
	
	/**
	 * Creates a String representation of the current date and time.
	 * 
	 * @return A string representation of the current date and time.
	 */
	private static String getCurrentTimeStamp() {
		 
		java.util.Date today = new java.util.Date();
		return dateFormat.format(today.getTime());
 
	}
	
	/**
	 * Attempts to close c, s and r and silently fails if they can't be.
	 * 
	 * If there is no need to clean up any of the parameters, simply pass null 
	 * for that parameter.
	 * 
	 * @param c - A Connection that is to be closed
	 * @param s - A Statement that is to be closed
	 * @param r - A ResultSet that is to be closed
	 */
	private void connectionCleanup(Connection c, Statement s, ResultSet r) {
		//Close the Connection
		try {
			if (c != null) c.close();
		} catch (SQLException e) {
			//Silently fail
		}
		
		//Close the Statement
		try {
			if (s != null) s.close();
		} catch (SQLException e) {
			//Silently fail
		}
		
		//Close the ResultSet
		try {
			if (r != null) r.close();
		} catch (SQLException e) {
			//Silently fail
		}
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
