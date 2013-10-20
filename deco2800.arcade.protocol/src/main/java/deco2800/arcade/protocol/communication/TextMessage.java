package deco2800.arcade.protocol.communication;

import java.util.ArrayList;
import java.util.List;

import deco2800.arcade.model.Player;
import deco2800.arcade.protocol.UserRequest;

public class TextMessage extends UserRequest {

	private List<Integer> recipients = new ArrayList<Integer>();
	private String text;
	private String senderUsername;
	private int chatID;
	
	public TextMessage(List<Integer> recipients, String text, Player player,
			int chatID) {
		super();
		this.recipients = recipients;
		this.text = text;
		this.senderUsername = player.getUsername();
		this.playerID = player.getID();
		this.chatID = chatID;
	}

	public TextMessage() {
	}

	public List<Integer> getRecipients() {
		return recipients;
	}

	public void setRecipients(List<Integer> recipients) {
		this.recipients = recipients;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getSenderUsername() {
		return senderUsername;
	}

	public void setSenderUsername(String senderUsername) {
		this.senderUsername = senderUsername;
	}

	public int getSenderID() {
		return playerID;
	}

	public void setSenderID(int senderID) {
		this.playerID = senderID;
	}

	public int getChatID() {
		return chatID;
	}

	public void setChatID(int chatID) {
		this.chatID = chatID;
	}
}
