package deco2800.arcade.packman.test;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.junit.Test;

import deco2800.arcade.packman.PackCompress;
import deco2800.arcade.packman.PackageUtils;


public class packmanTest {
	
/*	@Test
	public void gettingClassName() {
		assertEquals(PackageClient.getClassName(1), "Pong");		
	}
*/	
	@Test
	public void test() {
		assertEquals(1,1);
	}
	
	@Test
	public void MD5TestNotNull() {
		assertTrue(PackageUtils.genMD5("test.txt") != null);
	}
	
	@Test
	public void MD5Test() {
		assertEquals(PackageUtils.genMD5("test.txt"), "016FD69CDBF285D51490D1FF753B7436");
	}
	
	@Test
	public void compressionTest() throws IOException {
		PackCompress packCompress = new PackCompress();
		packCompress.compress("test.jar", "test1.tar.gz");
		packCompress.expand("test1.tar.gz", "test2.jar");
        assertEquals(getClasses("test.jar"),getClasses("test2.jar"));
	}
	
	private List<String> getClasses(String path) throws IOException {
		List<String> classNames=new ArrayList<String>();
		ZipInputStream zip=new ZipInputStream(new FileInputStream(path));
		for(ZipEntry entry=zip.getNextEntry();entry!=null;entry=zip.getNextEntry())
		    if(entry.getName().endsWith(".class") && !entry.isDirectory()) {
		        StringBuilder className=new StringBuilder();
		        for(String part : entry.getName().split("/")) {
		            if(className.length() != 0)
		                className.append(".");
		            className.append(part);
		            if(part.endsWith(".class"))
		                className.setLength(className.length()-".class".length());
		        }
		        classNames.add(className.toString());
		    }
		zip.close();
		return classNames;
	}
	
}