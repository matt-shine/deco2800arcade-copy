package deco2800.arcade.arcadeui;

import java.util.ArrayList;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import deco2800.arcade.client.AchievementClient;
import deco2800.arcade.model.Achievement;
import deco2800.arcade.model.AchievementProgress;

public class AchievementList extends OverlayWindowContent {

	AchievementClient achClient;
	ArrayList<Achievement> achievements;
	AchievementProgress playerProgress;
	ArrayList<String> awardedIDs;
	Overlay overlay = null;
	Skin skin = null;
	boolean initialised = false;
	
	
	
	public AchievementList(Overlay overlay, Skin skin) {
		this.overlay = overlay;
		this.skin = skin;
	}
	
	
	@Override
	public void act(float d) {
		if (!initialised) {
			initialised = true;
			generateButtons();
		}
	}
	
	private void generateButtons() {
		
    	for (int i = this.getChildren().size - 1; i > 0; i--) {
    		this.removeActor(this.getChildren().get(i));
    	}
		
    	//This seems to be broken right now
    	
		/*
		achClient = new AchievementClient(overlay.getNetworkClient());
		achievements = achClient.achievementsForGame(overlay.getHost().getGame());
		playerProgress = achClient.progressForPlayer(overlay.getPlayer());
		
		for(Achievement ach : achievements) {
		    int achProgress = playerProgress.progressForAchievement(ach);
		    double percentage = 100 * (achProgress / (double)ach.awardThreshold);
		}
		*/

		// getting a list of the achievements a player has been awarded
		//awardedIDs = playerProgress.awardedAchievementIDs();
		//achievements = achClient.achievementsForIDs(awardedIDs);
		
		@SuppressWarnings("unused")
		float width = getWidth(), height = getHeight(), x = getX(), y = getY();
		int num = 5;
		
		for (int i = 0; i < num; i++) {
			
			TextButton l = new TextButton("Test Achievement " + i, skin);
			l.setSize(this.getWidth(), 60);
			
			this.addActor(l);
			l.setPosition(30, ((num - i) * 80));
			l.setSize(width - 60, 60);
			
		}
		

	}
	
	@Override
	public void resize(int w, int h) {
		generateButtons();
	}
}
