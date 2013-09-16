package deco2800.arcade.client;

import java.util.ArrayList;
import java.util.HashSet;

import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.client.network.listener.NetworkListener;
import deco2800.arcade.model.Player;
import deco2800.arcade.protocol.BlockingMessage;
import deco2800.arcade.protocol.player.PlayerRequest;


public class PlayerClient extends NetworkListener {
	private NetworkClient networkClient;
	
	
	public PlayerClient(NetworkClient networkClient) {
		//TODO
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
    public Player loadPlayer(int id) {
        
        PlayerRequest request = new PlayerRequest();
        request.playerID = id;
        BlockingMessage response = BlockingMessage.request(networkClient.kryoClient(),
                                                           request);
        
       // AchievementsForIDsResponse resp = (AchievementsForIDsResponse)r;

	// We should do some aggressive caching of Achievements here because
	// they're immutable - once we've retrieved it from the server once
	// we shouldn't ever need to ask for it again.

        return null;
    }

}
