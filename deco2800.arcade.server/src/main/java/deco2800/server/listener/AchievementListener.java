package deco2800.server.listener;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import deco2800.arcade.protocol.achievement.AchievementsForIDsRequest;
import deco2800.arcade.protocol.achievement.AchievementsForIDsResponse;
import deco2800.server.ArcadeServer;
import deco2800.server.database.DatabaseException;
import java.util.ArrayList;
import deco2800.arcade.model.Achievement;

public class AchievementListener extends Listener {

	@Override
	public void received(Connection connection, Object object) {
		super.received(connection, object);

		if (object instanceof AchievementsForIDsRequest){
			
			System.out.println("[server]: Just got a AchievementsForIDsRequest packet");
			
			AchievementsForIDsRequest achievementsForIDsRequest = (AchievementsForIDsRequest) object;
			ArrayList<String> achievementIDs = achievementsForIDsRequest.achievementIDs;
			
			
			//try {
				//database look up
				ArrayList<Achievement> achievements = ArcadeServer.instance().getAchievementStorage().achievementsForIDs(achievementIDs);

				AchievementsForIDsResponse achievementsForIDsResponse = new AchievementsForIDsResponse();

				achievementsForIDsResponse.achievements = achievements;
				/*
				if (result == null){
					creditBalanceResponse.balance = -1;
					creditBalanceResponse.description = "No credit balance found";
				} else {
				*/
				
				// TODO needs to be updated to return an achievmenet and not a string, just 
				// for testing the string is there, so delete that at some point
				//achievementsForIDsResponse.achievement = "This should be an achievement instance";
				
				//}
			   		
				connection.sendTCP(achievementsForIDsResponse);
				
			/*} catch (DatabaseException e) {
				e.printStackTrace();
				CreditBalanceResponse creditBalanceResponse = new CreditBalanceResponse();
				creditBalanceResponse.balance = -1;
				creditBalanceResponse.description = e.getMessage();
				connection.sendTCP(creditBalanceResponse);
				
			}*/
		}
	}


}
