package deco2800.arcade.protocol.communication;

import java.util.HashMap;

import deco2800.arcade.model.ChatNode;

/**
 * Handles the sorting and storing of the chatHistory for a player
 */
public class ChatHistory {

	private HashMap<Integer, ChatNode> history;

	/**
	 * Creates a new ChatHistory
	 */
	public ChatHistory() {
		history = new HashMap<Integer, ChatNode>();
	}

	/**
	 * Gets the ChatHistory
	 * 
	 * @return HashMap&lt;Integer, ChatNode&gt;
	 */
	public HashMap<Integer, ChatNode> getChatHistory() {
		return history;
	}

	/**
	 * imports the chatHistory from external source
	 * 
	 * @param personalChatHistory
	 *            Hsitory to be added
	 */
	public void setChatHistory(HashMap<Integer, ChatNode> personalChatHistory) {
		history = personalChatHistory;
	}

}