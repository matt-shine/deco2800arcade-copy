package deco2800.server.listener;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import deco2800.arcade.protocol.highscore.*;
import deco2800.server.ArcadeServer;
import deco2800.server.database.DatabaseException;
import deco2800.server.database.HighscoreDatabase;

public class HighscoreListener extends Listener {
	
	public HighscoreListener() {
		System.out.println("High Score Listener Initialised");
	}
	
	@Override
    public void received(Connection connection, Object object) {
		 if (object instanceof AddScoreRequest) {
			 AddScoreRequest asr = (AddScoreRequest)object;
			 System.out.println("Recieved message");
		 }
	}
}
