package deco2800.arcade.arcadeui;

import java.util.ArrayList;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import deco2800.arcade.client.AchievementClient;
import deco2800.arcade.model.Achievement;
import deco2800.arcade.model.AchievementProgress;

public class AchievementList extends Table {

	AchievementClient achClient;
	ArrayList<Achievement> achievements;
	AchievementProgress playerProgress;
	ArrayList<String> awardedIDs;
	
	public AchievementList(Overlay overlay) {
		achClient = new AchievementClient(overlay.getNetworkClient());
		achievements = achClient.achievementsForGame(overlay.getHost().getGame());
		playerProgress = achClient.progressForPlayer(overlay.getPlayer());

		/*for(Achievement ach : achievements) {
		    int achProgress = playerProgress.progressForAchievement(ach);
		    double percentage = 100 * (achProgress / (double)ach.awardThreshold);
		}*/

		// getting a list of the achievements a player has been awarded
		awardedIDs = playerProgress.awardedAchievementIDs();
		achievements = achClient.achievementsForIDs(awardedIDs);

		for(Achievement ach : achievements) {
		    System.out.println(ach.name);
		}
		
		System.out.println(achievements);
		
		
	}
	
}
