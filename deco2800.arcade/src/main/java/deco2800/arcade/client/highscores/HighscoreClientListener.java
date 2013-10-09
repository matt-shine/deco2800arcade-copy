package deco2800.arcade.client.highscores;

import deco2800.arcade.client.network.listener.NetworkListener;
import deco2800.arcade.protocol.credit.CreditBalanceResponse;
import deco2800.arcade.protocol.highscore.*;
import com.esotericsoftware.kryonet.Connection;

public class HighscoreClientListener extends NetworkListener {
	
	private HighscoreClient hsClient;
	
	/**
	 * Creates a new HighscoreClientListener.
	 * 
	 * @param hsClient The HighscoreClient that messages will be send to when 
	 * responses are received from the database.
	 */
	public HighscoreClientListener(HighscoreClient hsClient) {
		this.hsClient = hsClient;
	}
	
	
	@Override
	public void received(Connection connection, Object object) {
		super.received(connection, object);
		
		if (object instanceof GetScoreResponse){
			hsClient.responseRecieved((GetScoreResponse)object); //Send the response back to the highscore client
		}
	}
}
