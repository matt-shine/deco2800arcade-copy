package deco2800.arcade.communication;


import java.util.HashMap;
import java.util.Map;

import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.model.Player;
import deco2800.arcade.protocol.communication.VoiceMessage;

public class CommunicationNetwork {
	
	protected Player player;
	protected NetworkClient networkClient;
	protected Map<String, ChatWindow> currentChats;
	
	@SuppressWarnings("unused")
	private VoiceMessage voiceMessage;
	
	public class ChatWindow {
		CommunicationView window;
		CommunicationController controller;
		
		public ChatWindow(){
			window = new CommunicationView();
			controller = new CommunicationController(window, networkClient);
		}
	}

	public CommunicationNetwork(Player player, NetworkClient networkClient){
		this.player = player;
		this.networkClient = networkClient;
		currentChats = new HashMap<String, ChatWindow>();
	}
	
	public void createNewChat(String username){
		currentChats.put(username, new ChatWindow());
	}
	
	public void updateChat(String username){
		//get chatwindow from map and update textarea.
	}
	
}
