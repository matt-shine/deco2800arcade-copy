package deco2800.arcade.client.network.listener;

import com.esotericsoftware.kryonet.Connection;

import deco2800.arcade.protocol.multiplayerGame.NewMultiGameRequest;
import deco2800.arcade.protocol.multiplayerGame.NewMultiResponse;

public class MultiplayerListener extends NetworkListener {

	@Override
	public void connected(Connection connection) {
		super.connected(connection);
	}

	@Override
	public void disconnected(Connection connection) {
		super.disconnected(connection);
	}

	@Override
	public void idle(Connection connection) {
		super.idle(connection);
	}

	@Override
	public void received(Connection connection, Object object) {
		super.received(connection, object);
		
		if (object instanceof NewMultiGameRequest){
			
			System.out.println("Recieved message from server.");
		} else if (object instanceof NewMultiResponse) {
			NewMultiResponse response = (NewMultiResponse) object;
			if (response.OK == NewMultiResponse.OK) {
				System.out.println("Connect OK");
			}
		}
		else if (object instanceof String) {
			System.out.println("Game Found");
		} 
	}
	
}
