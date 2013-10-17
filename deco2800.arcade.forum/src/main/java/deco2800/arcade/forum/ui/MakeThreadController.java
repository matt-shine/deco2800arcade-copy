package deco2800.arcade.forum.ui;

import com.esotericsoftware.kryonet.*;

import java.awt.*;
import java.awt.event.*;

import deco2800.arcade.forum.ClientConnection;
import deco2800.arcade.forum.ForumException;
import deco2800.arcade.model.forum.ForumUser;
import deco2800.arcade.protocol.Protocol;
import deco2800.arcade.protocol.forum.*;

public class MakeThreadController implements ActionListener {
	private MakeThreadView view;
	private Client connection;
	
	public MakeThreadController (MakeThreadView viewModel) throws ForumException {
		this.view = viewModel;
		this.connection = ClientConnection.getClient("", 0, 0);
		this.connection.addListener(new Listener() {
			public void received(Connection con, Object object) {
				if (object instanceof InsertParentThreadResponse) {
					System.out.println("Server response is received");
					InsertParentThreadResponse response = (InsertParentThreadResponse) object;
					if (response.error != "") {
						System.out.println("yeah... didnt work");
					} else {
						if (response.result != -1) {
							System.out.println("Thread Posted");
						} else {
							System.out.println("Thread failed to post");
						}
					}
				}
				return;
			}
		});
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("submit clicked");
		InsertParentThreadRequest request = new InsertParentThreadRequest();
		request.topic = "SSSS";
		request.message = "AAA";
		request.createdBy = 0;
		request.category = "roflmao";
		request.tags = "lol";
		this.connection.sendTCP(request);
		System.out.println("pThread request is sent");
		return;
	}
	
}