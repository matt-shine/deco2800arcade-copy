package deco2800.arcade.packman;

import java.io.File;

public class PackageServer {
	
	
	/**
	 * Initialiser
	 * 
	 * Create the 'Release' directory if it does not exist
	 */
	public PackageServer() {
		File releaseDir = new File("Release");
		
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
	 * Test function
	 * Double a number 
	 *
	 * THIS IS #TEST CODE, AND CAN BE REMOVED
	 *
	 * @param num
	 * @return 2*num
	 */
	public int doubleNum(int num) {
		return 2*num;
	}
}
