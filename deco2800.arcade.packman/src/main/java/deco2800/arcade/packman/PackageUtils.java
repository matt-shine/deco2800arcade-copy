package deco2800.arcade.packman;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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
	
	/** Look up table for hex characters */
	final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();
	
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
	        hexChars[j * 2] = hexArray[(b & 0xF0) >> 4];
	        hexChars[j * 2 + 1] = hexArray[b & 0x0F];
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
		byte[] dataBytes = new byte[1024];
		
		try {
			md = MessageDigest.getInstance("MD5");
			fis = Files.newInputStream(Paths.get(fileName));
		} catch (NoSuchAlgorithmException e1) {
			e1.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} 
		
		DigestInputStream dis = new DigestInputStream(fis, md);
		
		try {
			while (dis.read(dataBytes) != -1);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
		return bytesToHex(md.digest());
	}
}
