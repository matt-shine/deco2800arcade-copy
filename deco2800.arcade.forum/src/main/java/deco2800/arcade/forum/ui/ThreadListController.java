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
	private ThreadListModel model;
	private ClientConnection connection;
	public ThreadListController (GeneralDiscussion v, ThreadListModel m) throws ForumException {
		this.view = v;
		this.model = m;
		this.connection = new ClientConnection("", 0, 0);
		System.out.println("Request load");
		GetParentThreadsRequest request = new GetParentThreadsRequest();
		request.start = 0;
		request.end = 0;
		request.limit = 10;
		request.category = this.model.get_category();
		request.userId = 0;
		System.out.println("Request sent");		
		this.connection.getClient().sendTCP(request);
		System.out.println("pThread request is sent");
		this.view.btnNextButton.addActionListener(new NextTenListener());
		this.view.btnPrevButton.addActionListener(new PrevTenListener());
		
		this.connection.addListener(new Listener() {
			public void received(Connection con, Object object) {
				if (object instanceof GetParentThreadsResponse) {
					System.out.println("Server response is received");
					GetParentThreadsResponse response = (GetParentThreadsResponse) object;
					if (response.error != "") {
						System.out.println(response.error);
						System.out.println("yeah... didnt work");
					} else {
						if (response.result != null) {
							view.clearThreadPanel();
							model.thread_load(ParentThreadProtocol.getParentThreads(response.result));
							System.out.println("Thread loaded");
							view.clearThreadPanel();
							for (int i = 0; i < model.get_size(); i++) {
								view.addInnerThreadPanel(model.get_thread(i));
							}
							view.updateThreadPanel();
						} 
						/*else {
							System.out.println("Failed to load thread");
							JOptionPane.showMessageDialog(null, "Failed to load threads", 
									"Error", JOptionPane.ERROR_MESSAGE);
						}*/
					}
				}
				if (object instanceof GetChildThreadsResponse) {
					System.out.println("Server response is received");
					GetChildThreadsResponse response = (GetChildThreadsResponse) object;
					if (response.error != "") {
						System.out.println(response.error);
						System.out.println("yeah... didnt work");
					} else {
						if (response.result != null) {
							model.child_thread_load(ChildThreadProtocol.getChildThreads(response.result));
							System.out.println("Thread loaded");
							view.threadGrid.setRows(model.get_child_size() + 1);
							for(int j = 0; j < model.get_child_size(); j++) {
								view.display_child(model.get_child_thread(j));
							}
							view.update_display();
						} 
						/*else {
							System.out.println("Failed to load thread");
							JOptionPane.showMessageDialog(null, "Failed to load threads", 
									"Error", JOptionPane.ERROR_MESSAGE);
						}*/
					}
				}
				return;
			}
		});		
	}

	
	
	private void nextTen() {
		System.out.println("Request load");
		GetParentThreadsRequest request = new GetParentThreadsRequest();
		request.start = this.model.get_thread(this.model.get_size()-1).getId();
		request.end = 0;
		request.limit = 9;
		request.category = this.model.get_category();
		request.userId = 0;
		System.out.println("Request sent");		
		this.connection.getClient().sendTCP(request);
		System.out.println("pThread request is sent");
	}
	
	private void prevTen() {
		System.out.println("Request load");
		GetParentThreadsRequest request = new GetParentThreadsRequest();
		request.start = 0;
		request.end = this.model.get_thread(0).getId();
		System.out.println(request.end);
		request.limit = 9;
		request.category = this.model.get_category();
		request.userId = 0;
		System.out.println("Request sent");		
		this.connection.getClient().sendTCP(request);
		System.out.println("pThread request is sent");
	}
	
	private class NextTenListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			nextTen();
		}
	}
	
	private class PrevTenListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			prevTen();
		}
	}
	
	public void request_childs(int parentid) {
		GetChildThreadsRequest request = new GetChildThreadsRequest();
		request.pid = parentid;
		request.start = 0;
		request.end = 0;
		request.limit = 0;
		request.userId = 0;
		System.out.println("Get childs request sent");		
		this.connection.getClient().sendTCP(request);
	}
	
	
}