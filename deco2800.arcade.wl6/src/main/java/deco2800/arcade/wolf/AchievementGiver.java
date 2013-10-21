package deco2800.arcade.wolf;

import deco2800.arcade.client.GameClient;

public class AchievementGiver {
	
	
	public static deco2800.arcade.model.Player p = null;
	public static GameClient g = null;
	
	public static void init(deco2800.arcade.model.Player pl, GameClient ga) {
		p = pl;
		g = ga;
	}
	
	
	public static void give(String a) {
		if (g != null && p != null) {
			
			g.incrementAchievement(a);
			
			
		}
		
		
	}
	
	
}
