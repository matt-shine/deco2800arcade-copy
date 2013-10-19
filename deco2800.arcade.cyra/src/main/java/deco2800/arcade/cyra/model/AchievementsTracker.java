package deco2800.arcade.cyra.model;

/** This class tracks various information that will be used for current user's
 * achievements, for example the number of hearts lost and also the time taken
 * until the user completes the game.
 *
 * @author Game Over
 */
public class AchievementsTracker {	
	int time = 0;
	int heartsLost = 0;
	int enemiesKilled = 0;
	
	public void incrementHeartsLost() {
		heartsLost++;
	}
	
	public void incrementEnemiesKilled() {
		enemiesKilled++;
	}
	
	public void addToTime(int delta) {
		time += delta;
	}
	
	public void printStats() {
		System.out.println("Time: "+time);
		System.out.println("Hearts Lost: "+heartsLost);
		System.out.println("Enemies Killed: "+enemiesKilled);
	}
}
