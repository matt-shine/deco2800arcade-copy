package deco2800.arcade.protocol.communication;

import java.util.ArrayList;

import deco2800.arcade.protocol.UserRequest;

public class ChatRequest  extends UserRequest {

	public ArrayList<String> participants = new ArrayList<String>();
	public String invite;
	public String sender;
	public int chatID;
	
}
