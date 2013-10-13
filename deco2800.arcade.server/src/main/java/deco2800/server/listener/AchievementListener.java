package deco2800.server.listener;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import deco2800.arcade.protocol.achievement.*;
import deco2800.arcade.protocol.BlockingMessage;
import deco2800.server.ArcadeServer;
import deco2800.server.database.DatabaseException;
import java.util.ArrayList;
import deco2800.arcade.model.Achievement;
import deco2800.arcade.model.AchievementProgress;

public class AchievementListener extends Listener {

	@Override
	public void received(Connection connection, Object object) {
		super.received(connection, object);

		if (object instanceof AchievementsForIDsRequest){		 			
			AchievementsForIDsRequest req = (AchievementsForIDsRequest) object;
            System.out.println("[Server]: AchievementsForIDsRequest recieved (" + req.achievementIDs);

			try {
				//database look up
				ArrayList<Achievement> achievements = ArcadeServer.instance().getAchievementStorage().achievementsForIDs(req.achievementIDs);

				if (achievements == null) {
					// Bad things happened look up failed, find out why act appropriately
					// or panic
                    System.out.println("Couldn't look up achievements!!!");
				} else {
					System.out.println(achievements.toString());
                    AchievementsForIDsResponse resp = new AchievementsForIDsResponse();
                    resp.achievements = achievements;
			   		BlockingMessage.respond(connection, req, resp);
				}
				
			} catch (DatabaseException e) {
				e.printStackTrace();
				
			}
		}
		
		if (object instanceof IncrementProgressRequest){
			IncrementProgressRequest incrementProgressRequest = (IncrementProgressRequest) object;
			String achievementID = incrementProgressRequest.achievementID;
			int playerID= incrementProgressRequest.playerID;
            System.out.println("[Server]:  IncrementProgressRequest recieved (" + achievementID + ")");
			
			try {
				//update database
				int newProgress = ArcadeServer.instance().getAchievementStorage().incrementProgress(playerID, achievementID);
                // and tell the client if we have new progress
                if (newProgress != -1) {
                    IncrementProgressResponse resp = new IncrementProgressResponse();
                    resp.newProgress = newProgress;
                    if(Achievement.isComponentID(achievementID))
                        resp.achievementID = Achievement.idForComponentID(achievementID);
                    else
                        resp.achievementID = achievementID;
                    connection.sendTCP(resp);
                }
			} catch (DatabaseException e) {
				e.printStackTrace();
				
			}
		}
		if (object instanceof AchievementsForGameRequest){
			System.out.println("[Server]:  AchievementsForGameRequest recieved");
			
            AchievementsForGameRequest req = (AchievementsForGameRequest)object;
            AchievementsForGameResponse resp = new AchievementsForGameResponse();
            String gameID = req.gameID;
            
            try {
				//update database
			    resp.achievements =
						ArcadeServer.instance().getAchievementStorage().achievementsForGame(gameID);
                BlockingMessage.respond(connection, req, resp);
			} catch (DatabaseException e) {
				e.printStackTrace();
                // can't let the client keep blocking, just send an empty list
                resp.achievements = new ArrayList<Achievement>();
                BlockingMessage.respond(connection, req, resp);
            }			
		}
		if (object instanceof ProgressForPlayerRequest){
			System.out.println("[Server]:  ProgressForPlayerRequest recieved");
			
			ProgressForPlayerRequest progressForPlayerRequest = (ProgressForPlayerRequest) object;
			int playerID = progressForPlayerRequest.playerID;
			
			
			try {
				//update database
				AchievementProgress achievementProgress = 
						ArcadeServer.instance().getAchievementStorage().progressForPlayer(playerID);
	
					
				ProgressForPlayerResponse progressForPlayerResponse = new ProgressForPlayerResponse();
			   		
				connection.sendTCP(progressForPlayerResponse);
				
			} catch (DatabaseException e) {
				e.printStackTrace();
				
			}
		}
	}


}
