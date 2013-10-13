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
