package deco2800.arcade.protocol.communication;

import java.util.HashMap;
import java.util.Map;

import deco2800.arcade.model.ChatNode;
import deco2800.arcade.protocol.UserRequest;


public class ChatHistory extends UserRequest {

	private Map<Integer, ChatNode> history;
	
	public ChatHistory(){
		history = new HashMap<Integer, ChatNode>();
	}
	
	public Map<Integer, ChatNode> getChatHistory() {
		return history;
	}
	
	
	public void setChatHistory(Map<Integer, ChatNode> personalChatHistory) {
		history = personalChatHistory;
	}
	
	
}