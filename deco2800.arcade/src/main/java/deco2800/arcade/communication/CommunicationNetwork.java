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
import deco2800.arcade.protocol.communication.TextMessage;
import deco2800.arcade.protocol.communication.UserQuery;
import deco2800.arcade.protocol.communication.VoiceMessage;

public class CommunicationNetwork {
	
	private Player player;
	protected NetworkClient networkClient;
	protected Map<String, ChatWindow> currentChats;
	
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
		private boolean userQueryResult;
		
		public ChatWindow(String chatID){
			window = new CommunicationView();
			window.setWindowSize(500, 250);
					
			// This is messy (and glitchy), but it's basically reformatting the chatID so it doesn't include the user's own name
			if (chatID.contains(player.getUsername()+",")){
				this.chatTitle = chatID.replaceFirst(player.getUsername()+",", "");
			} else if (chatID.contains(","+player.getUsername()+",")){
				this.chatTitle = chatID.replaceFirst(player.getUsername()+",", "");
			} else if (chatID.contains(","+player.getUsername())){
				this.chatTitle = chatID.replaceFirst(","+player.getUsername(), "");
			} else if (chatID.contains(player.getUsername())){
				this.chatTitle = chatID.replaceFirst(player.getUsername(), "");
			}
			
			window.setWindowTitle("Chatting with: " + this.chatTitle);
			
			model = new CommunicationModel(chatID);
			controller = new CommunicationController(window, model, networkClient, getPlayer());
			controller.setFocus();
		}
		
		public void updateChat(TextMessage textMessage){
			date = new Date();
			controller.updateChat(dateFormat.format(date) + " - " + textMessage.username + ": " + textMessage.text + "\n");
			
			// To invite someone to a chat: type "/invite " followed by a username
			if (textMessage.text.startsWith("/invite ") && !textMessage.text.substring(8).equals(this.invite)){
				this.invite = textMessage.text.substring(8);
				
				// This stuff is for checking if a user is actually online before trying to invite them:
				/*
				// Need to check whether the person you want to invite is online or not!
				UserQuery userQuery = new UserQuery();
				userQuery.username = this.invite;
				userQuery.sender = player.getUsername();
				networkClient.sendNetworkObject(userQuery);
				
				if (this.userQueryResult == true){
					// This is so only the person who sent the invite will send the chatRequest
					if (textMessage.username.equals(player.getUsername())){
						ChatRequest chatRequest = new ChatRequest();
						chatRequest.invite = this.invite;
						chatRequest.participants = textMessage.chatID+","+this.invite;
						networkClient.sendNetworkObject(chatRequest);
					}
					
					model.addParticipant(this.invite);
					controller.updateChat(dateFormat.format(date) + " - " + this.invite + " was added to the conversation.\n");
					window.setWindowTitle("Chatting with: " + this.chatTitle + "," + this.invite);
					currentChats.put(textMessage.chatID+","+this.invite, currentChats.get(textMessage.chatID));
				}
				*/
				
				
				// This is so only the person who sent the invite will send the chatRequest (as everyone is receiving the same message)
				if (textMessage.username.equals(player.getUsername())){
					ChatRequest chatRequest = new ChatRequest();
					chatRequest.invite = this.invite;
					chatRequest.participants = textMessage.chatID+","+this.invite;
					networkClient.sendNetworkObject(chatRequest);
				}
				
				model.addParticipant(this.invite);
				controller.updateChat(dateFormat.format(date) + " - " + this.invite + " was added to the conversation.\n");
				window.setWindowTitle("Chatting with: " + this.chatTitle + "," + this.invite);
				currentChats.put(textMessage.chatID+","+this.invite, currentChats.get(textMessage.chatID));
			}
			
			// To remove someone from a chat: type "/kick " followed by a username
			if (textMessage.text.startsWith("/kick ")){
				String kick = textMessage.text.substring(6);
				
				if (player.getUsername().equals(kick)){
					controller.updateChat(dateFormat.format(date) + " - " + " You were kicked from the conversation.\n");
					currentChats.remove(textMessage.chatID);
					ArrayList<String> participants = model.getParticipants();
					for (String participant : participants){
						model.removeParticipant(participant);
					}
					window.sendButton.setEnabled(false);
				} else {
					model.removeParticipant(kick);				
					controller.updateChat(dateFormat.format(date) + " - " + kick + " was kicked from the conversation.\n");
					
					String newChatID = "";
					if (textMessage.chatID.contains(kick+",")){
						newChatID = textMessage.chatID.replaceFirst(kick+",", "");
					} else if (textMessage.chatID.contains(","+kick+",")){
						newChatID = textMessage.chatID.replaceFirst(kick+",", "");
					} else if (textMessage.chatID.contains(","+kick)){
						newChatID = textMessage.chatID.replaceFirst(","+kick, "");
					} else if (textMessage.chatID.contains(kick)){
						newChatID = textMessage.chatID.replaceFirst(kick, "");
					}
					
					//String newChatTitle = "";
					// This is messy (and glitchy), but it's basically reformatting the chatID so it doesn't include the user's own name
					if (newChatID.contains(player.getUsername()+",")){
						this.chatTitle = newChatID.replaceFirst(player.getUsername()+",", "");
					} else if (newChatID.contains(","+player.getUsername()+",")){
						this.chatTitle = newChatID.replaceFirst(player.getUsername()+",", "");
					} else if (newChatID.contains(","+player.getUsername())){
						this.chatTitle = newChatID.replaceFirst(","+player.getUsername(), "");
					} else if (newChatID.contains(player.getUsername())){
						this.chatTitle = newChatID.replaceFirst(player.getUsername(), "");
					}
					
					currentChats.put(newChatID, currentChats.get(textMessage.chatID));
					window.setWindowTitle("Chatting with: " + this.chatTitle);
				}
				if (this.invite.equals(kick)){
					this.invite = "";
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
		currentChats = new HashMap<String, ChatWindow>();
	}
		
	public void createNewChat(String chatID){
		currentChats.put(chatID, new ChatWindow(chatID));
	}
	
	public void updateChat(String chatID, TextMessage message){
		currentChats.get(chatID).updateChat(message);
	}
	
	//Check if chat exists before making new window.
	public boolean checkIfChatExists(String username){
		if (currentChats.get(username) != null){
			return true;
		} else {
			return false;
		}		
	}
	
	public void userQuery(boolean result){
		if (result == true){
			currentChats.get(player.getUsername()).userQueryResult = true;
		} else {
			currentChats.get(player.getUsername()).userQueryResult = false;
		}	
	}
	
}
