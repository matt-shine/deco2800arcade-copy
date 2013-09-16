package deco2800.arcade.client.network.listener;

import com.esotericsoftware.kryonet.Connection;

import deco2800.arcade.communication.CommunicationNetwork;
import deco2800.arcade.protocol.communication.ChatRequest;
import deco2800.arcade.protocol.communication.ChatResponse;
import deco2800.arcade.protocol.communication.TextMessage;

public class CommunicationListener extends NetworkListener {
	
	CommunicationNetwork communicationNetwork;
	
	public CommunicationListener(CommunicationNetwork communicationNetwork) {
		this.communicationNetwork = communicationNetwork;
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
		
		/*
		 * If a text message is received the message is added to the
		 * corresponding chat instance.
		 */
		if (object instanceof TextMessage){
			 TextMessage textMessage = (TextMessage) object;
			 communicationNetwork.recieveTextMesage(textMessage);
		}
		
		/*
		 * If a chat request is received the player will join the
		 * existing chat.
		 */
		if (object instanceof ChatRequest){
			System.out.println(communicationNetwork.getPlayer().getUsername() + " received chatRequest");
			
			ChatRequest chatRequest = (ChatRequest) object;
			communicationNetwork.joinExistingChat(chatRequest);
		}
		
		/*
		 * If a chat response is received and the participant is offline, 
		 * the participant is removed from the chat instance.
		 */
		if (object instanceof ChatResponse){
			System.out.println(communicationNetwork.getPlayer().getUsername() + " received chatResponse");
			
			ChatResponse chatResponse = (ChatResponse) object;
			if(chatResponse.response == "offline"){
				communicationNetwork.leaveChat(chatResponse.chatID, chatResponse.sender);
				System.out.println(chatResponse.sender + "has left the conversation");
			}
		}
		
	}
	
}
