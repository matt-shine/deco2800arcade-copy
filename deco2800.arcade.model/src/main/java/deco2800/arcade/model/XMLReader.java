package deco2800.arcade.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.xml.stream.*;

public class XMLReader {
		
	public static AccoladeContainer getAccolades(String fileLocation) 
			throws NumberFormatException, IOException{
		AccoladeContainer output = new AccoladeContainer();
		Accolade tmpAccolade;
		String tmpName = null, tmpMessage = null, tmpPopupMessage = null, tmpUnit = null, 
				tmpTag = null, tmpImage = null;
		Integer tmpPopup =null, tmpValue = null, tmpID =null, tmpGameID = null;
		Double tmpModifier = null;
		String tag;
		String line;
		String data = "";
		
		BufferedReader xmlFile = new BufferedReader(new FileReader(fileLocation));
		while((line = xmlFile.readLine()) != null){
			//<name> "Grenades Exploded" </name>
			tag = line.substring(line.indexOf("<")+1, line.indexOf(">"));
			
			// "Grenades Exploded" </name>
			line = line.substring(line.indexOf(">")+1);
			//Grenades Exploded
			if(line.indexOf("<") != -1){
				data = line.substring(0, line.indexOf("<"));
				data = data.trim().replace("\"", "");
			} 
			
			if(tag.equals("id")){ tmpID = Integer.parseInt(data);
			} else if(tag.equals("name")){ tmpName = data;
			} else if (tag.equals("message")){ tmpMessage = data;
			} else if (tag.equals("modifier")){ tmpModifier = Double.parseDouble(data);
			} else if (tag.equals("popup")){ tmpPopup = Integer.parseInt(data);
			} else if (tag.equals("popupMessage")){ tmpPopupMessage = data;
			} else if (tag.equals("unit")){	tmpUnit = data;
			} else if (tag.equals("tag")){ tmpTag = data;
			} else if (tag.equals("image")){ tmpImage = data;
			} else if (tag.equals("value")){ tmpValue = Integer.parseInt(data);
			} else if (tag.equals("gameID")){ tmpGameID = Integer.parseInt(data);}
			//TODO might not need to store the gameID inside of the xml file. 
			//Meh (could maybe include multiple games in the one xml)
			
			if(tag.equals("/accolade")){
				if((tmpName!=null) && (tmpMessage!=null) &&	(tmpPopup!=null) && (tmpPopupMessage!=null) && 
						(tmpUnit!=null) && (tmpTag!=null) && (tmpImage!=null)){
					tmpAccolade = new Accolade(tmpName, tmpMessage, 
							tmpPopup, tmpPopupMessage, tmpModifier, 
							tmpUnit, tmpTag, tmpImage);
					
					//Give it an id if one exists
					
					if(tmpID!=null){ tmpAccolade.setID(tmpID);
					} 					
					//Give it a value if specified
					if(tmpValue!=null){ tmpAccolade.setValue(tmpValue);
					} 
					//possibly remove the value from the xml
					output.add(tmpAccolade);
				} else {
					//THROW SOME ERROR SAYING NOT ALL REQUIRED ELMENTS ARE HERE AND TO CHECK THE XML
				}//DONE CHECKING IF ALL FIELDS WERE CREATED
			}//DONE CREATING AND APPENDING THE ACCOLADE	
			
		}
		
		xmlFile.close();
		return output;
		
	}
	
	public static String readXML(String fileLocation) throws FileNotFoundException{
		String xml = "";
		String line = null;
		if(!(new File(fileLocation).exists())){
			throw new FileNotFoundException("The files does not exist: " + fileLocation);		
		}
		
		try {
			BufferedReader xmlFile = new BufferedReader(new FileReader(fileLocation));	
			while((line = xmlFile.readLine()) != null){
				xml += line.trim();
			}		
			xmlFile.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return xml;
	}
	
	public boolean resetRequest(String fileLocation){
		//implment resetRequest function
		//checks the file for a reset flag and returns true or false.
		//The reset flag would remove all entries from the table and then build
		//jsut the ones in the xml file 
		return false;
	}
	
	public boolean resetRequestRemove(String fileLocation){
		//TODO implement resetRequestRemove function
		//exactly like resetRequest except it also removes the flag fromt he file once done
		return false;
	}
}
