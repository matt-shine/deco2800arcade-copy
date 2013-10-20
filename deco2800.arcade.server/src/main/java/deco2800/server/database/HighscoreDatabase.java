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

/**
 * HighscoreDatabase sits on the server-side of the systeam and contains a 
 * number of queries that are run in order to fetch data from and add data
 * to the database.
 * 
 * This class is not directly accessed by games. In order ot utilise this
 * class, game designers must use the HighscoreClient class, that sits on the
 * client-side of the system.
 * 
 * @author TeamA
 */
public class HighscoreDatabase {
	private boolean initialised = false;
	
	
	//====================================
	//Database Setup Methods
	//====================================
	
	/**
	 * Creates the highscore databases if they do not already exist.
	 * 
	 * @throws DatabaseException If the database can't be initialised.
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
		//Run the query corresponding to the requestID.
		try {
		switch (gsReq.requestID) {
			case 1: return getGameTopPlayers(gsReq.game_ID, gsReq.limit, gsReq.type, gsReq.highestIsBest); //Return value of query with requestID 1
			case 2: return getUserHighScore(gsReq.username, gsReq.game_ID, gsReq.type, gsReq.highestIsBest); //Return value of query with requestID 2
			case 3: return getUserRanking(gsReq.username, gsReq.game_ID, gsReq.type, gsReq.highestIsBest); //Return value of query with requestID 3
			case 4: return getWinsGetLosses(gsReq.username, gsReq.game_ID);
			case 5: return null;

			}
		} catch (DatabaseException e) {
			//catch exception
		}
		
		//This should never be reached, as all requestIDs should be covered in the switch
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
		//Array list to store the data returned from query
		List<String> data = new ArrayList<String>();
		//topCount keeps track of the number of rows return
		int topCount = 0;
		//used in the query to order the results returned. 
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
			
			while(resultSet.next() && topCount < top) {
				data.add(String.valueOf(resultSet.getString("Player")));
				data.add(String.valueOf(resultSet.getInt("Score")));
				data.add(String.valueOf(resultSet.getDate("Date")));
				data.add(String.valueOf(resultSet.getString("Score_Type")));
				topCount++;
			}
			
			return data;
		} catch (SQLException e) {
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
	 * 
	 * @param User_ID - users id to query against
	 * @param Game_ID - game id to query against
	 * @param type - type of score that needs to be retrieved
	 * @throws DatabaseException 
	 */
	public List<String> getUserHighScore(String Username, String Game_ID, String type, boolean highestIsBest) throws DatabaseException{
		//Array list to store the data returned from query
		List<String> data = new ArrayList<String>();
		//used in the query to order the results returned. 
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
				data.add(String.valueOf(resultSet.getString("Player")));
				data.add(String.valueOf(resultSet.getInt("Score")));
				data.add(String.valueOf(resultSet.getDate("Date")));
				data.add(String.valueOf(resultSet.getString("Score_Type")));
				break;
			}

			return data;
		} catch (SQLException e) {
			throw new DatabaseException(
					"Unable to get player information from database", e);
		} finally {
			connectionCleanup(connection, statement, resultSet);
		}
	}
	
	/**
	 * Request ID: 3
	 * Displays the users ranking in the highscores for the specified game and score type
	 * Returns -1 as the ranking (score) if the player cannot be found
	 * 
	 * @param User_ID - users id to query against
	 * @param Game_ID
	 * @param type - type of score that needs to be retrieved
	 * @throws DatabaseException 
	 */
	public List<String> getUserRanking(String Username, String Game_ID, String type, boolean highestIsBest) throws DatabaseException{
		//Array list to store the data returned from query
		List<String> data = new ArrayList<String>();
		//first ranking initialised to 1
		int ranking = 1;
		//used in the query to order the results returned. 
		String order;
		//used to break the loop when the user is found.
		boolean userFound = false;
		
		if (!initialised) {
			initialise();
		}

		if (highestIsBest) {
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
			String select = "SELECT DISTINCT H.Player, Score FROM PLAYER_HIGHSCORES AS H, PLAYER_HIGHSCORES_DATA AS D WHERE H.HID = D.HID AND H.GameID='" + Game_ID + "' AND D.Score_Type='" + type + "' ORDER BY D.Score " + order;
			resultSet = statement.executeQuery(select);
			//stores all found users so that if the top 2 scores are from the same person the third
			//scorer (if different) will be ranked second
			LinkedList<String> foundUsers = new LinkedList<String>();
			
			while (resultSet.next()) {
				String user = resultSet.getString("Player");
				if (user.equals(Username)) {
					userFound = true;
					break;
				} else {
					if (!foundUsers.contains(user)) {
						foundUsers.add(user);
						ranking++;
					}
				}
			}
			//set up data to be sent back to client side
			data.add(Username);
			if(userFound){
				data.add(String.valueOf(ranking));
			} else {
				data.add("-1");
			}
			data.add("");
			data.add("rank");
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
	 * requestID: 4
	 * 
	 * Returns a list of 8 strings. The first four represent the wins score and 
	 * last 4 represent the number of losses for the player and game.
	 * 
	 * @param User_ID - users id to query against
	 * @param Game_ID - game id to query against
	 * @param type - type of score that needs to be retrieved
	 * @throws DatabaseException 
	 */
	public List<String> getWinsGetLosses(String Username, String Game_ID) throws DatabaseException {
		//Array list to store the data returned from query
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
			String getWins = "SELECT COUNT(*) AS WINS FROM PLAYER_HIGHSCORES AS H, PLAYER_HIGHSCORES_DATA AS D WHERE H.HID = D.HID AND H.GameID = '" + Game_ID + "' AND H.Player = '" + Username + "' AND D.Score = 1 AND D.Score_Type = 'WinLoss'";
			resultSet = statement.executeQuery(getWins);
			while(resultSet.next()) {
				data.add(Username);
				data.add(String.valueOf(resultSet.getInt("WINS")));
				data.add("");
				data.add("wins");
			}
			
			String getLosses = "SELECT COUNT(*) AS LOSS FROM PLAYER_HIGHSCORES AS H, PLAYER_HIGHSCORES_DATA AS D WHERE H.HID = D.HID AND H.GameID = '" + Game_ID + "' AND H.Player='" + Username + "' AND D.Score = -1 AND D.Score_Type = 'WinLoss'";
			resultSet = statement.executeQuery(getLosses);
			while(resultSet.next()) {
				data.add(Username);
				data.add(String.valueOf(resultSet.getInt("LOSS")));
				data.add("");
				data.add("losses");
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
	
		
	//======================
	//Adding Score Methods
	//======================
	
	/**
	 * Inserts a new score entity into the database.
	 * 
	 * @param Game_ID The game that the score is being stored for
	 * @param Username The user that the score is being stored for
	 * 
	 * @return The id of the new score entity that has been entered.
	 * 
	 * @throws DatabaseException If the insert query fails
	 * @throws SQLException
	 */
	private int addHighscore(String Game_ID, String Username) throws DatabaseException, SQLException {
		int hid = 0;
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		
		String insertTableSQL = "INSERT INTO PLAYER_HIGHSCORES"
				+ "(Player, GameID, Date) VALUES"
				+ "('" + Username + "','" + Game_ID +  "', '"
				+ getCurrentTimeStamp() + "')";
		try {
			// Get a connection to the database
			connection = Database.getConnection();
			statement = connection.createStatement();
			statement.execute(insertTableSQL, Statement.RETURN_GENERATED_KEYS);
			resultSet = statement.getGeneratedKeys();
			
	        while (resultSet.next()){
	        	hid = resultSet.getInt(1);
	        }
		} catch (SQLException e) {
			throw new DatabaseException(
					"Unable to add highscore information to database", e);
		} finally {
			connectionCleanup(connection, statement, resultSet);
		}
		
		
		return hid;
	}
	
	/** Inserts a set of non-null score, type pairs into the database,
	 * linking them to user and game.
	 * 
	 * @param game_ID The ID of the game that the scores are being stored for
	 * @param username The user that the scores are being stored for
	 * @param types The types of all of the scores that are being stored
	 * @param score The score that is being stored for the type
	 * 
	 * @throws DatabaseException If the insert query fails.
	 * @throws SQLException 
	 */
	public void updateScore(String game_ID, String username, LinkedList<Integer> scores, LinkedList<String> types) throws DatabaseException, SQLException{
		//insert a new highscore instance and get the generate key
		int hid = addHighscore(game_ID, username);
		
		if (!initialised) {
			initialise();
		}
		//store all scores (single and multiple)
		for(int i = 0; i < scores.size(); i++){
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
			} catch (SQLException e) {
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
		java.util.Date date= new java.util.Date();
		return new Timestamp(date.getTime());
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
			if (c != null) {
				c.close();
			}
		} catch (SQLException e) {
			//Silently fail, no need to worry
		}
		
		//Close the Statement
		try {
			if (s != null) {
				s.close();
			}
		} catch (SQLException e) {
			//Silently fail, no need to worry
		}
		
		//Close the ResultSet
		try {
			if (r != null) {
				r.close();
			}
		} catch (SQLException e) {
			//Silently fail, no need to worry
		}
	}
}
