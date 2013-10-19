package deco2800.arcade.client.network.listener;

import com.esotericsoftware.kryonet.Connection;

import deco2800.arcade.client.Arcade;
import deco2800.arcade.client.ArcadeSystem;
import deco2800.arcade.protocol.lobby.LobbyMessageRequest;
import deco2800.arcade.protocol.lobby.LobbyMessageResponse;


public class LobbyMessageListener extends NetworkListener {
	
	Arcade arcade;
	
	/**
	 * The constructor for the listener.
	 * Takes an arcade to allow methods to be called
	 * @param arcade The client arcade
	 */
	public LobbyMessageListener(Arcade arcade) {
		this.arcade = arcade;
	}

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
		
		//Need to fix this up
		/*
		if (object instanceof LobbyMessageRequest) {
		LobbyMessageRequest request = (LobbyMessageRequest)object;
		request.playerID = arcadeUI.getPlayer().getID();
		request.message = arcadeUI.getLobby().chatfield.getText();
		
		LobbyMessageResponse response = new LobbyMessageResponse();
        response.playerID = arcadeUI.getPlayer().getID();
		response.message = arcadeUI.getLobby().chatfield.getText();
        connection.sendTCP(response);
}


else if (object instanceof LobbyMessageResponse) {


updateChat(LobbyMessageResponse response);

arcade.getLobby().updateChat((LobbyMessageResponse)object);
 
}
*/
}
	
		} 
		
	


