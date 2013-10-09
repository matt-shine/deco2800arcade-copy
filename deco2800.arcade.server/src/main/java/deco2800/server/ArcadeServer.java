package deco2800.server;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.net.BindException;

import com.esotericsoftware.kryonet.Server;

import deco2800.arcade.protocol.Protocol;
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
	
	// Package manager
	@SuppressWarnings("unused")
	private PackageServer packServ;

    private GameStorage gameStorage;
	
	// Server will communicate over these ports
	private static final int TCP_PORT = 54555;
	private static final int UDP_PORT = 54777;
    private static final int FILE_TCP_PORT = 54666;

    private Server fileServer;
	
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
        server.startFileserver();
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
		
        this.imageStorage = new ImageStorage();

		//do achievement database initialisation
		this.achievementStorage = new AchievementStorage(imageStorage);
		this.highscoreDatabase = new HighscoreDatabase();
		
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
        server.addListener(new LibraryListener());
        server.addListener(new PlayerListener());
    }

    /**
     * Start the server running
     */
    public void startFileserver() {
        Server server = new Server();
        System.out.println("File Server starting");
        server.start();
        try {
            server.bind(FILE_TCP_PORT);
            System.out.println("File Server bound");
        } catch (BindException b) {
            System.err.println("Error binding file server: Address already in use");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Protocol.register(server.getKryo());
        //server.addListener(new ConnectionListener(connectedUsers));
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