package deco2800.arcade.client;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.HashMap;
import deco2800.arcade.model.Game;
import deco2800.arcade.model.Player;
import deco2800.arcade.model.Achievement;
import deco2800.arcade.model.AchievementProgress;
import deco2800.arcade.protocol.achievement.*;
import deco2800.arcade.protocol.*;
import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.client.AchievementListener;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Connection;
import deco2800.arcade.client.network.listener.NetworkListener;

public class AchievementClient extends NetworkListener {

    private NetworkClient networkClient;
    private HashSet<AchievementListener> listeners;
    private HashMap<String, Achievement> achievementsForIDsCache;

    public AchievementClient(NetworkClient networkClient) {
        this.listeners = new HashSet<AchievementListener>();
        this.achievementsForIDsCache = new HashMap<String, Achievement>();
    }

    public void setNetworkClient(NetworkClient client) {
        if(this.networkClient != null) {
            this.networkClient.removeListener(this);
        }
        this.networkClient = client;
        if(this.networkClient != null) {
            this.networkClient.addListener(this);
        }
    }

    @Override
    public void received(Connection connection, Object object) {
        super.received(connection, object);

        if(object instanceof IncrementProgressResponse) {
            final IncrementProgressResponse resp = (IncrementProgressResponse)object;
            // need to do this in a different thread as this method is going to stop other network
            // requests from being served
            new Thread(new Runnable() {
                    public void run() {
                        Achievement ach = achievementForID(resp.achievementID);
                        if(resp.newProgress == ach.awardThreshold) {
                            for(AchievementListener l : listeners) {
                                l.achievementAwarded(ach);
                            }
                        } else {
                            for(AchievementListener l : listeners) {
                                l.progressIncremented(ach, resp.newProgress);
                            }
                        }
                    }
            }).start();
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
    	ArrayList<String> nonCached = new ArrayList<String>();
    	ArrayList<Achievement> result = new ArrayList<Achievement>();
    	
    	//Only do query for non cached achievements (filter out already cached)
    	for(String ID:achievementIDs){
    		if(!achievementsForIDsCache.containsKey(ID)){
    			nonCached.add(ID);
    		}else{
    			result.add(achievementsForIDsCache.get(ID));
    		}
    	}
        
    	//Actually get the non-cached achievements from database
        AchievementsForIDsRequest request = new AchievementsForIDsRequest();
        request.achievementIDs = nonCached;
        BlockingMessage r = BlockingMessage.request(networkClient.kryoClient(),
                                                           request);
        AchievementsForIDsResponse response = (AchievementsForIDsResponse)r;
        
        //Add newly retrieved achievements to cache and to return result
        for(Achievement achievement: response.achievements){
        	achievementsForIDsCache.put(achievement.id, achievement);
        	result.add(achievementsForIDsCache.get(achievement.id));
        }

        return result;
    }
    
    /**
     * Returns an unsorted list of a game's achievements.
     *
     * @param game The game to fetch achievements for.
     * @return A list of Achievements for the supplied game.
     */
    public ArrayList<Achievement> achievementsForGame(Game game) {
        AchievementsForGameRequest req = new AchievementsForGameRequest();
        req.gameID = game.id;
        
        BlockingMessage r = BlockingMessage.request(networkClient.kryoClient(),
                                                       req);
        AchievementsForGameResponse resp = (AchievementsForGameResponse)r;
        return resp.achievements;
    }

    /**
     * Returns an AchievementProgress instance representing the player's
     * progress in every achievement.
     *
     * @param player The player to fetch achievement progress for.
     * @return An AchievementProgress instance with the player's progress.
     */
    public AchievementProgress progressForPlayer(Player player) {
    	ProgressForPlayerRequest request = new ProgressForPlayerRequest();
    	request.playerID = player.getID();
    	
    	BlockingMessage r = BlockingMessage.request(networkClient.kryoClient(),
                request);
    	
    	ProgressForPlayerResponse response = (ProgressForPlayerResponse) r;
    	
        //HashMap<String, Integer> progress = new HashMap<String, Integer>();
        //HashMap<String, Boolean> awarded = new HashMap<String, Boolean>();
        return response.achievementProgress;
    }
    
    /**
     * Increments the player's progress for the achievement with ID
     * `achievementID`.
     *
     * @param achievementID The ID of the achievement.
     * @param player        The player whose progress should be incremented.
     */
    public void incrementProgress(String achievementID, Player player) {
    	IncrementProgressRequest request = new IncrementProgressRequest();
    	request.achievementID = achievementID;
    	request.playerID = player.getID();
    	
    	networkClient.sendNetworkObject(request);
        
    }

    /**
     * Adds a listener to be notified of achievement events. The listener is
     * only notified of events after they've been confirmed by the server.
     *
     * (This is used to power GameClient's achievement overlay)
     *
     * @param listener The listener to add.
     */
    public void addListener(AchievementListener listener) {
        listeners.add(listener);
    }

    /**
     * Removes an achievement listener. If the listener is null or wasn't added
     * in the first place, nothing happens.
     *
     * @param listener The listener to remove.
     */
    public void removeListener(AchievementListener listener) {
        listeners.remove(listener);
    }

}