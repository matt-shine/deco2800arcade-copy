package deco2800.arcade.client.network.listener;

import com.esotericsoftware.kryonet.Connection;

import deco2800.arcade.communication.CommunicationNetwork;
import deco2800.arcade.protocol.communication.ChatHistory;
import deco2800.arcade.protocol.communication.TextMessage;

public class CommunicationListener extends NetworkListener {

	CommunicationNetwork communicationNetwork;

	public CommunicationListener(CommunicationNetwork communicationNetwork) {
		this.communicationNetwork = communicationNetwork;
	}

	@Override
	public void idle(Connection connection) {
		super.idle(connection);
	}

	@Override
	public void received(Connection connection, Object object) {
		super.received(connection, object);

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
