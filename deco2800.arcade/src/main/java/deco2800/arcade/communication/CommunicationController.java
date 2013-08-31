package deco2800.arcade.communication;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import deco2800.arcade.communication.CommunicationView;
import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.model.Player;
import deco2800.arcade.protocol.communication.TextMessage;

public class CommunicationController {
	
	private CommunicationView window;
	private NetworkClient networkClient;
	private TextMessage textMessage;
	private CommunicationModel model;
	private Player player;

	public CommunicationController(CommunicationView window, CommunicationModel model, NetworkClient networkClient, Player player){
		this.window = window;
		this.model = model;
		this.networkClient = networkClient;
		this.player = player;
		textMessage = new TextMessage();
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
		textMessage.username = player.getUsername();
		textMessage.chatID = participants.hashCode();
				
		for(String participant : participants){
			textMessage.recipient = participant;
			this.networkClient.sendNetworkObject(textMessage);
		}
		
		window.setTextInput("");
		window.input.requestFocus();
	}
	
	public void updateChat(String message){
		window.appendTextArea(message);
	}
	
	public void setFocus() {
		window.input.requestFocus();
	}
	
}
