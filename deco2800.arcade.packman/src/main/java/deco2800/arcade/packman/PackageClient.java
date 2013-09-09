package deco2800.arcade.packman;

import java.io.File;

/**
 * Client for package manager
 */
public class PackageClient {
	
	
	/**
	 * Initialiser
	 * 
	 * Create the 'Games' directory if it does not exist
	 */
	public PackageClient() {
		File releaseDir = new File("Games");
		
		// Create the Release directory if it doesn't exist
		if (!releaseDir.exists()) {
			System.out.println("Creating directory: " + releaseDir);
			
			if (releaseDir.mkdirs()) {
				System.out.println("Created: " + releaseDir);
			} else {
				System.out.println("Failed creating: " + releaseDir);
			}
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
