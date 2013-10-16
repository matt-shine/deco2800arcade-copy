package deco2800.arcade.accolades;

public class AccoladeSystem {
	//TODO organise the methods so that all the string operations are at the bottom
	
	/**The constructor used when automatically fetching the Game and Player ID
	 */
	public AccoladeSystem(){
		//start the local variables for the overlay system
	}
	
	//constructor used
	/** The constructor used when  specifying the game and player ID.
	 * @param gameID The primary DBkey of your game.
	 * @param playerID The primary DBkey of the player.
	 */
	public AccoladeSystem(int gameID, int playerID){
		//start the local variables for the overlay system
	}
	
	/** Increments an accolade by {@param increment} amount. Uses a string for
	 * the accolade.
	 * @param accolade The accolade to be modified in string form.
	 * @param increment The total you would like to increment the accolade by.
	 */
	public void push(String accolade, int increment){
		push(fetchID(accolade), increment);
	}
	
	/** Increments an accolade by {@param increment} amount. Uses an integer for
	 * the accolade.
	 * @param accolade The accolade to be modified in string form.
	 * @param increment The total you would like to increment the accolade by.
	 */
	public void push(int accolade, int increment){
		//say HIIIII to the server, then pass along 
		//"pXXXX,aXXXXXX, int"
	}
	
	//returns the Primary key for the string the accolade is associated with
	/** Provides the int version of an accolade primary key to be used in the
	 * server communication (names may be updated, but the int's will not).
	 * @param accolade The string name for the accolade (matches database).
	 * @return The primary key for the accolade.
	 */
	public int fetchID(String accolade){
		return -1;
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
		return new Watcher(fetchID(accolade), variable);
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
	
	/** Constructs a watcher with a default time of 30 seconds to be started
	 *  at the beginning of each round. Uses the string name of the accolade.
	 * @param accolade The primaryID of the accolade to be watched.
	 * @param variable The change in this Integer will be increment the accolade.
	 * @return A new watcher to be used during gameplay.
	 */
	public Watcher watch(int accoladeID, Object variable){
		return new Watcher(accoladeID, variable);
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
		
		/** Constructs a watcher that periodically increments and accolade based
		 * 	the difference between the initial value of the variable and every
		 * 	push after that. This constructor defaults the push to every 30 
		 *  seconds.
		 * @param accolade The key of the accolade you're incrementing.
		 * @param variable The variable that the accolade relates to (eg:score).
		 */
		public Watcher(int accoladeID, Object variable){
			this(accoladeID, variable, 30);
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







