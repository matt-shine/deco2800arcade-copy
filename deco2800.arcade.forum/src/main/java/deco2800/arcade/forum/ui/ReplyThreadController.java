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

public class ReplyThreadController implements ActionListener {
	private ReplyThreadView view;
	private ClientConnection connection;
	private int pid;
	
	public ReplyThreadController (ReplyThreadView viewModel, int pid) throws ForumException {
		this.view = viewModel;
		this.pid = pid;
		this.connection = new ClientConnection("", 0, 0);
		this.connection.addListener(new Listener() {
			public void received(Connection con, Object object) {
				if (object instanceof InsertChildThreadResponse) {
					System.out.println("Server response is received");
					InsertChildThreadResponse response = (InsertChildThreadResponse) object;
					if (response.error != "") {
						System.out.println("yeah... didnt work");
					} else {
						System.out.println("cthread posted without a problem");
						view.closeWindow();
					}
				}
				return;
			}
		});
		this.view.submitBtn.addActionListener(this);
		this.view.cancelBtn.addActionListener(new CloseActionListener());
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		System.out.println("submit clicked");
		InsertChildThreadRequest request = new InsertChildThreadRequest();
		request.message = this.view.textPane.getText();
		request.createdBy = 0;
		request.pThread = this.pid;
		this.connection.getClient().sendTCP(request);
		System.out.println("cThread request is sent");
		return;
	}
	
	private class CloseActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			view.closeWindow();
		}
	}
}