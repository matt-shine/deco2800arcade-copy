package deco2800.arcade.client.highscores;

import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.protocol.highscore.*;

public class HighscoreClient {
	String Username;
	String Game_ID;
	
	private NetworkClient client;
	
	/**
	 * Initializes a new HighscoreClient object that can be used for 
	 * inserting and retrieving high score information from the database.
	 * 
	 * @param Username The username of the user who the scores are for.
	 * @param Game_ID The game that the scores are for.
	 * 
	 * @throws DatabaseException if the database cannot be initialized.
	 */
	public HighscoreClient(String Username, String Game_ID, NetworkClient client) {
		this.Username = Username;
		this.Game_ID = Game_ID;
		this.client = client;
		
		testMessage();
	}

	/**
	 * Creates a new HighscoreClient object without a user. This is only used 
	 * only for retrieving scores that do not require a User_ID. If any methods 
	 * that require a user are called, they will fail throwing an exception.
	 * 
	 * @param Game_ID The game that scores are being retrieved for.
	 * 
	 * @throws DatabaseException if the database cannot be initialized.
	 */
	public HighscoreClient(String Game_ID, NetworkClient client) {
		this(null, Game_ID, client);
	}
	
	
	/**
	 * A test message that will be sent to the database to show connection 
	 * between client and database.
	 */
	public void testMessage() {
		
		//Create the test message that will be sent
		AddScoreRequest asr = new AddScoreRequest();
		
		//Send a test message to the server
		this.client.sendNetworkObject(asr);	
	}
	
	
}
