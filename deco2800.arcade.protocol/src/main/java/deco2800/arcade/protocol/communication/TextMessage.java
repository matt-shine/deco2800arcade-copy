package deco2800.arcade.protocol.communication;

import java.util.ArrayList;
import java.util.List;

import deco2800.arcade.protocol.UserRequest;

public class TextMessage extends UserRequest {
	private List<Integer> recipients = new ArrayList<Integer>();
	private String text;
	private String senderUsername;
	private int senderID;
	private int chatID;
	
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
		return senderID;
	}
	
	public void setSenderID(int senderID) {
		this.senderID = senderID;
	}
	
	public int getChatID() {
		return chatID;
	}
	
	public void setChatID(int chatID) {
		this.chatID = chatID;
	}
}
