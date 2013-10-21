package deco2800.arcade.forum;

import java.io.IOException;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import deco2800.arcade.protocol.forum.ForumTestRequest;
import deco2800.arcade.protocol.forum.ForumTestResponse;

public class TestConnection2 {
	public static void main(String[] args) throws Exception {
		TestConnection2 test = null;
		try {
			test= new TestConnection2();
			System.out.println("Waiting...");
			while(test.model == "") {
			}
			test.con.closeConnection();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			System.exit(0);
		}
	}
	
	public ClientConnection con;
	public String model;
	
	public TestConnection2() throws ForumException {
		this.model = "";
		this.con = new ClientConnection("", 0, 0);
		TestListener lis = new TestConnection2.TestListener();
		this.con.addListener(lis);
		ForumTestRequest request = new ForumTestRequest();
		request.num = 2;
		this.con.getClient().sendTCP(request);
		System.out.println("Request is sent");
	}
	
	public class TestListener extends Listener {
		public TestListener() {
			super();
		}
		
		@Override
		public void connected(Connection connection) {
			super.connected(connection);
			System.out.println("connected");
		}
		
		@Override
		public void disconnected(Connection connection) {
			super.disconnected(connection);
			System.out.println("disconnected");
		}
		
		@Override
		public void received(Connection connection, Object object) {
			super.received(connection, object);
			System.out.println("received");
			if (object instanceof ForumTestResponse) {
				ForumTestResponse response = (ForumTestResponse) object;
				//if (response.error != "") {
					System.out.println("Response Result: " + response.result);
					System.err.println("Error Result: " + response.error);
					model = response.result;
				//} else {
				//	System.err.println("Response error:");
				//	System.err.println(response.error);
				//}
			}
		}
		
		@Override 
		public void idle(Connection connection) {
			super.idle(connection);
		}
	}
}
