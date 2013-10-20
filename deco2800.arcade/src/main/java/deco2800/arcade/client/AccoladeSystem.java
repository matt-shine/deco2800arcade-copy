package deco2800.arcade.client;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import deco2800.arcade.model.Accolade;
import deco2800.arcade.model.AccoladeContainer;

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
		
	
	private Map<String,Integer> nameIDPairs;
	private AccoladeContainer localAccolades; //progress for popupevent
	private Map<Double,WatchedAccolade> watchedVariables; //Prepared variables ready for scheduling
	private Map<Double, TimedPush> timerTasks; //timertasks recreated when the timer begins
	private Timer timer;
	private ResultSet serverData; 
	private int playerID;
	private int gameID;
	private boolean timerRunning = false;
	//TODO set this to false when the server is working
	private boolean offlineMode = true; 
	
	//when progress%popup = 0 a popup is overlayed on screen
	private static final int PROGRESS = 0;
	private static final int POPUP=1;
	
	
	
	/**The constructor used when automatically fetching the Game and Player ID
	 */
	public AccoladeSystem(){
		//start the local variables for the overlay system
		//TODO Automatically pull the game id and the player id
	}
	
	//constructor used
	/** The constructor used when  specifying the game and player ID.
	 * @param gameID The primary DBkey of your game.
	 * @param playerID The primary DBkey of the player.
	 */
	public AccoladeSystem(int gameID, int playerID, String accoladeFolder){
		//start the local variables for the overlay system
		this.playerID = playerID;
		this.gameID = gameID;
		//this.serverData = server.getTable(this.playerID + "," + this.gameID);
		
		//TODO put these try catches into the methods of each function.
		//This is the local copy of the accolade progress, it's to save 
		//bandwidth for checking when to do the accolade popup message
		try {
			this.localAccolades = this.makeLocal(this.serverData);
		} catch (SQLException error) {
			// TODO Auto-generated catch block
			System.console().printf("There was an error creating the localCopy "
					+ "of the accolade progress: " + error.toString());
			error.printStackTrace();
		}
		
		//these pairs are used for lazy game makers that refer to their 
		//accolades by the string instead of the primary key
		try {
			nameIDPairs = this.makeStringIDPairs(this.serverData);
		} catch (SQLException error) {
			System.console().printf("There was an error creating the Name and "
					+ "primary key pairs for the accolades: " + error.toString());
			error.printStackTrace();
		}	
		/**
		 * *load in the xml file - make sure to assign a playerID to it
		 * *check each xml module
		 * *if a key isn't assigned, then create new accolade server side and modify xml to include the 
		 * the newly assigned AccoladeID (this allows developers to later modify their accolade information by just changing the accolade xml file
		 * *also check for an <imageUpdated>1</imageUpdated> flag, to tell the game to store the new image (also stores the new image if)
		 * *Additionally, if the new filepath is different an manual image update occurs. (IE use the image update flag if the file has the same name)
		 */
		
	}
	
	/** Increments an accolade by {@param increment} amount. Uses an integer for
	 * the accolade.
	 * @param accolade The accolade to be modified in string form.
	 * @param increment The total you would like to increment the accolade by.
	 */
	public void push(Double accoladeID, int increment){
		//TODO check that the increment is a positive value
		Accolade tmpAccolade = localAccolades.get(accoladeID);
		int progress = tmpAccolade.getValue();
		int popup = tmpAccolade.getPopup();
		
		tmpAccolade.setValue(progress + increment);
		//DO THe same increment to the server
		

		
		/**
		 * try {
		 * 		server.put(this.playerID.toString() + "," 
		 * 				+ this.accoladeID.toString() + "," + increment.toString());
		 * 		this.localAccolades.get(accoladeID)[0] += increment;
		 * 		if(progress + increment == popup){
		 * 		//make an overlay message appear here
		 * 		}
		 * } catch (SQLException error) {
		 * 		System.Console().printf("There was an error updating the " +
		 * 			"player progress on the server");
		 * 		error.printStackTrace();
		 * }
		 * 		
		 */
	}
	
	/** Provides the int version of an accolade primary key to be used in the
	 * server communication (names may be updated, but the int's will not).
	 * @param accolade The string name for the accolade (matches database).
	 * @return The primary key for the accolade.
	 */
	public Double fetchID(String accolade) throws NullPointerException{
		/**
		if(this.nameIDPairs.containsKey(accolade)){
			return this.nameIDPairs.get(accolade);
		} else {
			throw new NullPointerException("No accolade by that name exists. Check your XML");
		}
		**/
		return 1.11;
	}
	
	/** 
	 * @param table The player_accolades table retrieved from the server
	 * @return An accolade Hashmap<accolade_id, accolade_progress> for local tracking of popups
	 * @throws SQLException //TODO find out when the sql exception is thrown
	 */
	//TODO
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
	
	/** 
	 * @param table The player_accolades table retrieved from the server
	 * @return An accolade Hashmap<accolade_id, accolade_name> for local tracking of popups
	 * @throws SQLException //TODO find out when the sql exception is thrown
	 */
	private HashMap<String, Integer> makeStringIDPairs(ResultSet table) throws SQLException{
		HashMap<String, Integer> stringIDPairs = new HashMap<String, Integer>();
		while(table.next()){
			//accoladeID,accoladeProgress
			stringIDPairs.put(table.getString("name"),table.getInt("accolade_ID"));
		}
		return stringIDPairs;
	}
	
	
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
	
	/** Removes the accolade from the update schedule. Assumes that accolade exists
	 * @param accoladeID The accolade that you do not want automatically updated anymore
	 * @throws NullPointerException If an accolade with that accolade ID is not being watched.
	 */
	public void stopWatching(int accoladeID)throws NullPointerException{
		if(!this.watchedVariables.containsKey(accoladeID)){
			throw new NullPointerException("There is no accolade being tracked with that ID");			
		}
		if(this.timerRunning){
			WatchedAccolade accolade = this.watchedVariables.get(accoladeID);
			//Stops the timerTask running
			timerTasks.get(accoladeID).cancel();
			//then removes it so it isn't initialised again
			timerTasks.remove(accoladeID);
		}
		
		
	}
	
	/**
	 * Starts the timer for all the watched variables.
	 */
	public void start(){
		this.timer = new Timer();
		this.timerRunning = true;
		
		//Schedule all the watched variables as timertasks
		for(Map.Entry entry: this.watchedVariables.entrySet()){
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
		
		public void run(){
			//TODO add a function that allows the developers to do a manual push 
			// before their variable is reset (say the player dies and their score is reset to zero
			int newValue = Integer.valueOf((String)this.variable);
			//push different if more, or push full value if lower
			if( newValue > this.prevValue){
				push(this.accoladeID,newValue-this.prevValue);
			} else {
				push(this.accoladeID,newValue);
			}
		}
	}//End of Timed Push
	
	
}







