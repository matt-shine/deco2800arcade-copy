package deco2800.arcade.packman;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import deco2800.arcade.packman.PackageUtils;

/**
 * Client for package manager
 */
public class PackageClient {
	final static Logger LOGGER = LoggerFactory.getLogger(PackageClient.class);
	
	private static final String SP = File.separator;
	private static final String GAME_FOLDER = ".." + SP + "games";
	private static final Class<?>[] PARAMS = new Class[] {URL.class};

	/**
	 * Initialiser
	 * 
	 * Create the 'Games' directory if it does not exist
	 */
	public PackageClient() {
		
		// Create the games folder
		LOGGER.debug("Creating directory: {}", GAME_FOLDER);
		if (PackageUtils.createDirectory(GAME_FOLDER)) {
			LOGGER.debug("Created: {}", GAME_FOLDER);
		} else {
			LOGGER.debug("Failed creating: {}", GAME_FOLDER);
		}
			
		// Get a list of files in the game folder
		String fileName;
		File folder = new File(GAME_FOLDER);
		File[] listOfFiles = folder.listFiles();		
		
		// For each file, if it is a jar file add it to the classpath
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				fileName = listOfFiles[i].getName();
				if (fileName.toLowerCase().endsWith(".jar")) {
					addJar(GAME_FOLDER + SP + fileName);
				}
			}
		}
		
	}
	
	/**
	 * Add a jar to the classpath when given the path to the jar.
	 * 
	 * @param jarPath
	 * @return true for success, false for failed
	 */
	public static Boolean addJar(String jarPath) {
		
		URL jarURL;
		
		// Get the URL for the file
		try {
			jarURL = new File(jarPath).toURI().toURL();
		} catch (MalformedURLException e) {
			LOGGER.error(e.toString());
			return false;
		}
		
		// Get the system classloader
		URLClassLoader sysloader = (URLClassLoader) ClassLoader.getSystemClassLoader();
		Class<URLClassLoader> sysclass = URLClassLoader.class;
		
		// Try and add the file URL to the classloader
		try {
                    Method method = sysclass.getDeclaredMethod("addURL", PARAMS);
                    method.setAccessible(true);
                    method.invoke(sysloader, new Object[] {jarURL});
		} catch (NoSuchMethodException e) {
		    LOGGER.error(e.toString());
		    return false;
		} catch (InvocationTargetException e) {
	            LOGGER.error(e.toString());
	            return false; 
		} catch (IllegalAccessException e) {
	            LOGGER.error(e.toString());
	            return false;
		}
		
		return true;
	}
	
	/**
	 * Gets the games class name from its ID.
	 * 
	 * If the game exists on the clients file system, this method will retrieve
	 * the game's class name and return it. If the game does not exist on the 
	 * client's file system, the method will return null.
	 */
	public static String getClassName(int gameID) {
		
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
