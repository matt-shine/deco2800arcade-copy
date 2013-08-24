package deco2800.server.listener;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import deco2800.arcade.protocol.communication.CommunicationRequest;
import deco2800.arcade.protocol.communication.TextMessage;

public class CommunicationListener extends Listener {
	
	private Server server;
	
	public CommunicationListener(Server server) {
		this.server = server;
	}
	
	@Override
	public void received(Connection connection, Object object) {
		super.received(connection, object);

		if (object instanceof CommunicationRequest){
			CommunicationRequest contact = (CommunicationRequest) object;
			TextMessage message = new TextMessage();
			message.text = contact.username + " connected!";
			this.server.sendToAllExceptTCP(connection.getID(), message);
		}
		
		if(object instanceof TextMessage){
			TextMessage textMessage = (TextMessage) object;
			System.out.println(textMessage.text);
			this.server.sendToAllTCP(textMessage);
		}
	}

}
