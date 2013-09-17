package deco2800.arcade.client;

import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.model.Player;
import deco2800.arcade.protocol.BlockingMessage;
import deco2800.arcade.protocol.player.*;

public class PlayerClient {
	private NetworkClient networkClient = null;


	/**
	 * Loads a Player from the server database.
	 * 
	 * @param playerID
	 *            The player's playerID
	 * @return Returns the Player with the specified playerID.
	 */
	public Player loadPlayer(int playerID) {

		PlayerRequest request = new PlayerRequest();
		request.playerID = playerID;
		PlayerResponse response = (PlayerResponse)BlockingMessage.request(
				networkClient.kryoClient(), request);

		return response.player;
	}

}
