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
		//this.player = player;
		this.player = new Player();
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
		
		System.out.println("I want to send: ");
		System.out.println(textMessage.text);
		
		player.setUsername("debuguser1");
		textMessage.username = player.getUsername();
		
		for(String username : participants){
			textMessage.recipient = username;
			
			System.out.println("I want to send to: ");
			System.out.println(textMessage.recipient);
			
			System.out.println("I am sending from: ");
			System.out.println(textMessage.username);
			
			this.networkClient.sendNetworkObject(textMessage);
		}
		
		window.setTextInput("");
	}
	
	public void updateChat(String message){
		window.appendTextArea(message);
	}
	
}
