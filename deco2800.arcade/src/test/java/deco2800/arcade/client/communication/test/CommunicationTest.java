package deco2800.arcade.client.communication.test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.esotericsoftware.kryonet.Connection;

import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.client.network.NetworkException;
import deco2800.arcade.client.network.listener.CommunicationListener;
import deco2800.arcade.communication.CommunicationNetwork;
import deco2800.arcade.model.Player;
import deco2800.arcade.protocol.communication.ChatHistory;
import deco2800.arcade.protocol.communication.TextMessage;

public class CommunicationTest {

	private Player player1, player2, player3;

	private CommunicationNetwork comm1, comm2, comm3;


	private NetworkClient client1, client2, client3;
	
	private CommunicationListener listener1, listener2, listener3;
	
	@Before
	public void initialise() throws NetworkException {
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
		
		client1 = new NetworkClient("127.0.0.1", 54555, 54777);

		client2 = new NetworkClient("127.0.0.1", 54555, 54777);
		
		client3 = new NetworkClient("127.0.0.1", 54555, 54777);
		
		player1 = new Player(123, "THIS IS NOT A VALID PATH.html", info, null,
				null, null, null, privset);
		player2 = new Player(234, "THIS IS NOT A VALID PATH.html", info2, null,
				null, null, null, privset);
		player3 = new Player(235, "THIS IS NOT A VALID PATH.html", info3, null,
				null, null, null, privset);
		
		comm1 = new CommunicationNetwork(player1, client1);
		comm2 = new CommunicationNetwork(player2, client2);
		comm3 = new CommunicationNetwork(player3, client3);
		listener1 = new CommunicationListener(comm1);
		listener2 = new CommunicationListener(comm2);
		listener3 = new CommunicationListener(comm3);
		client1.addListener(listener1);
		client2.addListener(listener2);
		client3.addListener(listener3);
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
	 * Test currently calls the listeners recieved method manually, once proper
	 * sending has been set up this should be done automatically. Must change
	 * test when this happens.
	 */
	@Test
	public void sendMessage() {
		Connection connection = null;
		TextMessage message = new TextMessage();
		List<Integer> chatParticipants = new ArrayList<Integer>();

		chatParticipants.add(player1.getID());
		chatParticipants.add(player2.getID());
		chatParticipants.add(player3.getID());
		comm1.createChat(chatParticipants);

		message.recipients = chatParticipants;
		message.chatID = 1111;
		message.senderID = 123;
		message.senderUsername = "Chuck Norris";
		message.text = "QWERTYUIOP";
		comm1.sendTextMessage(message);

		listener2.received(connection, message);

		
		assertEquals(comm1.getCurrentChat().getID(), comm2.getCurrentChat()
				.getID());
		assertEquals(comm1.getCurrentChat().getParticipants().get(0), comm2
				.getCurrentChat().getParticipants().get(0));
		assertEquals(comm1.getCurrentChat().getText(), comm2.getCurrentChat()
				.getText());
		
		TextMessage message1 = new TextMessage();
		message1.recipients = chatParticipants;
		message1.chatID = 1112;
		message1.senderID = 123;
		message1.senderUsername = "Chuck Norris";
		message1.text = "New Game";
		comm1.sendTextMessage(message1);
		listener3.received(connection, message1);
		listener2.received(connection, message1);
		
		assertEquals(comm1.getCurrentChat().getText(), comm2.getCurrentChat()
				.getText());
		assertEquals(comm1.getCurrentChat().getText(), comm3.getCurrentChat()
				.getText());

	}
}
