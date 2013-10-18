package deco2800.server;

import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
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

import deco2800.server.webserver.ArcadeWebserver;

/**
 * Implements the KryoNet server for arcade games which uses TCP and UDP
 * transport layer protocols.
 * 
 * @see http://code.google.com/p/kryonet/
 */
public class ArcadeServer {

	// Keep track of which users are connected
	// TODO We only need one of these (probably SessionManager), but I wasn't
	// 		sure which one to keep
	SessionManager sessionManager = new SessionManager();
	private Set<String> connectedUsers = new HashSet<String>();
	
	//Replay data
	private ReplayStorage replayStorage;
	
	//singleton pattern
	private static ArcadeServer instance;

	// Public and private key pair help handshake with clients
	private KeyPair keyPair;
	private String algorithm = "RSA";
	
	private MatchmakerQueue matchmakerQueue;
	
	// Package manager
	private PackageServer packServ;

    private GameStorage gameStorage;
	
	// Server will communicate over these ports
	private static final int TCP_PORT = 54555;
	private static final int UDP_PORT = 54777;
<<<<<<< HEAD

=======
    private static final int FILE_TCP_PORT = 54666;

    private Server fileServer;
	
>>>>>>> master
	/**
	 * Retrieve the singleton instance of the server
	 * 
	 * @return game server instance
	 */
	public static ArcadeServer instance() {
		if (instance == null) {
			instance = new ArcadeServer();
		}
		return instance;
	}

	/**
	 * Initializes and starts Server
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		ArcadeServer server = new ArcadeServer();
		server.start();
    server.startFileserver();
		ArcadeWebserver.startServer( );
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
	 * @return creditStorage service
	 */
	public CreditStorage getCreditStorage() {
		return this.creditStorage;
	}

	/**
	 * * Access the server's achievement storage facility
	 * @return AchievementStorage currently in use by the arcade
	 */
	public AchievementStorage getAchievementStorage() {
		return this.achievementStorage;
	}
    
    /**
     * Accessor for the server's image storage.
     * @return ImageStorage currently in use by the arcade
     */
    public ImageStorage getImageStorage() {
	return this.imageStorage;
    }
	
	/**
	 * Access the replay records.
	 * @return replayStorate service
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
	 * Binds TCP/UDP ports to the server instance, registers classes and adds
	 * listeners
	 */
	public void start() {
		Server server = new Server(131072, 16384);
		System.out.println("Server starting");
		server.start();
		try {
			server.bind(TCP_PORT, UDP_PORT);
			System.out.println("Server bound");
		} catch (BindException b) {
			System.err.println("Error binding server: Address already in use");
		} catch (IOException e) {
			e.printStackTrace();
		}
<<<<<<< HEAD

		generateKeyPair();

		Protocol.register(server.getKryo());

		// Connection listener manages the handshake process and associates
		// clients with a shared secret key.
		server.addListener(new ConnectionListener(sessionManager, keyPair));

		// FIXME these need to be behind a proxy that unseals messages
		server.addListener(new CreditListener());
		server.addListener(new GameListener());
		server.addListener(new AchievementListener());
		server.addListener(new ReplayListener());
		server.addListener(new HighscoreListener());
		server.addListener(new CommunicationListener(server));

=======
		
        Protocol.register(server.getKryo());
        server.addListener(new ConnectionListener(connectedUsers));
        server.addListener(new CreditListener());
        server.addListener(new GameListener());
        server.addListener(new AchievementListener());
        server.addListener(new ReplayListener());
        server.addListener(new HighscoreListener());
        server.addListener(new CommunicationListener(server));
>>>>>>> master
        server.addListener(new PackmanListener());
        server.addListener(new MultiplayerListener(matchmakerQueue));
        server.addListener(new LobbyListener());
        server.addListener(new LibraryListener());
        server.addListener(new PlayerListener());
        server.addListener(new ImageListener());

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
        server.addListener(new FileServerListener());
    }

	/**
	 * Generate the server public and private key that is used to handshake with
	 * the client.
	 */
	private void generateKeyPair() {
		KeyPairGenerator kpg = null;
		try {
			kpg = KeyPairGenerator.getInstance(algorithm);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		if (kpg != null) {
			kpg.initialize(2048);
			KeyPair keyPair = kpg.generateKeyPair();

			this.keyPair = keyPair;
		} else {
			// Something went wrong
			this.keyPair = null;
		}
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
