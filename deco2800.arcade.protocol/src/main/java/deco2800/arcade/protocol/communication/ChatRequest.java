package deco2800.arcade.protocol.communication;

import java.util.ArrayList;
import java.util.List;

import deco2800.arcade.protocol.UserRequest;

public class ChatRequest  extends UserRequest {

	public List<String> participants = new ArrayList<String>();
	public String sender;
	public String invite;
	public int chatID;
	
}
