package deco2800.arcade.model;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * A ChatNode is a single chat instance. It contains the participants of the
 * chat and a queue containing the chat history.
 * 
 */
public class ChatNode {

	private List<Integer> participants;
	private Queue<String> chatHistory;
	private String owner;

	/**
	 * Zero-arg Constructor for Kryo
	 */
	public ChatNode() {
	}

	/**
	 * Creates a chat with a list of participants.
	 * 
	 * @param chatParticipants
	 */
	public ChatNode(List<Integer> chatParticipants) {
		participants = new ArrayList<Integer>(chatParticipants);
		chatHistory = new ArrayDeque<String>();
	}

	/**
	 * Creates a chat with a single participant.
	 * 
	 * @param participant
	 */
	public ChatNode(int participant) {
		participants = new ArrayList<Integer>();
		participants.add(participant);
		chatHistory = new ArrayDeque<String>();
	}

	public int getID() {
		return participants.hashCode();
	}

	/**
	 * Removes the participant with PlayerID from the chat instance.
	 * 
	 * @param playerId
	 */
	public void removeParticipant(int playerId) {
		participants.remove(participants.indexOf(playerId));
	}

	/**
	 * Adds the participant with PlayerID to the chat instance.
	 * 
	 * @param playerId
	 */
	public void addParticipant(int playerId) {
		participants.add(playerId);
	}

	/**
	 * Returns the participants of the chat instance.
	 */
	public List<Integer> getParticipants() {
		return participants;
	}

	/**
	 * Adds a message to the chat instance.
	 * 
	 * @param message
	 */
	public void addMessage(String message) {
		chatHistory.add(message);
	}

	public Queue<String> getChatHistory() {
		return chatHistory;
	}

	public void setOwner(String username) {
		owner = username;
	}

	public String getOwner() {
		return owner;
	}

}