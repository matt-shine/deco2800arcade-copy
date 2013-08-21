package deco2800.server.listener;

import java.util.HashMap;
import java.util.Map;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

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
			
			//Send Message to everyone telling them user has connected.
			textMessage.text = contact.username + " connected!";
			this.server.sendToAllExceptTCP(connection.getID(), textMessage);
		}
		
		
		if(object instanceof TextMessage){
			textMessage = (TextMessage) object;
			this.server.sendToTCP(connectedUsers.get(textMessage.username), textMessage);
		}
	}

}
