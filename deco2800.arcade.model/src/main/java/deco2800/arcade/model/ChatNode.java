package deco2800.arcade.model;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 * A ChatNode is a single chat instance. It contains the participants of the
 * chat and a queue containing the chat history.
 * 
 */
public class ChatNode extends JLabel {

	private List<Integer> participants;
	private Queue<String> chatHistory;
	

	/**
	 * Creates a chat with a list of participants.
	 * 
	 * @param chatParticipants
	 */
	public ChatNode(List<Integer> chatParticipants) {
		participants = new ArrayList<Integer>(chatParticipants);
		chatHistory = new ArrayDeque<String>();
		setUpLabel();
	}

	/**
	 * Creates a chat with a single participant.
	 * 
	 * @param participant
	 */
	public ChatNode(int participant) {
		participants = new ArrayList<Integer>();
		participants.add(participant);
		setUpLabel();
	}
	
	private void setUpLabel() {
		setBackground(Color.WHITE);
		setOpaque(true);
		setHorizontalAlignment(SwingConstants.CENTER);
		setPreferredSize(new Dimension(250, 50));
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

}

// private void parseInput(TextMessage textMessage){
// String text = textMessage.text;
//
// if(textMessage.username.equals(username)){
// if (text.startsWith("/?") || text.startsWith("/help")){
// controller.updateChat("To add a user to the conversation, type /invite username\n");
// controller.updateChat("To remove a user from the conversation, type /kick username\n");
// } else if (textMessage.text.startsWith("/invite ")){
// invite = textMessage.text.substring(8);
//
// if (!model.getParticipants().contains(this.invite)){
// ChatRequest chatRequest = new ChatRequest();
// chatRequest.invite = invite;
// chatRequest.chatID = textMessage.chatID;
// chatRequest.sender = username;
// chatRequest.participants.addAll(model.getParticipants());
// chatRequest.participants.add(invite);
// networkClient.sendNetworkObject(chatRequest);
// } else {
// controller.updateChat(dateFormat.format(date) + " - " + this.invite +
// " is already in the conversation.\n");
// }
// } else if(textMessage.text.startsWith("/kick ")){
// String kick = textMessage.text.substring(6);
// kickUser(textMessage, kick);
// }else{
// controller.updateChat(dateFormat.format(date) + " - " + textMessage.username
// + ": " + textMessage.text + "\n");
// }
// }
// }

// public void inviteUser(ChatResponse chatResponse){
// date = new Date();
// if (chatResponse.response.equals("available")){
// model.addParticipant(chatResponse.invite);
// //controller.updateChat(dateFormat.format(date) + " - " + chatResponse.invite
// + " was added to the conversation by " + chatResponse.sender + "\n");
// controller.systemChat(dateFormat.format(date) + " - " + chatResponse.invite +
// " was added to the conversation by " + chatResponse.sender + "\n");
//
// this.chatTitle = model.getParticipants().toString();
// ArrayList<String> otherParticipants = new ArrayList<String>();
// otherParticipants.addAll(model.getParticipants());
// otherParticipants.remove(player.getUsername());
// if (otherParticipants.isEmpty()){
// this.chatTitle = "Yourself";
// } else {
// this.chatTitle = otherParticipants.toString();
// }
// window.setWindowTitle("Chatting with: " + this.chatTitle);
//
// int newChatID = model.getParticipants().hashCode();
// currentChats.put(newChatID, currentChats.get(chatResponse.chatID));
// } else {
// if (chatResponse.sender.equals(player.getUsername())){
// controller.updateChat(dateFormat.format(date) + " - " + "Unable to add " +
// chatResponse.invite + ", user not found.\n");
// }
// }
// }
//
// public void kickUser(TextMessage textMessage, String kick){
// if (player.getUsername().equals(kick)){
// controller.updateChat(dateFormat.format(date) + " - " +
// " You were kicked from the conversation.\n");
// currentChats.remove(textMessage.chatID);
// ArrayList<String> participants = model.getParticipants();
// for (String participant : participants){
// model.removeParticipant(participant);
// }
// window.sendButton.setEnabled(false);
// } else {
// if (model.getParticipants().contains(kick)){
// model.removeParticipant(kick);
// controller.updateChat(dateFormat.format(date) + " - " + kick +
// " was kicked from the conversation by " + textMessage.username + "\n");
// currentChats.put(model.getParticipants().hashCode(),
// currentChats.get(textMessage.chatID));
//
// //currentChats.remove()
//
// this.chatTitle = model.getParticipants().toString();
// ArrayList<String> otherParticipants = new ArrayList<String>();
// otherParticipants.addAll(model.getParticipants());
// otherParticipants.remove(player.getUsername());
// if (otherParticipants.isEmpty()){
// this.chatTitle = "Yourself";
// } else {
// this.chatTitle = otherParticipants.toString();
// }
// window.setWindowTitle("Chatting with: " + this.chatTitle);
// } else {
// if (textMessage.username.equals(player.getUsername())){
// controller.updateChat(dateFormat.format(date) + " - " + "Unable to kick " +
// kick + ", user not found.\n");
// }
// }
// }
// }

