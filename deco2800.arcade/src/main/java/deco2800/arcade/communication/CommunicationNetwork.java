package deco2800.arcade.communication;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.model.ChatNode;
import deco2800.arcade.model.Player;
import deco2800.arcade.protocol.communication.ChatHistory;
import deco2800.arcade.protocol.communication.TextMessage;

/**
 * Handles the list of individual chat instances. Allowing you to create, leave,
 * join and delete chats.
 * 
 */
public class CommunicationNetwork {

	private Player player;
	private NetworkClient networkClient;
	//private Map<Integer, ChatNode> chatNodes = new HashMap<Integer, ChatNode>();
	private HashMap<Integer, ChatNode> chatNodes = new HashMap<Integer, ChatNode>();
	private TextMessage textMessage;
	private CommunicationView view = null;
	private ChatNode currentChat;
	private CommunicationController controller;
	private ChatHistory chatHistory;

	/**
	 * Initialises an empty list of chat instances.
	 * 
	 * @param player
	 * @param networkClient
	 */
	public CommunicationNetwork(Player player, NetworkClient networkClient) {
		this.player = player;
		this.networkClient = networkClient;
	}

	/**
	 * Creates a chatNode with the playerIDs of the participants
	 * 
	 * @param chatParticipants
	 */
	public void createChat(List<Integer> chatParticipants) {
		if(!chatNodes.containsKey(chatParticipants.hashCode())) {
			ChatNode node = new ChatNode(chatParticipants);
			chatNodes.put(chatParticipants.hashCode(), node);
			currentChat = node;
		}
	}

	/**
	 * Forwards the TextMessage to the server so it can handle it
	 * 
	 * @param message
	 */
	public void sendTextMessage(TextMessage message) {
		networkClient.sendNetworkObject(message);
	}

	/**
	 * Adds a message received to the chat history of the corresponding chat
	 * instance.
	 * 
	 * @param textMessage
	 */
	public void recieveTextMesage(TextMessage textMessage) {
		// TO DO:
		// If you don't have the window open, then display a notification in the
		// chat area
		// else, display the message in the open chat window
		int chatID = textMessage.getChatID();
		ChatNode node = chatNodes.get(chatID);
		if (node == null) {
			node = new ChatNode(textMessage.getRecipients());
			chatNodes.put(textMessage.getRecipients().hashCode(), node);
		}
		node.addMessage(textMessage.getText());
		currentChat = node;

		//Shouldn't be null, but current tests are written from the point of view that no-one is logged in I believe
		if (view != null){
			view.receiveText(textMessage);
		}
		
		// Temporary:
		//System.out.println(textMessage.getSenderUsername() + ": " + textMessage.getText());
	}

	/**
	 * Adds a user to an existing chat.
	 * 
	 * @param chat
	 * @param playerId
	 */
	public void inviteUser(ChatNode chat, int playerId) {
		ChatNode node = chat;
		node.addParticipant(playerId);
	}

	/**
	 * Leaves an existing chat instance.
	 * 
	 * @param chatId
	 * @param playerId
	 */
	public void leaveChat(int chatId, int playerId) {
		ChatNode node = chatNodes.get(chatId);
		node.removeParticipant(playerId);
	}

	/**
	 * Returns a map of current chat instances.
	 */
	//public Map<Integer, ChatNode> getCurrentChats() {
	public HashMap<Integer, ChatNode> getCurrentChats() {
		return chatNodes;
	}

	public ChatNode getCurrentChat() {
		return currentChat;
	}

	public void setCurrentChat(ChatNode chat) {
		currentChat = chat;
	}

	/**
	 * Returns the player that is apart of the current chat instances.
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * Sets the player to be the owner of the current chat instances.
	 * 
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
	 * 
	 * @param networkClient
	 */
	public void setNetworkClient(NetworkClient networkClient) {
		this.networkClient = networkClient;
	}

	/**
	 * Updates the Communication Network for the logged-in Player
	 * 
	 * @param player
	 * @param view 
	 */
	public void loggedIn(Player player, CommunicationView view) {
		this.player = player;
		this.chatNodes = new HashMap<Integer, ChatNode>();
		this.textMessage = new TextMessage();
		this.chatHistory = new ChatHistory();
		this.view = view;
		this.view.setCommunicationNetwork(this);
		//this.model = new CommunicationModel();
		this.controller = new CommunicationController(view, null, this);
	}

	/**
	 * Chat history is received and ready to be loaded for the appropriate
	 * friend
	 * 
	 * @param receivedHistory
	 */
	public void receiveChatHistory(ChatHistory receivedHistory) {
		chatNodes = receivedHistory.getChatHistory();		
		
		//A ChatNode probably needs to contain the usernames of the participants
		for (Entry<Integer, ChatNode> entry : chatNodes.entrySet()) {
			view.addChatNode(entry.getValue());
		}
		/*
		 * TODO If the chat history is with someone who is not in the active
		 * chat window, display a notification and then load the chat history
		 * when you click them
		 * 
		 * Also, only display a notification if the history has changed since
		 * last time
		 * 
		 * Finally, there may have to be a limit imposed on how much history is
		 * sent/received as it could clog the network, let alone the chat window
		 */
		
		// Temporary:
		for (Entry<Integer, ChatNode> entry : chatNodes.entrySet()) {
			System.out.println("History with: " + entry.getKey() + " is: " + entry.getValue().getChatHistory().toString());
		}
	}
	
}
