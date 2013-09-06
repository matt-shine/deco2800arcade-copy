package deco2800.arcade.arcadeui;

import java.util.ArrayList;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import deco2800.arcade.client.AchievementClient;
import deco2800.arcade.model.Achievement;
import deco2800.arcade.model.AchievementProgress;

public class AchievementList extends Table {

	AchievementClient achClient;
	ArrayList<Achievement> achievements;
	AchievementProgress playerProgress;
	ArrayList<String> awardedIDs;
	
	public AchievementList(Overlay overlay, Skin skin) {
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
		
		
		
		for (int i = 0; i < 4; i++) {
			
			TextButton l = new TextButton("Test Achievement", skin);
			l.setSize(this.getWidth(), 60);
			
			this.add(l).space(20).top().left();
			this.row();
		}
		
		
		
	}
	
	@Override
	public void act(float d) {
		super.act(d);
	}
	
}
