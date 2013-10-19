package deco2800.arcade.cyra.model;

import deco2800.arcade.cyra.world.Sounds;

public class ResultsScreen {

	private final static int DECREMENT_AMOUNT = 3976;
	
	private int healthAtLastCall;
	private int timeAtLastCall;
	private int timeBonus;
	private int healthBonus;
	
	private float count;
	private boolean isShowing;
	private boolean playSound;
	
	public ResultsScreen() {
		count = 0;
		timeAtLastCall = 0;
		healthAtLastCall = Player.DEFAULT_HEARTS;
		isShowing = false;
	}
	
	public void showResults(int time, int hearts) {
		timeBonus = (time - timeAtLastCall) * 10000;
		timeAtLastCall = time;
		healthBonus = (hearts) * 100000;
		healthAtLastCall = hearts;
		isShowing = true;
		playSound = true;
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
		return addToScore;
	}
	
	public int getTimeBonus() {
		return timeBonus;
	}
	
	public int getHealthBonus() {
		return healthBonus;
	}
	
	public boolean isShowing() {
		return isShowing;
	}
}
