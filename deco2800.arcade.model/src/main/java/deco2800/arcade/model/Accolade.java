package deco2800.arcade.model;
import java.util.NoSuchElementException;

import javax.script.ScriptEngineManager;
import javax.script.ScriptEngine;
import javax.script.ScriptException;

public class Accolade {
	

	private String name;
	private int id, gameID, value, modifier, modifiedValue;
	private String string, unit, tag, image;
	private Accolade next, prev;
	
	
	
	
	/** game Based Constructor - GAMEID is store in the container class to 
	 * reduce redundancy
	 * @param ID The accolade ID.
	 * @param Value The progress of the accolade.
	 * @param Name The plain name identifier
	 * @param String The display string that will be used to make toString
	 * @param Unit The unit to be used as part of toString
	 * @param modifier This is to modify the accolade into something interesting,
	 * 			say grenades as tonnes of TNT or something similar
	 * @param Tag Combined tag that is used as part of Global_Accolades.Table
	 * @param Image The location of the associated accolade image.
	 */
	public Accolade(int ID, int Value, 
			String Name,String String, String Unit, int modifier, String Tag,
			String Image){
		this.id = ID;
		this.value = Value;
		this.modifier = modifier;
		this.string = String.replace("$VALUE", "%s").replace("$UNIT", "%s");
		
		this.unit = Unit;
		//this.modifier = modifier.replace("$VALUE", "%s"); //depreciated
		this.tag = Tag; //might not need this
		this.image = Image; //this might end up being stored as a directory type.
		this.modifiedValue = resolveValue();
	}
	
//	/** Player Centric constructor - 
//	 */
//	public accolade(int GameID, int ID, int Value, 
//			String Name,String String, String Unit, String Modifier, String Tag,
//			String Image){
//		this.gameID = GameID;
//		accolade(ID, Value, Name, String, Unit, Modifier, Tag, Image);
//	}
	
	//returns the accolade ID
	public int getID(){
		return this.id;
	}
	
	public int getValue(){
		return this.value;
	}
	
	public int getGameID(){
		return this.gameID;
	}
	
	public String getName(){
		return name;
	}
	
	public String getUnit(){
		return this.unit;
	}
	
	public int getModifier(){
		return this.modifier;
	}
	
	public String getTag(){
		return this.tag;
	}
	public String getImagePath(){
		return this.image;
	}
	
	//Only an avalible option because it's possible to construct an accolade 
	//without it
	public void setGameID(int GameID){
		gameID = GameID;
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
	
	public String toString(){
		String s = "";
		//{0} and {1} were added on creation
		s = String.format(string, modifiedValue, unit);
		return s;
	}
	
	private int resolveValue(){
		return value / modifier;
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
