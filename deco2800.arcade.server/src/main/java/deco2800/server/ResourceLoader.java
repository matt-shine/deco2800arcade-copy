package deco2800.arcade.server;

import java.util.regex.*;
import deco2800.arcade.server.ResourceHandler;
import java.io.File;

public class ResourceLoader {
	
	/**
	 * Traverses the server's resources folder and passes files with names
	 * matching the given pattern to the handler for loading. 
	 *
	 * @param p        The pattern to match filenames against.
	 * @param handler  The handler used for handling matching files.
	 * @param maxDepth The maximum depth allowed for folders, relative to
	 *                   the server's resources folder. 0 will look for
	 *                   files in the resources folder, 1 will look for
	 *                   files in the resources folder as well as in the
	 *                   first level of subdirectories, etc.
	 */
	public static void loadFilesMatchingPattern(Pattern p, 
			int maxDepth,
			ResourceHandler handler) {
		try {
			String sep = File.separator;
			ClassLoader cl = ResourceLoader.class.getClassLoader();
			File f = new File(cl.getResource("").toURI());
			File resDir = new File(f.getPath() + sep + ".." + sep + ".." + sep + "resources" + sep + "main");
			loadFilesMatchingPattern(resDir, p, maxDepth, handler);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void loadFilesMatchingPattern(File dir,
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
				loadFilesMatchingPattern(f, p, 
					maxDepth - 1, handler);
			}
		}
	}

}