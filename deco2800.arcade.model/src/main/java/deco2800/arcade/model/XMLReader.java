package deco2800.arcade.model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.xml.stream.*;

public class XMLReader {
	XMLInputFactory inputFactory = XMLInputFactory.newInstance();
	//TODO add in a general read xml file
	
	public AccoladeContainer getAccolades(String fileLocation){
		Accolade tmpAccolade;
		//Probably should test the file exists before this is ran
		String tmpName = null, tmpMessage = null, tmpPopupMessage = null, tmpUnit = null, 
				tmpTag = null, tmpImage = null;
		//Supplying a value is for developers during alpha testing
		//Supply an ID signifies an existing accolade is to be changed
		//A missing ID signifies a new accolade must be created in the server
		//the update image flag means the image should be forced to update
		int tmpPopup = -1, tmpValue = -1, tmpID = -1, tmpGameID = -1;
		double tmpModifier = -1;
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
					if((tmpName!=null) && (tmpMessage!=null) && 
							(tmpPopup!=-1) && (tmpPopupMessage!=null) && 
							(tmpUnit!=null) && (tmpTag!=null) && (tmpImage!=null)){
						tmpAccolade = new Accolade(tmpName, tmpMessage, 
								tmpPopup, tmpPopupMessage, tmpModifier, 
								tmpUnit, tmpTag, tmpImage);
						//Give it an id if one exists
						if(tmpID!=-1){
							tmpAccolade.setID(tmpID);
						}
						//Give it a value if specified
						if(tmpValue!=-1){
							tmpAccolade.setValue(tmpValue);
							//then remove value flag from the xml
						}
						accoladeContainer.add(tmpAccolade);
						//reset all the vars
						
					}
				}
			}//END OF FILE              
			if(tmpGameID!=-1){
				accoladeContainer.setGameID(tmpGameID);
			}else {
				//GO NUTS MAYYYUNN< SOMETHINS IS WRONG
				//TODO implement exception for missing gameID
			}
		} catch (FileNotFoundException fileNotFound){
			//add in some sort of throw here for file not found
			
		}catch(Exception E){
			//TODO add in catch statements for each exception
		
		}//END OF TRYCATCH
		return accoladeContainer;
	}//END OF GET ACCOLADES
	
	public boolean resetRequest(String fileLocation){
		//implment resetRequest function
		//checks the file for a reset flag and returns true or false.
		return false;
	}
	
	public boolean resetRequestRemove(String fileLocation){
		//TODO implement resetRequestRemove function
		//exactly like resetRequest except it also removes the flag fromt he file once done
		return false;
	}
}
