package deco2800.arcade.packman;

import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.log4j.PatternLayout;
import org.apache.log4j.Logger;
import org.apache.log4j.Level;
import org.apache.log4j.ConsoleAppender;

import deco2800.arcade.packman.PackCompress;
import deco2800.arcade.packman.PackageUtils;


/**
 * The class for executing the "gradle pack" task to populate the Release folder
 * on the server.
 *
 */
public class Pack {
    private List<String> games = new ArrayList<String>();
    
    private static final String SP = File.separator;
    
    private static final String RELEASE_FOLDER = ".." + SP + 
    											"deco2800.arcade.server" +
    											SP + "Release";
    
    private static final String VERSION_0 = "0.0.0";
    
	private static Logger log;
    

    public Pack() {

    }

    /**
     * ENTRY POINT
     *
     * @param args
     */
    public static void main(String[] args) {
    	// Init custom logging

    	// Define layout
    	PatternLayout layout = new PatternLayout();
    	layout.setConversionPattern("%d{ISO8601} %c:%t %-5p  - %m%n");

    	// Create appender
    	ConsoleAppender appender = new ConsoleAppender();
    	appender.setLayout(layout);
    	appender.activateOptions();

    	// Get our logger and add appender.
    	log = Logger.getLogger("[Packman]");
    	log.setLevel(Level.INFO);
    	log.addAppender(appender);
    	
		// Create the release folder
		log.info("Creating directory: " + RELEASE_FOLDER);
		if (PackageUtils.createDirectory(RELEASE_FOLDER)) {
			log.info("Created: " + RELEASE_FOLDER);
		} else {
			log.info("Failed creating: " + RELEASE_FOLDER);
		}
    	
    	
        Pack pack = new Pack();

        pack.populateGames();

        pack.populateRelease();
    }

    /**
     * Populate the release folder based on VERSION and RELEASE_VERSION vars
     * in each game class.
     * Assumes games has already been populated.
     */
    public void populateRelease() {
        List<String> versions;
        String version;
        String releaseVersion;
        
        PackCompress packer = new PackCompress();
        
        for (String game : games) {

            versions = getVersions(game);

            if (versions.size() > 1) {
                version = versions.get(0);
                releaseVersion = versions.get(1);

                if (version.equals(VERSION_0) && releaseVersion.equals(VERSION_0)) {
                    log.error("No version specified: " + game);
                } else if (releaseVersion.compareTo(version) > 0) {
                    log.warn("RELEASE_VERSION > VERSION. Not releasing: " + game);
                } else if (version.compareTo(releaseVersion) > 0) {
                    log.warn("VERSION > RELEASE_VERSION. Not releasing: " + game);
                } else if (version.equals(releaseVersion)) {
                    log.info("Copying JAR to Releases: " + game);

                    File src = null;
                    File dest = null;
                    String srcPath = ".." + SP + "deco2800.arcade." + game + 
                    				 SP + "build" + SP + "libs" + SP + 
                    				 "deco2800.arcade." + game + "-" + 
                    				 version + ".jar";
                    
                    String destPath = RELEASE_FOLDER + SP + game + 
                    				  "-" + version + ".tar.gz";

                    src = new File(srcPath);
                    dest = new File(destPath);

                    if (src != null && dest != null) {
                        try {
                        	packer.compress(srcPath, destPath);
                        	//packer.expand(destPath, RELEASE_FOLDER + SP + 
                        	//				game + "-" + version + ".jar");
                        } catch (IOException e) {
                            log.error("[Packman] Failed to copy JAR to Release directory", e);
                        }
                    }
                }
            }
        }
    }

    private List<String> getVersions(String game) {
        List<String> versions = new ArrayList<String>(2);
        versions.add(VERSION_0); // Defaults
        versions.add(VERSION_0);
        String[] split;

        List<String> fileContents = readFile("../deco2800.arcade." + game + "/VERSION");

        for (String line : fileContents) {
            // Skip comments and newlines
            if (line.length() == 0 || line.charAt(0) == '#' || line.charAt(0) == ' ' ||
                    line.charAt(0) == '\n' || line.charAt(0) == '\r') {
                continue;
            }

            split = line.split("=");
            if (split.length > 1) {
                if (line.contains("RELEASE_VERSION=")) {
                    versions.set(1, split[1]);
                } else if (line.contains("VERSION=")) {
                    versions.set(0, split[1]);
                }
            }
        }

        return versions;
    }

    /**
     * Helper method to populate the games list
     */
    public void populateGames() {
        List<String> fileContents = readFile("games.txt");

        for (String game : fileContents) {
            if (game.charAt(0) != '#' && game.charAt(0) != '\n') {
                games.add(game);
            }
        }
    }

    private List<String> readFile(String filename) {
        BufferedReader reader = null;
        File file = new File(filename);
        String line;
        List<String> file_contents = new ArrayList<String>();

        try {
            reader = new BufferedReader(new FileReader(file));
            while ((line = reader.readLine()) != null) {
                file_contents.add(line);
            }
        } catch (FileNotFoundException e) {
            //log.error("File not found: " + filename);
        } catch (IOException e) {
            log.error("IO Error", e);
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
            }
        }

        return file_contents;
    }
}
