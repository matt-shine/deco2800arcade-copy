package deco2800.arcade.client;


import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.client.network.listener.NetworkListener;
import deco2800.arcade.model.Player;
import deco2800.arcade.protocol.BlockingMessage;
import deco2800.arcade.protocol.player.*;

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
	 * Loads a Player from the server database.
	 * 
	 * @param playerID
	 *            The player's playerID
	 * @return Returns the Player with the specified playerID.
	 */
	public Player loadPlayer(int playerID) {

		PlayerRequest request = new PlayerRequest();
		request.setPlayerID(playerID);
		PlayerResponse response = (PlayerResponse)BlockingMessage.request(
				networkClient.kryoClient(), request);

		return response.getPlayer();
	}


}
