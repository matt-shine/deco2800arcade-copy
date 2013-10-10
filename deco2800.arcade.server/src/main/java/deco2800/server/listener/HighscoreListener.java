package deco2800.server.listener;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import deco2800.arcade.protocol.highscore.*;
import deco2800.server.ArcadeServer;
import deco2800.server.database.DatabaseException;
import deco2800.server.database.HighscoreDatabase;

public class HighscoreListener extends Listener {
	
	/*hsDatabase is used to insert, fetch and delete data from the database.*/
	HighscoreDatabase hsDatabase;
	
	/**
	 * Creates a new HighscoreListener object.
	 */
	public HighscoreListener() {
		hsDatabase = ArcadeServer.instance().getHighscoreDatabase();
	}
	
	/**
	 * As the scores must be sent across the network as a single string, this 
	 * methods converts the string that was sent across the network back to an 
	 * array, so that it it can be worked with.
	 * 
	 * @param serialScores: A string of comma separated values that will be 
	 * converted into an array, split at commas.
	 * 
	 * @return An array of strings created from separating serialScores at 
	 * every comma.
	 */
	private String[] deserialisedScores(String serialScores) {
		return serialScores.split(",");
	}
	
	/**
	 * Returns a string of comma separated values representing the data in 
	 * queryResult. The first value in queryResult is skipped, as it is the 
	 * number of columns.
	 * 
	 * @param queryResult - The values, return by a query, that are to be 
	 * turned into a string. Must always have at least 1 value (that is the 
	 * number of columns).
	 * 
	 * @return queryResult as a String of comma separated values.
	 */
	private String serialisedData(List<String> queryResult) {
		String output = "";
		
		Iterator<String> resultIterator = queryResult.iterator();
		resultIterator.next();
		
		//Traverse through the scoreQueue, adding values to output
		while (resultIterator.hasNext()) {
			output = output + resultIterator.next() + ",";
		}
		
		return output;
	}
	
	
	
	@Override
    public void received(Connection connection, Object object) {
		
		//if the request sent is to add a score
		 if (object instanceof AddScoreRequest) {
			 AddScoreRequest asr = (AddScoreRequest)object;
			 String[] scoreQueue = deserialisedScores(asr.scoreQueue);
			 
			 LinkedList<String> types = new LinkedList<String>();
			 LinkedList<Integer> scores = new LinkedList<Integer>();
			 
			 /*This following is temporary, simply for showing that the 
			  * connection has succeeded and the data has been sent correctly.*/
			 System.out.println("Recieved add score request for username:" 
					 + asr.player_id +" and Game_ID:" + asr.game_ID + ". Scores: "); 
			 for (int i = 0; i < scoreQueue.length; i+=2) {
				 types.addLast(scoreQueue[i]);
				 scores.addLast(Integer.parseInt(scoreQueue[i+1]));
				 
				 System.out.println("    Type: " + scoreQueue[i] + "; Value: " + scoreQueue[i+1] + ".");
			 }
			 
			 try {
				 hsDatabase.updateScore(asr.game_ID, asr.player_id, scoreQueue[0], Integer.parseInt(scoreQueue[1]));
			 } catch (Exception e) {
				 e.printStackTrace();
			 }
			 
		 
		 } else if (object instanceof GetScoreRequest) {
			 GetScoreRequest gsReq = (GetScoreRequest)object;

			 //Get the data that's needed
			 List<String> queryResult = hsDatabase.fetchData(gsReq);
			 
			 //hsDatabase.fetchData doesn't return anything at the moment, so this is just a placeholder column number for until it does
			 queryResult = new LinkedList<String>();
			 queryResult.add(0, "5");
			 
			 //Create the response
			 GetScoreResponse gsRes = new GetScoreResponse();
			 gsRes.columnNumbers = Integer.parseInt(queryResult.get(0));
			 gsRes.data = serialisedData(queryResult);
			 
			 //Send the response
			 connection.sendTCP(gsRes);
		 }
	}
}
