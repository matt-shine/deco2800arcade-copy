
package deco2800.server;

import java.util.regex.*;
import deco2800.server.ResourceHandler;
import java.io.File;
import java.net.URISyntaxException;

public class ResourceLoader {

    /**
     * Returns a file corresponding to the given path relative to the server's
     * resources directory. The path shouldn't start with a separator.
     *
     * @param path  The file's path relative to the resources directory
     * @return The file corresponding to the supplied path
     */
    public static File load(String path) {
        return new File(getResourcesDirectory().getPath() + File.separator + path);
    }

    /**
     * Returns the server's resources directory as a File
     * @return A File representing the server's resources directory
     */
    public static File getResourcesDirectory() {
        String sep = File.separator;
        ClassLoader cl = ResourceLoader.class.getClassLoader();
        try {
            File f = new File(cl.getResource("").toURI());
            return new File(f.getPath() + sep + ".." + sep + ".." + sep + "resources" + sep + "main");
        } catch(URISyntaxException e) {
            return null; // probably a bad idea
        }
    }
	
	/**
	 * Traverses the server's resources folder and passes files with names
	 * matching the given pattern to the handler for loading. 
	 *
	 * @param p        The pattern to match filenames against.
	 * @param handler  The handler used for matching files.
	 * @param maxDepth The maximum depth allowed for folders, relative to
	 *                   the server's resources folder. 0 will look for
	 *                   files in the resources folder, 1 will look for
	 *                   files in the resources folder as well as in the
	 *                   first level of subdirectories, etc.
	 */
	public static void handleFilesMatchingPattern(Pattern p, 
			int maxDepth,
			ResourceHandler handler) {
	   	handleFilesMatchingPattern(getResourcesDirectory(), p, maxDepth, handler);
	}

    /**
     * Recursive helper method used for the public handleFilesMatchingPattern
     */
	private static void handleFilesMatchingPattern(File dir,
			Pattern p,
			int maxDepth,
			ResourceHandler handler) {
		if (maxDepth < 0 || dir.listFiles() == null) {
			return;
		}

		for (File f : dir.listFiles()) {
			if (p.matcher(f.getName()).matches()) {
				handler.handleFile(f);
			}
			
			if (f.isDirectory()) {
				handleFilesMatchingPattern(f, p, 
					maxDepth - 1, handler);
			}
		}
	}

}
