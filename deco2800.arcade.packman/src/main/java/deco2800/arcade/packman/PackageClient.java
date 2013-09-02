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
	
}
