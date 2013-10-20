package deco2800.arcade.cyra.model;

import deco2800.arcade.cyra.world.Sounds;

public class ResultsScreen {

	private final static int DECREMENT_AMOUNT = 3976;
	private final static float INITIAL_WAIT_TIME = 4f;
	
	private int healthAtLastCall;
	private int timeAtLastCall;
	private int timeBonus;
	private int healthBonus;
	private int callNumber;
	private int rankMultiplier;
	
	private float count;
	private boolean isShowing;
	private boolean playSound;
	
	public ResultsScreen() {
		count = 0;
		timeAtLastCall = 0;
		healthAtLastCall = Player.DEFAULT_HEARTS;
		isShowing = false;
		callNumber = 0;
		
	}
	
	public void showResults(int time, int hearts, float rank) {
		int maxTime = 100;
		if (callNumber ==1) {
			maxTime = 270;
		}
		rankMultiplier = (int)(rank * 100);
		timeBonus = (maxTime-time + timeAtLastCall) * 10000;
		if (timeBonus <0) timeBonus=0;
		timeAtLastCall = time;
		healthBonus = (hearts) * 100000;
		healthAtLastCall = hearts;
		isShowing = true;
		playSound = true;
		Sounds.playVictoryMusic();
		count = -INITIAL_WAIT_TIME;
		callNumber++;
	}
	
	public int update(float delta) {
		boolean timeDone = false;
		boolean healthDone = false;
		count += delta;
		int addToScore = 0;
		if (count >= 0.05f) {
			
			if (timeBonus >= DECREMENT_AMOUNT) {
				addToScore += DECREMENT_AMOUNT;
				timeBonus -= DECREMENT_AMOUNT;
			} else if (timeBonus > 0) {
				addToScore += timeBonus;
				timeBonus = 0;
			} else {
				timeDone = true;
			}
			
			if (healthBonus >= DECREMENT_AMOUNT) {
				addToScore += DECREMENT_AMOUNT;
				healthBonus -= DECREMENT_AMOUNT;
			} else if (healthBonus > 0) {
				addToScore += healthBonus;
				healthBonus = 0;
			} else {
				healthDone = true;
			}
			
			if (!timeDone || !healthDone) {
				if (playSound) {
					Sounds.playCoinSound(0.5f);
				}
				playSound = !playSound;
				count = 0f;
			} else if (count >= 3f){
				isShowing = false;
				
			}
		}
		return addToScore * rankMultiplier;
	}
	
	public int getTimeBonus() {
		return timeBonus;
	}
	
	public int getHealthBonus() {
		return healthBonus;
	}
	
	public int getRankMultiplier() {
		return rankMultiplier;
	}
	
	public boolean isShowing() {
		return isShowing;
	}
}
