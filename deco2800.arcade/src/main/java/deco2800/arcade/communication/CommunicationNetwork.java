package deco2800.arcade.communication;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.model.Player;
import deco2800.arcade.protocol.communication.ChatRequest;
import deco2800.arcade.protocol.communication.ChatResponse;
import deco2800.arcade.protocol.communication.TextMessage;
import deco2800.arcade.protocol.communication.VoiceMessage;

public class CommunicationNetwork {
	
	private Player player;
	protected NetworkClient networkClient;
	protected Map<Integer, ChatWindow> currentChats;
	
	@SuppressWarnings("unused")
	private VoiceMessage voiceMessage;
	
	DateFormat dateFormat = new SimpleDateFormat("h:mm aa");
	Date date = new Date();
		
	
	/************************************************************/
	
	public class ChatWindow {
		private CommunicationView window;
		private CommunicationController controller;
		private CommunicationModel model;
		
		private String chatTitle = "";
		private String invite = "";
		
		public ChatWindow(ArrayList<String> participants){
			window = new CommunicationView();
			window.setWindowSize(500, 250);
							
			ArrayList<String> otherParticipants = new ArrayList<String>();
			otherParticipants.addAll(participants);
			otherParticipants.remove(player.getUsername());
			if (otherParticipants.isEmpty()){
				this.chatTitle = "Yourself";
			} else {
				this.chatTitle = otherParticipants.toString();
			}
			window.setWindowTitle("Chatting with: " + this.chatTitle);
			
			model = new CommunicationModel(participants);
			controller = new CommunicationController(window, model, networkClient, getPlayer());
			controller.setFocus();
		}
		
		public void updateChat(TextMessage textMessage){
			date = new Date(); //Get the current time
			
			if (textMessage.text.startsWith("/?") || textMessage.text.startsWith("/help")){
				if (textMessage.username.equals(player.getUsername())){
					controller.updateChat("To add a user to the conversation, type /invite username\n");
					controller.updateChat("To remove a user from the conversation, type /kick username\n");
				}
			} else if (textMessage.text.startsWith("/invite ")){ //To invite someone to a chat: type "/invite " followed by a username
				this.invite = textMessage.text.substring(8);
				
				if (!model.getParticipants().contains(this.invite)){ //Make sure the person you are inviting isn't already in the conversation
					ChatRequest chatRequest = new ChatRequest();
					if (textMessage.username.equals(player.getUsername())){ //Only the person who typed /invite should send the chatRequest
						chatRequest.invite = this.invite;
						chatRequest.chatID = textMessage.chatID;
						chatRequest.sender = player.getUsername();
						chatRequest.participants.addAll(model.getParticipants());
						chatRequest.participants.add(this.invite);
						networkClient.sendNetworkObject(chatRequest);
					}
				} else {
					if (textMessage.username.equals(player.getUsername())){
						controller.updateChat(dateFormat.format(date) + " - " + this.invite + " is already in the conversation.\n");
					}
				}
			} else if (textMessage.text.startsWith("/kick ")){	//To kick someone from a chat: type "/kick " followed by a username
				String kick = textMessage.text.substring(6);
				kickUser(textMessage, kick);
			} else {
				controller.updateChat(dateFormat.format(date) + " - " + textMessage.username + ": " + textMessage.text + "\n");
			}
		}
		
		public void inviteUser(ChatResponse chatResponse){
			date = new Date();
			if (chatResponse.response.equals("available")){
				model.addParticipant(chatResponse.invite);
				controller.updateChat(dateFormat.format(date) + " - " + chatResponse.invite + " was added to the conversation by " + chatResponse.sender + "\n");
				
				this.chatTitle = model.getParticipants().toString();
				ArrayList<String> otherParticipants = new ArrayList<String>();
				otherParticipants.addAll(model.getParticipants());
				otherParticipants.remove(player.getUsername());
				if (otherParticipants.isEmpty()){
					this.chatTitle = "Yourself";
				} else {
					this.chatTitle = otherParticipants.toString();
				}			
				window.setWindowTitle("Chatting with: " + this.chatTitle);
				
				int newChatID = model.getParticipants().hashCode();
				currentChats.put(newChatID, currentChats.get(chatResponse.chatID));
			} else {
				if (chatResponse.sender.equals(player.getUsername())){
					controller.updateChat(dateFormat.format(date) + " - " + "Unable to add " + chatResponse.invite + ", user not found.\n");
				}
			}
		}
		
		public void kickUser(TextMessage textMessage, String kick){
			if (player.getUsername().equals(kick)){
				controller.updateChat(dateFormat.format(date) + " - " + " You were kicked from the conversation.\n");
				currentChats.remove(textMessage.chatID);				
				ArrayList<String> participants = model.getParticipants();
				for (String participant : participants){
					model.removeParticipant(participant);
				}
				window.sendButton.setEnabled(false);
			} else {
				if (model.getParticipants().contains(kick)){
					model.removeParticipant(kick);		
					controller.updateChat(dateFormat.format(date) + " - " + kick + " was kicked from the conversation by " + textMessage.username + "\n");
					currentChats.put(model.getParticipants().hashCode(), currentChats.get(textMessage.chatID));
					
					this.chatTitle = model.getParticipants().toString();
					ArrayList<String> otherParticipants = new ArrayList<String>();
					otherParticipants.addAll(model.getParticipants());
					otherParticipants.remove(player.getUsername());
					if (otherParticipants.isEmpty()){
						this.chatTitle = "Yourself";
					} else {
						this.chatTitle = otherParticipants.toString();
					}
					window.setWindowTitle("Chatting with: " + this.chatTitle);
				} else {
					if (textMessage.username.equals(player.getUsername())){
						controller.updateChat(dateFormat.format(date) + " - " + "Unable to kick " + kick + ", user not found.\n");
					}
				}
			}
		}

	}
	
	/***********************************************************/

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public CommunicationNetwork(Player player, NetworkClient networkClient){
		this.setPlayer(player);
		this.networkClient = networkClient;
		currentChats = new HashMap<Integer, ChatWindow>();
	}
		
	public void createNewChat(ArrayList<String> participants){
		currentChats.put(participants.hashCode(), new ChatWindow(participants));
	}
	
	public void updateChat(int chatID, TextMessage message){
		currentChats.get(chatID).updateChat(message);
	}
		
	public boolean checkIfChatExists(int chatID){
		if (currentChats.get(chatID) != null){
			return true;
		} else {
			return false;
		}		
	}
	
	public void inviteUser(ChatResponse chatResponse) {
		currentChats.get(chatResponse.chatID).inviteUser(chatResponse);
	}
	
}
