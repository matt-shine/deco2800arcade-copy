package deco2800.arcade.packman;

import deco2800.arcade.packman.PackageUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PackageServer {
	final static Logger LOGGER = LoggerFactory.getLogger(PackageServer.class);
	
	private static final String RELEASE_FOLDER = "Release";
	
	/**
	 * Initialiser
	 * 
	 * Create the 'Release' directory if it does not exist
	 */
	public PackageServer() {
		
		// Create the release folder
		LOGGER.debug("Creating directory: {}", RELEASE_FOLDER);
		if (PackageUtils.createDirectory(RELEASE_FOLDER)) {
			LOGGER.debug("Created: {}", RELEASE_FOLDER);
		} else {
			LOGGER.debug("Failed creating: {}", RELEASE_FOLDER);
		}
	}

    /**
     * Print confirmation of successful connection by client to package server
     */
    public void printSuccess() {
        LOGGER.info("PACKMAN: Client connection success");
    }

    /**
     * Prototype method for calling genMD5 method on a file
     * Nathan: Change this so that it calls your MD5 method
     *
     * It should throw a DB exception so that it can be caught in the
     * request listener and handled there (with a different
     * response so the client knows the request failed)
     *
     * Can't currently throw a DatabaseException because that class
     * is contained within ArcadeServer, which this class depends on
     * creating a cyclic dependency.
     * TODO possible abstract DatabaseException out of ArcadeServer so
     * we can include it?
     *
     * Until then, this method should just return null if there was an
     * error of any kind.
     */
    public String getMD5ForGame(String gameId) {
        return "TODO";
    }
}
