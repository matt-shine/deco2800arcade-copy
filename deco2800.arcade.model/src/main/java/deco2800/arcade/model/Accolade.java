package deco2800.arcade.model;
import java.text.DecimalFormat;
import java.util.NoSuchElementException;
//TODO Fix the comment section to allow the helped popup in other java classes




import javax.script.ScriptEngineManager;
import javax.script.ScriptEngine;
import javax.script.ScriptException;

public class Accolade {
	//TODO move the container specific overrides to the AccoladeContainer class	
	
	//BASE variables - IE the accolade must at the very least have these
	private int id, popup,gameID;
	private double modifier;
	private String name, message, popupMessage, unit, tag, imagePath;
	//OPTIONAL variables - these are assigned as needed through .setX commands
	private int value;
	private String resolvedMessage;
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
	
	public Accolade setID(int key){
		this.id = key;
		return this;
	}
	
	public Accolade setValue(int value){
		this.value = value;
		return this;
	}
	
	public Accolade setGameID(int gameID){
		this.gameID = gameID;
		return this;
	}
	
	//returns the accolade ID
	public int getID(){
		return this.id;
	}
	
	public int getValue(){
		return this.value;
	}
	
	public int getGameID(){
		//TODO add in error throwing for a nullpointer exception
		return this.gameID;
	}
	
	public String getName(){
		return this.name;
	}
	public String getRawString(){
		//TODO make a toString method
		return this.message;
	}
	public String toString(){
		String s = this.message.replace("%VALUE", "%s").replace("%UNIT", "%s");
		String firstReplace, secondReplace;
		double value = this.value*this.modifier;
		
		//In some instances the unit might occur before the value
		if(this.message.indexOf("%VALUE")<this.message.indexOf("%UNIT")){
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
	
	private double resolveValue(){
		//TODO figure this out
		return this.value / this.modifier;
	}
		/**
		ScriptEngineManager mgr = new ScriptEngineManager();
		 ScriptEngine scriptEngine = mgr.getEngineByName("Javascript");
		
		System.out.println("modifier: " + modifier);
		modifier = String.format(modifier, value).replaceAll("\\s+","");
		System.out.println("modifier: " + modifier);
		
		try{
			System.out.println("testing 3+2");
			System.out.println(scriptEngine.eval("3+2"));
			//modifiedValue = scriptEngine.eval(modifier);
		} catch (ScriptException e){
			System.out.println("There was an error parsing the equation :" +
								modifier);
			System.out.println(e + e.toString());
		}
		return modifiedValue;
	} **/
	
	private boolean hasNext(){
		return next==null;
	}
	
	private boolean hasPrev(){
		return prev==null;
	}
	
}
