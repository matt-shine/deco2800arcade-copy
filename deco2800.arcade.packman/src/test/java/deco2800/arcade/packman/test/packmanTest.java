package deco2800.arcade.packman.test;

import static org.junit.Assert.*;

/*import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
*/
import org.junit.Test;

import deco2800.arcade.packman.PackageUtils;


public class packmanTest {
	
/*	@Test
	public void gettingClassName() {
		assertEquals(PackageClient.getClassName(1), "Pong");		
	}
*/	
	@Test
	public void Test() {
		assertEquals(1,1);
	}
	
	@Test
	public void MD5Test() {
		assertTrue(PackageUtils.genMD5("games.txt") != null);
	}
	
/**	@Test
	public void CompressionTest() {	
		PackCompress.Compress("slf4j-api-1.7.5.jar", "test1.tar.gz");
		PackCompress.Expand("test1.tar.gz", "test2.jar");
		BufferedReader bfr2 = new BufferedReader(new InputStreamReader(
	            System.in));
	    String s1 = "", s2 = "";
	    String y = "", z = "";

	    File file1 = new File("slf4j-api-1.7.5.jar");
	    File file2 = new File("test2.jar");

	    BufferedReader bfr = new BufferedReader(new FileReader(file1));
	    BufferedReader bfr1 = new BufferedReader(new FileReader(file2));

	    while ((z = bfr1.readLine()) != null)
	        s2 += z;

	    while ((y = bfr.readLine()) != null)
	        s1 += y;
        assertEquals(s1,s2);
	}*/
}