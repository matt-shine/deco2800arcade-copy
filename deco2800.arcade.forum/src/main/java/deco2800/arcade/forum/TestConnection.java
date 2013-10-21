package deco2800.arcade.forum;

import deco2800.arcade.protocol.forum.ForumTestRequest;
import deco2800.arcade.protocol.forum.ForumTestResponse;


public class TestConnection {
	
	public static void main(String[] args) throws Exception {
		ClientConnection con = new ClientConnection("127.0.0.1", 54555, 54777);
		con.addListener(new ForumClientListener());
		con.getClient().getKryo().register(ForumTestRequest.class);
		con.getClient().getKryo().register(ForumTestResponse.class);
		ForumTestRequest request = new ForumTestRequest();
		request.num = 666;
		con.getClient().sendTCP(request);
	}
	
}
