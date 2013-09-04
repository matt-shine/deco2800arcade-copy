package deco2800.arcade.server;

import java.io.File;

public interface ResourceHandler {
	
	/**
	 * Called when a file needs to be handled.
	 *
	 * @param file  The file to handle
	 */
	public void handleFile(File file);

}