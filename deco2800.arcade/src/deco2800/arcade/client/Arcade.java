package deco2800.arcade.client;
import java.awt.Dialog;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.badlogic.gdx.backends.lwjgl.LwjglCanvas;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import deco2800.arcade.protocol.ConnectRequest;
import deco2800.arcade.protocol.ConnectionOK;
import deco2800.arcade.protocol.Protocol;
import deco2800.arcade.tictactoe.TicTacToe;


public class Arcade extends JFrame {

	//private static String username = "Bob";
	private static String serverIPAddress = "10.33.1.133";
	private static Object[] availableGames = {"Tic Tac Toe"};
	
	public static void main(String[] args) {
		final Arcade arcade = new Arcade();
		arcade.setSize(640, 480);
		arcade.setVisible(true);
		arcade.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		Client client = new Client();
		client.start();
		
		try {
			client.connect(5000, serverIPAddress, 54555, 54777);
			
			Protocol.register(client.getKryo());
			
			String username = null;
			while (username == null || username.isEmpty()) {
				username = JOptionPane.showInputDialog(arcade, "Enter Username");
			}
			
			client.addListener(new Listener() {
				public void received(Connection connection, Object object) {
					if (object instanceof ConnectionOK) {
						Object selectedGame = JOptionPane.showInputDialog(arcade, "Select a game", "Cancel", JOptionPane.PLAIN_MESSAGE,null,availableGames,availableGames[0]);
						if (selectedGame.equals(availableGames[0])) {
							LwjglCanvas canvas = new LwjglCanvas(new TicTacToe(), true);
							canvas.getCanvas().setSize(640,480);
							arcade.add(canvas.getCanvas());
						}
					}
				}
			});
			
			ConnectRequest creq = new ConnectRequest();
			creq.username = username;
			client.sendTCP(creq);
			

			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
