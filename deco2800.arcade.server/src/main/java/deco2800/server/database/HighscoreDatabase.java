package deco2800.server.database;

import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import deco2800.arcade.protocol.highscore.GetScoreRequest;

public class HighscoreDatabase {
	private boolean initialised = false;
	
	
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
			ResultSet tableData = connection.getMetaData().getTables(null, null, "PLAYER_HIGHSCORES", null);
			
			
			if (!tableData.next()) {
				statement.execute("CREATE TABLE PLAYER_HIGHSCORES(HID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1)," + 
							"Player VARCHAR(30) NOT NULL," +
							"GameID VARCHAR(30) NOT NULL," +
							"Date TIMESTAMP, " +
							"PRIMARY KEY (HID))");
			}
			//Create game scores table 
			tableData = connection.getMetaData().getTables(null, null, "PLAYER_HIGHSCORES_DATA", null);
			if (!tableData.next()) {
				statement.execute("CREATE TABLE PLAYER_HIGHSCORES_DATA(ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1)," +
							"Score_Type VARCHAR(50)," +
							"HID INTEGER," +
							"Score INTEGER," +
							"FOREIGN KEY(HID) REFERENCES PLAYER_HIGHSCORES(HID))");
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
	
	/**
	 * A general method called by HighscoreListener that picks a query to run,
	 * based on the value of requestType and returns.
	 * 
	 * @param requestType - The identifier of the request that is bring sent. 
	 * It corresponds to a query that is to be run.
	 * @param game_ID - The game that the request is being sent for
	 * @param username - The user that the scores are being sent for
	 * 
	 * @return A list of strings containing the data returned by the query. 
	 * The first value is the number of columns that are bring returned.
	 */
	public List<String> fetchData(GetScoreRequest gsReq) {
		//Run the query corresponding to the requestID. This switch statement is probably going to get pretty big.
		//System.out.println("adding a score should not get here.");
		try {
		switch (gsReq.requestID) {
			case 1: return getGameTopPlayers(gsReq.game_ID, gsReq.limit, gsReq.type, gsReq.highestIsBest); //Return value of query with requestID 1
			case 2: return getUserHighScore("Matt", "Pong", "Number", gsReq.highestIsBest); //Return value of query with requestID 2
			case 3: return null; //Return value of query with requestID 3
			case 4: return null;
			case 5: return null;

			}
		} catch (DatabaseException e) {
			//bad
		}
		
		/*This should never be reached, as all requestIDs should be covered in the switch*/
		return null;
	}
	
	/** 
	 * requestID: 1
	 * 
	 * Displays an amount of top players for a specified game
	 * 
	 * @param Game_ID - game id to query against
	 * @param top - number of top players to display
	 * @throws DatabaseException 
	 */
	public List<String> getGameTopPlayers(String Game_ID, int top, String type, boolean highestIsBest) throws DatabaseException{
		List<String> data = new ArrayList<String>();
		int topCount = 0;
		String order;
		
		if (!initialised) {
			initialise();
		}
		
		if (highestIsBest){
			order = "DESC";
		} else {
			order = "ASC";
		}
		
		// Get a connection to the database
		Connection connection = Database.getConnection();
		Statement statement = null;
		ResultSet resultSet = null;
		
		try {
			statement = connection.createStatement();
			String getTop = "SELECT * FROM PLAYER_HIGHSCORES AS H, PLAYER_HIGHSCORES_DATA AS D WHERE H.HID = D.HID AND H.GameID='" + Game_ID + "' AND D.Score_Type='" + type + "' ORDER BY D.Score " + order;
			resultSet = statement.executeQuery(getTop);
			
			while(resultSet.next() && topCount <= top) {
				data.add(String.valueOf(resultSet.getString("Player")));
				data.add(String.valueOf(resultSet.getInt("Score")));
				data.add(String.valueOf(resultSet.getDate("Date")));
				data.add(String.valueOf(resultSet.getString("Score_Type")));
				topCount++;
			}
			return data;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException(
					"Unable to get player information from database", e);
		} finally {
			connectionCleanup(connection, statement, resultSet);
		}
	}
	
	/**
	 * requestID: 2
	 * 
	 * Displays a string representation of the users score for the specified game and type of score
	 * @param User_ID - users id to query against
	 * @param Game_ID - game id to query against
	 * @param type - type of score that needs to be retrieved
	 * @throws DatabaseException 
	 */
	public List<String> getUserHighScore(String Username, String Game_ID, String type, boolean highestIsBest) throws DatabaseException{
		System.out.println("get User high score request..");
		
		List<String> data = new ArrayList<String>();
		String order;
		
		if (!initialised) {
			initialise();
		}
		
		if(highestIsBest){
			order = "DESC";
		}else {
			order = "ASC";
		}
		
		// Get a connection to the database
		Connection connection = Database.getConnection();

		Statement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery("SELECT * FROM PLAYER_HIGHSCORES AS H, " +
					"PLAYER_HIGHSCORES_DATA AS D WHERE H.HID = D.HID AND H.GameID='" + Game_ID + "' AND D.Score_type='" + 
					type + "' AND H.Player='" + Username + "' ORDER BY D.Score " + order);
			while(resultSet.next()) {
				System.out.println("Player: " + resultSet.getString("Player"));
				System.out.println("Score: " + resultSet.getInt("Score"));
				System.out.println("query passed........");
			}

			return data;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException(
					"Unable to get player information from database", e);
		} finally {
			connectionCleanup(connection, statement, resultSet);
		}
	}
	
	/**
	 * Displays the users ranking in the highscores for the specified game and score type
	 * @param User_ID - users id to query against
	 * @param Game_ID
	 * @param type - type of score that needs to be retrieved
	 * @throws DatabaseException 
	 */
	public String getUserRanking(String Username, String Game_ID, String type) throws DatabaseException{
		/*String data = null;

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
		}*/
		return null;
	}
	
	
	/**
	 * Displays a string representation of all the users highscores aggregated into an average. This could be used by the games
	 * if they wish to display this as a way of comparing the users score against the average player
	 * @param Game_ID - game id to query against
	 * @param type - type of score that needs to be retrieved
	 * @throws DatabaseException 
	 */
	public String getAvgUserHighScore(String Game_ID, String type) throws DatabaseException{
		/*String data = null;

		if (!initialised) {
			initialise();
		}
		
		// Get a connection to the database
		Connection connection = Database.getConnection();

		Statement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery("SELECT AVG(s.SCORE) as SCORE from HIGHSCORES_PLAYER h INNER JOIN " +
					"HIGHSCORES_DATA s on h.HID = s.HID WHERE h.GameId='" + Game_ID + "' AND Score_type='" + 
					type + "';");
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
		} */
		return null;
	}
	
	
	public String getTopPlayers() throws DatabaseException, SQLException{
		String data = null;
		/*
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		
		String selectTableSQL = "SELECT * FROM HIGHSCORES_PLAYER";
		
		try {
			// Get a connection to the database
			connection = Database.getConnection();
			statement = connection.createStatement();
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
		}*/
		
		return data;
	}
	
	
	//======================
	//Adding Score Methods
	//======================
	
	public int addHighscore(String Game_ID, String Username) throws DatabaseException, SQLException {
		int hid = 0;
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		
		String insertTableSQL = "INSERT INTO PLAYER_HIGHSCORES"
				+ "(Player, GameID, Date) VALUES"
				+ "('" + Username + "','" + Game_ID +  "', '"
				+ getCurrentTimeStamp() + "')";
		System.out.println(insertTableSQL);
		try {
			// Get a connection to the database
			connection = Database.getConnection();
			statement = connection.createStatement();
			
			statement.execute(insertTableSQL, Statement.RETURN_GENERATED_KEYS);
			
			resultSet = statement.getGeneratedKeys();
	        while(resultSet.next()){
	        	hid = resultSet.getInt(1);
	        }
			
	        

		} catch 	(SQLException e) {
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
	public void updateScore(String Game_ID, String username, LinkedList<Integer> scores, LinkedList<String> types) throws DatabaseException, SQLException{
		int hid = addHighscore(Game_ID, username);
		System.out.println("new HighScore ID = " + hid);
		if (!initialised) {
			initialise();
		}
		
		for(int i = 0; i < scores.size(); i++){
			System.out.println("Score to be added > game: " + Game_ID + ", player: " + username + ", type: " + types.get(i) + ", score: " + scores.get(i));
			
			//Get a connection to the database
			Connection connection = Database.getConnection();
			Statement statement = null;
			ResultSet resultSet = null;
			try {
				statement = connection.createStatement();
				String insertTableSQL = "INSERT INTO PLAYER_HIGHSCORES_DATA"
						+ "(Score_Type, HID, Score) VALUES"
						+ "('" + types.get(i) + "'," + hid +  ", " + scores.get(i) + ")";
				
				statement.executeUpdate(insertTableSQL);
				System.out.println("InsertQUERY:" + insertTableSQL);
				System.out.println("inserted correctly");
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DatabaseException(
						"Unable to get player information from database", e);
			} finally {
				connectionCleanup(connection, statement, resultSet);
			}
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
	private static Timestamp getCurrentTimeStamp() {
		Timestamp time = new java.sql.Timestamp(new Timestamp(0).getTime());
		System.out.println("Current time =" + time);
		return time;
		
 
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
			//Silently fail, no need to worry
		}
		
		//Close the Statement
		try {
			if (s != null) s.close();
		} catch (SQLException e) {
			//Silently fail, no need to worry
		}
		
		//Close the ResultSet
		try {
			if (r != null) r.close();
		} catch (SQLException e) {
			//Silently fail, no need to worry
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
