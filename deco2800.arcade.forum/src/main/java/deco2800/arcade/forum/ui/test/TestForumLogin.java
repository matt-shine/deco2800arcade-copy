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
import deco2800.arcade.protocol.forum.*;

/**
 * Test version of ForumLogin.java. MVC is not implemented.
 * Tips: 
 * <ul>
 * 	<li>Set network listener when a view is created.</li>
 *  <li>Use internal class make easy to read and fix</li>
 *  <li>Do not sent a class or a class which contains classes that have 
 *  a constructor which requires arguments (cause serialization error). </li>
 *  <li>To run this, off course, you need run server beforehand.</li>
 * </ul>
 * 
 * @author Junya, Team Forum
 */
public class TestForumLogin extends JFrame {
	private ForumUser userModel;
	private ClientConnection connection;
	
	/**
	 * Constructor: create interface and model and establish connection.
	 * 
	 * @throws ForumException
	 */
	public TestForumLogin() throws ForumException {
		super("Login");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(450, 300);
		this.setLocation(300, 200);
		this.getContentPane().setLayout(new FlowLayout());
		
		this.connection = new ClientConnection("", 0, 0);
		this.connection.addListener(new Listener() {
			/* print disconnected message if connection is disconnected 
			 * Generally caused by server's error
			 * @see com.esotericsoftware.kryonet.Listener#disconnected(com.esotericsoftware.kryonet.Connection)
			 */
			public void disconnected(Connection connection) {
				System.out.println("disconnected");
			}
		});
		this.userModel = new ForumUser(0, null);
		this.getContentPane().add(new LoginView(this.connection, this.userModel));
	}
	
	/**
	 * GUI of LoginView
	 */
	public class LoginView extends JPanel implements Observer {
		private static final long serialVersionUID = 1L;
		private ForumUser userModel;
		private JTextField nameField;
		private JButton loginButton;
		private JTextField statusField;
		private LoginController controller;
		private ClientConnection connection;
		
		/**
		 * Constructor: some parameters are passed from the top-level class. 
		 * It creates interface, controller (for view and connection).
		 * @param connection
		 * @param userModel
		 * @throws ForumException
		 */
		public LoginView(ClientConnection connection, ForumUser userModel) throws ForumException {
			super();
			this.setLayout(new GridLayout(3, 1));
			this.userModel = userModel;
			this.nameField = new JTextField(20);
			this.loginButton = new JButton("Login");
			this.statusField = new JTextField(20);
			this.controller = new LoginController(userModel, connection);
			this.connection = connection;
			this.add(this.nameField);
			this.add(this.loginButton);
			this.add(this.statusField);
			this.connection.addListener(new Listener() {
				/* Actions if server's response is received */
				public void received(Connection con, Object object) {
					if (object instanceof GetForumUserResponse) {
						System.out.println("Server response is received");
						GetForumUserResponse response = (GetForumUserResponse) object;
						if (response.error != "") {
							System.err.println("ResponseError:" + response.error);
						} else {
							if (response.result != null) {
								System.out.println("Login success");
								LoginView.this.userModel.setData(response.result.id, response.result.name);
								LoginView.this.statusField.setText(LoginView.this.userModel.toString());
								con.close();
							} else {
								LoginView.this.statusField.setText("Not registered");
							}
						}
					}
					return;
				}
			});
		}
		
		/**
		 * Controller for on-click mouse event (Action Listener).
		 */
		private class LoginController implements ActionListener {
			private ForumUser userModel;
			private ClientConnection connection;
			
			public LoginController(ForumUser userModel, ClientConnection connection) throws ForumException {
				this.userModel = userModel;
				this.connection = connection;
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
				/* Send request to server */
				this.connection.getClient().sendTCP(request);
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
