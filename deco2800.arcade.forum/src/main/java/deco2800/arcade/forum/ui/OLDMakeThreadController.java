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

public class OLDMakeThreadController implements ActionListener {
	private MakeThreadView view;
	private ClientConnection connection;
	
	public OLDMakeThreadController (MakeThreadView viewModel) throws ForumException {
		this.view = viewModel;
		this.connection = new ClientConnection("", 0, 0);
		this.connection.addListener(new Listener() {
			public void received(Connection con, Object object) {
				if (object instanceof InsertParentThreadResponse) {
					System.out.println("Server response is received");
					InsertParentThreadResponse response = (InsertParentThreadResponse) object;
					if (response.error != "") {
						System.out.println("yeah... didnt work");
					} else {
						if (response.result != 0) {
							System.out.println("Thread Posted");
							view.closeWindow();
						} else {
							System.out.println("Thread failed to post");
							JOptionPane.showMessageDialog(null, "Failed to post thread", 
									"Error", JOptionPane.ERROR_MESSAGE);
						}
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
		InsertParentThreadRequest request = new InsertParentThreadRequest();
		request.topic = this.view.TitleTBox.getText();
		request.message = this.view.textPane.getText();
		request.createdBy = 0;
		request.category = this.view.categoryCBox.getSelectedItem().toString();
		request.tags = this.view.TagsTBox.getText();
		this.connection.getClient().sendTCP(request);
		System.out.println("pThread request is sent");
		return;
	}
	
	private class CloseActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			view.closeWindow();
		}
	}
}