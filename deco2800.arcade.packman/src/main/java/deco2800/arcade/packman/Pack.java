package deco2800.arcade.packman;

import java.lang.String;
import java.lang.System;
import java.util.ArrayList;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import deco2800.arcade.packman.PackCompress;
import deco2800.arcade.packman.PackageUtils;


/**
 * The class for executing the "gradle pack" task to populate the Release folder
 * on the server.
 *
 */
public class Pack {
	final static Logger logger = LoggerFactory.getLogger(Pack.class);
	
    private ArrayList<String> games = new ArrayList<String>();
    
    private static final String sp = File.separator;
    
    private static final String releaseFolder = ".." + sp + 
    											"deco2800.arcade.server" +
    											sp + "Release";
    
    
    
    public Pack(String[] args) {

    }

    /**
     * ENTRY POINT
     *
     * @param args
     */
    public static void main(String[] args) {
    	//Configure log4j
    	BasicConfigurator.configure();
		// Create the release folder
		logger.debug("Creating directory: {}", releaseFolder);
		if (PackageUtils.createDirectory(releaseFolder)) {
			logger.debug("Created: {}", releaseFolder);
		} else {
			logger.debug("Failed creating: {}", releaseFolder);
		}
    	
    	
        Pack pack = new Pack(args);

        pack.populateGames();

        pack.populateRelease();
    }

    /**
     * Populate the release folder based on VERSION and RELEASE_VERSION vars
     * in each game class.
     * Assumes games has already been populated.
     */
    public void populateRelease() {
        ArrayList<String> versions;
        String version;
        String releaseVersion;
        
        PackCompress packer = new PackCompress();
        
        for (String game : games) {

            versions = getVersions(game);

            if (versions.size() > 1) {
                version = versions.get(0);
                releaseVersion = versions.get(1);

                if (version.equals("0.0.0") && releaseVersion.equals("0.0.0")) {
                    logger.debug("No version specified: {}", game);
                } else if (releaseVersion.compareTo(version) > 0) {
                    logger.debug("RELEASE_VERSION > VERSION. Not releasing: {}", game);
                } else if (version.compareTo(releaseVersion) > 0) {
                    logger.debug("VERSION > RELEASE_VERSION. Not releasing: {}", game);
                } else if (version.equals(releaseVersion)) {
                    logger.debug("Copying JAR to Releases: {}", game);

                    File src = null;
                    File dest = null;
                    String srcPath = ".." + sp + "deco2800.arcade." + game + 
                    				 sp + "build" + sp + "libs" + sp + 
                    				 "deco2800.arcade." + game + "-" + 
                    				 version + ".jar";
                    
                    String destPath = releaseFolder + sp + game + 
                    				  "-" + version + ".tar.gz";

                    src = new File(srcPath);
                    dest = new File(destPath);

                    if (src != null && dest != null) {
                        try {
                            //copyFile(src, dest);
                        	packer.compress(srcPath, destPath);
                        	//packer.Expand(destPath, releaseFolder + sp + game + "-" + version + ".jar");
                        } catch (IOException e) {
                            logger.error("[Packman] Failed to copy JAR to Release directory");
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    private void copyFile(File src, File dest) throws IOException {
        InputStream in = new FileInputStream(src);
        OutputStream out = new FileOutputStream(dest);
        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        in.close();
        out.close();
    }

    private ArrayList<String> getVersions(String game) {
        ArrayList<String> versions = new ArrayList<String>(2);
        versions.add("0.0.0"); // Defaults
        versions.add("0.0.0");
        String[] split;

        ArrayList<String> fileContents = readFile("../deco2800.arcade." + game + "/VERSION");

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
        ArrayList<String> file_contents = readFile("games.txt");

        for (String game : file_contents) {
            if (game.charAt(0) != '#' && game.charAt(0) != '\n') {
                games.add(game);
            }
        }
    }

    private ArrayList<String> readFile(String filename) {
        BufferedReader reader = null;
        File file = new File(filename);
        String line;
        ArrayList<String> file_contents = new ArrayList<String>();

        try {
            reader = new BufferedReader(new FileReader(file));
            while ((line = reader.readLine()) != null) {
                file_contents.add(line);
            }
        } catch (FileNotFoundException e) {
            //System.err.println("File not found: " + filename);
        } catch (IOException e) {
            e.printStackTrace();
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
