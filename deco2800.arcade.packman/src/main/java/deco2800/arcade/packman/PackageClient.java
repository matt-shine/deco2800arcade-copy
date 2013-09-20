package deco2800.arcade.packman;

import java.io.File;

import deco2800.arcade.packman.PackageUtils;

/**
 * Client for package manager
 */
public class PackageClient {
	
	private static final String gameFolder = ".." + File.separator + "games";
	
	/**
	 * Initialiser
	 * 
	 * Create the 'Games' directory if it does not exist
	 */
	public PackageClient() {
		
		// Create the games folder
		System.out.println("Creating directory: " + gameFolder);
		if (PackageUtils.createDirectory(gameFolder)) {
			System.out.println("Created: " + gameFolder);
		} else {
			System.out.println("Failed creating: " + gameFolder);
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
