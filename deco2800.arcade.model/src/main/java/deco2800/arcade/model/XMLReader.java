package deco2800.arcade.model;

import java.io.File;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import deco2800.arcade.model.Accolade;
import deco2800.arcade.model.AccoladeContainer;
 

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLReader {
	String fileLocation;
	
	public AccoladeContainer getAccolades(String fileLocation){
		Accolade temp;
		Element element;
		String tmpName, tmpMessage, tmpPopupMessage, tmpUnit, tmpTag, tmpImagePath;
		int tmpPopup;
		//The container to be stored to
		AccoladeContainer accoladeContainer = new AccoladeContainer();
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            File file = new File(fileLocation);
            if (file.exists()) {
                Document doc = db.parse(file);
                Element xmlDoc = doc.getDocumentElement();
                
                NodeList accolades = xmlDoc.getElementsByTagName("accolade");
                
                if (accolades !=null && accolades.getLength() > 0){
                	for(int x = 0; x < accolades.getLength(); x++){
                		element = (Element) accolades.item(x);
                		//Repeat for each eccential field
                		tmpName = element.getElementsByTagName("name").item(0).getTextContent();
                					//getTextContent();
                		//temp = new Accolade();
                		
                		//TODO create accolade after reading in from xml
                		/** make the accolade without ID
                		 * if the xml has an id, then assign it afterwards
                		 */
                				//getString("name");
                		
                	}//END OF FOR
                }//END OF IF
                
            }//END OF IF EXISTS
		} catch(Exception E){
			//TODO add in catch statements for each exception
		
		}//END OF TRYCATCH
		return accoladeContainer;
	}//END OF GET ACCOLADES

}
