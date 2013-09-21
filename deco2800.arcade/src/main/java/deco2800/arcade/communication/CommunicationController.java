package deco2800.arcade.communication;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import deco2800.arcade.communication.CommunicationView;
import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.protocol.communication.TextMessage;
//TODO commenting?
public class CommunicationController {
	
	private CommunicationView window;
	private NetworkClient networkClient;
	private TextMessage textMessage;
	private CommunicationModel model;

	public CommunicationController(CommunicationView window, CommunicationModel model, NetworkClient networkClient){
		this.window = window;
		this.model = model;
		this.networkClient = networkClient;
		
		window.addSendButtonListener(new SendButtonActionListener());
	}
	
	private class SendButtonActionListener implements ActionListener {
		
		public void actionPerformed(ActionEvent event) {
			sendTextMessage(window.getTextInput());
		}
	}
	
	private void sendTextMessage(String message){
		ArrayList<String> participants = model.getParticipants();
		textMessage.text = message;
		
		for(String username : participants){
			textMessage.recipient = username;
			this.networkClient.sendNetworkObject(textMessage);
		}
		
		window.setTextInput("");
	}
	
	public void updateChat(String message){
		window.appendTextArea(message);
	}
	
}
