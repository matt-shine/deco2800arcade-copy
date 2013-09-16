package deco2800.arcade.communication;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.model.Player;
import deco2800.arcade.protocol.communication.ChatRequest;
import deco2800.arcade.protocol.communication.TextMessage;

/**
 * Handles the list of individual chat instances. 
 * Allowing you to create, leave, join and delete chats.
 *
 */
public class CommunicationNetwork {
	
	private Player player;
	private NetworkClient networkClient;
	private Map<Integer, ChatNode> chatNodes;
	private TextMessage textMessage;
	private ChatRequest chatRequest;
	private CommunicationView view;
	private ChatNode currentChat;

	/**
	 * Initialises an empty list of chat instances.
	 * @param player
	 * @param networkClient
	 */
	public CommunicationNetwork(Player player, NetworkClient networkClient){
		System.out.println("Creating a CommunicationNetwork");
		
		this.player = player;
		this.networkClient = networkClient;
		this.chatNodes = new HashMap<Integer, ChatNode>();
		this.textMessage = new TextMessage();
		this.chatRequest = new ChatRequest();
		this.view = new CommunicationView();
		
		System.out.println(this.networkClient);
	}
	
	/**
	 * Creates a new chat instance containing a list of participants.
	 * Sends a chatRequest to each participant.
	 * @param chatParticipants
	 */
	public void createChat(List<String> chatParticipants){
		chatRequest.participants = chatParticipants;
		chatRequest.chatID = chatParticipants.hashCode();
		chatRequest.sender = player.getUsername();
		
		ChatNode node = new ChatNode(chatParticipants);
		chatNodes.put(chatRequest.chatID,node);
		currentChat = node;
		view.addChatNode(node);

		for(String participant : chatParticipants){
			if(!participant.equals(player.getUsername())){
				chatRequest.invite = participant;
				networkClient.sendNetworkObject(chatRequest);
			}
		}
	}
	
	/**
	 * Instead of creating a new chat and sending out chat requests, 
	 * joins an existing chat instance after receiving a chat request.
	 * @param request
	 */
	public void joinExistingChat(ChatRequest request){
		ChatNode node = new ChatNode(request.participants);
		chatNodes.put(request.chatID, node);
		currentChat = node;
		view.addChatNode(node);
	}
	
	/**
	 * Adds a message received to the chat history of the
	 * corresponding chat instance.
	 * @param textMessage
	 */
	public void recieveTextMesage(TextMessage textMessage){
		int chatID = textMessage.chatID;
		ChatNode node = chatNodes.get(chatID);
		node.addMessage(textMessage.text);
		System.out.println(textMessage.text);
	}
	
	
	/**
	 * Adds a user to an existing chat.
	 * @param chat
	 * @param playerId
	 */
	public void inviteUser(ChatNode chat, String playerId){
		ChatNode node = chat;
		chatRequest.participants = node.getParticipants();
		chatRequest.chatID = chat.getID();
		chatRequest.sender = player.getUsername();
		
		node.addParticipant(playerId);
		
		chatRequest.invite = playerId;
		networkClient.sendNetworkObject(chatRequest);
	}
	
	
	/**
	 * Leaves an existing chat instance.
	 * @param chatId
	 * @param playerId
	 */
	public void leaveChat(int chatId, String playerId){
		ChatNode node = chatNodes.get(chatId);
		node.removeParticipant(playerId);
	}
	
	/**
	 * Returns a map of current chat instances.
	 */
	public Map<Integer, ChatNode> getCurrentChats(){
		return chatNodes;
	}
	
	public ChatNode getCurrentChat(){
		return currentChat;
	}
	
	public void setCurrentChat(ChatNode chat){
		currentChat = chat;
	}
	
	/**
	 * Sends a text message to all the participants in a
	 * chat instance.
	 * @param chat
	 * @param message
	 */
	public void sendTextMessage(ChatNode chat, String message){
		List<String> participants = chat.getParticipants();
		chat.addMessage(message);
		textMessage.text = message;
		textMessage.username = player.getUsername();
		textMessage.chatID = chat.getID();
				
		for(String participant : participants){
			textMessage.recipient = participant;
			networkClient.sendNetworkObject(textMessage);
		}
	}
	
	/**
	 * Returns the player that is apart of the current chat instances.
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * Sets the player to be the owner of the current chat instances.
	 * @param player
	 */
	public void setPlayer(Player player) {
		this.player = player;
	}

	/**
	 * Returns the network client being used to communicate.
	 */
	public NetworkClient getNetworkClient() {
		return networkClient;
	}

	/**
	 * Sets the network client being used to communicate.
	 * @param networkClient
	 */
	public void setNetworkClient(NetworkClient networkClient) {
		this.networkClient = networkClient;
	}
	
	
}
