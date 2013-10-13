package deco2800.arcade.client.highscores;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.protocol.highscore.*;

public class HighscoreClient {
	private String Username;
	private String Game_ID;
	private NetworkClient client;
	
	/*Stores the response that the server sends back*/
	private GetScoreResponse gsRes;
	
	/*Stores the scores that were sent back from the server*/
	private List<Highscore> scoreResponseList;
	
	/*Used for queuing up scores that will be sent to the server. Even elements 
	 *are score types, odd elements are score values. Score values are stored 
	 *as strings.*/
	private LinkedList<String> scoreQueue;
	private int queuedScoresCount = 0;
	
	//All of the valid score types, excluding WinLoss
	private final String validScoreTypes[] = 
		{"Time", "Number", "Distance"} ;
	
	
	//=============================================================
	//Initializers 
	//=============================================================
	
	/**
	 * Initializes a new HighscoreClient object that can be used for 
	 * inserting and retrieving high score information from the database.
	 * 
	 * @param user The username of the user who the scores are for.
	 *
	 * @param game The game that the scores are for.
	 * 
	 * @param client The network client for the game; a handle on the database
	 */
	public HighscoreClient(String user, String game, NetworkClient client) {
		if (game != null && client != null) {
			this.Username = user;
			this.Game_ID = game;
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
	 * @param game The game that scores are being retrieved for.
	 * 
	 * @param client The network client for the game; a handle on the database.
	 */
	public HighscoreClient(String game, NetworkClient client) {
		this(null, game, client);
	}
	
	
	
	//=============================================================
	//Fetching Score Methods
	//=============================================================
	
	//--------------------
	//Utility Methods
	//--------------------
	
	/**
	 * Creates and sends a new GetScoreRequest to the server of type 
	 * requestType and waits until a response is received. Once the response 
	 * is received it is stored in this.gsReq.
	 * 
	 * @param requestID An integer representing the request that is being 
	 * sent.
	 */
	private void sendScoreRequest(GetScoreRequest gsReq) {
		//Build the request
		gsReq.username = this.Username;
		gsReq.game_ID = this.Game_ID;
		
		//Check the type is valid
		checkTypeValidity(gsReq.type);
		
		//Send the response, and wait for a reply
		this.gsRes = null;
		this.client.sendNetworkObject(gsReq); //Send the request
		
		//Wait until a response has been received from the server. Only check every 20ms.
		//Will not continue past here until a response has been received.
		while (gsRes == null) {
			try { Thread.sleep(20); } 
			catch (InterruptedException e) { break; }
		}

		//Convert the scores back to something readable
		this.scoreResponseList = deserialiseAsHighscore(this.gsRes.data);
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
	 * Converts a string of comma separated values into a list of Highscore 
	 * objects that can be iterated through by the user.
	 * 
	 * @return
	 */
	private List<Highscore> deserialiseAsHighscore(String serialScores) {
		String[] splitScores = serialScores.split(",");
		int numberOfColumns = 4; //Total number of columns
		int currentColumn = 0; //The current column being modified
		

		//Make the list that will be output once created
		List<Highscore> output = new ArrayList<Highscore>();
		Highscore currentItem = new Highscore();
		
		for (String scoreItem : splitScores) {
			//Set the properties for the score items in the list
			switch (currentColumn) {
				case 0:
					currentItem.playerName = scoreItem;
					break;
				case 1:
					currentItem.score = Integer.parseInt(scoreItem);
					break;
				case 2:
					currentItem.date = scoreItem;
					break;
				case 3:
					currentItem.type = scoreItem;
					
					output.add(currentItem); //Last column, so add this one to the list and move on
					currentItem = new Highscore(); //Create a new item for the next row
					break;
			}
			
			//Move onto the next column, wrapping around to the start
			currentColumn = (currentColumn + 1) % numberOfColumns; 
		}

		return output;
	}
	
	
	//--------------------
	//Public Methods
	//--------------------
	
	/**
	 * requestID: 1. This function is user INDEPENDENT.
	 * 
	 * @param limit The number of top players to be returned.
	 * 
	 * @param highestIsBest If having a high score is best for your game, then
	 * set this to true. If having a low score is best, then set this to false.
	 * 
	 * @return A list of Highscore objects. 
	 */
	public List<Highscore> getGameTopPlayers(int limit, boolean highestIsBest, String type) {
		GetScoreRequest gsReq = new GetScoreRequest();
		gsReq.requestID = 1; //Telling the server which query to run
		gsReq.limit = limit;
		gsReq.type = type;
		gsReq.highestIsBest = highestIsBest;
		
		//Send the request off, waiting for response before continuing
		sendScoreRequest(gsReq);
		
		//Now that the response is back, return the data to the user
		return this.scoreResponseList;
	}
	
	
	
	//=============================================================
	//Adding Score Methods
	//=============================================================
	
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
	 * Queues up a new score item to be added to a multi-score entity.
	 * 
	 * @param type The type of the score that is being stored. For information 
	 * on what types are available, refer to the documentation. If an invalid 
	 * type is provided, an UnsupportedScoreTypeException will be thrown.
	 * 
	 * @param value The value of the score that is being stored. The database 
	 * only supports integers.
	 */
	public void addMultiScoreItem(String type, int value) {
		if (checkTypeValidity(type)) {
			scoreQueue.add(type);
			scoreQueue.add(Integer.toString(value));
			this.queuedScoresCount++;
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
		/*A new list is initialized rather than clearing the current list to 
		 * ensure that the list is not cleared before it is sent off to the 
		 * server. The garbage collector will, obviously, clean up any stray 
		 * lists.*/
		this.scoreQueue = new LinkedList<String>();
		this.queuedScoresCount = 0;
	}
	
	/**
	 * Returns the number of scores that are currently queued up to be sent to 
	 * the database.
	 */
	public int queuedScoreCount() {
		return this.queuedScoresCount;
	}
	
	/**
	 * Checks string type to ensure that it is a valid score type. Returns true 
	 * if it is, and false if it isn't.
	 * 
	 * @param type The type this is to be checked.
	 */
	private boolean checkTypeValidity(String type) {
		//Check if type is in validScoreTypes
		for (String validType : validScoreTypes) {
			if (validType.equals(type)) return true;
		}
		
		//Type is invalid, throw an exception
		throw new UnsupportedScoreTypeException(type + " is not a valid score type");
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
	
	
	
	//=============================================================
	//Public General Utility Methods
	//=============================================================
	/**
	 * Prints out all of the scores in hs to the console. This can be used for 
	 * debugging to ensure that scores are being stored correctly.
	 * 
	 * @param hs A list of scores that are to be printed to the console.
	 */
	public static void printHighscores(List<Highscore> hs) {
		System.out.println("Scores Printed:");
		
		for (int i = 0; i < hs.size(); i++) {
			System.out.println("Row: " + i);
			System.out.println("        Name: " + hs.get(i).playerName);
			System.out.println("        Score: " + hs.get(i).score);
			System.out.println("        Date: " + hs.get(i).date);
			System.out.println("        Type: " + hs.get(i).type);
		}
	}
	
}
