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
	
	public CommunicationListener(Server server) {
		this.server = server;
		textMessage = new TextMessage();
		connectedUsers = new HashMap<String, Integer>();
	}
	
	@Override
	public void received(Connection connection, Object object) {
		super.received(connection, object);
		

		if (object instanceof CommunicationRequest){
			//Add users connectionID to list of connected users.
			CommunicationRequest contact = (CommunicationRequest) object;
			connectedUsers.put(contact.username, connection.getID());
			System.out.println(contact.username + " connected to the server with ID: " + connection.getID());
			
			//Send Message to everyone telling them user has connected.
			//textMessage.text = contact.username + " connected!";
			//this.server.sendToAllExceptTCP(connection.getID(), textMessage);
		}
		
		if(object instanceof ChatRequest){
			ChatRequest chatRequest = (ChatRequest) object;
			ChatResponse chatResponse = new ChatResponse();
			
			if (chatRequest.invite != null){
				if (connectedUsers.containsKey(chatRequest.invite)){	//Check if the invited user is online
					chatResponse.response = "available";
					chatResponse.invite = chatRequest.invite;
					chatResponse.chatID = chatRequest.chatID;
					chatResponse.sender = chatRequest.sender;
					this.server.sendToTCP(connectedUsers.get(chatRequest.invite), chatRequest);	//Send the chatRequest to the invited user
					chatRequest.participants.remove(chatRequest.invite);	//Don't send the response to the person you are inviting
					for (String participant : chatRequest.participants){
						this.server.sendToTCP(connectedUsers.get(participant), chatResponse);	//Send the chatResponse to the chat participants
					}
				} else {
					chatResponse.response = "unavailable";
					chatResponse.invite = chatRequest.invite;
					chatResponse.chatID = chatRequest.chatID;
					chatResponse.sender = chatRequest.sender;
					chatRequest.participants.remove(chatRequest.invite);	//Remove the invited person from the participants as they are unavailable
					for (String participant : chatRequest.participants){
						this.server.sendToTCP(connectedUsers.get(participant), chatResponse); //Send the chatResponse to the chat participants
					}
				}
			} else {
				for (String participant : chatRequest.participants){
					this.server.sendToTCP(connectedUsers.get(participant), chatRequest);
				}
			}
		}
				
		if(object instanceof TextMessage){
			textMessage = (TextMessage) object;
			this.server.sendToTCP(connectedUsers.get(textMessage.recipient), textMessage);
		}
	}

}
