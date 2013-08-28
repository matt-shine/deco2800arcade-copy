package deco2800.arcade.communication;


import java.util.HashMap;
import java.util.Map;

import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.model.Player;
import deco2800.arcade.protocol.communication.ChatRequest;
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
			window.setWindowSize(500, 250);
			window.setWindowTitle("Chatting with: " + username);
			model = new CommunicationModel(username);
			controller = new CommunicationController(window, model, networkClient, player);
			controller.setFocus();
		}
		
		public void updateChat(TextMessage textMessage){
			controller.updateChat(textMessage.username + ": " + textMessage.text + "\n");
		}
		
	}
	
	/***********************************************************/
	
	public void updatePlayer(Player player){
		this.player = player;
	}
	
	public CommunicationNetwork(Player player, NetworkClient networkClient){
		this.player = player;
		this.networkClient = networkClient;
		currentChats = new HashMap<String, ChatWindow>();
	}
		
	// This is client-side, so we need a way for the server to tell a user, "Someone wants to create a chat with you, so createNewChat with them"
	public void createNewChat(String username){
		currentChats.put(username, new ChatWindow(username));
	}
	
	public void updateChat(String username, TextMessage message){
		currentChats.get(username).updateChat(message);
	}
	
	//Check if chat exists before making new window.
	public boolean checkIfChatExists(String username){
		if (currentChats.get(username) != null){
			return true;
		} else {
			return false;
		}		
	}
	
}
