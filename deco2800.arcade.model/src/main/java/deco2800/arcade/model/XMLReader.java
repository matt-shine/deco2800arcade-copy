package deco2800.arcade.model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.xml.stream.*;

public class XMLReader {
	XMLInputFactory inputFactory = XMLInputFactory.newInstance();
	//TODO add in a general read xml file
	
	public AccoladeContainer getAccolades(String fileLocation){
		//Probably should test the file exists before this is ran
		String tmpName, tmpMessage, tmpPopupMessage, tmpUnit, 
				tmpTag, tmpImage;
		//Supplying a value is for developers during alpha testing
		//Supply an ID signifies an existing accolade is to be changed
		//A missing ID signifies a new accolade must be created in the server
		//the update image flag means the image should be forced to update
		int tmpPopup, tmpValue, tmpID, tmpGameID;
		double tmpModifier;
		AccoladeContainer accoladeContainer = new AccoladeContainer();
		try {
			XMLStreamReader xmlFile = inputFactory.createXMLStreamReader(
					new FileInputStream(fileLocation));
			while(xmlFile.hasNext()){
				if(xmlFile.isStartElement()){
					String tag = xmlFile.getLocalName(); //Assigned here so it only needs to be fetched once
					if(tag.equals("id")){ 
						tmpID = Integer.parseInt(xmlFile.getElementText());
					} else if(tag.equals("name")){ 
						tmpName = xmlFile.getElementText();
					} else if (tag.equals("message")){
						tmpMessage = xmlFile.getElementText();
					} else if (tag.equals("modifier")){
						tmpModifier = Double.parseDouble(xmlFile.getElementText());
					} else if (tag.equals("popup")){
						tmpPopup = Integer.parseInt(xmlFile.getElementText());
					} else if (tag.equals("popupMessage")){
						tmpPopupMessage = xmlFile.getElementText();
					} else if (tag.equals("unit")){
						tmpUnit = xmlFile.getElementText();
					}else if (tag.equals("tag")){
						tmpTag = xmlFile.getElementText();
					} else if (tag.equals("image")){
						tmpImage = xmlFile.getElementText();
					} else if (tag.equals("value")){
						tmpValue = Integer.parseInt(xmlFile.getElementText());
					} else if (tag.equals("gameID")){
						tmpGameID = Integer.parseInt(xmlFile.getElementText());
					}//END OF PSUDEOSWITCH
				} else if(xmlFile.isEndElement() && 
						xmlFile.getLocalName().equals("accolade")){
					//check that all the minimal items are set
					//Make the accolade here
					//add it to the container
				}
			}
			

                
		} catch (FileNotFoundException fileNotFound){
			//add in some sort of throw here for file not found
			
		}catch(Exception E){
			//TODO add in catch statements for each exception
		
		}//END OF TRYCATCH
		return accoladeContainer;
	}//END OF GET ACCOLADES

}
