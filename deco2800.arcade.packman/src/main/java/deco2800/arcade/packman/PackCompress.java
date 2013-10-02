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
	
	Packer packer;
	Map<String, String> p;
	
	/**
	 * Initialiser
	 * 
	 * Create an object that can compress and expand Jar files that have 
	 * been packed with Pack200.
	 */
	public PackCompress() {
		
		// Create the Packer object
		packer = Pack200.newPacker();
		
		// Initialize the state by setting the desired properties
	    p = packer.properties();
	    // take more time choosing codings for better compression
	    p.put(Packer.EFFORT, "7");  // default is "5"
	    // use largest-possible archive segments (>10% better compression).
	    p.put(Packer.SEGMENT_LIMIT, "-1");
	    // reorder files for better compression.
	    p.put(Packer.KEEP_FILE_ORDER, Packer.FALSE);
	    // smear modification times to a single value.
	    p.put(Packer.MODIFICATION_TIME, Packer.LATEST);
	    // ignore all JAR deflation requests,
	    // transmitting a single request to use "store" mode.
	    p.put(Packer.DEFLATE_HINT, Packer.FALSE);
	    // discard debug attributes
	    p.put(Packer.CODE_ATTRIBUTE_PFX+"LineNumberTable", Packer.STRIP);
	    // throw an error if an attribute is unrecognized
	    p.put(Packer.UNKNOWN_ATTRIBUTE, Packer.ERROR);
	}
	
	public void Compress(String inputPath, String outputPath) throws IOException {
        JarFile jarFile = new JarFile(inputPath);
        FileOutputStream fos = new FileOutputStream(outputPath);
        
        // Call the packer
        packer.pack(jarFile, fos);
        jarFile.close();
        fos.close();
	}
	
	public void Expand(String inputPath, String outputPath) throws IOException {
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
