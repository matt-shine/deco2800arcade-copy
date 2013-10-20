package deco2800.server;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.net.BindException;

import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.esotericsoftware.kryonet.Server;

import deco2800.arcade.protocol.Protocol;
import deco2800.server.database.AchievementStorage;
import deco2800.server.database.ChatStorage;
import deco2800.server.database.CreditStorage;
import deco2800.server.database.ImageStorage;
import deco2800.server.database.DatabaseException;
import deco2800.server.database.ReplayStorage;
import deco2800.server.database.GamePath;
import deco2800.server.listener.CommunicationListener;
import deco2800.server.listener.LobbyListener;
import deco2800.server.listener.MultiplayerListener;
import deco2800.server.listener.ReplayListener;
import deco2800.server.listener.ConnectionListener;
import deco2800.server.listener.CreditListener;
import deco2800.server.listener.GameListener;
import deco2800.server.listener.PackmanListener;
import deco2800.server.listener.HighscoreListener;
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
	
	//Create a logger for ArcadeServer
	private static Logger logger = LoggerFactory.getLogger(ArcadeServer.class);

	// Keep track of which users are connected
	private Set<String> connectedUsers = new HashSet<String>();
	
	//Replay data
	private ReplayStorage replayStorage;
	
	//singleton pattern
	private static ArcadeServer instance;
	
	private MatchmakerQueue matchmakerQueue;
	
	// Package manager
	private PackageServer packServ;
	//table storying gameID, path and and md5hash of the path
	private GamePath gamePath;

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
	 * Access the server's credit storage facility
	 * @return
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
		//Configure logger to save to ServerLogs.log as per file
		PropertyConfigurator.configure("src/main/resources/log4j.properties");
		
        instance = this;

        this.gameStorage = new GameStorage();
        logger.debug("gameStorage added to ArcadeServer");
        
        try {
            this.creditStorage = new CreditStorage();
            logger.debug("creditStorage added to ArcadeServer");
        } catch (Exception e) {
            //Do nothing, yet ;P
        }
        
        this.gamePath = new GamePath();
        
        //CODE SMELL
		this.replayStorage = new ReplayStorage();
		logger.debug("replayStorage added to ArcadeServer");
		//this.playerStorage = new PlayerStorage();
		//this.friendStorage = new FriendStorage();
		this.chatStorage = new ChatStorage();
		logger.debug("chatStorage added to ArcadeServer");
		
        this.imageStorage = new ImageStorage();
        logger.debug("imageStorage added to ArcadeServer");

		//do achievement database initialisation
		this.achievementStorage = new AchievementStorage(imageStorage);
		logger.debug("achievementStorage added to ArcadeServer");
		this.highscoreDatabase = new HighscoreDatabase();
		logger.debug("highscoreDatabase added to ArcadeServer");
		this.matchmakerQueue = MatchmakerQueue.instance();
		logger.debug("matchmakerQueue added to ArcadeServer");
		this.packServ = new PackageServer();
		logger.debug("PackageServer added to ArcadeServer");
		
		logger.info("Added all databases to Server, about to initialise them");


		
		//Init highscore database
		try {
			highscoreDatabase.initialise();
			logger.debug("highscoreDatabase initialised");
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
		
		//init GamePath database
		try {
			gamePath.initialise();
			gamePath.addTheGames();
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
		
		//initialize database classes
		try {
            gameStorage.initialise();
            logger.debug("gameStorage initialised");
			creditStorage.initialise();
			logger.debug("creditStorage initialised");
            imageStorage.initialise();
            logger.debug("imageStorage initialised");
			//playerStorage.initialise();
            
			achievementStorage.initialise();
			logger.debug("achievementStorage initialised");
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// once the db is fine, load in achievement data from disk
		this.achievementStorage.loadAchievementData();
		logger.debug("Achievement Data loaded into database");
		
		logger.info("All databases added and initialised, ArcadeServer Started");
	}
	
	/**
	 * Start the server running
	 */
	public void start() {
		Server server = new Server(131072, 16384);
		logger.info("Server starting");
		server.start();
		try {
			server.bind(TCP_PORT, UDP_PORT);
			logger.info("Server bound, TCP_PORT: {}, UDP_PORT: {}", TCP_PORT, UDP_PORT);
		} catch (BindException b) {
			logger.error("Error binding server: Address already in use");
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
        server.addListener(new ImageListener());

    }

    /**
     * Start the server running
     */
    public void startFileserver() {
        Server server = new Server();
        logger.info("File Server starting");
        server.start();
        try {
            server.bind(FILE_TCP_PORT);
            logger.info("File Server bound, TCP_PORT: {}, UDP_PORT: {}", TCP_PORT, UDP_PORT);
        } catch (BindException b) {
            logger.error("Error binding file server: Address already in use");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Protocol.register(server.getKryo());
        server.addListener(new FileServerListener());
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
