package deco2800.arcade.protocol.communication;

import java.util.ArrayList;
import java.util.List;

import deco2800.arcade.protocol.UserRequest;

public class TextMessage extends UserRequest {
	public List<Integer> recipients = new ArrayList<Integer>();
	public String text;
	public String senderName;
	public int senderID;
	public int chatID;
}
