package deco2800.arcade.client;

import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.protocol.multiplayerGame.*;
import deco2800.arcade.client.network.listener.*;

public class MultiplayerTest {
	
	private NetworkClient client;
	
	public MultiplayerTest(NetworkClient client) {
		this.client = client;
		client.addListener(new MultiplayerListener());
		NewMultiGameRequest newRequest = new NewMultiGameRequest();
		newRequest.requestType = MultiGameRequestType.NEW;
		newRequest.gameId = "1";
		newRequest.playerID = 1;
		System.out.println("Sending new multiplayer request");
		client.sendNetworkObject(newRequest);
		System.out.println("Sent new multiplayer request");
		
		
	}
	
	private void connectToServer() {
		NewMultiGameRequest newRequest = new NewMultiGameRequest();

	}
	
}
