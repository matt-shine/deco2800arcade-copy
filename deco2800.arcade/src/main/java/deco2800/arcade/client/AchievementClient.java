package deco2800.arcade.client;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;
import deco2800.arcade.utils.Handler;
import deco2800.arcade.utils.AsyncFuture;
import deco2800.arcade.model.Game;
import deco2800.arcade.model.Player;
import deco2800.arcade.model.Achievement;
import deco2800.arcade.model.AchievementStatistics;
import deco2800.arcade.model.AchievementProgress;
import deco2800.arcade.protocol.achievement.*;
import deco2800.arcade.protocol.*;
import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.client.AchievementListener;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Connection;
import com.badlogic.gdx.Gdx;
import deco2800.arcade.client.network.listener.NetworkListener;

public class AchievementClient extends NetworkListener {

    private NetworkClient networkClient;
    private HashSet<AchievementListener> listeners;
    private ConcurrentHashMap<String, Achievement> achievementsCache;

    public AchievementClient(NetworkClient networkClient) {
        this.listeners = new HashSet<AchievementListener>();
        this.achievementsCache = new ConcurrentHashMap<String, Achievement>();
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

    /**
     * Legacy method that's now a wrapper around getAchievementForID - you 
     * should move to using that method in order to control whether you want
     * the request to block or not.
     */
    @Deprecated
    public Achievement achievementForID(String achievementID) {
	return getAchievementForID(achievementID).get();	
    }
    
    /**
     * Returns a future which will be provided with the Achievement
     * corresponding to the given achievement ID. If no such achievement
     * exists, the future will be cancelled.    
     *
     * @param achievementID The ID of the achievement to request.
     * @return A future to be provided with the request's result.
     */
    public AsyncFuture<Achievement> getAchievementForID(String achievementID) {
	// we'll just wrap the list-based version and provide the future
	// with the first element of that version's future
	ArrayList<String> ids = new ArrayList<String>();
	ids.add(achievementID);

	final AsyncFuture<Achievement> future = new AsyncFuture<Achievement>();	
	final AsyncFuture<ArrayList<Achievement>> listFuture = getAchievementsForIDs(ids);
	
	listFuture.setHandler(new Handler<ArrayList<Achievement>> () {
	    public void handle(ArrayList<Achievement> response) {
		if (response == null)
		    future.cancel(false);
		else
		    future.provide(response.get(0));
	    }
	});
	
	return future;
    }
    
    /**
     * Legacy method that's now a wrapper around getAchievementsForIDs - you 
     * should move to using that method in order to control whether you want
     * the request to block or not.
     */
    @Deprecated
    public ArrayList<Achievement> achievementsForIDs(
	    ArrayList<String> achievementIDs) {
	return getAchievementsForIDs(achievementIDs).get();
    }

    public AsyncFuture<ArrayList<Achievement>> getAchievementsForIDs(
	    final ArrayList<String> achievementIDs) {
	final AsyncFuture<ArrayList<Achievement>> future = 
	    new AsyncFuture<ArrayList<Achievement>>();
	ArrayList<String> nonCached = new ArrayList<String>();
    	
	// see if there's any non-cached IDs that we'll need to request
    	for(String id : achievementIDs){
	    if (!achievementsCache.containsKey(id))
		nonCached.add(id);
    	}

	if (!nonCached.isEmpty()) {
	    // make a request if we need to cache any
	    AchievementsForIDsRequest req = new AchievementsForIDsRequest();
	    req.achievementIDs = nonCached;
	    
	    AsyncFuture<NetworkObject> response = networkClient.request(req);
	    response.setHandler(new Handler<NetworkObject>() {
		public void handle(NetworkObject obj) {
		    AchievementsForIDsResponse resp = (AchievementsForIDsResponse)obj;

		    // just fill in the cache
		    for (Achievement ach : resp.achievements) {
			achievementsCache.put(ach.id, ach);
		    }

		    // and then build our result (this is a separate pass to make sure
		    // the result has the same order as the list of IDs)
		    ArrayList<Achievement> result = new ArrayList<Achievement>();
		    for (String id : achievementIDs) {
			result.add(achievementsCache.get(id));
		    }
		    
		    future.provide(result);
		}
	    });

	} else {
	    // otherwise we're able to just provide the future now
	    ArrayList<Achievement> result = new ArrayList<Achievement>();
	    for (String id : achievementIDs) {
		result.add(achievementsCache.get(id));
	    }

	    future.provide(result);
	}
        
        return future;

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

    /**
     * Implementation for servicing IncrementProgressResponses and dispatching
     * the appropriate calls to listeners.
     */
    @Override
    public void received(Connection connection, Object object) {
        super.received(connection, object);

        if(object instanceof IncrementProgressResponse) {
            final IncrementProgressResponse resp = (IncrementProgressResponse)object;
            // need to do this in a different thread as this method is going to stop other network
            // requests from being served
            new Thread(new Runnable() {
                    public void run() {
                        final Achievement ach = achievementForID(resp.achievementID);
			// want to post these on the GDX thread so we can actually display overlays
			Gdx.app.postRunnable(new Runnable() {
			    public void run() {
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
			});
		    }
            }).start();
        }
    }
}
