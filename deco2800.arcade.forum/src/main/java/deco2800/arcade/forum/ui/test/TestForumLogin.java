package deco2800.arcade.forum.ui.test;

import java.awt.*;
import java.awt.event.*;
import java.util.Observable;
import java.util.Observer;
import javax.swing.*;

import com.esotericsoftware.kryonet.*;

import deco2800.arcade.forum.ClientConnection;
import deco2800.arcade.forum.ForumException;
import deco2800.arcade.model.forum.ForumUser;
import deco2800.arcade.protocol.Protocol;
import deco2800.arcade.protocol.forum.*;

/**
 * Test version of ForumLogin.java
 * @author Junya
 *
 */
public class TestForumLogin extends JFrame {
	private ForumUser userModel;
	
	public TestForumLogin() throws ForumException {
		super("Login");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(450, 300);
		this.setLocation(300, 200);
		this.getContentPane().setLayout(new FlowLayout());
		
		this.userModel = new ForumUser(0, null);
		this.getContentPane().add(new LoginView(this.userModel));
	}
	
	public class LoginView extends JPanel implements Observer {
		private static final long serialVersionUID = 1L;
		private ForumUser userModel;
		private JTextField nameField;
		private JButton loginButton;
		private JTextField statusField;
		private LoginController controller;
		
		public LoginView(ForumUser userModel) throws ForumException {
			super();
			this.setLayout(new GridLayout(3, 1));
			this.userModel = userModel;
			this.nameField = new JTextField(20);
			this.loginButton = new JButton("Login");
			this.statusField = new JTextField(20);
			this.controller = new LoginController(userModel);
			
			this.add(this.nameField);
			this.add(this.loginButton);
			this.add(this.statusField);
		}
		
		private class LoginController implements ActionListener {
			private ForumUser userModel;
			private Client connection;
			
			public LoginController(ForumUser userModel) throws ForumException {
				this.userModel = userModel;
				this.connection = ClientConnection.getClient("", 0, 0);
				this.connection.addListener(new Listener() {
					public void received(Connection con, Object object) {
						if (object instanceof GetForumUserResponse) {
							System.out.println("Server response is received");
							GetForumUserResponse response = (GetForumUserResponse) object;
							if (response.error != "") {
								LoginView.this.statusField.setText("Error: " + response.error);
							} else {
								if (response.result != null) {
									System.out.println("Login success");
									LoginController.this.userModel.setData(response.result.getId(), response.result.getName());
								} else {
									LoginView.this.statusField.setText("Not registered");
								}
							}
						}
						return;
					}
				});
				LoginView.this.loginButton.addActionListener(this);
			}
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String userName = LoginView.this.nameField.getText();
				System.out.println("LoginButton clicked");
				System.out.println(userName);
				GetForumUserRequest request = new GetForumUserRequest();
				request.userId = 0;
				request.userName = userName;
				this.connection.sendTCP(request);
				System.out.println("Request is sent");
				return;
			}
			
		}

		@Override
		public void update(Observable o, Object arg) {
			// TODO Auto-generated method stub
			
		}
	}
	
	public static void main(String[] args) throws ForumException {
		TestForumLogin frame = new TestForumLogin();
		frame.setVisible(true);
	}
	
}
