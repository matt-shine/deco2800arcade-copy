package deco2800.server.listener;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import deco2800.arcade.protocol.achievement.*;
import deco2800.server.ArcadeServer;
import deco2800.server.database.DatabaseException;
import java.util.ArrayList;
import deco2800.arcade.model.Achievement;
import deco2800.arcade.model.AchievementProgress;
import deco2800.arcade.model.Player;
import deco2800.arcade.model.Game;

public class AchievementListener extends Listener {

	@Override
	public void received(Connection connection, Object object) {
		super.received(connection, object);

		if (object instanceof AchievementsForIDsRequest){
			
			System.out.println("[Server]: AchievementsForIDsRequest recieved");
			
			AchievementsForIDsRequest achievementsForIDsRequest = (AchievementsForIDsRequest) object;
			ArrayList<String> achievementIDs = achievementsForIDsRequest.achievementIDs;
			
			
			try {
				//database look up
				ArrayList<Achievement> achievements = ArcadeServer.instance().getAchievementStorage().achievementsForIDs(achievementIDs);

				if (achievements == null){
					// Bad things happened look up failed, find out why act appropriately
					// or panic
				} else {
					
				AchievementsForIDsResponse achievementsForIDsResponse = new AchievementsForIDsResponse();

				achievementsForIDsResponse.achievements = achievements;
			   		
				connection.sendTCP(achievementsForIDsResponse);
				}
				
			} catch (DatabaseException e) {
				e.printStackTrace();
				
			}
		}
		
		if (object instanceof IncrementProgressRequest){
			System.out.println("[Server]:  IncrementProgressRequest recieved");
			
			IncrementProgressRequest incrementProgressRequest = (IncrementProgressRequest) object;
			String achievementID = incrementProgressRequest.achievementID;
			Player player = incrementProgressRequest.player;
			
			
			try {
				//update database
				ArcadeServer.instance().getAchievementStorage().incrementProgress(player, achievementID);
				//will need to get a return value when I figure out how to return stuff
				//to the client
					
				IncrementProgressResponse incrementProgressResponse = new IncrementProgressResponse();
			   		
				connection.sendTCP(incrementProgressResponse);
				
			} catch (DatabaseException e) {
				e.printStackTrace();
				
			}
		}
		if (object instanceof AchievementsForGameRequest){
			System.out.println("[Server]:  AchievementForGameRequest recieved");
			
			AchievementsForGameRequest achievementsForGameRequest = (AchievementsForGameRequest) object;
			Game game = achievementsForGameRequest.game;
			
			
			try {
				//update database
				ArrayList<Achievement> achievements = 
						ArcadeServer.instance().getAchievementStorage().achievementsForGame(game);
	
					
				AchievementsForGameResponse achievementsForGameResponse = new AchievementsForGameResponse();
				achievementsForGameResponse.achievements = achievements;
			   		
				connection.sendTCP(achievementsForGameResponse);
				
			} catch (DatabaseException e) {
				e.printStackTrace();
				
			}
		}
		if (object instanceof ProgressForPlayerRequest){
			System.out.println("[Server]:  ProgressForPlayerRequest recieved");
			
			ProgressForPlayerRequest progressForPlayerRequest = (ProgressForPlayerRequest) object;
			Player player = progressForPlayerRequest.player;
			
			
			try {
				//update database
				AchievementProgress achievementProgress = 
						ArcadeServer.instance().getAchievementStorage().progressForPlayer(player);
	
					
				ProgressForPlayerResponse progressForPlayerResponse = new ProgressForPlayerResponse();
			   		
				connection.sendTCP(progressForPlayerResponse);
				
			} catch (DatabaseException e) {
				e.printStackTrace();
				
			}
		}
	}


}
