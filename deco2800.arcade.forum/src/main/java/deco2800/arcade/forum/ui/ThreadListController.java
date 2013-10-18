package deco2800.arcade.forum.ui;

import com.esotericsoftware.kryonet.*;

import java.awt.*;
import java.awt.event.*;

import javax.swing.JOptionPane;

import deco2800.arcade.forum.ClientConnection;
import deco2800.arcade.forum.ForumException;
import deco2800.arcade.model.forum.ForumUser;
import deco2800.arcade.protocol.Protocol;
import deco2800.arcade.protocol.forum.*;

public class ThreadListController {
	private GeneralDiscussion view;
	private ClientConnection connection;
	public ThreadListController (GeneralDiscussion viewModel) throws ForumException {
		this.view = viewModel;
		this.connection = new ClientConnection("", 0, 0);
		
		System.out.println("Request load");
		GetParentThreadsRequest request = new GetParentThreadsRequest();
		request.start = 0;
		request.end = 0;
		request.limit = 10;
		System.out.println("request send");		
		this.connection.getClient().sendTCP(request);
		System.out.println("pThread request is sent");
		
		this.connection.addListener(new Listener() {
			public void received(Connection con, Object object) {
				if (object instanceof GetParentThreadsResponse) {
					System.out.println("Server response is received");
					GetParentThreadsResponse response = (GetParentThreadsResponse) object;
					if (response.error != "") {
						System.out.println("yeah... didnt work");
					} else {
						if (response.result != null) {
							System.out.println("Thread achieved");
						} else {
							System.out.println("Failed to get thread");
							JOptionPane.showMessageDialog(null, "Failed to get thread", 
									"Error", JOptionPane.ERROR_MESSAGE);
						}
					}
				}
				return;
			}
		});
	}

}