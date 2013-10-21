package deco2800.arcade.packman;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class PackageUtils {
	
	private static final int CHUNK_SIZE = 1024;
	private static final int UPPER_BYTE = 0xF0;
	private static final int LOWER_BYTE = 0x0F;
	private static final int NO_DATA = -1;
	
	/**
	 * Do nothing. This should never be called.
	 */
	private PackageUtils() {
		
	}
	
	
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
		if (!releaseDir.exists() && releaseDir.mkdirs() == false) {
			return false;
		} 
		
		return true;
	}
	
	/** Look up table for hex characters */
	final static char[] hexArray = "0123456789ABCDEF".toCharArray();
	
	/**
	 * Convert a byte array to a string
	 * 
	 * @param bytes Array of hex bytes
	 * @return A string of bytes representing the hex data
	 */
	public static String bytesToHex(byte[] bytes) {
	    char[] hexChars = new char[bytes.length * 2];
	    int j=0;
	    
	    for ( final byte b : bytes ) {
	        hexChars[j * 2] = hexArray[(b & UPPER_BYTE) >> 4];
	        hexChars[j * 2 + 1] = hexArray[b & LOWER_BYTE];
	        j++;
	    }
	    return new String(hexChars);
	}
	
	/**
	 * Generate an MD5 hash for a file
	 * 
	 * @param fileName File to generate MD5 string for
	 * @return MD5 string representing hex byte array
	 * @return null if no hash can be generated
	 */
	public static String genMD5(String fileName) {
		MessageDigest md;
		InputStream fis;
		byte[] dataBytes = new byte[CHUNK_SIZE];
		
		try {
			md = MessageDigest.getInstance("MD5");
			fis = new FileInputStream(fileName);
		} catch (NoSuchAlgorithmException e1) {
			e1.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} 
		
		DigestInputStream dis = new DigestInputStream(fis, md);
		
		try {
			// Can't use a single line while loop due to Sonar
			while (true) {
				if (dis.read(dataBytes) == NO_DATA) {
					break;
				}
			}
			
			dis.close();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
		return bytesToHex(md.digest());
	}
}
