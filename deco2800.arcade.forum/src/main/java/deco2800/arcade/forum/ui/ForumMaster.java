package deco2800.arcade.forum.ui;

import java.awt.*;
import javax.swing.*;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import deco2800.arcade.forum.*;

/**
 * Main class for forum interface
 *
 * @author TeamForum
 */
public class ForumMaster {
	
	public ForumMaster() {
		//Initialize new JFrame for forum interface
		JFrame f = new JFrame("Arcade Forum");
		f.setSize(1024, 768);
		f.setLocation(300,200);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.getContentPane().setLayout(null);
  
		ForumUi view = new ForumUi(f);
		new ForumUIControl(view);
	}
	
	/**
	 * Return established client-side KryoNet connection.
	 * 
	 * @return	ClientConnection, including KryoNet.Client
	 * @throws ForumException
	 * @see deco2800.arcade.forum.ClientConnection
	 */
	private ClientConnection initConnection() throws ForumException {
		ClientConnection con = new ClientConnection("", 0, 0);
		ClientConnection.registerProtocol(con.getClient());
		DisconnectedListener listener = new DisconnectedListener();
		con.addListener(listener);
		return con;
	}
	
	private class DisconnectedListener extends Listener {
		@Override
		/**
		 * Note; no guarantee to invoke this method according to KryoNet Javadoc.
		 */
		public void disconnected(Connection con) {
			System.err.println("Connection is disconnected");
		}
	}
}