package deco2800.arcade.communication;

import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.model.Player;
import deco2800.arcade.protocol.communication.VoiceMessage;

public class CommunicationNetwork {
	
	protected Player player; 
	protected NetworkClient networkClient;
	@SuppressWarnings("unused")
	private VoiceMessage voiceMessage;

	public CommunicationNetwork(Player player, NetworkClient networkClient){
		this.player = player;
		this.networkClient = networkClient;
	}
	
	public void createNewChat(){
		new ChatWindow();
	}
	

	
}
