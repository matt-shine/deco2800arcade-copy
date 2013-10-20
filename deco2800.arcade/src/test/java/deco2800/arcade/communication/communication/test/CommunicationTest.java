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
import deco2800.arcade.communication.CommunicationView;
import deco2800.arcade.model.ChatNode;
import deco2800.arcade.model.Player;
import deco2800.arcade.protocol.communication.ChatHistory;
import deco2800.arcade.protocol.communication.TextMessage;

public class CommunicationTest {

	private Player player1, player2, player3;

	private CommunicationNetwork comm1, comm2, comm3;

	private CommunicationListener listener1, listener2, listener3;

	private CommunicationView view;

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

		view = new CommunicationView();

		comm1 = new CommunicationNetwork(player1, null);
		comm1.loggedIn(player1, view);
		comm2 = new CommunicationNetwork(player2, null);
		comm2.loggedIn(player2, view);
		comm3 = new CommunicationNetwork(player3, null);
		comm3.loggedIn(player3, view);
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
	@Test
	public void chatHistory() {
		List<Integer> chatParticipants = new ArrayList<Integer>();
		chatParticipants.add(player1.getID());
		chatParticipants.add(player2.getID());
		chatParticipants.add(player3.getID());
		
		Connection connection = null;
		ChatHistory chathistory = new ChatHistory();
		HashMap<Integer, ChatNode> history = new HashMap<Integer, ChatNode>();
		ChatNode node = new ChatNode(chatParticipants);
		ChatNode compNode = new ChatNode(chatParticipants);
		
		node.addMessage("12:30 PM - Rick Astley: Testing chat", "Rick Astley");
		node.addMessage("12:35 PM - Rick Astley: Testing chat2", "Rick Astley");
		node.addMessage("12:36 PM - Stewie Griffin: Testing chat3", "Stewie Griffin");
		node.addMessage("12:50 PM - Stewie Griffin: Testing chat5", "Stewie Griffin");
		node.addMessage("12:49 PM - Paul Wade: Testing chat4", "Paul Wade");
		
		compNode.addMessage("12:30 PM - Rick Astley: Testing chat", "Rick Astley");
		compNode.addMessage("12:35 PM - Rick Astley: Testing chat2", "Rick Astley");
		compNode.addMessage("12:36 PM - Stewie Griffin: Testing chat3", "Stewie Griffin");
		compNode.addMessage("12:50 PM - Stewie Griffin: Testing chat5", "Stewie Griffin");
		compNode.addMessage("12:49 PM - Paul Wade: Testing chat4", "Paul Wade");
		
		history.put(player1.getID(), node);
		history.put(player2.getID(), node);
		history.put(player3.getID(), node);
		chathistory.setChatHistory(history);
		listener1.received(connection, chathistory);
		listener2.received(connection, chathistory);
		listener3.received(connection, chathistory);
		
		int size = node.getChatHistory().size();
		for (int i = 0; i < size; i++) {
			assertEquals(compNode.getChatHistory().peek().getUsername(), comm1.getCurrentChats().get(player1.getID()).getChatHistory().peek().getUsername());
			assertEquals(compNode.getChatHistory().poll().getMessage(), comm1.getCurrentChats().get(player1.getID()).getChatHistory().poll().getMessage());
		}
	}

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
		assertEquals(comm1.getCurrentChats().get(message1.getChatID())
				.getParticipants(),
				comm2.getCurrentChats().get(message1.getChatID())
						.getParticipants());
		assertEquals(comm1.getCurrentChats().get(message1.getChatID())
				.getChatHistory().peek().getMessage(), comm2.getCurrentChats()
				.get(message1.getChatID()).getChatHistory().peek().getMessage());
	}
}
