package deco2800.arcade.client.highscores;

import java.util.Arrays;
import java.util.HashMap;

import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.protocol.highscore.*;

public class HighscoreClient {
	String Username;
	String Game_ID;
	private NetworkClient client;
	private HashMap<String, Integer> scoreMap;
	
	//All of the valid score types, excluding WinLoss
	private final String validScoreTypes[] = 
		{"Time", "Number", "Distance"} ;
	
	/*NOTES!
	 * 
	 * - None of the following methods have been tested.
	 * - None of the following methods have been implemented on the server-side
	 * 
	 * - Using a HashMap for storing multiple scores probably isn't a good idea 
	 * 	 as the order that the scores are logged will matter.
	 * 
	 * - Need to build tests for all of the methods that can be tested locally.
	 */
	
	
	//==============================
	//Initializers 
	//==============================
	
	/**
	 * Initializes a new HighscoreClient object that can be used for 
	 * inserting and retrieving high score information from the database.
	 * 
	 * @param Username The username of the user who the scores are for.
	 * @param Game_ID The game that the scores are for.
	 */
	public HighscoreClient(String Username, String Game_ID, NetworkClient client) {
		this.Username = Username;
		this.Game_ID = Game_ID;
		this.client = client;
		
		//Init the score map that is used for multi-valued scores
		scoreMap = new HashMap<String, Integer>();
		
		
		
		//!!!!!The following shows how to send messages to the server.
		AddScoreRequest asr = new AddScoreRequest(); //Create the test message that will be sent
		this.client.sendNetworkObject(asr);	 //Send a test message to the server
	}

	/**
	 * Creates a new HighscoreClient object without a user. This is only used 
	 * only for retrieving scores that do not require a User_ID. If any methods 
	 * that require a user are called, they will fail throwing an exception.
	 * 
	 * @param Game_ID The game that scores are being retrieved for.
	 */
	public HighscoreClient(String Game_ID, NetworkClient client) {
		this(null, Game_ID, client);
	}
	
	
	//==============================
	//Adding Score Methods
	//==============================
	
	//--------------------
	//Single-Valued Scores
	//--------------------
	/**
	 * Stores a single-valued score - attached to the current user and game - 
	 * in the database.
	 * 
	 * @param type: The type of the score that is being stored. For information 
	 * on what types are available, refer to the documentation. If an invalid 
	 * type is provided, an UnsupportedScoreTypeException will be thrown.
	 * 
	 * @param value: The value of the score that is being stored. The database 
	 * only supports integers.
	 */
	public void storeScore(String type, int value) {
		clearMultiScoreQueue(); //Clear the map as it's used by the scores
		addMultiScoreItem(type, value); //Add the item to the map, checking type
		sendScoresToServer();
	}
	
	//--------------------
	//Multi-Valued Scores
	//--------------------
	
	/**
	 * Queues up a new score item to be added to a multi-score entity. Note
	 * that when storeScore, sendMultiScoreItems, 
	 * 
	 * @param type: The type of the score that is being stored. For information 
	 * on what types are available, refer to the documentation. If an invalid 
	 * type is provided, an UnsupportedScoreTypeException will be thrown.
	 * 
	 * @param value: The value of the score that is being stored. The database 
	 * only supports integers.
	 */
	public void addMultiScoreItem(String type, int value) {
		if (typeIsValid(type)) {
			scoreMap.put(type, value);
		} else {
			//TODO Throw an UnsupportedScoreTypeException; "`type` is not a valid score type."
		}
	}
	
	/**
	 * Stores all of the currently queued scores to the database and stores 
	 * them as a multi-valued score entity. When called, this method clears all 
	 * of the scores that are currently queued.
	 */
	public void sendMultiScoreItems() {
		/*This method simply points to another method in order to make the 
		 *API look nicer and have nicer names.*/
		sendScoresToServer();
	}
	
	/**
	 * Clears all of the currently queued scores.
	 */
	public void clearMultiScoreQueue() {
		/*A new map is initialized rather than clearing the current map to 
		 * ensure that the map is not cleared before it is sent off to the 
		 * server. The garbage collector will, obviously, clean up any stray 
		 * maps.*/
		this.scoreMap = new HashMap<String, Integer>();
	}
	
	/**
	 * Checks string type to ensure that it is a valid score type. Returns true 
	 * if it is, and false if it isn't.
	 * 
	 * @param type: The type this is to be validated.
	 */
	private boolean typeIsValid(String type) {
		//Check if type is in validScoreTypes
		for (String validType : validScoreTypes) {
			if (validType.equals(type)) return true;
		}
		
		return false; //None of the types in validScoreTypes match type
	}
	
	/**
	 * Sends the current map of scores to the server so that they can be stored 
	 * in the database. If the class was instantiated without a username,
	 * scores cannot be sent to the server and a UserRequiredException will be 
	 * thrown.
	 */
	private void sendScoresToServer() {
		if (Username != null) { //If the class was instantiated with a user
			//Build the request that is being sent
			AddScoreRequest asr = new AddScoreRequest();
			asr.Username = Username;
			asr.Game_ID = Game_ID;
			asr.scoreMap = scoreMap;
			
			//Send the request
			this.client.sendNetworkObject(asr);
		} else {
			//TODO Throw a UsernameRequiredException; "HighscoreClient must be instantiated with a username in order to be able to add scores".
		}
		
		clearMultiScoreQueue();
	}
	
	
	//--------------------
	//Win/Loss Scores
	//--------------------
	public void logWin() {
		sendWinLoss(1);
	}
	
	public void logLoss() {
		sendWinLoss(-1);
	}
	
	/**
	 * Sends a Win/Loss score to the database.
	 * 
	 * @param val: 1 for a win, -1 for a loss.
	 */
	private void sendWinLoss(int val) {
		clearMultiScoreQueue();
		scoreMap.put("WinLoss", val);
		sendScoresToServer();
	}
	
	
	
	//======================
	//Fetching Score Methods
	//======================
	
	
	
	
}
