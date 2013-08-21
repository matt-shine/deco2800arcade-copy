package deco2800.server.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import deco2800.arcade.model.Achievement;
import deco2800.arcade.model.AchievementProgress;
import deco2800.arcade.model.Player;
import deco2800.arcade.model.Game;

/**
 * Implements Achievement storage on database.
 *
 */
public class AchievementStorage {

	/**
	 * Creates the Achievement table and sets initialised to TRUE on completion
	 * 
	 * @throws	DatabaseException	If SQLException occurs. 
	 */
	public  void initialise() throws DatabaseException{

		//Get a connection to the database
		Connection connection = Database.getConnection();

		try {
			
			// TODO Peter this gets called when the server is started, called from
			// server/server/ArcadeServer.java below is the creditstorage code
			// (Copied this from credit storage)
			
			/*
			ResultSet tableData = connection.getMetaData().getTables(null, null, "CREDITS", null);
			if (!tableData.next()){
				Statement statement = connection.createStatement();
				statement.execute("CREATE TABLE CREDITS(id INT PRIMARY KEY," +
						"USERNAME VARCHAR(30) NOT NULL," +
						"CREDITS INT NOT NULL)");
			}
			*/
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException("Unable to create achievements table", e);
		}
	}
	
    /**
     * Utility method for fetching a single achievement. This is just a wrapper
     * around achievementsForIDs, which is a more efficient method for
     * fetching many achievements.
     * 
     * @param  achievementID            The unique ID for the achievement.
     * @throws IllegalArgumentException If an achievement with the provided ID
     *                                  doesn't exist.
     * @return The Achievement matching the provided ID.
     */
    public Achievement achievementForID(String achievementID) {
        ArrayList<String> achievementIDs = new ArrayList<String>();
        achievementIDs.add(achievementID);
        return achievementsForIDs(achievementIDs).get(0);
    }
    
    /**
     * Returns a list of Achievements corresponding to the supplied list of
     * IDs. The ordering of the returned Achievements matches that of the
     * supplied list of IDs.
     *
     * @param achievementIDs            The list of unique achievement IDs to
     *                                  fetch data for from the server.
     * @throws IllegalArgumentException If any of the provided IDs don't have
     *                                  corresponding achievements.
     * @return A list of Achievements corresponding to the supplied IDs.
     */
    public ArrayList<Achievement> achievementsForIDs(
            ArrayList<String> achievementIDs) {
        ArrayList<Achievement> achievements = new ArrayList<Achievement>();

        return achievements;
    }
    
    /**
     * Returns an unsorted list of a game's achievements.
     *
     * @param gameId	The game to fetch achievements for.
     * @return			A list of Achievements for the supplied game.
     */
    public ArrayList<Achievement> achievementsForGame(Game gameId) {
        ArrayList<Achievement> achievements = new ArrayList<Achievement>();

        return achievements;
    }

    
    /**
     * Returns an AchievementProgress instance representing the player's
     * progress in every achievement.
     *
     * @param player The player to fetch achievement progress for.
     * @return An AchievementProgress instance with the player's progress.
     */ 
    public AchievementProgress progressForPlayer(Player player) {
    	//Not sure exactly what this method will entail/does 
    	//focus on other methods for now
        
        return new AchievementProgress(player);
    }
    
    
    /**
     * Increments the player's progress for the achievement with ID
     * `achievementID`. 
     *
     * @param achievementID The ID of the achievement.
     * @param player        The player whose progress should be incremented.
     */
    public void incrementProgress(String achievementID, Player player) {

    }
	
}
