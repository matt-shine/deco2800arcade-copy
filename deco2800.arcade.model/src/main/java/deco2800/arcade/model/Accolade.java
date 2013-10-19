package deco2800.arcade.model;
import java.text.DecimalFormat;
import java.util.NoSuchElementException;
//TODO Add in a popup string method

import javax.script.ScriptEngineManager;
import javax.script.ScriptEngine;
import javax.script.ScriptException;

public class Accolade {
	//TODO move the container specific overrides to the AccoladeContainer class	
	
	//BASE variables - IE the accolade must at the very least have these
	private int  popup;
	private double modifier;
	private String name, message, popupMessage, unit, tag, imagePath;
	//OPTIONAL variables - these are assigned as needed through .setX commands
	private Integer id, gameID, value;
	private Accolade next, prev; //FOR THE ACCOLADECONTAINER
	
	
	/** Create a new accolade. Use .setID and .setValue as required.
	 * @param Name The plain name identifier
	 * @param Message The display string that will be used to make toString
	 * @param Unit The unit to be used as part of toString
	 * @param modifier This is to modify the accolade into something interesting,
	 * 			eg grenades as tonnes of TNT etc
	 * @param tag Combined tag that is used as part of Global_Accolades.Table
	 * @param popup When the accolade is a multiple of this a message overlays on screen
	 * @param popupMessage The message to appear onscreeen
	 * @param image The location of the associated accolade image.
	 */
	public Accolade(String name, String message, int popup, String popupMessage, 
			double modifier, String unit, String tag, String imagePath){
		this.name = name;
		this.message = message;
		this.popup = popup;
		this.popupMessage = popupMessage;
		//.replace("%VALUE", "{0}").replace("%%UNIT", "{1}")
		this.modifier = modifier;
		this.unit = unit;
		this.tag = tag; 
		this.imagePath = imagePath; //this might end up being stored as a directory type.
		//TODO - properly get the resolved path
		//We'll make the tostring path return the resolved string and the getstring return the literal string.
		//this.modifiedValue = resolveValue();
	}
	
	//SET STUFF
	public Accolade setID(int key){
		this.id = key;
		return this;
	}
	
	public String getRawString(){
		return this.message;
	}
	
	public Accolade setValue(int value){
		this.value = value;
		return this;
	}
	
	public Accolade setGameID(int gameID){
		this.gameID = gameID;
		return this;
	}
	
	//GET STUFF
	public int getID(){
		return this.id;}
	
	public int getValue(){
		return this.value;}
	
	public int getGameID(){
		//TODO add in error throwing for a nullpointer exception
		return this.gameID;}
	
	public String getName(){
		return this.name;}
	
	public String getUnit(){
		return this.unit;
	}
	
	public double getModifier(){
		return this.modifier;
	}
	
	public String getTag(){
		return this.tag;
	}
	public String getImagePath(){
		return this.imagePath;
	}
	
	public String toString(){
		return parseString(this.message);}
	
	public String getPopup(){
		return parseString(this.popupMessage);}
	
	//HAS STUFF
	
	public boolean hasID(){
		return this.id==null;
	}
	public boolean hasValue(){
		return this.value==null;
	}
	
	//OTHER STUFF	
	private String parseString(String message){
		String s = message.replace("%VALUE", "%s").replace("%UNIT", "%s");
		String firstReplace, secondReplace;
		double value = this.value*this.modifier;
		
		//In some instances the unit might occur before the value
		if( message.indexOf("%VALUE")< message.indexOf("%UNIT")){
			firstReplace = String.valueOf((long) value);
			secondReplace = this.unit;
		} else {
			firstReplace = this.unit;
			secondReplace = String.valueOf((long) value);
		}
		
		if(value > 1){
		value = Math.ceil(value);
		//It's expected that if the value is smaller than 1 the developer intended it to be small
		}
		return String.format(s, firstReplace, secondReplace);
	}
	

		
	public void setNext(Accolade accolade){
		//maybe add in some error checking - not an accolade etc
		next = accolade;
	}
	public void setPrev(Accolade accolade){
		//maybe add in some error checking - not an accolade etc
		prev = accolade;
	}
	
	//error thrown in the container
	public Accolade Next(){
//		if(!hasNext()){
//			throw new NoSuchElementException("There are no accolades after " 
//											+ this.name);
//		}
		return next;
	}
	public Accolade Prev(){
		if(!hasPrev()){	throw new NoSuchElementException("There are no " +
				"accolades before " + name);}
		return prev;
	}
	
	private boolean hasNext(){
		return next==null;
	}
	
	private boolean hasPrev(){
		return prev==null;
	}
	
}
