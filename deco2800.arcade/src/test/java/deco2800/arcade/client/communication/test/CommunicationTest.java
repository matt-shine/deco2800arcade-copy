package deco2800.arcade.client.communication.test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mockito.Mockito;

import deco2800.arcade.communication.CommunicationNetwork;
import deco2800.arcade.model.Player;
import deco2800.arcade.protocol.communication.TextMessage;

public class CommunicationTest {

	Player player1 = new Player(789, "Chuck Norris", null);
	Player player2 = new Player(987, "Jackie Chan", null);
	Player player3 = new Player(897, "Bruce Lee", null);
	// NetworkClient client = Mockito.mock(NetworkClient.class);
	// Not sure how to set up mock NetworkCleint or CommNetwork as they try
	// to connect to the server in their constructors.
	CommunicationNetwork comm1 = new CommunicationNetwork(player1, null);
	CommunicationNetwork comm2 = new CommunicationNetwork(player2, null);
	CommunicationNetwork comm3 = new CommunicationNetwork(player3, null);

	/**
	 * Tests the creating of CommunicationNetwork.
	 */
	@Test
	public void initTest() {
		assertEquals(player1, comm1.getPlayer());
		assertEquals(789, comm1.getPlayer().getID());
		assertEquals("Chuck Norris", comm1.getPlayer().getUsername());
		assertEquals(player2, comm2.getPlayer());
		assertEquals(987, comm2.getPlayer().getID());
		assertEquals("Jackie Chan", comm2.getPlayer().getUsername());
		assertEquals(player3, comm3.getPlayer());
		assertEquals(897, comm3.getPlayer().getID());
		assertEquals("Bruce Lee", comm3.getPlayer().getUsername());
		Mockito.mock(CommunicationNetwork.class);
		CommunicationNetwork dud = Mockito.mock(CommunicationNetwork.class);
	}

	/**
	 * Tests the creating of a node and adding participants into it.
	 */
	@Test
	public void initChat() {
		List<Integer> chatParticipants = new ArrayList<Integer>();
		chatParticipants.add(player1.getID());
		chatParticipants.add(player2.getID());
		chatParticipants.add(player3.getID());
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
		chatParticipants.add(player3.getID());

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
	 * Not sure how to check chat history at the moment. No getHistory() method?
	 */
	@Test
	public void chatHistory() {
		List<Integer> chatParticipants = new ArrayList<Integer>();
		List<String> chatHistory = new ArrayList<String>();
		chatParticipants.add(player1.getID());
		chatParticipants.add(player2.getID());
		chatParticipants.add(player3.getID());
		comm1.createChat(chatParticipants);
		comm1.getCurrentChat().addMessage("TESTING 1");
		comm1.getCurrentChat().addMessage("TESTING 2");
		comm1.getCurrentChat().addMessage("NANANANANANAN BATMAN!");
		comm1.getCurrentChat().addMessage("suicidal W4t3RM3L0N !@#$%^&*()");
		chatHistory.add("TESTING 1");
		chatHistory.add("TESTING 2");
		chatHistory.add("NANANANANANAN BATMAN!");
		chatHistory.add("suicidal W4t3RM3L0N !@#$%^&*()");

		// assertEquals(chatHistory, comm1.getCurrentChat().getHistory());
	}

	/**
	 * Not Sure how to test this when server is down and many methods in
	 * ChatNode are currently commented out.
	 */
	@Test
	public void sendMessage() {
		List<Integer> chatParticipants = new ArrayList<Integer>();
		chatParticipants.add(player1.getID());
		chatParticipants.add(player2.getID());
		chatParticipants.add(player3.getID());
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
