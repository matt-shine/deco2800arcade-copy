package deco2800.server.webserver;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

public class FileReader {
	static String readFile(String path, Charset encoding) 
			throws IOException 
			{
	    RandomAccessFile raf = new RandomAccessFile(path, "r");
	    FileChannel inChannel = raf.getChannel();
	    
		ByteBuffer encoded = ByteBuffer.allocate((int) inChannel.size());
		inChannel.read(encoded);
		
		byte[] bytes = encoded.array();
		
		inChannel.close();
		raf.close();
		
		return new String(bytes, encoding);
			}
	
	static ByteBuffer readBinaryFile(String path) throws IOException
	{
	    RandomAccessFile raf = new RandomAccessFile(path, "r");
        FileChannel inChannel = raf.getChannel();
        
        ByteBuffer encoded = ByteBuffer.allocate((int) inChannel.size());
        inChannel.read(encoded);
        
        inChannel.close();
        raf.close();

        return encoded;
	}
}
