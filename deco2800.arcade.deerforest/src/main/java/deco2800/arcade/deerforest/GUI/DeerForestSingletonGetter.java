package deco2800.arcade.deerforest.GUI;

import deco2800.arcade.client.ArcadeSystem;
import deco2800.arcade.client.GameClient;

public class DeerForestSingletonGetter {

	private static DeerForest instance = null;

    /**
     * Get the instance of DeerForest
     *
     * @return the singleton instance of deerForest
     */
	public static DeerForest getDeerForest() {
		if(instance == null) {
			GameClient game = ArcadeSystem.getCurrentGame();
			if(game instanceof DeerForest) {
				instance = (DeerForest) game;
			}
		}
		
		return instance;
	}
}
