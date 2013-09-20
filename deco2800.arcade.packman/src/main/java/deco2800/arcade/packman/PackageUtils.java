package deco2800.arcade.packman;

import java.io.File;

public class PackageUtils {
	
	
	/**
	 * Create a folder if it doesn't exist
	 * 
	 * @param dirName Name of the directory to be created
	 * @return true if the directory already existed or was created
	 * @return false if the directory could not be created
	 */
	public static Boolean createDirectory(String dirName) {
		
		File releaseDir = new File(dirName);
		
		// Create the Release directory if it doesn't exist
		if (!releaseDir.exists()) {
			
			if (releaseDir.mkdirs()) {
				return true;
			} else {
				return false;
			}
		}
		
		return true;
	}
}
