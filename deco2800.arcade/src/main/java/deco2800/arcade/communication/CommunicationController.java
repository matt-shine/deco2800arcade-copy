package deco2800.arcade.communication;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import deco2800.arcade.communication.CommunicationView;
import deco2800.arcade.model.ChatNode;
import deco2800.arcade.protocol.communication.TextMessage;

public class CommunicationController {

	private CommunicationView view;
	private CommunicationNetwork network;
	private CommunicationModel model;

	public CommunicationController(CommunicationView view, CommunicationModel model, CommunicationNetwork network){
		this.view = view;
		this.model = model;
		this.network = network;
		
		view.addSendListener(new SendListener());
	}
	
	private class SendListener implements ActionListener {

		public void actionPerformed(ActionEvent event) { //You pressed Send
			ChatNode node = network.getCurrentChat();
			//This should never be null, because the chat window won't be open if it leads to nowhere... but it is during testing
			if (node != null){				
				TextMessage message = new TextMessage();
				message.setChatID(node.getID()); //Is this right?
				message.setSenderID(network.getPlayer().getID());
				message.setText(view.getMessage());
				
				if (node.getParticipants() == null){
					System.out.println("You are trying to send to nobody! This won't happen normally because a chat window will only be open if you have someone to talk to. "
							+ "It will however, happen during testing because this chat window is open by default!");
				} else {
					message.setRecipients(node.getParticipants());
					network.sendTextMessage(message);
				}
			}
		}
	}
	
	
	
}
