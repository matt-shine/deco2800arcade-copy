package deco2800.server;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.net.BindException;

import com.esotericsoftware.kryonet.Server;

import deco2800.arcade.protocol.Protocol;
import deco2800.server.database.ChatStorage;
import deco2800.server.database.CreditStorage;
import deco2800.server.database.ImageStorage;
import deco2800.server.database.DatabaseException;
import deco2800.server.database.ReplayStorage;
import deco2800.server.listener.CommunicationListener;
import deco2800.server.listener.LobbyListener;
import deco2800.server.listener.MultiplayerListener;
import deco2800.server.listener.ReplayListener;
import deco2800.server.listener.ConnectionListener;
import deco2800.server.listener.CreditListener;
import deco2800.server.listener.GameListener;
import deco2800.server.listener.PackmanListener;
import deco2800.server.listener.HighscoreListener;
import deco2800.server.database.HighscoreDatabase;
import deco2800.server.database.*;
import deco2800.server.listener.*;
import deco2800.arcade.packman.PackageServer;

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
	
	private MatchmakerQueue matchmakerQueue;
	
	// Package manager
	private PackageServer packServ;

    private GameStorage gameStorage;
	
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
	
	//Chat History storage
	private ChatStorage chatStorage;
	
	// Credit storage service
	private CreditStorage creditStorage;
	//private PlayerStorage playerStorage;
	//private FriendStorage friendStorage;

    private ImageStorage imageStorage;
	
	// Highscore database storage service
	private HighscoreDatabase highscoreDatabase;
	
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
     * Access the server's game storage
     * @return gameStorage
     */
    public GameStorage getGameStorageDatabase() {
        return gameStorage;
    }
	
	/**
	 * Access the server's chat history storage
	 * @return
	 */
	public ChatStorage getChatStorage(){
		return this.chatStorage;
	}
	
	/**
	 * Create a new Arcade Server.
	 * This should generally not be called.
	 * @see ArcadeServer.instance()
	 */
	public ArcadeServer() {

        instance = this;

        this.gameStorage = new GameStorage();
        try {
            this.creditStorage = new CreditStorage();
        } catch (Exception e) {
            //Do nothing, yet ;P
        }
        
        
        
        //CODE SMELL
		this.replayStorage = new ReplayStorage();
		//this.playerStorage = new PlayerStorage();
		//this.friendStorage = new FriendStorage();
		this.chatStorage = new ChatStorage();
		
        this.imageStorage = new ImageStorage();

		//do achievement database initialisation
		this.achievementStorage = new AchievementStorage(imageStorage);
		this.highscoreDatabase = new HighscoreDatabase();
		this.matchmakerQueue = MatchmakerQueue.instance();
		this.packServ = new PackageServer();


		
		//Init highscore database
		try {
			highscoreDatabase.initialise();
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
		
		//initialize database classes
		try {
            gameStorage.initialise();
			creditStorage.initialise();
            imageStorage.initialise();
			//playerStorage.initialise();
            
			achievementStorage.initialise();
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
        server.addListener(new PackmanListener());
        server.addListener(new MultiplayerListener(matchmakerQueue));
        server.addListener(new LobbyListener());
        server.addListener(new LibraryListener());
        server.addListener(new PlayerListener());
	}

    /**
     * Return the packServ object.
     * Possible temporary fix just to get network communication
     * between client and packman server operational.
     * TODO don't reveal packServ object publically
     * @return the packServ variable
     */
    public PackageServer packServ() {
        return packServ;
    }


}

