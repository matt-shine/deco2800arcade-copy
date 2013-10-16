package deco2800.arcade.server.database;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.junit.Test;

import deco2800.server.database.ChatStorage;

public class TestChatStorage {
	/**
	 * Tests the adding of chat history and retrieving of that history by
	 * playersID. Also checks order is intact.
	 */
	@Test
	public void AddingHistory() {
		HashMap<Integer, List<String>> chatHistory = new HashMap<Integer, List<String>>();
		List<String> chat123 = new ArrayList<String>();
		List<String> chat9999 = new ArrayList<String>();
		List<String> chat124 = new ArrayList<String>();
		List<String> chat125 = new ArrayList<String>();
		List<String> chat127 = new ArrayList<String>();
		List<String> chat126 = new ArrayList<String>();

		chat123.add("123");
		chat123.add("123");
		chat124.add("124");
		chat125.add("125");
		chat126.add("126");
		chat126.add("126");
		chat127.add("127");
		chat9999.add("9999");

		chatHistory.put(123, chat123);
		chatHistory.put(124, chat124);
		chatHistory.put(126, chat126);
		chatHistory.put(127, chat127);
		chatHistory.put(125, chat125);
		chatHistory.put(9999, chat9999);

		ChatStorage cs = new ChatStorage();
		cs.addChatHistory(9999, 123, "123");
		cs.addChatHistory(9999, 126, "126");
		cs.addChatHistory(9999, 123, "123");
		cs.addChatHistory(9999, 9999, "9999");
		cs.addChatHistory(9999, 124, "124");
		cs.addChatHistory(9999, 125, "125");
		cs.addChatHistory(9999, 127, "127");
		cs.addChatHistory(9999, 126, "126");

		for (Entry<Integer, List<String>> entry : chatHistory.entrySet()) {
			assertEquals(entry.getValue(),
					cs.getChatHistory(9999).get(entry.getKey()));
		}
	}
}
