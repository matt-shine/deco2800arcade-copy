package deco2800.arcade.packman;

import java.lang.System;
import deco2800.arcade.packman.PackageUtils;

public class PackageServer {
	
	private static final String releaseFolder = "Release";
	
	/**
	 * Initialiser
	 * 
	 * Create the 'Release' directory if it does not exist
	 */
	public PackageServer() {
		
		// Create the release folder
		System.out.println("Creating directory: " + releaseFolder);
		if (PackageUtils.createDirectory(releaseFolder)) {
			System.out.println("Created: " + releaseFolder);
		} else {
			System.out.println("Failed creating: " + releaseFolder);
		}
	}

    /**
     * Print confirmation of successful connection by client to package server
     */
    public void printSuccess() {
        System.out.println("PACKMAN: Client connection success");
    }
}
