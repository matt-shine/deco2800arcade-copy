package deco2800.server;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.net.BindException;

import com.esotericsoftware.kryonet.Server;

import deco2800.arcade.protocol.Protocol;
import deco2800.server.database.CreditStorage;
import deco2800.server.database.ImageStorage;
import deco2800.server.database.DatabaseException;
<<<<<<< HEAD
import deco2800.server.database.ReplayStorage;
import deco2800.server.listener.CommunicationListener;
import deco2800.server.listener.ReplayListener;
=======
import deco2800.server.database.ForumStorage;
>>>>>>> Create and set the ForumStorage
import deco2800.server.listener.ConnectionListener;
import deco2800.server.listener.CreditListener;
import deco2800.server.listener.GameListener;
import deco2800.server.listener.HighscoreListener;
import deco2800.server.database.HighscoreDatabase;
import deco2800.arcade.packman.PackageServer;
import deco2800.server.database.AchievementStorage;
import deco2800.server.listener.AchievementListener;

/** 
 * Implements the KryoNet server for arcade games which uses TCP and UDP
 * transport layer protocols. 
 * 
 * @see http://code.google.com/p/kryonet/ 
 */
public class ArcadeServer {

	// Keep track of which users are connected
	private Set<String> connectedUsers = new HashSet<String>();
	
	//Replay data
	private ReplayStorage replayStorage;
	
	//singleton pattern
	private static ArcadeServer instance;
	
	// Package manager
	@SuppressWarnings("unused")
	private PackageServer packServ;
	
	// Server will communicate over these ports
	private static final int TCP_PORT = 54555;
	private static final int UDP_PORT = 54777;
	
	/**
	 * Retrieve the singleton instance of the server
	 * @return the game server
	 */
	public static ArcadeServer instance() {
		if (instance == null) {
			instance = new ArcadeServer();
		}
		return instance;
	}
	
	/**
	 * Initializes and starts Server
	 * Binds Ports
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		ArcadeServer server = new ArcadeServer();
		server.start();
	}

	//Achievement storage service
	private AchievementStorage achievementStorage;
	
	// Credit storage service
	private CreditStorage creditStorage;
	//private PlayerStorage playerStorage;
	//private FriendStorage friendStorage;

    private ImageStorage imageStorage;
	
	// Highscore database storage service
	private HighscoreDatabase highscoreDatabase;
	
	/* Forum strage service */
	private ForumStorage forumStorage;
	
	/**
	 * Access the server's credit storage facility
	 * @return
	 */
	public CreditStorage getCreditStorage() {
		return this.creditStorage;
	}
	
	/**
	 * * Access the Serer's achievement storage facility
	 * @return AchievementStorage currently in use by the arcade
	 */
	public AchievementStorage getAchievementStorage() {
		return this.achievementStorage;
	}
	
	/**
	 * Access the replay records.
	 * @return
	 */
	public ReplayStorage getReplayStorage()
	{
	    return this.replayStorage;
	}
	
	/**
	 * Access the server's high score storage
	 */
	public HighscoreDatabase getHighscoreDatabase() {
		return this.highscoreDatabase;
	}
	
	/**
	 * Create a new Arcade Server.
	 * This should generally not be called.
	 * @see ArcadeServer.instance()
	 */
	public ArcadeServer() {
		this.creditStorage = new CreditStorage();
<<<<<<< HEAD
		this.replayStorage = new ReplayStorage();
=======
		this.forumStorage = new ForumStorage();
>>>>>>> Create and set the ForumStorage
		//this.playerStorage = new PlayerStorage();
		//this.friendStorage = new FriendStorage();
		
        this.imageStorage = new ImageStorage();

		//do achievement database initialisation
		this.achievementStorage = new AchievementStorage(imageStorage);
		this.highscoreDatabase = new HighscoreDatabase();
		
		this.packServ = new PackageServer();
		
		//initialize database classes
		try {
			creditStorage.initialise();
            imageStorage.initialise();
			//playerStorage.initialise();
<<<<<<< HEAD
            
			achievementStorage.initialise();
			
			highscoreDatabase.initialise();
=======
			this.forumStorage.initialise();
>>>>>>> Create and set the ForumStorage
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// once the db is fine, load in achievement data from disk
		this.achievementStorage.loadAchievementData();
	}
	
	/**
	 * Start the server running
	 */
	public void start() {
		Server server = new Server();
		System.out.println("Server starting");
		server.start();
		try {
			server.bind(TCP_PORT, UDP_PORT);
			System.out.println("Server bound");
		} catch (BindException b) {
			System.err.println("Error binding server: Address already in use");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Protocol.register(server.getKryo());
		server.addListener(new ConnectionListener(connectedUsers));
		server.addListener(new CreditListener());
		server.addListener(new GameListener());
		server.addListener(new AchievementListener());
		server.addListener(new ReplayListener());
		server.addListener(new HighscoreListener());
		server.addListener(new CommunicationListener(server));
		
	}
}
