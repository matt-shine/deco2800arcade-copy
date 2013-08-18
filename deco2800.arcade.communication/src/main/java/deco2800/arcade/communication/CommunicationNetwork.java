package deco2800.arcade.communication;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.model.Player;
import deco2800.arcade.protocol.communication.TextMessage;
import deco2800.arcade.protocol.communication.VoiceMessage;

public class CommunicationNetwork {
	
	protected Player player; 
	protected NetworkClient networkClient;
	private TextMessage textMessage;
	@SuppressWarnings("unused")
	private VoiceMessage voiceMessage;

	public CommunicationNetwork(Player player, NetworkClient networkClient){
		this.player = player;
		this.networkClient = networkClient;
		this.textMessage = new TextMessage();
		//textMessage.username = player.getUsername();
	}
	
	public void createInterface(){
		JFrame chat = new JFrame("Testing Chat");
		chat.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container container = chat.getContentPane();
		JTextArea textarea = new JTextArea(5,20);
		textarea.setEditable(false);
		final JTextArea input = new JTextArea(5,20);
		JScrollPane scrollPane = new JScrollPane(textarea);
		JButton button = new JButton("SEND");
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				sendTextMessage(input.getText());
			}
		});
		container.add(button, BorderLayout.SOUTH);
		container.add(scrollPane, BorderLayout.NORTH);
		container.add(input, BorderLayout.CENTER);
		chat.pack();
		chat.setVisible(true);
		
		
		

	}
	
	
	public void sendTextMessage(String message){
		textMessage.text = message;
		System.out.println(message);
		this.networkClient.sendNetworkObject(textMessage);
	}
	
	public void sendVoiceMessage() {
	}
	
	

	
}
