package deco2800.server.listener;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import deco2800.arcade.protocol.achievement.*;
import deco2800.arcade.protocol.NetworkObject;
import deco2800.server.ArcadeServer;
import deco2800.server.database.AchievementStorage;
import deco2800.server.database.DatabaseException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import deco2800.arcade.model.Achievement;
import deco2800.arcade.model.AchievementProgress;
import deco2800.arcade.model.Game;

public class AchievementListener extends Listener {
	
    private static Logger logger = LoggerFactory.getLogger(AchievementListener.class);


    @Override
    public void received(Connection connection, Object object) {
	super.received(connection, object);
	AchievementStorage storage = ArcadeServer.instance().getAchievementStorage();

	if (object instanceof AchievementsForIDsRequest){		 			
	    AchievementsForIDsRequest req = (AchievementsForIDsRequest) object;
        logger.debug("AchievementsForIDsRequest recieved ID's: {}", req.achievementIDs);
	    AchievementsForIDsResponse resp = new AchievementsForIDsResponse();
	    resp.makeResponse(req);

	    try {
		ArrayList<Achievement> achievements = storage.achievementsForIDs(req.achievementIDs);

		logger.debug("Sending back achievements: {}", achievements.toString());
		resp.achievements = achievements;	     				
	    } catch (DatabaseException e) {
	    logger.error("Failed to retrieve ID's from database");
		e.printStackTrace();
		
		resp.achievements = new ArrayList<Achievement>();
		for (String id : req.achievementIDs)
		    resp.achievements.add(null);
	    }

	    NetworkObject.respond(connection, req, resp);
	} else if (object instanceof IncrementProgressRequest){
	    IncrementProgressRequest req = (IncrementProgressRequest) object;
        logger.debug("IncrementProgressRequest recieved for {}", req.achievementID);
			
	    try {
		//update database
		int newProgress = storage.incrementProgress(req.playerID, req.achievementID);
                // and tell the client if we have new progress
                if (newProgress != -1) {
                    IncrementProgressResponse resp = new IncrementProgressResponse();
                    resp.newProgress = newProgress;
                    if(Achievement.isComponentID(req.achievementID))
                        resp.achievementID = Achievement.idForComponentID(req.achievementID);
                    else
                        resp.achievementID = req.achievementID;

		    // this isn't a response in the request/response manner as the request is
		    // not tied to any future and this response is optional, so we just use sendTCP
		    connection.sendTCP(resp);
                }
	    } catch (DatabaseException e) {
		// ok to not respond here - we're not required to make a response to this
		// particular request so if an error occurs we can just log that it failed
	    	logger.info("Failed to increment achievement progress of {}", req.achievementID);
	    }
	} else if (object instanceof AchievementsForGameRequest){
	    
			
            AchievementsForGameRequest req = (AchievementsForGameRequest)object;
            AchievementsForGameResponse resp = new AchievementsForGameResponse();
            
            try {
            	logger.debug("AchievementsForGameRequest recieved for {}", req.gameID);
		resp.achievements = storage.achievementsForGame(req.gameID);
	    } catch (DatabaseException e) {
	    	logger.error("Failed to retrieve achievements for {}", req.gameID);
		resp.achievements = null;
            }

	    NetworkObject.respond(connection, req, resp);
	} else if (object instanceof ProgressForPlayerRequest){
			
	    ProgressForPlayerRequest req = (ProgressForPlayerRequest)object;
	    ProgressForPlayerResponse resp = new ProgressForPlayerResponse();			
			
	    try {
	    	logger.debug("ProgressForPlayerRequest recieved for player with ID: {}", req.playerID);
	    	resp.achievementProgress = storage.progressForPlayer(req.playerID);		
	    } catch (DatabaseException e) {
	    	logger.error("Failed to get progress for player: {}", req.playerID);
	    	resp.achievementProgress = null;		
	    }

	    NetworkObject.respond(connection, req, resp);
	}
    }


}
