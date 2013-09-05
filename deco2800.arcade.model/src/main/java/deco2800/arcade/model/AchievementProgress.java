package deco2800.arcade.model;

import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import deco2800.arcade.model.Achievement;
import deco2800.arcade.model.Player;

public class AchievementProgress {
    
    private HashMap<String, Boolean> awarded;
    private HashMap<String, Integer> progress;

    public AchievementProgress(HashMap<String, Integer> progress,
                               HashMap<String, Boolean> awarded) {
        // ok to just clone - Strings, Integers and Booleans are immutable
        this.progress = (HashMap<String, Integer>)progress.clone();
        this.awarded = (HashMap<String, Boolean>)awarded.clone();
    }
    
    /**
     * Returns the progress of this instance's player for the given achievement.
     *
     * @param achievement  The achievement of question
     */
    public int progressForAchievement(Achievement achievement) {
        if(!progress.containsKey(achievement.id)) {
            return 0;
        } else {
            return progress.get(achievement.id);
        }
    }

    /**
     * Returns a list of achievement IDs corresponding to all of the
     * achievements that the player has been awarded. The list is in no
     * particular order.
     */
    public ArrayList<String> awardedAchievementIDs() {
        return new ArrayList(awarded.keySet());
    }
    
    /**
     * Returns a list of achievement IDs corresponding to all of the
     * achievements that the player has some progress in but hasn't
     * been awarded with yet.
     */
    public ArrayList<String> inProgressAchievementIDs() {
        ArrayList<String> list = new ArrayList();
        for(Map.Entry<String, Integer> e : progress.entrySet()) {
            if(!awarded.containsKey(e.getKey())) {
                list.add(e.getKey());
            }
        }
        return list;
    }

}