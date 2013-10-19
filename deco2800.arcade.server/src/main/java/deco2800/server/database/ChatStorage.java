package deco2800.server.database;

import java.util.HashMap;
import deco2800.arcade.model.ChatNode;
import deco2800.arcade.protocol.communication.TextMessage;

public class ChatStorage {

	private HashMap<Integer, HashMap<Integer, ChatNode>> chatStorage;
	
	public ChatStorage(){
		this.chatStorage = new HashMap<Integer, HashMap<Integer, ChatNode>>();
	}
		
	public HashMap<Integer, ChatNode> getChatHistory(int playerID){
		return chatStorage.get(playerID);
	}
	
	public void addChatHistory(TextMessage textMessage, int playerID){
		HashMap<Integer, ChatNode> chatHistory = new HashMap<Integer, ChatNode>();
		int nodeID = textMessage.getChatID();
		
		if(chatStorage.containsKey(playerID)) {
			if(chatStorage.get(playerID).containsKey(nodeID)) {
				ChatNode node = chatStorage.get(playerID).get(nodeID);
				node.addMessage(textMessage.getText());
			} else {
				ChatNode node = new ChatNode(textMessage.getRecipients());
				node.addMessage(textMessage.getText());
				chatStorage.get(playerID).put(nodeID, node);
			}
		} else {
			ChatNode node = new ChatNode(textMessage.getRecipients());
			node.addMessage(textMessage.getText());
			chatHistory.put(nodeID, node);
			chatStorage.put(playerID, chatHistory);	
		}
	}
	
}
