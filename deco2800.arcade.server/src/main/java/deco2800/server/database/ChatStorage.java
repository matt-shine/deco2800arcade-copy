package deco2800.server.database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class ChatStorage {

	private HashMap<Integer, HashMap<Integer, List<String>>> chatStorage;
	
	public ChatStorage(){
		this.chatStorage = new HashMap<Integer, HashMap<Integer, List<String>>>();
	}
		
	public HashMap<Integer, List<String>> getChatHistory(int playerID){
		return chatStorage.get(playerID);
	}
	
	public void addChatHistory(int playerID, int senderID,  String text){
		HashMap<Integer, List<String>> chatHistory = new LinkedHashMap<Integer, List<String>>();
		List<String> chatLines = new ArrayList<String>();
		
		//Assume playerID belongs to "Bob"
		if (chatStorage.get(playerID) == null){ //This means Bob has no chat history saved at all
			chatLines.add(text);
			chatHistory.put(senderID, chatLines);	//Start a new ChatHistory for this sender
			chatStorage.put(playerID, chatHistory);	
		} else { //Bob has chat history saved for SOMEONE
			chatHistory = chatStorage.get(playerID);
			if (chatStorage.get(playerID).get(senderID) == null){ //But it turns out it wasn't for our sender
				chatLines.add(text);
				chatHistory.put(senderID, chatLines);	//Start a new ChatHistory for this sender
				chatStorage.put(playerID, chatHistory);	
			} else { //Bob already has some chat history with this sender!
				chatLines = chatStorage.get(playerID).get(senderID); //Get the existing chat history so the new text can be added to it
				chatLines.add(text);
				chatHistory.put(senderID, chatLines);
				chatStorage.put(playerID, chatHistory);
			}
		}
	}
	
}
