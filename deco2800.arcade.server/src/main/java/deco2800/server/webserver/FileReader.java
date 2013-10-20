package deco2800.server.webserver;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

public final class FileReader {
	
	/**
	 * Ensures that the class can never be instantiated
	 */
	private FileReader() {
		
	}
	
	/**
	 * Reads the contents of a file and outputs the data in the specified encoding
	 * 
	 * @param path, the local path of the file to read as a string
	 * @param encoding, the Charset to use, typically Charset.forName("utf-8")
	 * @return the contents of the file as a string
	 * @throws IOException if the file could not be opened
	 */
	public static String readFile(String path, Charset encoding) 
			throws IOException {
		// Open the file and get its data channel
	    RandomAccessFile raf = new RandomAccessFile(path, "r");
	    FileChannel inChannel = raf.getChannel();
	    
	    // Read the contents into a byte array
		ByteBuffer encoded = ByteBuffer.allocate( (int) inChannel.size() );
		inChannel.read(encoded);
		byte[] bytes = encoded.array();
		
		inChannel.close();
		raf.close();
		
		// Return the byte array as a string encoded as specified
		return new String(bytes, encoding);
	}
	
	/**
	 * Read the contents of a file as a UTF-8 String
	 * 
	 * @param path, the local path to the file to read
	 * @return the file contents as a UTF-8 string
	 * @throws IOException if the file could not be opened
	 */
	public static String readFileUtf8(String path) throws IOException
	{
	    return FileReader.readFile( path, Charset.forName("UTF-8" ) );
	}
	
	/**
	 * Read the contents of a binary file
	 * 
	 * @param path, the local path to the file to read
	 * @return a ByteBuffer containing the encoded data read from the file
	 * @throws IOException if the file could not be opened
	 */
	public static ByteBuffer readBinaryFile(String path) throws IOException {
		// Open the file and get its data channel
	    RandomAccessFile raf = new RandomAccessFile(path, "r");
        FileChannel inChannel = raf.getChannel();
        
        // Read the contents into the buffer to be returned
        ByteBuffer encoded = ByteBuffer.allocate((int) inChannel.size());
        inChannel.read(encoded);
        
        inChannel.close();
        raf.close();

        return encoded;
	}
}
