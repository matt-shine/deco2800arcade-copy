package deco2800.arcade.model.test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.junit.Before;
import org.junit.Test;

import deco2800.arcade.model.Accolade;
import deco2800.arcade.model.AccoladeContainer;
import deco2800.arcade.model.XMLReader;


public class XMLReaderTest {
	private Accolade a1;
	private Accolade a2;
	private Accolade a3;
	private Accolade a4;
	private Accolade a5;
	
	
	@Before
	public void initialise(){ //justbeing lazy and using the init from accoladeTest
		/** Accolade(String name, String message, int popup, String popupMessage, 
		* float modifier, String unit, String tag, String imagePath) **/
		a1 = new Accolade("Name1", "String of Accolade1: %VALUE %UNIT", 10, 
				"PopUp Message of Accolade1: %VALUE %UNIT", 1, "Unit1", "Tag1", 
				"Path1").setID(1).setValue(100);
		a2 = new Accolade("Name2", "String of Accolade2: %VALUE %UNIT", 20, 
				"PopUp Message of Accolade2: %VALUE %UNIT", 2, "Unit2", "Tag2", 
				"Path2").setID(2).setValue(200);
		a3 = new Accolade("Name3", "String of Accolade3: %VALUE %UNIT", 30, 
				"PopUp Message of Accolade3: %VALUE %UNIT", 3, "Unit3", "Tag3", 
				"Path3").setID(3).setValue(300);
		a4 = new Accolade("Name4", "String of Accolade4: %VALUE %UNIT", 40, 
				"PopUp Message of Accolade4: %VALUE %UNIT", 4, "Unit4", "Tag4", 
				"Path4").setID(4).setValue(400);
		a5 = new Accolade("Name5", "String of Accolade5: %VALUE %UNIT", 50, 
				"PopUp Message of Accolade5: %VALUE %UNIT", 5, "Unit5", "Tag5", 
				"Path5").setID(5).setValue(500);
	}
	
	/** test the read from xml and the readToString funtion
	 * @throws FileNotFoundException 
	 * @throws XMLStreamException 
	 * @throws UnsupportedEncodingException 
	 * 
	 */
	@Test
	public void XMLReaderTest1() throws FileNotFoundException, UnsupportedEncodingException, XMLStreamException {
		try {
			//this doesn't really do anything just yet.
			String testFile = XMLReader.readXML("src/test/java/deco2800/arcade/model/test/xmlTests/newaccolade.xml");
			System.out.println(new File("src/test/java/deco2800/arcade/model/test/xmlTests/newaccolade.xml").getAbsolutePath());
			AccoladeContainer xmlReadTest = XMLReader.getAccolades("src/test/java/deco2800/arcade/model/test/xmlTests/newaccolade.xml");
		}catch (FileNotFoundException error) {
			fail("The test file is missing.");
			error.printStackTrace();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//XMLStreamReader xmlTestFile = XMLReader.readXML("src/test/java/deco2800/arcade/model/test/xmlTests/newaccolade.xml");
		//System.out.print(XMLReader.XMLToString(xmlTestFile));
		//fail("Not yet implemented");
	}

}
