package deco2800.arcade.client;

import deco2800.arcade.model.Achievement;
import deco2800.arcade.model.Player;

public interface AchievementListener {
	/**
	 * Called when an achievement is awarded to a player.
	 *
	 * @param achievement The achievement awarded.
	 */
	public void achievementAwarded(Achievement achievement);
    
	/**
	 * Called when a player's progress in an achievement is incremented
	 * and they haven't been awarded with it yet (i.e their progress hasn't
	 * reached the threshold for that achievement).
	 *
	 * @param achievement The achievement progress was incremented for.
	 * @param progress    The player's new progress (saves a call to
	 *                    AchievementClient.progressForPlayer).
	 */
	public void progressIncremented(Achievement achievement, int progress);
}