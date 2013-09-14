package deco2800.arcade.communication;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.model.Player;
import deco2800.arcade.protocol.communication.ChatRequest;
import deco2800.arcade.protocol.communication.TextMessage;

public class CommunicationNetwork {
	
	private Player player;
	private NetworkClient networkClient;
	private Map<Integer, ChatNode> chatNodes;
	private TextMessage textMessage;
	private ChatRequest chatRequest;

	public CommunicationNetwork(Player player, NetworkClient networkClient){
		this.player = player;
		this.networkClient = networkClient;
		this.chatNodes = new HashMap<Integer, ChatNode>();
		this.textMessage = new TextMessage();
		this.chatRequest = new ChatRequest();
	}
	
	public void createChat(List<String> chatParticipants){
		chatRequest.participants = chatParticipants;
		chatRequest.chatID = chatParticipants.hashCode();
		chatRequest.sender = player.getUsername();
		
		chatNodes.put(chatRequest.chatID,new ChatNode(chatParticipants));

		for(String participant : chatParticipants){
			if(participant != player.getUsername()){
				chatRequest.invite = participant;
				networkClient.sendNetworkObject(chatRequest);
			}
		}
	}
	
	public void joinExistingChat(ChatRequest request){
		chatNodes.put(request.chatID, new ChatNode(request.participants));
	}
	
	public void inviteUser(int chatID, String playerId){
		ChatNode node = chatNodes.get(chatID);
		chatRequest.participants = node.getParticipants();
		chatRequest.chatID = chatID;
		chatRequest.sender = player.getUsername();
		
		node.addParticipant(playerId);
		
		chatRequest.invite = playerId;
		networkClient.sendNetworkObject(chatRequest);
	}
	
	public void leaveChat(int chatID, String playerId){
		ChatNode node = chatNodes.get(chatID);
		node.removeParticipant(playerId);
	}
	
	public Map<Integer, ChatNode> getCurrentChats(){
		return chatNodes;
	}
	
	public void sendTextMessage(ChatNode chat, String message){
		List<String> participants = chat.getParticipants();
		textMessage.text = message;
		textMessage.username = player.getUsername();
		textMessage.chatID = participants.hashCode();
				
		for(String participant : participants){
			textMessage.recipient = participant;
			networkClient.sendNetworkObject(textMessage);
		}
	}
	
	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public NetworkClient getNetworkClient() {
		return networkClient;
	}

	public void setNetworkClient(NetworkClient networkClient) {
		this.networkClient = networkClient;
	}
	
	
}
