package deco2800.arcade.client.highscores;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Timer;

import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.protocol.highscore.*;

public class HighscoreClient {
	private String Username;
	private String Game_ID;
	private NetworkClient client;
	
	/*Stores the response that the server sends back*/
	private GetScoreResponse gsRes;
	
	/*Used for queuing up scores that will be sent to the server. Even elements 
	 *are score types, odd elements are score values. Score values are stored 
	 *as strings.*/
	private LinkedList<String> scoreQueue;
	
	//All of the valid score types, excluding WinLoss
	private final String validScoreTypes[] = 
		{"Time", "Number", "Distance"} ;
	
	/*NOTES!
	 * - None of the following methods have been tested.
	 * - None of the following methods have been implemented on the server-side
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
	 *
	 * @param Game_ID The game that the scores are for.
	 * 
	 * @param client The network client for the game; a handle on the database
	 */
	public HighscoreClient(String Username, String Game_ID, NetworkClient client) {
		if (Game_ID != null && client != null) {
			this.Username = Username;
			this.Game_ID = Game_ID;
			this.client = client;
			
			//Allow responses to be received
			this.client.addListener(new HighscoreClientListener(this));
			
			//Init the score list that is used for sending scores
			scoreQueue = new LinkedList<String>();
		} else {
			throw new NullPointerException("Game_ID and client can't be null");
		}
	}

	/**
	 * Creates a new HighscoreClient object without a user. This is only used 
	 * only for retrieving scores that do not require a User_ID. If any methods 
	 * that require a user are called, they will fail throwing an exception.
	 * 
	 * @param Game_ID The game that scores are being retrieved for.
	 * 
	 * @param client The network client for the game; a handle on the database.
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
	 * @param type The type of the score that is being stored. For information 
	 * on what types are available, refer to the documentation. If an invalid 
	 * type is provided, an UnsupportedScoreTypeException will be thrown.
	 * 
	 * @param value The value of the score that is being stored. The database 
	 * only supports integers.
	 */
	public void storeScore(String type, int value) {
		clearMultiScoreQueue(); //Clear the map as it's used by the scores
		addMultiScoreItem(type, value); //Add item to the array, checking type
		sendScoresToServer();
	}
	
	
	//--------------------
	//Multi-Valued Scores
	//--------------------
	
	/**
	 * Queues up a new score item to be added to a multi-score entity. Note
	 * that when storeScore, sendMultiScoreItems, 
	 * 
	 * @param type The type of the score that is being stored. For information 
	 * on what types are available, refer to the documentation. If an invalid 
	 * type is provided, an UnsupportedScoreTypeException will be thrown.
	 * 
	 * @param value The value of the score that is being stored. The database 
	 * only supports integers.
	 */
	public void addMultiScoreItem(String type, int value) {
		if (typeIsValid(type)) {
			scoreQueue.add(type);
			scoreQueue.add(Integer.toString(value));
		} else {
			throw new UnsupportedScoreTypeException(type + " is not a valid score type");
		}
	}
	
	/**
	 * Stores all of the currently queued scores to the database and stores 
	 * them as a multi-valued score entity. When called, this method clears all 
	 * of the scores that are currently queued.
	 * 
	 * If no scores have been queued, a NoQueuedScoresException will be 
	 * thrown.
	 */
	public void sendMultiScoreItems() {
		if (scoreQueue.size() > 0) { //If at least 1 score has been added
			sendScoresToServer();
		} else {
			throw new NoQueuedScoresException();
		}
	}
	
	/**
	 * Clears all of the currently queued scores.
	 */
	public void clearMultiScoreQueue() {
		/*A new map is initialized rather than clearing the current map to 
		 * ensure that the map is not cleared before it is sent off to the 
		 * server. The garbage collector will, obviously, clean up any stray 
		 * maps.*/
		this.scoreQueue = new LinkedList<String>();
	}
	
	/**
	 * Checks string type to ensure that it is a valid score type. Returns true 
	 * if it is, and false if it isn't.
	 * 
	 * @param type The type this is to be checked.
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
	 * scores cannot be sent to the server and a NoUsernameAvailableException
	 * will be thrown.
	 */
	private void sendScoresToServer() {
		if (Username != null) { //If the class was instantiated with a user
			//Build the request that is being sent
			AddScoreRequest asr = new AddScoreRequest();
			asr.username = Username;
			asr.game_ID = Game_ID;
			asr.scoreQueue = serialisedScores();
			
			//Send the request
			this.client.sendNetworkObject(asr);
		} else {
			throw new NoUsernameAvailableException("HighscoreClient must be" +
					" instantiated with a username in order to be able to" + 
					" add scores");
		}
		
		clearMultiScoreQueue();
	}
	
	/**
	 * Turns the scoreQueue list into a single string of comma separated values 
	 * so that it can be send across the network.
	 * 
	 * @return A string of comma separated values representing the values in 
	 * the scoreQueue list.
	 */
	private String serialisedScores() {
		String output = "";
		Iterator<String> scoreIterator = scoreQueue.iterator();
		
		//Traverse through the scoreQueue, adding values to output
		while (scoreIterator.hasNext()) {
			output = output + scoreIterator.next() + ",";
		}
		
		return output;
	}
	
	
	//--------------------
	//Win/Loss Scores
	//--------------------
	/**
	 * Logs a win in the the database for the current user and game
	 */
	public void logWin() {
		sendWinLoss(1);
	}
	
	/**
	 * Logs a loss in the the database for the current user and game
	 */
	public void logLoss() {
		sendWinLoss(-1);
	}
	
	/**
	 * Sends a Win/Loss score to the database.
	 * 
	 * @param val 1 for a win, -1 for a loss.
	 */
	private void sendWinLoss(int val) {
		clearMultiScoreQueue();
		
		//Not using addMultiScoreItem as type doesn't need to be validated
		scoreQueue.add("WinLoss");
		scoreQueue.add(Integer.toString(val));
		
		sendScoresToServer();
	}
	
	
	
	//======================
	//Fetching Score Methods
	//======================
	
	//--------------------
	//Utility Methods
	//--------------------
	
	/**
	 * Creates and sends a new GetScoreRequest to the server of type 
	 * requestType and waits until a response is received. Once the response 
	 * is received it is stored in this.gsReq.
	 * 
	 * @param requestType An integer representing the request that is being 
	 * sent.
	 */
	private void sendScoreRequest(int requestID) {
		//Build the request
		GetScoreRequest gsReq = new GetScoreRequest();
		gsReq.username = this.Username;
		gsReq.game_ID = this.Game_ID;
		gsReq.requestID = requestID;
		
		//Send the response, and wait for a reply
		this.gsRes = null;
		this.client.sendNetworkObject(gsReq); //Send the request
		
		//Wait until a response has been received from the server. Only check every 20ms.
		while (gsRes == null) {
			try { Thread.sleep(20); } 
			catch (InterruptedException e) { break; }
		}
		
		//Will only reach here when a response has been received.
		
	}
	
	/**
	 * Called by HighscoreClientListener whenever a GetScoreResponse is 
	 * received from the server.
	 * 
	 * @param gsRes The response that was received
	 */
	protected void responseRecieved(GetScoreResponse gsRes) {
		this.gsRes = gsRes; //Store the response so it can be used
	}
	
	/**
	 * Returns the number of columns that is returned 
	 * @return the number of columns that is returned.
	 */
	public int responseTest(int num) {
		//This will block until this.gsRes is updated with the correct information.
		sendScoreRequest(num);
		
		if (this.gsRes != null) {
			//gsRes has been updated, so information from it can be returned.
			return this.gsRes.columnNumbers;
		} else {
			return 0; //This is bad. This shouldn't happen.
		}
	}
	
	
	//--------------------
	//Public Methods
	//--------------------
	
	
	
}
