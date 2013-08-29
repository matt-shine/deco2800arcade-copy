package deco2800.server.listener;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import deco2800.arcade.protocol.communication.ChatRequest;
import deco2800.arcade.protocol.communication.CommunicationRequest;
import deco2800.arcade.protocol.communication.TextMessage;
import deco2800.arcade.protocol.communication.UserQuery;

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
			List<String> participants = Arrays.asList(chatRequest.participants.split(","));

			if (chatRequest.invite != null){
				this.server.sendToTCP(connectedUsers.get(chatRequest.invite), chatRequest);
			} else {
				for (String participant : participants){
					this.server.sendToTCP(connectedUsers.get(participant), chatRequest);
				}
			}
		}
		
		if (object instanceof UserQuery){
			// Check if a user is online
			UserQuery userQuery = (UserQuery) object;
			if (connectedUsers.containsKey(userQuery.username)){
				userQuery.response = true;
			} else {
				userQuery.response = false;
			}
			this.server.sendToTCP(connectedUsers.get(userQuery.sender), userQuery);
		}
		
		if(object instanceof TextMessage){
			textMessage = (TextMessage) object;
			this.server.sendToTCP(connectedUsers.get(textMessage.recipient), textMessage);
		}
	}

}
