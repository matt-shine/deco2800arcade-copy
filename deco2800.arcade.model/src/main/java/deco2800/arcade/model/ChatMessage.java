package deco2800.arcade.model;

public class ChatMessage<username, message> {

	private String username;
	private String message;

	/**
	 * Initial the chat message
	 * */
	public ChatMessage(String username, String message) {
		this.username = username;
		this.message = message;
	}

	/**
	 * Get the message sender
	 * */
	public String getUsername() {
		return username;
	}

	/**
	 * Get user's message
	 * */
	public String getMessage() {
		return message;
	}
}
