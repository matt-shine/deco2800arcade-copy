package deco2800.server.database;

import java.util.HashMap;
import java.util.List;

public class ChatStorage {

	private HashMap<Integer, List<String>> chatHistory;
	
	public ChatStorage(){
		this.chatHistory = new HashMap<Integer, List<String>>();
	}
		
	public List<String> getChatHistory(int playerID){
		return chatHistory.get(playerID);
	}
	
	public void addChatHistory(int playerID, List<String> chat){
		for (String chatLine : chat){
			chatHistory.get(playerID).add(chatLine);
		}
	}
	
	
}
