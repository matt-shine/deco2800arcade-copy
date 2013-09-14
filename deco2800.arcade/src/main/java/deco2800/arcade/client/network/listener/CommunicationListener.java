package deco2800.arcade.client.network.listener;

import com.esotericsoftware.kryonet.Connection;

import deco2800.arcade.communication.CommunicationNetwork;
import deco2800.arcade.protocol.communication.ChatRequest;
import deco2800.arcade.protocol.communication.ChatResponse;
import deco2800.arcade.protocol.communication.TextMessage;

public class CommunicationListener extends NetworkListener {
	
	CommunicationNetwork communicationNetwork;
	
	public CommunicationListener(CommunicationNetwork communicationNetwork) {
		// TODO Auto-generated constructor stub
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
		
		if (object instanceof TextMessage){
			 TextMessage textMessage = (TextMessage) object;
		}
		
		if (object instanceof ChatRequest){
			ChatRequest chatRequest = (ChatRequest) object;
			communicationNetwork.joinExistingChat(chatRequest);
		}
		
		if (object instanceof ChatResponse){
			ChatResponse chatResponse = (ChatResponse) object;
			if(chatResponse.response == "offline"){
				communicationNetwork.leaveChat(chatResponse.chatID, chatResponse.sender);
				System.out.println(chatResponse.sender + "has left the conversation");
			}
		}
		
	}
	
}
