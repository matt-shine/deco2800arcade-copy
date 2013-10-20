package deco2800.arcade.protocol.communication;

import java.util.ArrayList;
import java.util.List;

import deco2800.arcade.model.Player;
import deco2800.arcade.protocol.UserRequest;

/**
 * Object that is sent over the network. Contains all necessary details of
 * message
 */
public class TextMessage extends UserRequest {

	private List<Integer> recipients = new ArrayList<Integer>();
	private String text;
	private String senderUsername;
	private int chatID;

	/**
	 * Constructor for TextMessage
	 * 
	 * @param recipients
	 *            List of recipients of this message
	 * @param text
	 *            being sent to each recipient
	 * @param player
	 *            the sender
	 * @param chatID
	 *            the ID of the chatNode being sent from/to
	 */
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

	/**
	 * @return recipients List&lt;Integer&gt;
	 */
	public List<Integer> getRecipients() {
		return recipients;
	}

	/**
	 * Sets the recipients of this TextMessage
	 * 
	 * @param recipients
	 *            List&lt;Integer&gt; of playerIDs of each recipient
	 */
	public void setRecipients(List<Integer> recipients) {
		this.recipients = recipients;
	}

	/**
	 * @return text to be sent to players
	 */
	public String getText() {
		return text;
	}

	/**
	 * Set the text to be sent to recipients of this TextMessage
	 * 
	 * @param text
	 *            to be sent
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * @return Username of the sender of the TextMessage
	 */
	public String getSenderUsername() {
		return senderUsername;
	}

	/**
	 * Set the sender of the message
	 * 
	 * @param senderUsername
	 */
	public void setSenderUsername(String senderUsername) {
		this.senderUsername = senderUsername;
	}

	/**
	 * get the playerID of the sender
	 * 
	 * @return PlayerID
	 */
	public int getSenderID() {
		return playerID;
	}

	/**
	 * Sets the ID of the sender
	 * 
	 * @param senderID
	 */
	public void setSenderID(int senderID) {
		this.playerID = senderID;
	}

	/**
	 * Gets the ID of the ChatNode being sent to/from
	 * 
	 * @return chatID
	 */
	public int getChatID() {
		return chatID;
	}

	/**
	 * Sets the chatID of this TextMessage
	 * 
	 * @param chatID
	 */
	public void setChatID(int chatID) {
		this.chatID = chatID;
	}
}
