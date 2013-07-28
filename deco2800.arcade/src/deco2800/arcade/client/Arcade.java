package deco2800.arcade.client;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.reflections.Reflections;

import com.badlogic.gdx.backends.lwjgl.LwjglCanvas;

import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.client.network.NetworkException;
import deco2800.arcade.client.network.listener.AchievementListener;
import deco2800.arcade.client.network.listener.ConnectionListener;
import deco2800.arcade.client.network.listener.CreditListener;
import deco2800.arcade.client.network.listener.GameListener;
import deco2800.arcade.client.startup.UserNameDialog;
import deco2800.arcade.model.Game.ArcadeGame;
import deco2800.arcade.model.Player;
import deco2800.arcade.protocol.connect.ConnectionRequest;
import deco2800.arcade.protocol.credit.CreditBalanceRequest;
import deco2800.arcade.protocol.game.GameRequestType;
import deco2800.arcade.protocol.game.NewGameRequest;


public class Arcade extends JFrame {

	private static Arcade ARCADE;
	
	public static Arcade getInstance(){
		return ARCADE;
	}
	
	private NetworkClient client;

	private Player player;
	
	private int width, height;
	
	//private static String username = "Bob";
	private String serverIPAddress = "127.0.0.1";
	//private Object[] availableGames = {"Tic Tac Toe"};

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
		this.client.addListener(new AchievementListener());
		this.client.addListener(new ConnectionListener());
		this.client.addListener(new CreditListener());
		this.client.addListener(new GameListener());
	}
	
	private void connectAsUser(String username){
		ConnectionRequest connectionRequest = new ConnectionRequest();
		connectionRequest.username = username;
		
		this.client.sendNetworkObject(connectionRequest);
		
		CreditBalanceRequest creditBalanceRequest = new CreditBalanceRequest();
		creditBalanceRequest.username = username;
		
		this.client.sendNetworkObject(creditBalanceRequest);
		
		this.player = new Player();
		this.player.setUsername(username);
	}

	public void requestGameSession(GameClient gameClient){
		if (canvas != null){
			this.remove(canvas.getCanvas());
			//TODO anything else required to stop previous game
		}
		
		NewGameRequest newGameRequest = new NewGameRequest();
		newGameRequest.gameId = gameClient.getGame().gameId;
		newGameRequest.username = player.getUsername();
		newGameRequest.requestType = GameRequestType.NEW;
		this.client.sendNetworkObject(newGameRequest);
	}
	
	public void startSelectedGame(){
		this.canvas = new LwjglCanvas(selectedGame, true);
		this.canvas.getCanvas().setSize(width, height);
		this.add(this.canvas.getCanvas());
		selectedGame.addGameOverListener(new GameOverListener() {

			@Override
			public void notify(GameClient client) {
				canvas.stop();
				remove(canvas.getCanvas());
				selectGame();
			}
			
		});
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
				//Server unavailable - ask user whether or not to try again
				int userInput = JOptionPane.showConfirmDialog(ARCADE, "Unable to connect to Arcade Server - Try Again?", "Network Error", JOptionPane.YES_NO_OPTION);
				boolean keepTrying = (userInput == 0);
				if (!keepTrying){
					//Exit the program
					System.exit(0);
				}
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

	private Map<String,Class<? extends GameClient>> gameMap = new HashMap<String,Class<? extends GameClient>>();

	private GameClient selectedGame;
	
	private Map<String,Class<? extends GameClient>> getGameMap() {
		if (gameMap.isEmpty()) {
			Reflections reflections = new Reflections("deco2800.arcade");
			Set<Class<?>> possibleGames = reflections.getTypesAnnotatedWith(ArcadeGame.class);
			for (Class<?> g : possibleGames) {
				if (GameClient.class.isAssignableFrom(g)) {
					Class<? extends GameClient> game = g.asSubclass(GameClient.class);
					ArcadeGame aGame = g.getAnnotation(ArcadeGame.class);
					String gameId = aGame.id();
					gameMap.put(gameId, game);
				}
			}
			return gameMap;
		} else {
			return gameMap;
		}
	}
	
	private Set<String> findGameIds() {
		return getGameMap().keySet();
	}

	public void selectGame() {
		Object[] gameList = findGameIds().toArray();
		String selectedGameId = (String) JOptionPane.showInputDialog(this,"Select a game", "Cancel", JOptionPane.PLAIN_MESSAGE,null,gameList,gameList[0]);
		Class<? extends GameClient> gameClass = getGameMap().get(selectedGameId);
		try {
			Constructor<? extends GameClient> constructor = gameClass.getConstructor(Player.class, NetworkClient.class);
			selectedGame = constructor.newInstance(player, client);
			requestGameSession(selectedGame);
			
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
