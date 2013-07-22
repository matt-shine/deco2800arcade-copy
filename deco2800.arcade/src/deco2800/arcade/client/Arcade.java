package deco2800.arcade.client;
import javax.swing.JFrame;

import org.lwjgl.util.Dimension;

import com.badlogic.gdx.backends.lwjgl.LwjglCanvas;

import deco2800.arcade.client.model.Game;
import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.client.network.NetworkException;
import deco2800.arcade.client.startup.UserNameDialog;
import deco2800.arcade.protocol.connect.ConnectionRequest;


public class Arcade extends JFrame {

	private static Arcade ARCADE;
	
	public static Arcade getInstance(){
		return ARCADE;
	}
	
	private NetworkClient client;

	private int width, height;
	
	//private static String username = "Bob";
	private String serverIPAddress = "10.33.1.133";
	private Object[] availableGames = {"Tic Tac Toe"};

	private LwjglCanvas canvas;
	
	private Arcade(String[] args){
		this.width = 640;
		this.height = 480;
		
		this.setSize(width, height);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	private void connectToServer() throws ArcadeException{
		try {
			client = new NetworkClient(serverIPAddress, 54555, 54777);
			addListeners();
		} catch (NetworkException e) {
			e.printStackTrace();
			throw new ArcadeException("Unable to connect to Arcade Server", e);
		}
	}
	
	private void addListeners(){
		
	}
	
	private void connectAsUser(String username){
		ConnectionRequest connectionRequest = new ConnectionRequest();
		connectionRequest.username = username;
		
		this.client.sendNetworkObject(connectionRequest);
	}

	public void startGame(Game game){
		if (canvas != null){
			this.remove(canvas.getCanvas());
			//TODO anything else required to stop previous game
		}
		
		this.canvas = new LwjglCanvas(game, true);
		this.canvas.getCanvas().setSize(width, height);
		this.add(this.canvas.getCanvas());
	}
	
	public static void main(String[] args) {
		ARCADE = new Arcade(args);

		//Try to connect to the server until successful
		boolean connected = false;
		while (!connected){
			try {
				ARCADE.connectToServer();
				connected = true;
			} catch (ArcadeException e) {
				e.printStackTrace();
			}
		}

		//Get the username off the user and connect to the server with it
		String username = UserNameDialog.getUsername(ARCADE);
		ARCADE.connectAsUser(username);
		
//		try {
//
//
//
//			client.addListener(new Listener() {
//				public void received(Connection connection, Object object) {
//					if (object instanceof ConnectionResponse) {
//						Object selectedGame = JOptionPane.showInputDialog(arcade, "Select a game", "Cancel", JOptionPane.PLAIN_MESSAGE,null,availableGames,availableGames[0]);
//						if (selectedGame.equals(availableGames[0])) {
//							LwjglCanvas canvas = new LwjglCanvas(new TicTacToe(), true);
//							canvas.getCanvas().setSize(640,480);
//							arcade.add(canvas.getCanvas());
//						}
//					}
//				}
//			});
//
//			ConnectionRequest creq = new ConnectionRequest();
//			creq.username = username;
//			client.sendTCP(creq);
//
//
//
//
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}


	}

}
