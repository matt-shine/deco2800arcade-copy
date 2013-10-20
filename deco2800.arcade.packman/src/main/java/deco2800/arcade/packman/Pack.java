package deco2800.arcade.packman;

import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;

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
    final static Logger LOGGER = LoggerFactory.getLogger(Pack.class);
	
    private List<String> games = new ArrayList<String>();
    
    private static final String SP = File.separator;
    
    private static final String RELEASE_FOLDER = ".." + SP + 
                                "deco2800.arcade.server" +
                                SP + "Release";
    
    private static final String NO_VER = "0.0.0";
    
//    private static final int CHUNK = 1024;
    
    public Pack() {

    }

    /**
     * ENTRY POINT
     *
     */
    public static void main(String[] args) {
    	//Configure log4j
    	BasicConfigurator.configure();
		// Create the release folder
		LOGGER.debug("Creating directory: {}", RELEASE_FOLDER);
		if (PackageUtils.createDirectory(RELEASE_FOLDER)) {
			LOGGER.debug("Created: {}", RELEASE_FOLDER);
		} else {
			LOGGER.debug("Failed creating: {}", RELEASE_FOLDER);
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
        
        // For each game in the game list
        for (String game : games) {

            versions = getVersions(game);

            if (versions.size() <= 1) {
                continue;
            }
            
            version = versions.get(0);
            releaseVersion = versions.get(1);

            if (version.equals(NO_VER) && releaseVersion.equals(NO_VER)) {
                LOGGER.debug("No version specified: {}", game);
            } else if (releaseVersion.compareTo(version) > 0) {
                LOGGER.debug("RELEASE_VERSION > VERSION. Not releasing: {}", game);
            } else if (version.compareTo(releaseVersion) > 0) {
                LOGGER.debug("VERSION > RELEASE_VERSION. Not releasing: {}", game);
            } else if (version.equals(releaseVersion)) {
                LOGGER.debug("Copying JAR to Releases: {}", game);

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
                        //copyFile(src, dest);
                    	packer.compress(srcPath, destPath);
                    	//packer.Expand(destPath, releaseFolder + sp + game + "-" + version + ".jar");
                    } catch (IOException e) {
                        LOGGER.error("[Packman] Failed to copy JAR to Release directory");
                        LOGGER.error(e.toString());
                    }
                }
            }
        }
    }

//    private void copyFile(File src, File dest) throws IOException {
//        InputStream in = new FileInputStream(src);
//        OutputStream out = new FileOutputStream(dest);
//        byte[] buf = new byte[CHUNK];
//        int len;
//        while ((len = in.read(buf)) > 0) {
//            out.write(buf, 0, len);
//        }
//        in.close();
//        out.close();
//    }

    private List<String> getVersions(String game) {
        List<String> versions = new ArrayList<String>(2);
        versions.add(NO_VER); // Defaults
        versions.add(NO_VER);
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
            if (game.length() > 0 && game.charAt(0) != '#' && game.charAt(0) != '\n') {
                games.add(game);
            }
        }
    }

    private List<String> readFile(String filename) {
        BufferedReader reader = null;
        File file = new File(filename);
        String line;
        List<String> fileContents = new ArrayList<String>();

        try {
            reader = new BufferedReader(new FileReader(file));
            while ((line = reader.readLine()) != null) {
                fileContents.add(line);
            }
        } catch (FileNotFoundException e) {
            LOGGER.error("File not found: " + filename);
            LOGGER.error(e.toString());
        } catch (IOException e) {
            LOGGER.error(e.toString());
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                LOGGER.error(e.toString());
            }
        }

        return fileContents;
    }
}
