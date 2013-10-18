package deco2800.arcade.client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Insets;
import java.awt.event.WindowEvent;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JFrame;

import org.reflections.Reflections;

import com.badlogic.gdx.backends.lwjgl.LwjglCanvas;

import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.client.network.NetworkException;
import deco2800.arcade.client.network.listener.CommunicationListener;
import deco2800.arcade.client.network.listener.ConnectionListener;
import deco2800.arcade.client.network.listener.CreditListener;
import deco2800.arcade.client.network.listener.FileServerListener;
import deco2800.arcade.client.network.listener.GameListener;
import deco2800.arcade.client.network.listener.LibraryResponseListener;
import deco2800.arcade.client.network.listener.LobbyListener;
import deco2800.arcade.client.network.listener.MultiplayerListener;
import deco2800.arcade.client.network.listener.PackmanListener;
import deco2800.arcade.communication.CommunicationNetwork;
import deco2800.arcade.model.Game.ArcadeGame;
import deco2800.arcade.model.Game.InternalGame;
import deco2800.arcade.model.Player;
import deco2800.arcade.packman.PackageClient;
import deco2800.arcade.protocol.BlockingMessage;
import deco2800.arcade.protocol.communication.CommunicationRequest;
import deco2800.arcade.protocol.connect.ConnectionRequest;
import deco2800.arcade.protocol.credit.CreditBalanceRequest;
import deco2800.arcade.protocol.game.GameLibraryRequest;
import deco2800.arcade.protocol.game.GameRequestType;
import deco2800.arcade.protocol.game.NewGameRequest;
import deco2800.arcade.protocol.lobby.ActiveMatchDetails;
import deco2800.arcade.protocol.lobby.CreateMatchRequest;
import deco2800.arcade.protocol.lobby.LobbyRequestType;
import deco2800.arcade.protocol.lobby.NewLobbyRequest;
import deco2800.arcade.protocol.lobby.RemovedMatchDetails;
import deco2800.arcade.protocol.multiplayerGame.NewMultiGameRequest;
import deco2800.arcade.protocol.packman.GameUpdateCheckRequest;
import deco2800.arcade.protocol.packman.GameUpdateCheckResponse;

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

	private NetworkClient fileClient;

	private Player player;

	private int width, height;

	private String serverIPAddress = "127.0.0.1";

	private LwjglCanvas canvas;

	private GameClient selectedGame = null;

	private ProxyApplicationListener proxy;

	private CommunicationNetwork communicationNetwork;

	@SuppressWarnings("unused")
	private static PackageClient packClient;

	private static boolean multiplayerEnabled;

	private static boolean playerBetting;

	private static ArrayList<ActiveMatchDetails> matches = new ArrayList<ActiveMatchDetails>();

	// Width and height of the Arcade window
	private static final int ARCADE_WIDTH = 1280;
	private static final int ARCADE_HEIGHT = 720;
	private static final int MIN_ARCADE_WIDTH = 640;
	private static final int MIN_ARCADE_HEIGHT = 480;

	// Server will communicate over these ports
	private static final int TCP_PORT = 54555;
	private static final int UDP_PORT = 54777;
	private static final int FILE_TCP_PORT = 54666;

	/**
	 * ENTRY POINT
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Arcade arcade = new Arcade(args);

		ArcadeSystem.setArcadeInstance(arcade);

		arcade.addCanvas();

		System.out.println("Packman opened");
		packClient = new PackageClient();

		ArcadeSystem.goToGame(ArcadeSystem.UI);
	}

	/**
	 * Sets the instance variables for the arcade
	 * 
	 * @param args
	 */
	public Arcade(String[] args) {

		this.width = ARCADE_WIDTH;
		this.height = ARCADE_HEIGHT;
		initWindow();
	}

	/**
	 * Configure the window
	 */
	private void initWindow() {
		// create the main window
		this.setSize(new Dimension(width, height));
		this.setVisible(true);
		Insets insets = this.getInsets();
		this.setSize(new Dimension(width + insets.left + insets.right, height
				+ insets.bottom + insets.top));
		this.setMinimumSize(new Dimension(MIN_ARCADE_WIDTH, MIN_ARCADE_HEIGHT));
		this.getContentPane().setBackground(Color.black);

		// set shutdown behaviour
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(WindowEvent winEvt) {
				arcadeExit();
			}
		});
	}

	/**
	 * Completely exits arcade. The status code is always set to 0.
	 */
	public void arcadeExit() {
		removeCanvas();

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				System.exit(0);
			}
		});
	}

	/**
	 * Attempts to connect to the server
	 */
	public void startConnection() {
		// Try to connect to the server until successful
		boolean connected = false;
		while (!connected) {
			try {
				connectToServer();
				connected = true;
			} catch (ArcadeException e) {
				// TODO: error on connection failure
				System.out.println("Server Connection failed... Trying again.");
			}
		}

		connected = false;
		while (!connected) {
			try {
				connectToFileServer();
				connected = true;
			} catch (ArcadeException e) {
				// TODO: error on connection failure
				System.out
						.println("File Server connection failed... Trying again.");
			}
		}

	}

	/**
	 * Attempt to initiate a connection with the server.
	 * 
	 * @throws ArcadeException
	 *             if the connection failed.
	 */
	public void connectToServer() throws ArcadeException {
		try {
			// TODO allow server/port as optional runtime arguments or user
			// inputs.
			System.out.println("connecting to server");
			client = new NetworkClient(serverIPAddress, TCP_PORT, UDP_PORT);

			client.sendNetworkObject(new GameLibraryRequest());

			communicationNetwork = new CommunicationNetwork(player, this.client);
			addListeners();
		} catch (NetworkException e) {
			throw new ArcadeException("Unable to connect to Arcade Server ("
					+ serverIPAddress + ")", e);
		}
	}

	/**
	 * Attempt to initiate a connection with the file server.
	 * 
	 * @throws ArcadeException
	 *             if the connection failed.
	 */
	public void connectToFileServer() throws ArcadeException {
		try {
			// TODO allow server/port as optional runtime arguments xor user
			// inputs.
			System.out.println("connecting to file server");
			fileClient = new NetworkClient(serverIPAddress, FILE_TCP_PORT);
			addFileClientListeners();
		} catch (NetworkException e) {
			throw new ArcadeException(
					"Unable to connect to Arcade File Server ("
							+ serverIPAddress + ")", e);
		}
		fetchGameJar("pong", "1.0");
	}

	/**
	 * Add Listeners to the network client
	 */
	private void addListeners() {
		//this.client.addListener(new ConnectionListener());
		this.client.addListener(new CreditListener());
		this.client.addListener(new GameListener());
		this.client
				.addListener(new CommunicationListener(communicationNetwork));
		this.client.addListener(new PackmanListener());
		this.client.addListener(new MultiplayerListener(this));
		this.client.addListener(new LobbyListener());
		this.client.addListener(new LibraryResponseListener());
	}

	/**
	 * Add Listeners to the network client
	 */
	private void addFileClientListeners() {
		this.fileClient.addListener(new FileServerListener());
	}

	public void connectAsUser(String username) {
		// This should really GET the player with the details that were provided
		// at login, not create a new player!
		// For testing purposes, a specific ID number is given to debug users
		// and random users get a random ID
		int myID = 1 + (int) (Math.random() * 500);
		if (username.equals("debug")) {
			myID = 999;
		} else if (username.equals("debug1")) {
			myID = 888;
		} else if (username.equals("debug2")) {
			myID = 777;
		}

		System.out.println("My playerID is: " + myID);

		this.player = new Player(myID, username, "path/to/avatar");

		// This method has been removed from the deprecated Player(...); Waiting
		// for new Player(...) method to be created.
		// this.player.setUsername(username);

		this.communicationNetwork.loggedIn(this.player);

		ConnectionRequest connectionRequest = new ConnectionRequest();
		connectionRequest.playerID = myID;
		connectionRequest.username = username;
		// This breaks network communication at the moment!
		// Protocol.registerEncrypted(connectionRequest);
		this.client.sendNetworkObject(connectionRequest);

		CommunicationRequest communicationRequest = new CommunicationRequest();
		communicationRequest.playerID = myID;
		communicationRequest.username = username;
		this.client.sendNetworkObject(communicationRequest);

		CreditBalanceRequest creditBalanceRequest = new CreditBalanceRequest();
		creditBalanceRequest.playerID = myID;
		creditBalanceRequest.username = username;
		this.client.sendNetworkObject(creditBalanceRequest);

		/*
		 * As above, waiting for new Player(...) method //For testing chat: if
		 * (player.getID() == 888){ //This ID belongs to debug1 List<Integer>
		 * chat = new ArrayList<Integer>(); chat.add(999); //debug
		 * chat.add(888); //debug1 this.communicationNetwork.createChat(chat); }
		 * 
		 * if (player.getID() == 777){ //This ID belongs to debug2 List<Integer>
		 * chat = new ArrayList<Integer>(); chat.add(999); //debug
		 * chat.add(777); //debug2 this.communicationNetwork.createChat(chat); }
		 */

		// TODO FIX THIS!! - Causing Errors when logging in see
		// https://github.com/UQdeco2800/deco2800-2013/commit/78eb3e0ddb617b3dec3e74a55fab5b47d1b7abd0#commitcomment-4285661
		/*boolean[] privacy = { false, false, false, false, false, false, false,
				false };
		this.player = new Player(0, username, "", privacy);*/

		// This method has been removed from the deprecated Player(...); Waiting
		// for new Player(...) method to be created.
		// this.player.setUsername(username);

		// TODO move this call to be internal to Packman class
		// TODO iterate over actual game ids rather than just
		// using pong
		GameUpdateCheckRequest gameUpdateCheckRequest = new GameUpdateCheckRequest();
		gameUpdateCheckRequest.gameID = "pong";

		BlockingMessage r = BlockingMessage.request(client.kryoClient(),
				gameUpdateCheckRequest);

		GameUpdateCheckResponse resp = (GameUpdateCheckResponse) r;

		if (getCurrentGame() != null) {
			getCurrentGame().setPlayer(this.player);
			getCurrentGame().setThisNetworkClient(this.client);

		}

		// This is how you fetch game JARs
		// fetchGameJar("pong", "1.0");

		System.out.println("[CLIENT] GameUpdateCheckResponse received: "
				+ resp.md5);
	}

	/**
	 * Fetch the JAR for a given game/version from the server
	 * 
	 * A thread is spawned to fetch the game
	 * 
	 * @param gameID
	 * @param version
	 */
	public void fetchGameJar(String gameID, String version) {
		FileClient fc = new FileClient(gameID, version, fileClient);
		Thread t = new Thread(fc);
		t.start();
	}

	/**
	 * Ask the server to play a given game.
	 * 
	 * @param gameClient
	 *            the type of game to play
	 */
	public void requestGameSession(GameClient gameClient) {
		NewGameRequest newGameRequest = new NewGameRequest();
		newGameRequest.gameId = gameClient.getGame().id;
		newGameRequest.username = player.getUsername();
		newGameRequest.requestType = GameRequestType.NEW;
		this.client.sendNetworkObject(newGameRequest);
	}

	/**
	 * Begin playing the game in the <tt>selectedGame</tt> field.
	 */
	public void startGame(String gameid) {

		selectedGame = getInstanceOfGame(gameid);
		selectedGame.setNetworkClient(this.client);
		selectedGame.setPlayer(this.player);
		if (isMultiplayerEnabled()) {
			selectedGame.setMultiplayerOn();
		} else {
			selectedGame.setMultiplayerOff();
		}
		startGame(selectedGame);
	}

	/**
	 * Stop the current game
	 */
	public void stopGame() {

		if (selectedGame != null) {
			selectedGame.gameOver();
			proxy.dispose();
		}
		proxy.setTarget(new DummyApplicationListener());
	}

	public boolean isMultiplayerEnabled() {
		return multiplayerEnabled;
	}

	public void setMultiplayerEnabled(boolean multiplayerEnabled) {
		Arcade.multiplayerEnabled = multiplayerEnabled;
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
	public void addCanvas() {
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
	 * Removes the canvas from the frame. Should be called before shutdown.
	 * Never elsewhere, as there should only ever be one canvas.
	 */
	public void removeCanvas() {

		this.remove(this.canvas.getCanvas());

	}

	/**
	 * Start a GameClient
	 */
	public void startGame(final GameClient game) {
		proxy.setTarget(game);

		game.addGameOverListener(new GameOverListener() {

			@Override
			public void notify(GameClient client) {
			}

		});
	}

	private Map<String, Class<? extends GameClient>> gameMap = null;

	private Map<String, Class<? extends GameClient>> getGameMap() {

		if (gameMap != null) {
			return gameMap;
		}

		gameMap = new HashMap<String, Class<? extends GameClient>>();
		Reflections reflections = new Reflections("deco2800.arcade");
		Set<Class<?>> possibleGames = reflections
				.getTypesAnnotatedWith(ArcadeGame.class);
		for (Class<?> g : possibleGames) {
			if (GameClient.class.isAssignableFrom(g)) {
				Class<? extends GameClient> game = g
						.asSubclass(GameClient.class);
				ArcadeGame aGame = g.getAnnotation(ArcadeGame.class);
				String gameId = aGame.id();
				gameMap.put(gameId, game);
			}
		}
		return gameMap;
	}

	/**
	 * Returns all games except ones with the @InternalGame annotation
	 * 
	 * @return set of playable game ids
	 */
	public Set<String> findPlayableIds() {
		Map<String, Class<? extends GameClient>> games = new HashMap<String, Class<? extends GameClient>>(
				getGameMap());

		Iterator<Map.Entry<String, Class<? extends GameClient>>> it = games
				.entrySet().iterator();
		while (it.hasNext()) {

			Map.Entry<String, Class<? extends GameClient>> pair = it.next();

			if (pair.getValue().isAnnotationPresent(InternalGame.class)) {
				it.remove();
			}
		}

		return games.keySet();
	}

	/**
	 * Get a GameClient Instance of a game
	 * 
	 * @param id
	 *            Game id
	 * @return GameClient
	 */
	public GameClient getInstanceOfGame(String id) {
		Class<? extends GameClient> gameClass = getGameMap().get(id);
		try {
			if (gameClass != null) {
				Constructor<? extends GameClient> constructor = gameClass
						.getConstructor(Player.class, NetworkClient.class);
				GameClient game = constructor.newInstance(player, client);

				// add the overlay to the game
				if (!gameClass.isAnnotationPresent(InternalGame.class)) {
					GameClient overlay = getInstanceOfGame(ArcadeSystem.OVERLAY);

					// the overlay and the bridge are the same object, but
					// GameClient doesn't know that and it mightn't be that way
					// forever
					game.addOverlay(overlay);
					if (overlay instanceof UIOverlay) {
						game.addOverlayBridge((UIOverlay) overlay);
					}

				}

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

	/**
	 * Get the current game
	 * 
	 * @return selectedGame
	 */
	public GameClient getCurrentGame() {
		return selectedGame;
	}

	/**
	 * Adds a lobby match to the clients list of matches.
	 * 
	 * @param response
	 *            - The match to add.
	 */
	public static void addToMatchList(ActiveMatchDetails response) {
		matches.add(response);
	}

	/**
	 * Resets the client's list of lobby matches.
	 */
	public static void clearMatchList() {
		matches.removeAll(matches);
	}

	/**
	 * Removes a match from the client's list of lobby matches.
	 * 
	 * @param response
	 *            - The match to remove.
	 */
	public static void removeFromMatchList(RemovedMatchDetails response) {
		if (matches.contains(response)) {
			matches.remove(response);
		}
	}

	/**
	 * Returns an array of matches in the lobby
	 * 
	 * @return the list of matches
	 */
	public static ArrayList<ActiveMatchDetails> getMatches() {
		return matches;
	}

	public void createMultiplayerGame(NewMultiGameRequest request) {
		request.playerID = player.getID();
		this.client.sendNetworkObject(request);
	}

	/**
	 * Sends a CreateMatchRequest to the server.
	 * 
	 * @param request
	 */
	public void createMatch(CreateMatchRequest request) {
		this.client.sendNetworkObject(request);
	}

	/**
	 * Sends a populate request to the lobby, which responds by sending all
	 * matches currently active in the lobby.
	 */
	public void populateMatchList() {
		NewLobbyRequest request = new NewLobbyRequest();
		request.requestType = LobbyRequestType.POPULATE;
		this.client.sendNetworkObject(request);
	}

	/**
	 * Send a request to add this player to the list of players connected to the
	 * lobby.
	 */
	public void addPlayerToLobby() {
		NewLobbyRequest request = new NewLobbyRequest();
		request.playerID = player.getID();
		request.requestType = LobbyRequestType.JOINLOBBY;
		this.client.sendNetworkObject(request);
	}

	/**
	 * Send a request to remove this player from the list of players connected
	 * to the lobby.
	 */
	public void removePlayerFromLobby() {
		NewLobbyRequest request = new NewLobbyRequest();
		request.playerID = player.getID();
		request.requestType = LobbyRequestType.LEAVELOBBY;
		this.client.sendNetworkObject(request);
	}

	public void disposeGame() {
		selectedGame.dispose();
	}

	public boolean isPlayerBetting() {
		return playerBetting;
	}

	public void setPlayerBetting(boolean playerBetting) {
		Arcade.playerBetting = playerBetting;
	}

	/**
	 * Set selected game client
	 * 
	 * @param gameClient
	 *            GameClient
	 */
	public void setGame(GameClient gameClient) {
		selectedGame = gameClient;
	}

	/**
	 * Return all playable games
	 * 
	 * @return Set of Playable Games
	 */
	public Set<GameClient> findPlayableGames() {
		Map<String, Class<? extends GameClient>> games = getGameMap();

		Set<GameClient> gameSet = new HashSet<GameClient>();

		Iterator<Map.Entry<String, Class<? extends GameClient>>> it = games
				.entrySet().iterator();

		while (it.hasNext()) {
			Map.Entry<String, Class<? extends GameClient>> pair = it.next();
			if (pair.getValue().isAnnotationPresent(InternalGame.class)) {
				it.remove();
			} else {
				GameClient gameClient = getInstanceOfGame(pair.getKey());
				if (gameClient != null) {
					gameSet.add(gameClient);
				}

			}

		}
		return gameSet;
	}

	/**
	 * Send a request for a list of games to the server
	 */
	public void requestGames() {
		GameLibraryRequest gameLibraryRequest = new GameLibraryRequest();
		client.sendNetworkObject(gameLibraryRequest);
	}
}
