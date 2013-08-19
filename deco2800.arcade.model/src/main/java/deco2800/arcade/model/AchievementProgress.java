package deco2800.arcade.model;

import java.util.ArrayList;
import java.util.HashMap;
import deco2800.arcade.model.Achievement;
import deco2800.arcade.model.Player;

public class AchievementProgress {
    
    private Player player;
    private HashMap<String, Integer> progress;

    public AchievementProgress(Player player) {
        this.player = player;
        //this.progress = progress;
    }
    
    /**
     * Returns the progress of this instance's player for the given achievement.
     *
     * 
     */
    public int progressForAchievement(Achievement achievement) {
        return 0;
    }

    /**
     * Returns a list of achievement IDs corresponding to all of the
     * achievements that the player has been awarded.
     */
    public ArrayList<String> awardedAchievementIDs() {
        return null;
    }
    
    /**
     * Returns a list of achievement IDs corresponding to all of the
     * achievements that the player has some progress in but hasn't
     * been awarded with yet.
     */
    public ArrayList<String> inProgressAchievementIDs() {
        return null;
    }

}