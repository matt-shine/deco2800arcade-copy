package deco2800.arcade.communication;


import java.util.HashMap;
import java.util.Map;

import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.model.Player;
import deco2800.arcade.protocol.communication.TextMessage;
import deco2800.arcade.protocol.communication.VoiceMessage;

public class CommunicationNetwork {
	
	protected Player player;
	protected NetworkClient networkClient;
	protected Map<String, ChatWindow> currentChats;
	
	@SuppressWarnings("unused")
	private VoiceMessage voiceMessage;
	
	
	
	/************************************************************/
	
	public class ChatWindow {
		private CommunicationView window;
		private CommunicationController controller;
		private CommunicationModel model;
		
		public ChatWindow(String username){
			window = new CommunicationView();
			model = new CommunicationModel(username);
			controller = new CommunicationController(window, model, networkClient, player);
		}
		
		public void updateChat(TextMessage textMessage){
			controller.updateChat(textMessage.username + ": " + textMessage.text);
		}
	}
	
	/***********************************************************/
	
	

	public CommunicationNetwork(Player player, NetworkClient networkClient){
		this.player = player;
		this.networkClient = networkClient;
		currentChats = new HashMap<String, ChatWindow>();
	}
	
	public void createNewChat(String username){
		currentChats.put(username, new ChatWindow(username));
	}
	
	public void updateChat(String username, TextMessage message){
		ChatWindow tempWindow = currentChats.get(username);
		tempWindow.updateChat(message);
	}
	
	//TODO Check if chat exists before making new window.
	public void checkIfChatExists(){
		
	}
	
}
