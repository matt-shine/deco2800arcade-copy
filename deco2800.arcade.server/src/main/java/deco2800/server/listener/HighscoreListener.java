package deco2800.server.listener;

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
	
	@Override
    public void received(Connection connection, Object object) {
		 if (object instanceof AddScoreRequest) {
			 AddScoreRequest asr = (AddScoreRequest)object;
			 String[] scoreQueue = deserialisedScores(asr.scoreQueue);
			 
			 /*This following is temporary, simply for showing that the 
			  * connection has succeeded and the data has been sent correctly.*/
			 System.out.println("Recieved add score request for username:" 
					 + asr.Username +" and Game_ID:" + asr.Game_ID + ". Scores: "); 
			 for (int i = 0; i < scoreQueue.length; i+=2) {
				 System.out.println("    Type: " + scoreQueue[i] + "; Value: " + scoreQueue[i+1] + ".");
			 }
			 
		 } else if (object instanceof GetScoreRequest) {
			 GetScoreRequest gsReq = (GetScoreRequest)object;
			 
			 System.out.println("Recieved get score request for username:" 
					 + gsReq.Username +" and Game_ID:" + gsReq.Game_ID + ". RequestNum: " + gsReq.requestType);
			 
			 //Get the data corresponding to the response
			 
			 //Turn the data into a string of values
			 
			 //Create the response
			 GetScoreResponse gsRes = new GetScoreResponse();
			 gsRes.columnNumbers = 5;
			 gsRes.data = "Pants"; //This will be the string of values
			 
			 //Send the response
			 connection.sendTCP(gsRes);
		 }
	}
}
