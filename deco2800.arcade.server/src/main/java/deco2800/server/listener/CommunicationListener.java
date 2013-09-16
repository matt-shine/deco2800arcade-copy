package deco2800.server.listener;

import java.util.HashMap;
import java.util.Map;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import deco2800.arcade.protocol.communication.ChatRequest;
import deco2800.arcade.protocol.communication.ChatResponse;
import deco2800.arcade.protocol.communication.CommunicationRequest;
import deco2800.arcade.protocol.communication.TextMessage;

public class CommunicationListener extends Listener {
	
	private Server server;
	private Map<String, Integer> connectedUsers;
	private TextMessage textMessage;
	private ChatResponse chatResponse;
	
	public CommunicationListener(Server server) {
		this.server = server;
		this.textMessage = new TextMessage();
		this.connectedUsers = new HashMap<String, Integer>();
	}
	
	@Override
	public void received(Connection connection, Object object) {
		super.received(connection, object);
		
		System.out.println("Server received SOMETHING");

		if (object instanceof CommunicationRequest){
			CommunicationRequest contact = (CommunicationRequest) object;
			connectedUsers.put(contact.username, connection.getID());
			System.out.println("Server just added: " + contact.username);
		}
		
		if(object instanceof ChatRequest){
			ChatRequest chatRequest = (ChatRequest) object;
			
			System.out.println("Received chatRequest for: " + chatRequest.invite + " from: " + chatRequest.sender);
			
			chatResponse.response = "offline";
			chatResponse.chatID = chatRequest.chatID;
			chatResponse.sender = chatRequest.invite;
			
			if(participantOnline(chatRequest.invite)){
				chatResponse.response = "online";
				server.sendToTCP(connectedUsers.get(chatRequest.invite), chatRequest);
			}
			
			for (String participant : chatRequest.participants){
				server.sendToTCP(connectedUsers.get(participant), chatResponse);
			}
		}

				
		if(object instanceof TextMessage){
			textMessage = (TextMessage) object;
			server.sendToTCP(connectedUsers.get(textMessage.recipient), textMessage);
		}
	}
	
	private boolean participantOnline(String participant){
		return connectedUsers.containsKey(participant);
	}

}
