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

import org.reflections.Reflections;

import com.badlogic.gdx.backends.lwjgl.LwjglCanvas;

import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.client.network.NetworkException;
import deco2800.arcade.client.network.listener.AchievementListener;
import deco2800.arcade.client.network.listener.CommunicationListener;
import deco2800.arcade.client.network.listener.ConnectionListener;
import deco2800.arcade.client.network.listener.CreditListener;
import deco2800.arcade.client.network.listener.GameListener;
import deco2800.arcade.communication.CommunicationNetwork;
import deco2800.arcade.model.Game.ArcadeGame;
import deco2800.arcade.model.Player;
import deco2800.arcade.protocol.communication.CommunicationRequest;
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

	private NetworkClient client;

	private Player player;

	private int width, height;

	private String serverIPAddress = "127.0.0.1";

	private LwjglCanvas canvas;

	private GameClient selectedGame = null;

	private ProxyApplicationListener proxy;

	private CommunicationNetwork communicationNetwork;


	/**
	 * ENTRY POINT
	 * @param args
	 */
	public static void main(String[] args) {
		Arcade arcade = new Arcade(args);

		arcade.addCanvas();

		arcade.startGame("arcadeui");

	}

	/**
	 * Sets the instance variables for the arcade
	 * @param args
	 */
	private Arcade(String[] args){
		this.width = 640;
		this.height = 480;

		ArcadeSystem.setArcadeInstance(this);

		initWindow();
	}

	/**
	 * Configure the window
	 */
	private void initWindow() {
		//create the main window
		this.setSize(new Dimension(width, height));
		this.setVisible(true);
		Insets insets = this.getInsets();
		this.setSize(new Dimension(width + insets.left + insets.right, height + insets.bottom + insets.top));
		this.getContentPane().setBackground(Color.black);

		//set shutdown behaviour
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


	public void startConnection() {
		//Try to connect to the server until successful
		boolean connected = false;
		while (!connected){
			try {
				connectToServer();
				connected = true;
			} catch (ArcadeException e) {
				//TODO: error on connection failure
				System.out.println("Connection failed... Trying again.");
			}
		}

	}


	/**
	 * Attempt to initiate a connection with the server.
	 *
	 * @throws ArcadeException if the connection failed.
	 */
	public void connectToServer() throws ArcadeException{
		try {
			// TODO allow server/port as optional runtime arguments xor user inputs.
			client = new NetworkClient(serverIPAddress, 54555, 54777);
			communicationNetwork = new CommunicationNetwork(player, this.client);
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
		this.client.addListener(new CommunicationListener(communicationNetwork));
	}


	public void connectAsUser(String username){
		ConnectionRequest connectionRequest = new ConnectionRequest();
		connectionRequest.username = username;

		this.client.sendNetworkObject(connectionRequest);

		CommunicationRequest communicationRequest = new CommunicationRequest();
		communicationRequest.username = username;
		
		this.client.sendNetworkObject(communicationRequest);

		CreditBalanceRequest creditBalanceRequest = new CreditBalanceRequest();
		creditBalanceRequest.username = username;

		this.client.sendNetworkObject(creditBalanceRequest);

		this.player = new Player();
		this.player.setUsername(username);

		this.communicationNetwork.createInterface();
	}

	/**
	 * Ask the server to play a given game.
	 *
	 * @param gameClient the type of game to play
	 */
	public void requestGameSession(GameClient gameClient){
		NewGameRequest newGameRequest = new NewGameRequest();
		newGameRequest.gameId = gameClient.getGame().id;
		newGameRequest.username = player.getUsername();
		newGameRequest.requestType = GameRequestType.NEW;
		this.client.sendNetworkObject(newGameRequest);
	}

	/**
	 * Begin playing the game in the <tt>selectedGame</tt> field.
	 */
	public void startGame(String gameid){
		selectedGame = getInstanceOfGame(gameid);
		startGame(selectedGame);
	}


	/**
	 * Stop the current game
	 */
	public void stopGame(){
		if (selectedGame != null) {
			selectedGame.gameOver();
		}
		proxy.setTarget(new DummyApplicationListener());
	}

	/**
	 * returns true if the player exists
	 */
	public boolean hasPlayer() {
		return (this.player != null);
	}

	/**
	 * Start a GameClient
	 */
	public void addCanvas(){
		this.proxy = new ProxyApplicationListener();
		this.canvas = new LwjglCanvas(proxy, true);
		this.canvas.getCanvas().setSize(width, height);

		proxy.setTarget(new DummyApplicationListener());


		Object mon = new Object();
		synchronized (mon) {
			proxy.setThreadMonitor(mon);
			this.add(this.canvas.getCanvas());

			try {
				mon.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}



	/**
	 * Start a GameClient
	 */
	public void startGame(final GameClient game){
		proxy.setTarget(game);

		game.addGameOverListener(new GameOverListener() {

			@Override
			public void notify(GameClient client) {
			}

		});
	}

	private Map<String,Class<? extends GameClient>> getGameMap() {

		Map<String,Class<? extends GameClient>> gameMap = new HashMap<String,Class<? extends GameClient>>();

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
	}

	public Set<String> findGameIds() {
		return getGameMap().keySet();
	}

	public GameClient getInstanceOfGame(String id) {
		return getInstanceOfGame(id, false);
	}

	public GameClient getInstanceOfGame(String id, boolean asOverlay) {
		Class<? extends GameClient> gameClass = getGameMap().get(id);
		try {
			if (gameClass != null) {
				Constructor<? extends GameClient> constructor = gameClass.getConstructor(Player.class, NetworkClient.class);
				GameClient game = null;

				//add the overlay to the game
				if (id != "arcadeui") {
					game = constructor.newInstance(player, client);
					game.addOverlay(getInstanceOfGame("arcadeui", true));
				} else {
					//the overlay takes an extra param telling it that its the overlay
					constructor = gameClass.getConstructor(Player.class, NetworkClient.class, Boolean.class);
					game = constructor.newInstance(player, client, asOverlay);
				}

				System.err.println("a new " + id + " returned");

				return game;
			}
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}


}
