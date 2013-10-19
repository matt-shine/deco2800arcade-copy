package deco2800.arcade.protocol.communication;

import java.util.HashMap;
import deco2800.arcade.model.ChatNode;


public class ChatHistory {

	private HashMap<Integer, ChatNode> history;
	
	public ChatHistory(){
		history = new HashMap<Integer, ChatNode>();
	}
	
	public HashMap<Integer, ChatNode> getChatHistory() {
		return history;
	}
	
	public void setChatHistory(HashMap<Integer, ChatNode> personalChatHistory) {
		history = personalChatHistory;
	}
	
	
}