package deco2800.arcade.packman;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import java.util.jar.Pack200;
import java.util.jar.Pack200.*;

public class PackCompress {
	
	private Packer packer;
	
	/**
	 * Initialiser
	 * 
	 * Create an object that can compress and expand Jar files that have 
	 * been packed with Pack200.
	 */
	public PackCompress() {
		
	    // Create the Packer object
	    packer = Pack200.newPacker();
	    
	    Map<String, String> p;
		
	    // Initialize the state by setting the desired properties
	    p = packer.properties();
	    // take more time choosing codings for better compression
	    p.put(Packer.EFFORT, "9");  // default is "5"
	    // use largest-possible archive segments (>10% better compression).
	    p.put(Packer.SEGMENT_LIMIT, "-1");
	    // reorder files for better compression.
	    //p.put(Packer.KEEP_FILE_ORDER, Packer.FALSE);
	    // smear modification times to a single value.
	    p.put(Packer.MODIFICATION_TIME, Packer.LATEST);
	    // ignore all JAR deflation requests,
	    // transmitting a single request to use "store" mode.
	    //p.put(Packer.DEFLATE_HINT, Packer.FALSE);
	    // discard debug attributes
	    //p.put(Packer.CODE_ATTRIBUTE_PFX+"LineNumberTable", Packer.STRIP);
	    // throw an error if an attribute is unrecognized
	    p.put(Packer.UNKNOWN_ATTRIBUTE, Packer.ERROR);
	}
	
	/**
	 * Compress a jar file into a (normally) tar.gz file that is easier to 
	 * transfer across the network.
	 * 
	 * @param inputPath	Relative path to the input jar file
	 * @param outputPath Relative path to the desired location of the compressed file
	 * @throws IOException If file cannot be compressed
	 */
	public void compress(String inputPath, String outputPath) throws IOException {
        JarFile jarFile = new JarFile(inputPath);
        FileOutputStream fos = new FileOutputStream(outputPath);
        
        // Call the packer
        packer.pack(jarFile, fos);
        jarFile.close();
        fos.close();
	}
	
	/**
	 * Expands a (normally) tar.gz file to a jar file that is faster to load.
	 * 
	 * @param inputPath Relative path to the compressed file
	 * @param outputPath Relative path to the desired location of the jar file
	 * @throws IOException If the file cannot be expanded
	 */
	public void expand(String inputPath, String outputPath) throws IOException {
		File f = new File(inputPath);
		FileOutputStream fostream = new FileOutputStream(outputPath);
		JarOutputStream jostream = new JarOutputStream(fostream);
		
		Unpacker unpacker = Pack200.newUnpacker();
		// Call the unpacker
		unpacker.unpack(f, jostream);
		// Must explicitly close the output.
		jostream.close();
	}
	
}
