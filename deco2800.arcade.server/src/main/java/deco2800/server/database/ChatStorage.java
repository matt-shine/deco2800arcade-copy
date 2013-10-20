package deco2800.server.database;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import deco2800.arcade.model.ChatNode;
import deco2800.arcade.protocol.communication.TextMessage;

public class ChatStorage {

	private HashMap<Integer, HashMap<Integer, ChatNode>> chatStorage;
	private Date date;
	private SimpleDateFormat sdf = new SimpleDateFormat("h:mm a");
	
	public ChatStorage(){
		this.chatStorage = new HashMap<Integer, HashMap<Integer, ChatNode>>();
	}
		
	public HashMap<Integer, ChatNode> getChatHistory(int playerID){
		return chatStorage.get(playerID);
	}
	
	public void addChatHistory(TextMessage textMessage, int playerID){
		HashMap<Integer, ChatNode> chatHistory = new HashMap<Integer, ChatNode>();
		int nodeID = textMessage.getChatID();
		
		//Create a chat-friendly string
		date = new Date();
		String chatLine = sdf.format(date) + " - " + textMessage.getSenderUsername() + ": " + textMessage.getText();
		
		if(chatStorage.containsKey(playerID)) {
			if(chatStorage.get(playerID).containsKey(nodeID)) {
				ChatNode node = chatStorage.get(playerID).get(nodeID);
				node.addMessage(chatLine);
			} else { // Fairly certain test does not cover this branch yet 
				ChatNode node = new ChatNode(textMessage.getRecipients());
				node.setOwner(textMessage.getSenderUsername());
				node.addMessage(chatLine);
				chatStorage.get(playerID).put(nodeID, node);
			}
		} else {
			ChatNode node = new ChatNode(textMessage.getRecipients());
			node.setOwner(textMessage.getSenderUsername());
			node.addMessage(chatLine);
			chatHistory.put(nodeID, node);
			chatStorage.put(playerID, chatHistory);	
		}
	}
	
}
