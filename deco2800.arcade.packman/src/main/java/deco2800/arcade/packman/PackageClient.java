package deco2800.arcade.packman;

import java.io.File;
import java.lang.System;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import deco2800.arcade.packman.PackageUtils;

/**
 * Client for package manager
 */
public class PackageClient {
	final static Logger logger = LoggerFactory.getLogger(PackageClient.class);
	
	private static final String gameFolder = ".." + File.separator + "games";
	
	/**
	 * Initialiser
	 * 
	 * Create the 'Games' directory if it does not exist
	 */
	public PackageClient() {
		
		// Create the games folder
		logger.debug("Creating directory: {}", gameFolder);
		if (PackageUtils.createDirectory(gameFolder)) {
			logger.debug("Created: {}", gameFolder);
		} else {
			logger.debug("Failed creating: {}", gameFolder);
		}
	}
	
	/**
	 * Gets the games class name from its ID.
	 * 
	 * If the game exists on the clients file system, this method will retrieve
	 * the game's class name and return it. If the game does not exist on the 
	 * client's file system, the method will return null.
	 */
	public String getClassName(int gameID) {
		
		return null;
	}
	
	/**
	 * Download the game
	 * 
	 * This will delete the game if it exists on the client file system, and 
	 * will then download the game from the servers file system to the 'Games'
	 * directory. 
	 */
	public void getGame(int gameID) {
		
	}
}
