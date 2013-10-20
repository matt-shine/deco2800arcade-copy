package deco2800.arcade.client.network.listener;

import com.esotericsoftware.kryonet.Connection;

import deco2800.arcade.client.Arcade;
import deco2800.arcade.communication.CommunicationNetwork;
import deco2800.arcade.protocol.communication.ChatHistory;
import deco2800.arcade.protocol.communication.TextMessage;
import deco2800.arcade.protocol.connect.ConnectionResponse;
import deco2800.arcade.protocol.player.PlayerResponse;

public class CommunicationListener extends NetworkListener {

	CommunicationNetwork communicationNetwork;
	private Arcade arcade = null;

	public CommunicationListener(CommunicationNetwork communicationNetwork, Arcade arcade) {
		this.communicationNetwork = communicationNetwork;
		this.arcade = arcade;
	}

	@Override
	public void idle(Connection connection) {
		super.idle(connection);
	}

	@Override
	public void received(Connection connection, Object object) {
		super.received(connection, object);
		
		/**
		 * Server is replying to our login attempt
		 */
		if (object instanceof ConnectionResponse){
			ConnectionResponse response = (ConnectionResponse) object;
			if (response.playerID >= 0){
				if (response.register == false){
					arcade.loadPlayer(response.playerID);
				}
			} else if (response.playerID == -1){
				System.out.println("Incorrect password");
			}
		}
		
		/**
		 * PlayerClient is replying to our loadPlayer call
		 */
		if (object instanceof PlayerResponse){
			PlayerResponse response = (PlayerResponse) object;
			if (response.getPlayer() != null){
				arcade.connectAsUser(response.getPlayer());
			}
		}
		

		/**
		 * If a text message is received, the message is added to the
		 * corresponding chat instance.
		 */
		if (object instanceof TextMessage) {
			TextMessage textMessage = (TextMessage) object;
			communicationNetwork.recieveTextMesage(textMessage);
		}

		/**
		 * If ChatHistory is received, the communication network will handle it
		 */
		if (object instanceof ChatHistory) {
			ChatHistory chatHistory = (ChatHistory) object;
			communicationNetwork.receiveChatHistory(chatHistory);
		}
	}

}
