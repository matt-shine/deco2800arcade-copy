package deco2800.arcade.server.database;

import static org.junit.Assert.assertEquals;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import deco2800.arcade.model.Player;
import deco2800.arcade.protocol.communication.TextMessage;
import deco2800.server.database.ChatStorage;

public class TestChatStorage {

	private Player player1, player2;
	private TextMessage message1, message2, message3, message4, message5;
	private Date date = new Date();
	private SimpleDateFormat sdf = new SimpleDateFormat("h:mm a");

	@Before
	public void initialise() {
		boolean[] privset = { true, true, true, true, true, true, true };
		List<String> info = new ArrayList<String>();
		info.add("Ricky");
		info.add("Rick Astley");
		info.add("rick@astley.giveyouup");
		info.add("ARTS");
		info.add("#Rickroll");
		info.add("20");

		List<String> info2 = new ArrayList<String>();
		info2.add("Stewie");
		info2.add("Stewie Griffin");
		info2.add("stewie@griffin.killlouis");
		info2.add("Nope");
		info2.add("Kill Louis");
		info2.add("2");

		player1 = new Player(123, "THIS IS NOT A VALID PATH.html", info, null,
				null, null, null, privset);
		player2 = new Player(234, "THIS IS NOT A VALID PATH.html", info2, null,
				null, null, null, privset);

		List<Integer> chatParticipants = new ArrayList<Integer>();
		chatParticipants.add(player1.getID());
		chatParticipants.add(player2.getID());

		message1 = new TextMessage();
		message1.setRecipients(chatParticipants);
		message1.setChatID(chatParticipants.hashCode());
		message1.setSenderID(123);
		message1.setSenderUsername("Chuck Norris");
		message1.setText("New Game");

		message2 = new TextMessage();
		message2.setRecipients(chatParticipants);
		message2.setChatID(chatParticipants.hashCode());
		message2.setSenderID(123);
		message2.setSenderUsername("Chuck Norris");
		message2.setText("Playing Game");

		message3 = new TextMessage();
		message3.setRecipients(chatParticipants);
		message3.setChatID(chatParticipants.hashCode());
		message3.setSenderID(234);
		message3.setSenderUsername("Bruce Lee");
		message3.setText("Playing game some more");

		message4 = new TextMessage();
		message4.setRecipients(chatParticipants);
		message4.setChatID(chatParticipants.hashCode());
		message4.setSenderID(123);
		message4.setSenderUsername("Chuck Norris");
		message4.setText("Getting bored of game");

		message5 = new TextMessage();
		message5.setRecipients(chatParticipants);
		message5.setChatID(chatParticipants.hashCode());
		message5.setSenderID(234);
		message5.setSenderUsername("Bruce Lee");
		message5.setText("Rage quitting");
	}

	/**
	 * Tests the adding of chat history and retrieving of that history by
	 * playersID. Also checks order is intact.
	 */
	@Test
	public void AddingRecievingHistory() {
		List<String> history1 = new ArrayList<String>();
		List<String> history2 = new ArrayList<String>();

		List<Integer> chatParticipants = new ArrayList<Integer>();
		chatParticipants.add(player1.getID());
		chatParticipants.add(player2.getID());

		String chatLine1 = sdf.format(date) + " - "
				+ message1.getSenderUsername() + ": " + message1.getText();
		history1.add(chatLine1);

		String chatLine2 = sdf.format(date) + " - "
				+ message2.getSenderUsername() + ": " + message2.getText();
		history1.add(chatLine2);

		String chatLine3 = sdf.format(date) + " - "
				+ message3.getSenderUsername() + ": " + message3.getText();
		history2.add(chatLine3);

		String chatLine4 = sdf.format(date) + " - "
				+ message4.getSenderUsername() + ": " + message4.getText();
		history1.add(chatLine4);

		String chatLine5 = sdf.format(date) + " - "
				+ message5.getSenderUsername() + ": " + message5.getText();
		history2.add(chatLine5);

		ChatStorage cs = new ChatStorage();
		cs.addChatHistory(message1, 123);
		cs.addChatHistory(message2, 123);
		cs.addChatHistory(message3, 234);
		cs.addChatHistory(message4, 123);
		cs.addChatHistory(message5, 234);
		System.out.println(history1);
		for (int i = 0; i < history1.size(); i++) {
			assertEquals(history1.get(i), cs.getChatHistory(123).get(5008)
					.getChatHistory().poll().getMessage());
		}
		for (int i = 0; i < history2.size(); i++) {
			assertEquals(history2.get(i), cs.getChatHistory(234).get(5008)
					.getChatHistory().poll().getMessage());
		}
	}
}
