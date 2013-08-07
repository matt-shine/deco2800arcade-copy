package deco2800.arcade.client;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.WindowEvent;
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
import deco2800.arcade.client.startup.GameSelector;
import deco2800.arcade.client.startup.UserNameDialog;
import deco2800.arcade.model.Game.ArcadeGame;
import deco2800.arcade.model.Player;
import deco2800.arcade.protocol.connect.ConnectionRequest;
import deco2800.arcade.protocol.credit.CreditBalanceRequest;
import deco2800.arcade.protocol.game.GameRequestType;
import deco2800.arcade.protocol.game.NewGameRequest;

/**
 * The client application for running arcade games.
 *
 */
public class Arcade extends JFrame {

	/**
	 * Only exists to stop warning
	 */
	private static final long serialVersionUID = 3609353264826109097L;

	private static Arcade ARCADE;
	
	/**
	 * @return Returns an instance of the arcade
	 */
	public static Arcade getInstance(){
		return ARCADE;
	}
	
	private NetworkClient client;

	private Player player;
	
	private int width, height;
	
	private String serverIPAddress = "127.0.0.1";

	private LwjglCanvas canvas;
	
	private GameClient selectedGame = null;
	private GameClient mainUI = null;
	/**
	 * Sets the instance variables for the arcade
	 * @param args
	 */
	private Arcade(String[] args){
		this.width = 640;
		this.height = 480;
		
		this.setSize(new Dimension(width, height));
		this.setVisible(true);
		Insets insets = this.getInsets();
		this.setSize(new Dimension(width + insets.left + insets.right, height + insets.bottom + insets.top));
		this.getContentPane().setBackground(Color.black);
		
		this.addWindowListener(new java.awt.event.WindowAdapter() {
		    public void windowClosing(WindowEvent winEvt) {
		    	
				/* TODO: make the program shutdown properly. This line seems to 
		    	 * cause a deadlock. Not calling it will leave the program running in
		    	 * the background
		    	 */
		    	System.exit(0);
		    	
		    }
		});
	}

	/**
	 * Attempt to initiate a connection with the server.
	 * 
	 * @throws ArcadeException if the connection failed.
	 */
	private void connectToServer() throws ArcadeException{
		try {
			// TODO allow server/port as optional runtime arguments xor user inputs.
			client = new NetworkClient(serverIPAddress, 54555, 54777);
			addListeners();
		} catch (NetworkException e) {
			throw new ArcadeException("Unable to connect to Arcade Server (" + serverIPAddress + ")", e);
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

	/**
	 * Ask the server to play a given game.
	 * 
	 * @param gameClient the type of game to play
	 */
	public void requestGameSession(GameClient gameClient){
		NewGameRequest newGameRequest = new NewGameRequest();
		newGameRequest.gameId = gameClient.getGame().gameId;
		newGameRequest.username = player.getUsername();
		newGameRequest.requestType = GameRequestType.NEW;
		this.client.sendNetworkObject(newGameRequest);
	}
	
	/**
	 * Begin playing the game in the <tt>selectedGame</tt> field.
	 */
	public void startSelectedGame(){
		stopDashboard();
		startGame(selectedGame);
	}
	
	
	/**
	 * Starts the dashboard
	 */
	public void startDashboard(){
		mainUI = getInstanceOfGame("arcadeui");
		startGame(mainUI);
	}
	
	
	/**
	 * Stops the dashboard
	 */
	public void stopDashboard(){
		if (mainUI != null) {
			mainUI.gameOver(true);
		}
	}
	
	
	/**
	 * Start a GameClient
	 */
	public void startGame(final GameClient game){
		this.canvas = new LwjglCanvas(game, true);
		this.canvas.getCanvas().setSize(width, height);
		
		
		Object mon = new Object();
		synchronized (mon) {
			game.setArcadeThreadMonitor(mon);
			this.add(this.canvas.getCanvas());
			
			try {
				mon.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		game.addGameOverListener(new GameOverListener() {

			@Override
			public void notify(GameClient client) {
				canvas.stop();
				remove(canvas.getCanvas());
			}

			@Override
			public void notifySync(GameClient client) {
				canvas.stop();
				
				Object mon = new Object();
				synchronized (mon) {
					game.setArcadeThreadMonitor(mon);
					remove(canvas.getCanvas());
					
					try {
						mon.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
			}
			
		});
	}
	
	public static void main(String[] args) {
		ARCADE = new Arcade(args);

		//ARCADE.startDashboard();
		
		//Try to connect to the server until successful
		boolean connected = false;
		while (!connected){
			try {
				ARCADE.connectToServer();
				connected = true;
			} catch (ArcadeException e) {
				//Server unavailable - ask user whether or not to try again
				int userInput = JOptionPane.showConfirmDialog(ARCADE,
						e.getMessage() + "\nTry Again?", "Network Error",
						JOptionPane.YES_NO_OPTION);
				boolean keepTrying = (userInput == 0);
				if (!keepTrying) {
					//Exit the program
					System.exit(0);
				}
			}
		}

		//Get the username off the user and connect to the server with it
		String username = UserNameDialog.getUsername(ARCADE);
		ARCADE.connectAsUser(username);

	}

	private Map<String,Class<? extends GameClient>> gameMap = new HashMap<String,Class<? extends GameClient>>();

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

	/**
	 * Ask the user to select a game, then call <tt>requestGameSession</tt> on
	 * the selected game.
	 */
	public void selectGame() {
		while(selectedGame == null) {
			selectedGame = getUserGameSelection();
		}
		requestGameSession(selectedGame);
	}
	
	
	public GameClient getUserGameSelection() {
		Object[] gameList = findGameIds().toArray();
		String selectedGameId = (String) GameSelector.selectGame(this, gameList);
		return getInstanceOfGame(selectedGameId);
	}
	
	public GameClient getInstanceOfGame(String id) {
		Class<? extends GameClient> gameClass = getGameMap().get(id);
		try {
			if (gameClass != null) {
				Constructor<? extends GameClient> constructor = gameClass.getConstructor(Player.class, NetworkClient.class);
				GameClient game = null;
				
				//add the overlay to the game
				if (id != "arcadeui") {
					game = constructor.newInstance(player, client);
					game.addOverlay(getInstanceOfGame("arcadeui"));
				} else {
					//the overlay takes an extra param telling it that its the overlay
					constructor = gameClass.getConstructor(Player.class, NetworkClient.class, Boolean.class);
					game = constructor.newInstance(player, client, true);
				}
				
				return game;
			}
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
		return null;
	}
	
	
	
}
