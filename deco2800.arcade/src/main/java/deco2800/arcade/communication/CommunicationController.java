package deco2800.arcade.communication;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import deco2800.arcade.communication.CommunicationView;
import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.protocol.communication.TextMessage;

public class CommunicationController {
	
	private CommunicationView window;
	private NetworkClient networkClient;
	private TextMessage textMessage;

	public CommunicationController(CommunicationView window, NetworkClient networkClient){
		this.window = window;
		this.networkClient = networkClient;
		
		window.addSendButtonListener(new SendButtonActionListener());
	}
	
	private class SendButtonActionListener implements ActionListener {
		
		public void actionPerformed(ActionEvent event) {
			System.out.println("hello");
			sendTextMessage("debuguser", window.getTextInput());
		}
	
	}
	
	
	private void sendTextMessage(String username, String message){
		textMessage.text = message;
		textMessage.username = username;
		this.networkClient.sendNetworkObject(textMessage);
		window.setTextInput("");
	}
	
}
