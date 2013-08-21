package deco2800.arcade.client;

import java.util.ArrayList;
import deco2800.arcade.model.Game;
import deco2800.arcade.model.Player;
import deco2800.arcade.model.Achievement;
import deco2800.arcade.model.AchievementProgress;
import deco2800.arcade.protocol.achievement.*;
import deco2800.arcade.client.network.NetworkClient;

public class AchievementClient {

    private NetworkClient networkClient;

    public AchievementClient(NetworkClient networkClient) {
        this.networkClient = networkClient;
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
        //Because we do this this way we don't ever need to request data 
        //from the server for just a single ID... so i guess this saves writing
        //some packets and suchly.
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
        //see packet AchievementsForIDs in protocol
        return achievements;
    }
    
    /**
     * Returns an unsorted list of a game's achievements.
     *
     * @param game The game to fetch achievements for.
     * @return A list of Achievements for the supplied game.
     */
    public ArrayList<Achievement> achievementsForGame(Game game) {
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