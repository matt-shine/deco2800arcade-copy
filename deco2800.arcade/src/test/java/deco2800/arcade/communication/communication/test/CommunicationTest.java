package deco2800.arcade.communication.communication.test;

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

	private Player player1, player2, player3;

	private CommunicationNetwork comm1, comm2, comm3;

	private CommunicationListener listener1, listener2, listener3;

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

		List<String> info3 = new ArrayList<String>();
		info3.add("Paul");
		info3.add("Paul Wade");
		info3.add("paul@wade.programming");
		info3.add("SCIENCE");
		info3.add("Skill");
		info3.add("7");

		player1 = new Player(123, "THIS IS NOT A VALID PATH.html", info, null,
				null, null, null, privset);
		player2 = new Player(234, "THIS IS NOT A VALID PATH.html", info2, null,
				null, null, null, privset);
		player3 = new Player(235, "THIS IS NOT A VALID PATH.html", info3, null,
				null, null, null, privset);

		comm1 = new CommunicationNetwork(player1, null);
		comm2 = new CommunicationNetwork(player2, null);
		comm3 = new CommunicationNetwork(player3, null);
		listener1 = new CommunicationListener(comm1);
		listener2 = new CommunicationListener(comm2);
		listener3 = new CommunicationListener(comm3);
	}

	/**
	 * Tests the creating of CommunicationNetwork.
	 */
	@Test
	public void initTest() {
		assertEquals(player1, comm1.getPlayer());
		assertEquals(123, comm1.getPlayer().getID());
		assertEquals(player2, comm2.getPlayer());
		assertEquals(234, comm2.getPlayer().getID());
		assertEquals(player3, comm3.getPlayer());
		assertEquals(235, comm3.getPlayer().getID());
	}

	/**
	 * Tests the creating of a node and adding participants into it.
	 */
	@Test
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
	@Test
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
//	@Test
//	public void chatHistory() {
//		Connection connection = null;
//		ChatHistory chathistory = new ChatHistory();
//		HashMap<Integer, List<String>> history = new HashMap<Integer, List<String>>();
//		List<String> chat1 = new ArrayList<String>();
//		List<String> chat2 = new ArrayList<String>();
//		List<String> chat3 = new ArrayList<String>();
//
//		chat1.add("This is a test1");
//		chat1.add("This is a test2");
//		chat2.add("This is not a test");
//		chat2.add("The cake is a lie");
//		chat3.add("Aquaman is the best");
//
//		history.put(123, chat1);
//		history.put(234, chat2);
//		chathistory.updateChatHistory(history);
//		chathistory.updateChatHistory(123, chat3);
//		listener1.received(connection, chathistory);
//		listener2.received(connection, chathistory);
//
//		assertEquals(chathistory.getChatHistory(123),
//				comm1.getChatHistory(comm1.getPlayer().getID()));
//		assertEquals(chathistory.getChatHistory(234),
//				comm1.getChatHistory(comm2.getPlayer().getID()));
//		assertEquals(chathistory.getChatHistory(123),
//				comm2.getChatHistory(comm1.getPlayer().getID()));
//		assertEquals(chathistory.getChatHistory(234),
//				comm2.getChatHistory(comm2.getPlayer().getID()));
//
//	}

	/**
	 * Test currently calls the listeners recieved method manually, once proper
	 * sending has been set up this should be done automatically. Must change
	 * test when this happens.
	 */
	@Test
	public void sendMessage() {
		Connection connection = null;

		List<Integer> chatParticipants1 = new ArrayList<Integer>();
		List<Integer> chatParticipants2 = new ArrayList<Integer>();
		chatParticipants1.add(player1.getID());
		chatParticipants1.add(player1.getID());
		chatParticipants2.add(player2.getID());
		chatParticipants1.add(player3.getID());
		comm1.createChat(chatParticipants1);
		comm1.createChat(chatParticipants2);

		TextMessage message1 = new TextMessage();
		message1.setRecipients(chatParticipants1);
		message1.setChatID(chatParticipants1.hashCode());
		message1.setSenderID(123);
		message1.setSenderUsername("Chuck Norris");
		message1.setText("New Game");

		TextMessage message2 = new TextMessage();
		message2.setRecipients(chatParticipants2);
		message2.setChatID(chatParticipants2.hashCode());
		message2.setSenderID(123);
		message2.setSenderUsername("Chuck Norris");
		message2.setText("QWERTYUIOP");

		listener1.received(connection, message1);
		listener1.received(connection, message2);
		listener2.received(connection, message1);
		listener3.received(connection, message2);

		// Tests two methods of getting data (using chatID
		// (participants.hashcode()) and using current chat (the chat that the
		// last message recieved belongs to.
		assertEquals(comm1.getCurrentChats().get(message1.getChatID()).getID(),
				comm2.getCurrentChats().get(message1.getChatID()).getID());
		assertEquals(comm1.getCurrentChats().get(message2.getChatID()).getID(),
				comm3.getCurrentChat().getID());

		assertEquals(comm1.getCurrentChats().get(message1.getChatID())
				.getParticipants(),
				comm2.getCurrentChats().get(message1.getChatID())
						.getParticipants());
		assertEquals(comm1.getCurrentChats().get(message2.getChatID())
				.getParticipants(), comm3.getCurrentChat().getParticipants());

		assertEquals(comm1.getCurrentChats().get(message1.getChatID())
				.getText(), comm2.getCurrentChats().get(message1.getChatID())
				.getText());
		assertEquals(comm1.getCurrentChats().get(message2.getChatID())
				.getParticipants(), comm3.getCurrentChat().getParticipants());
	}
}
