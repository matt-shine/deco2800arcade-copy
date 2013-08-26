package deco2800.server.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class HighscoreDatabase {

	/* Create the highscore database if it does not exist */
	public  void initialise() throws DatabaseException{
		Connection connection = Database.getConnection();
		
		try {
			Statement statement = connection.createStatement();
			statement.execute("[INSERT CREATE TABLE QUERY HERE]");
		}catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException("Unable to create credits table", e);
		}
	}
	
	/* User Interface to Database Methods */
	
	/** Displays an amount of top players for a specified game
	 * @param Game_ID
	 * @param top - number of top players to display
	 */
	void getGameTopPlayers(String Game_ID, int top){
		
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
