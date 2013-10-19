package deco2800.arcade.accolades;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
//import deco.arcade.accolades.servercommunicator //this will be what ever jerry calls it

public class OldAccoladeSystem {
	//TODO organise the methods so that all the string operations are at the bottom
	//ServerCommunicator server = new ServerCommunicator();
	ResultSet serverData; 
	/**this will be the accolade info read from the xml 
	 * (uses a writetoxml function that is handy dandy
	 */
	
	HashMap<Integer,int[]> localAccolades;
	HashMap<String,Integer> nameIDPairs;
	int playerID;
	int gameID;
	
	final int PROGRESS = 0;
	final int POPUP=1; //when the value is a multiple of the 
	
	
	/**The constructor used when automatically fetching the Game and Player ID
	 */
	public OldAccoladeSystem(){
		//start the local variables for the overlay system
	}
	
	//constructor used
	/** The constructor used when  specifying the game and player ID.
	 * @param gameID The primary DBkey of your game.
	 * @param playerID The primary DBkey of the player.
	 */
	public OldAccoladeSystem(int gameID, int playerID){
		//start the local variables for the overlay system
		this.playerID = playerID;
		this.gameID = gameID;
		//this.serverData = server.getTable(this.playerID + "," + this.gameID);
		//This is the local copy of the accolade progress, it's to save 
		//bandwidth for checking when to do the accolade popup message
		try {
			localAccolades = this.makeLocal(this.serverData);
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
	}
	
	/** Increments an accolade by {@param increment} amount. Uses a string for
	 * the accolade.
	 * @param accolade The accolade to be modified in string form.
	 * @param increment The total you would like to increment the accolade by.
	 */
	public void push(String accolade, int increment){
		this.push(fetchID(accolade), increment);
	}
	
	/** Increments an accolade by {@param increment} amount. Uses an integer for
	 * the accolade.
	 * @param accolade The accolade to be modified in string form.
	 * @param increment The total you would like to increment the accolade by.
	 */
	public void push(int accoladeID, int increment){
		int progress = this.localAccolades.get(accoladeID)[0];
		int popup = this.localAccolades.get(accoladeID)[1];
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
	
	//returns the Primary key for the string the accolade is associated with
	/** Provides the int version of an accolade primary key to be used in the
	 * server communication (names may be updated, but the int's will not).
	 * @param accolade The string name for the accolade (matches database).
	 * @return The primary key for the accolade.
	 */
	public int fetchID(String accolade) throws NullPointerException{
		if(this.nameIDPairs.containsKey(accolade)){
			return this.nameIDPairs.get(accolade);
		} else {
			throw new NullPointerException("No accolade by that name exists. Check your XML");
		}
	}
	
	/** Constructs a watcher to be started at the beginning of
	 * 	each round. Uses the string name of the accolade.
	 * @param accolade The name of the accolade to be watched.
	 * @param variable The change in this Integer will be increment the accolade.
	 * @param time How often push should occur in seconds (defaults to 30 if 
	 * not provided).
	 * @return A new watcher to be used during gameplay.
	 */
	public Watcher watch(String accolade, Object variable, int time){
		return new Watcher(fetchID(accolade), variable, time);
	}
	
	/** Constructs a watcher with a default time of 30 seconds to be started
	 *  at the beginning of each round. Uses the string name of the accolade.
	 * @param accolade The name of the accolade to be watched.
	 * @param variable The change in this Integer will be increment the accolade.
	 * @return A new watcher to be used during gameplay.
	 */
	public Watcher watch(String accolade, Object variable){
		return new Watcher(fetchID(accolade), variable, 30);
	}
	
	/** Constructs a watcher with a default time of 30 seconds to be started
	 *  at the beginning of each round. Uses the string name of the accolade.
	 * @param accolade The primaryID of the accolade to be watched.
	 * @param variable The change in this Integer will be increment the accolade.
	 * @param time How often push should occur in seconds (defaults to 30 if 
	 * not provided).
	 * @return A new watcher to be used during gameplay.
	 */
	public Watcher watch(int accoladeID, Object variable, int time){
		return new Watcher(accoladeID, variable, time);
	}
	
	/** Constructs a watcher that periodically increments and accolade based
	 * 	the difference between the initial value of the variable and every
	 * 	push after that. This constructor defaults the push to every 30 
	 *  seconds.
	 * @param accolade The primaryID of the accolade to be watched.
	 * @param variable The change in this Integer will be increment the accolade.
	 * @return A new watcher to be used during gameplay.
	 */
	public Watcher watch(int accoladeID, Object variable){
		return new Watcher(accoladeID, variable, 30);
	}
	
	/** 
	 * @param table The player_accolades table retrieved from the server
	 * @return An accolade Hashmap<accolade_id, accolade_progress> for local tracking of popups
	 * @throws SQLException //TODO find out when the sql exception is thrown
	 */
	private HashMap<Integer,int[]> makeLocal(ResultSet table) throws SQLException{
		HashMap<Integer, int[]> accolades = new HashMap<Integer,int[]>();
		int[] accoladeInfo;
		while(table.next()){
			//accoladeID, AccoladeProgress/popup event
			accoladeInfo = new int[2];//TODO reduce this to 1 if needed
			accoladeInfo[PROGRESS] = table.getInt("value");
			accoladeInfo[POPUP] = table.getInt("popup");
			accolades.put(table.getInt("accolade_id"),accoladeInfo);
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
	
	
	/**
	 * @author Mitch
	 * This is the class used to periodically push accolade increments.
	 */
	public class Watcher{
		private int cycleTime;
		private int accoladeID;
		private int prevValue;
		private Object watchedVar;
		
		/** Constructs a watcher that periodically increments and accolade based
		 * 	the difference between the initial value of the variable and every
		 * 	push after that.
		 * @param accolade The key of the accolade you're incrementing.
		 * @param variable The variable that the accolade relates to (eg:score).
		 * @param time How often in seconds the push is to occur.
		 */
		public Watcher(int accolade, Object variable, int time){
			//TODO Create the timer!
			this.accoladeID = accolade;
			this.cycleTime = time;
			this.watchedVar = variable;
			//i have no clue what i'm doing here but i hope it works!
			this.prevValue = int.class.cast(variable);
		}
		

		
		/** Starts the timer and begins the push cycle. Use this at the start 
		 * of your round.
		 */
		public void start(){
			//TODO Finish this method
		}
		
		/** Stops the timer and pushes the leftover amount of your integer.
		 *  Use this when you finish a round.
		 */
		public void stop(){
			//TODO Finish this method
		}
		
		/** Pauses the timer allowing for resumption later.
		 */
		public void pause(){
			//TODO Finish this method
		}
		
		/** Resumes from {@link pause()} restarting the timer.
		 */
		public void unpause(){
			//TODO Finish this method
		}
		
		
	}
}







