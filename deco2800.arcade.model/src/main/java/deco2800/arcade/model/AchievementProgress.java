package deco2800.arcade.model;

import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import deco2800.arcade.model.Achievement;

/**
 * Contains a player's progress in achievements. Instances of this class are
 * immutable and don't stay sync'ed with the server.
 */
public class AchievementProgress {
    
    private HashMap<String, Boolean> awarded;
    private HashMap<String, Integer> progress;

    /**
     * Initialises an AchievementProgress instance with the given progress
     * and awarded maps. Both maps have achievement IDs as their keys - the
     * progress map tracks a player's progress in those achievements (any
     * achievements not in that map are assumed to have 0 progress) and the
     * awarded map tracks whether the player has been awarded that achievement.
     *
     * @param progress A map of achievement IDs to the player's progress in
     *                 the achievements.
     * @param awarded  A map of achievement IDs to whether the player has been
     *                 awarded those achievements.
     */
    public AchievementProgress(HashMap<String, Integer> progress,
                               HashMap<String, Boolean> awarded) {
	// ok to do this instead of a deep copy as Strings, Integers and Booleans
	// are immutable
        this.progress = new HashMap<String, Integer>(progress);
        this.awarded = new HashMap<String, Boolean>(awarded);
    }
    
    /**
     * Returns the progress in a given achievement.
     *
     * @param achievement The achievement in question
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
     * achievements that have been awarded. The list is in no particular
     * order.
     */
    public ArrayList<String> awardedAchievementIDs() {
        return new ArrayList<String>(awarded.keySet());
    }
    
    /**
     * Returns a list of achievement IDs corresponding to all of the
     * achievements that the player has some progress in but hasn't
     * been awarded with yet.
     */
    public ArrayList<String> inProgressAchievementIDs() {
        ArrayList<String> list = new ArrayList<String>();
        for(Map.Entry<String, Integer> e : progress.entrySet()) {
            if(!awarded.containsKey(e.getKey())) {
                list.add(e.getKey());
            }
        }
        return list;
    }

}