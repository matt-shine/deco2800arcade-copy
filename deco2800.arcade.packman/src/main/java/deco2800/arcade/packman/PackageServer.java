package deco2800.arcade.packman;

import java.io.File;
import java.lang.System;

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
     * Print confirmation of successful connection by client to package server
     */
    public void printSuccess() {
        System.out.println("PACKMAN: Client connection success");
    }
}
