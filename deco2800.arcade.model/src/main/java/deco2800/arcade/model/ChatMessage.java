package deco2800.arcade.model;

public class ChatMessage<username, message> {

	private String username;
	private String message;

	public ChatMessage(String username, String message) {
		this.username = username;
		this.message = message;
	}

	public String getUsername() {
		return username;
	}

	public String getMessage() {
		return message;
	}
}
