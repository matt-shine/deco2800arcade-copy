package deco2800.arcade.client;
import java.io.IOException;
//import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import deco2800.arcade.model.Accolade;
import deco2800.arcade.model.AccoladeContainer;
import deco2800.arcade.model.XMLReader;

//import deco.arcade.accolades.servercommunicator //this will be what ever jerry calls it

/** CHANGES
 * @author Mitch
 * Accolades now can longer be referred to by string name. They must be declared in the xml
 * The timer is now part of the accolade system, and multiple timer events are scheduled
 */
//TODO CHANEG THE LOCAL ACCOLADE STORAGE TO A ACCOLADECONTAINER
//TODO fix up the parameter comments for the methods
public class AccoladeSystem {
	//ServerCommunicator server = new ServerCommunicator();
	//TODO add in a watcherPush for games that reset score on death
	private AccoladeContainer localAccolades; //progress for popupevent
	private Map<Double,WatchedAccolade> watchedVariables; //Prepared variables ready for scheduling
	private Map<Double, TimedPush> timerTasks; //timertasks recreated when the timer begins
	private Timer timer;
	//private ResultSet serverData; 
	private String xmlFile;
	private int playerID;
	private int gameID;
	private boolean timerRunning = false;
	//TODO set this to true when server communications is working
	private boolean online = false; 
	
	//constructor used
	/** The constructor used when  specifying the game and player ID.
	 * @param gameID The primary DBkey of your game.
	 * @param playerID The primary DBkey of the player.
	 * @throws IOException 
	 * @throws NumberFormatException 
	 */
	public AccoladeSystem(int gameID, int playerID, String accoladeFolder) 
			throws NumberFormatException, IOException{
		//start the local variables for the overlay system
		this.playerID = playerID;
		this.gameID = gameID;
		this.xmlFile = accoladeFolder + "/accolades.xml";
		//TODO add in image management
		//this.serverData = server.getTable(this.playerID + "," + this.gameID);
		//read in the xml file.
		//set id's for any accolade that does not have them
		//Update the server files based on the localAccolades
		//Create the name, key pairs
		this.localAccolades = XMLReader.getAccolades(this.xmlFile);
		this.localAccolades.setGameID(this.gameID).setPlayerID(this.playerID);
		
		//Maybe throw this into the make local function
		if(online){
			boolean xmlChanged = false;
			//Build an accoladeContainer of the server
			//Loop through the local accolades
			//For the ones with ID's compare them against the server ones. 
			//If they are different then the accolade needs to be updated.
			//Remove them from the server accolade container as they are being fixed up
			
			//While looping. If an accolade does not have an id. A new accolade is made on the server
			//It's assigned to the accolades via .set
			
			//finally, any remaining server accolades are the added to the localaccolades
			//If anything in the localAccolades is changed then the xml is re-written
			if(xmlChanged){
				XMLReader.saveAccoladeContainer(this.localAccolades, this.xmlFile);
			}
			
		} else {
			Random rand = new Random();
			Double randomNum;
			//Cycle through the accolades testing for ones that do not have ID's
			for(Accolade accolade : this.localAccolades){
				while(!accolade.hasID()){
					randomNum = rand.nextDouble();
					try{
						this.localAccolades.get(this.gameID + randomNum);
					} catch (NoSuchElementException error){
						accolade.setID(this.gameID + randomNum);
					}
				}
				
				if(!accolade.hasValue()){
					accolade.setValue(0);
				}
			}//DONE RANDOMLY ASSIGNING KEYS and initialising values
			//NOW WRITE IT TO THE XMLFILE
			//TODO Write to the xml File
			XMLReader.saveAccoladeContainer(this.localAccolades, this.xmlFile);
		}		
	}
	
	//alternate constructor that will eventually just use the default server resources folder
	public AccoladeSystem(int gameID, int playerID) 
			throws NumberFormatException, IOException{
		//FIGURE OUT HOW TO LINK THE SERVER RESOURCE FOLDER
		this(gameID, playerID, "test");
	}
	
	/**The constructor used when automatically fetching the Game and Player ID
	 */
	public AccoladeSystem(){
		//start the local variables for the overlay system
		//TODO Automatically pull the game id and the player id
	}
	
	/** Increments an accolade by {@param increment} amount. Uses an integer for
	 * the accolade.
	 * @param accolade The accolade to be modified in string form.
	 * @param increment The total you would like to increment the accolade by.
	 */
	public void push(Double accoladeID, int increment){
		//TODO check that the increment is a positive value
		Accolade tmpAccolade = localAccolades.get(accoladeID);
		int value = tmpAccolade.getValue();
		int popup = tmpAccolade.getPopup();
		
		if((value + increment)/popup > value/popup){
			//TODO add the over lay stuff in here
			System.out.println(tmpAccolade.toPopupMessage());
		}
		tmpAccolade.setValue(value + increment);
		if(online){
			//TODO THe same increment to the server
		} else {
			//A DISEASED AIDS RIDDEN DIRTY KIND OF FIX
			try {
				XMLReader.saveAccoladeContainer(this.localAccolades, this.xmlFile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//CHANGE JUST THE VALUE FOR THIS ACCOLADE IN XML
				
	}
	
	/** Provides the Double version of an accolade primary key to be used in the
	 * server communication (names may be updated, but the int's will not).
	 * @param accolade The string name for the accolade (matches database).
	 * @return The primary key for the accolade.
	 */
	public Double fetchID(String accolade) throws NullPointerException{
		return this.localAccolades.get(accolade).getID();
	}
	
	/** 
	 * @param table The player_accolades table retrieved from the server
	 * @return An accolade Hashmap<accolade_id, accolade_progress> for local tracking of popups
	 * @throws SQLException //TODO find out when the sql exception is thrown
	 *
	private AccoladeContainer makeLocal(ResultSet table) throws SQLException{
		//this is supposed to be fetching from the server but i'll instead use just the 
		AccoladeContainer accolades = 
				new AccoladeContainer().setGameID(this.gameID).setPlayerID(this.playerID);
		
		//MAKE THE WHOLE ACCOLADE
		while(table.next()){
			Accolade tmpAccolade = new Accolade(table.getString("name"), 
					table.getString("message"), 
					table.getInt("popup"), 
					table.getString("popupMessage"), 
					table.getDouble("modifier"), 
					table.getString("unit"),
					table.getString("tag"),
					table.getString("imagePath")
					).setValue(table.getInt("value"));
		}
		return accolades;
	}
	
	private AccoladeContainer makeLocal(){
		
		if(online){
			//make local from server
			
		} else {
			//make local from xml file	
		}		
		return new AccoladeContainer();
	}
	*/
	
	@SuppressWarnings("unused") //This is used as part of a constructor to force offline (normally online by default
	private AccoladeSystem offline(){
		this.online = true;
		return this;
	}
	
	
	/** TODO remove this as it is no longer needed
	 * @param table The player_accolades table retrieved from the server
	 * @return An accolade Hashmap<accolade_id, accolade_name> for local tracking of popups
	 * @throws SQLException //TODO find out when the sql exception is thrown
	 *
	private HashMap<String, Integer> makeStringIDPairs(ResultSet table) throws SQLException{
		HashMap<String, Integer> stringIDPairs = new HashMap<String, Integer>();
		while(table.next()){
			//accoladeID,accoladeProgress
			stringIDPairs.put(table.getString("name"),table.getInt("accolade_ID"));
		}
		return stringIDPairs;
	}
	*/
	
	/**TIMER STUFF **/
	
	/** Add a watcher to the timer schedule to push accolade progress to the server
	 * @param accoladeID The primary key of the accolade to be updated
	 * @param variable The Integer that is being watched
	 * @param pushInterval How often in milliseconds the game performs the push
	 */
	public void watch(Double accoladeID, Object variable, int pushInterval){
		//Creates a new scheduled push or updates the old one.
		watchedVariables.put(accoladeID,
					new WatchedAccolade(accoladeID, variable, pushInterval));
	}
	
	//THis i called right before a variable is reset between timer events, 
	//it prevents losing the last bit of the variable. Useful for games where the score is reset on death.
	public void resetWatch(double accoladeID, int newvalue){
		this.timerTasks.get(accoladeID).setPrevValue(newvalue);//contains the push event
	}
	
	/** Removes the accolade from the update schedule. Assumes that accolade exists
	 * @param accoladeID The accolade that you do not want automatically updated anymore
	 * @throws NullPointerException If an accolade with that accolade ID is not being watched.
	 */
	public void stopWatching(Double accoladeID)throws NullPointerException{
		if(!this.watchedVariables.containsKey(accoladeID)){
			throw new NullPointerException("There is no accolade being tracked with that ID");			
		}
		if(this.timerRunning){
			//WatchedAccolade accolade = this.watchedVariables.get(accoladeID);
			//Stops the timerTask running
			timerTasks.get(accoladeID).cancel();
			//then removes it so it isn't initialised again
			timerTasks.remove(accoladeID);
		}
		this.watchedVariables.remove(accoladeID);
	}
	
	/**
	 * Starts the timer for all the watched variables.
	 */
	public void start(){
		this.timer = new Timer();
		this.timerRunning = true;
		
		//Schedule all the watched variables as timertasks
		//TODO add the types to Map.Entry
		for(Map.Entry<Double,WatchedAccolade> entry: this.watchedVariables.entrySet()){
			//make it a little easier to handle
			WatchedAccolade accolade = (WatchedAccolade) entry.getValue(); 
			TimedPush timedPush =  new TimedPush(accolade.accoladeID, accolade.variable);
			timerTasks.put(accolade.accoladeID, timedPush);
			timer.schedule(timedPush, accolade.interval);
		}	
	}
	
	/**
	 * Stop the timer and remove all watched values (should set watched variables at the start of each round
	 */
	public void stop(){
		this.timerRunning = false;
		///discards all the events
		this.timer.cancel();
		this.timer.purge();
		timerTasks.clear();
		try {
			XMLReader.saveAccoladeContainer(this.localAccolades, this.xmlFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**NO LONGER NEED PAUSE AND UNPAUSE AS ONLY CHANGES IN THE VARIABLE ARE 
	 * 						PUSHED TO THE SERVER **/
	
	/** END OF TIMER STUFF **/
	
	private class WatchedAccolade{		
		public Object variable;
		public Double accoladeID;
		public int interval;

		public WatchedAccolade(Double accoladeID2, Object variable, int interval){
			this.variable = variable;
			this.accoladeID = accoladeID2;
			this.interval = interval;
		}
	} //end of watched accolade
	
	private class TimedPush extends TimerTask{
		Object variable;
		Double accoladeID;
		int prevValue;
		
		public TimedPush(Double accoladeID, Object variable){
			this.accoladeID = accoladeID;
			this.variable = variable;
			this.prevValue = Integer.valueOf((String) variable);
		}
		public void setPrevValue(int value){
			run();
			this.prevValue = value;
		}
		
		public void run(){
			//TODO add a function that allows the developers to do a manual push 
			// before their variable is reset (say the player dies and their score is reset to zero
			int newValue = Integer.valueOf((String)this.variable);
			if(newValue>this.prevValue){
				push(this.accoladeID,newValue-this.prevValue);
			}

		}
	}//End of Timed Push
	
	
}







