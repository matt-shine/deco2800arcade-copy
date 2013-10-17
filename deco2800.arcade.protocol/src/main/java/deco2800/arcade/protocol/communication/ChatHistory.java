package deco2800.arcade.protocol.communication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import deco2800.arcade.protocol.UserRequest;


public class ChatHistory extends UserRequest {

	private HashMap<Integer, List<String>> history;
	
	public ChatHistory(){
		this.history = new HashMap<Integer, List<String>>();
	}
	
	public List<String> getChatHistory(int playerID){
		return history.get(playerID);
	}
	
	public void updateChatHistory(int playerID, List<String> lines){
		for (String line : lines){
			history.get(playerID).add(line);
		}
	}
	
	public void updateChatHistory(HashMap<Integer, List<String>> chatHistory){
		history = chatHistory;
	}

	public HashMap<Integer, List<String>> getAllHistory() {
		return history;
	}
	
}