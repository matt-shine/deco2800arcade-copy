package deco2800.arcade.client.communication.test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.esotericsoftware.kryonet.Connection;

import deco2800.arcade.client.network.listener.CommunicationListener;
import deco2800.arcade.communication.CommunicationNetwork;
import deco2800.arcade.model.Player;
import deco2800.arcade.protocol.communication.ChatHistory;
import deco2800.arcade.protocol.communication.TextMessage;

public class CommunicationTest {

	private Player player1;
	private Player player2;

	private CommunicationNetwork comm1;
	private CommunicationNetwork comm2;

	@Before
	public void initialise() {
		ArrayList<Boolean> privset = new ArrayList<Boolean>();
		privset.add(true);
		privset.add(true);
		privset.add(true);
		privset.add(true);
		privset.add(true);
		privset.add(true);
		privset.add(true);

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
		comm1 = new CommunicationNetwork(player1, null);
		comm2 = new CommunicationNetwork(player2, null);
	}

	/**
	 * Tests the creating of CommunicationNetwork.
	 */
	//@Test
	public void initTest() {
		assertEquals(player1, comm1.getPlayer());
		assertEquals(123, comm1.getPlayer().getID());
		assertEquals(player2, comm2.getPlayer());
		assertEquals(234, comm2.getPlayer().getID());
	}

	/**
	 * Tests the creating of a node and adding participants into it.
	 */
	//@Test
	public void initChat() {
		List<Integer> chatParticipants = new ArrayList<Integer>();
		chatParticipants.add(player1.getID());
		chatParticipants.add(player2.getID());
		comm1.createChat(chatParticipants);

		assertEquals(chatParticipants.hashCode(), comm1.getCurrentChat()
				.getID());
		assertEquals(chatParticipants, comm1.getCurrentChat().getParticipants());
	}

	/**
	 * Need to update this to use the CommuncationNetwork inviteUser method once
	 * NetworkClient mock is working
	 */
	//@Test
	public void addAndRemove() {
		List<Integer> chatParticipants = new ArrayList<Integer>();
		chatParticipants.add(player1.getID());
		chatParticipants.add(player2.getID());

		comm1.createChat(chatParticipants);
		comm1.getCurrentChat().removeParticipant(chatParticipants.remove(0));
		comm1.getCurrentChat().removeParticipant(chatParticipants.remove(0));

		assertEquals(chatParticipants, comm1.getCurrentChat().getParticipants());

		comm1.getCurrentChat().addParticipant(1211);
		comm1.getCurrentChat().addParticipant(421);
		comm1.getCurrentChat().addParticipant(9999);
		chatParticipants.add(1211);
		chatParticipants.add(421);
		chatParticipants.add(9999);

		assertEquals(chatParticipants, comm1.getCurrentChat().getParticipants());
	}

	/**
	 * Tests the chat history. Mostly just tests the transferring of ChatHistory
	 * object between CommunicationListener -> CommunicationNetwork
	 */
	//@Test
	public void chatHistory() {
		CommunicationListener listener = new CommunicationListener(comm1);
		Connection connection = null;
		ChatHistory chathistory = new ChatHistory();
		HashMap<Integer, List<String>> history = new HashMap<Integer, List<String>>();
		List<String> chat = new ArrayList<String>();

		chat.add("This is a test1");
		chat.add("This is a test2");
		chat.add("This is not a test");
		chat.add("The cake is a lie");
		chat.add("Aquaman is the best");

		history.put(123, chat);
		chathistory.updateChatHistory(history);
		listener.received(connection, chathistory);
		assertEquals(history.get(player1.getID()),
				comm1.getChatHistory(comm1.getPlayer().getID()));
	}

	/**
	 * Not Sure how to test this when server is down and many methods in
	 * ChatNode are currently commented out.
	 */
	//@Test
	public void sendMessage() {
		List<Integer> chatParticipants = new ArrayList<Integer>();
		chatParticipants.add(player1.getID());
		chatParticipants.add(player2.getID());
		comm1.createChat(chatParticipants);
		TextMessage message = new TextMessage();
		message.recipients = chatParticipants;
		message.chatID = 1111;
		message.senderID = 789;
		message.senderUsername = "Chuck Norris";
		message.text = "QWERTYUIOP";
		// comm1.sendTextMessage(message);
		// assertEquals(something, something);
	}
}
