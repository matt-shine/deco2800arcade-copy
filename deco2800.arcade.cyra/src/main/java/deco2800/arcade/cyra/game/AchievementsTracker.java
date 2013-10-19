package deco2800.arcade.cyra.game;

public class AchievementsTracker {
	/* Tracks the time, hearts lost, enemies killed  */
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
