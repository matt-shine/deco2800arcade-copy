package deco2800.arcade.burningskies.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import deco2800.arcade.burningskies.entities.BoringEnemy;
import deco2800.arcade.burningskies.entities.Enemy8;
import deco2800.arcade.burningskies.entities.Level1Enemy;

/**
 *  Create enemy objects for the game and adds them.
 */
public class SpawnList {
	
	private PlayScreen screen;
	
	// Spawn enemies at intervals
	private float currentInterval;
	private float interval;
	
	// Variables for linear increase in spawn rate
	private float currentTimer;
	private float decrementTimer;
	
	private int difficulty;
	
	private long enemyPoints;
	
	private static Texture[] enemyTex = {
		new Texture(Gdx.files.internal("images/ships/enemy1.png")),
		new Texture(Gdx.files.internal("images/ships/enemy2.png")),
		new Texture(Gdx.files.internal("images/ships/enemy3.png")),
		new Texture(Gdx.files.internal("images/ships/enemy8.png"))
	};
	
	/**
	 * Controls the spawn rate and how the enemy spawns 
	 * @param s
	 * @param difficulty
	 */
	public SpawnList(PlayScreen s, int difficulty){
		this.screen = s;
		currentInterval = 0;
		interval = 3f;	
		currentTimer = 0;
		decrementTimer = 5f;
		this.difficulty = difficulty;
		this.enemyPoints = (long) (1006493*(0.5*difficulty));
	}
		
	/**
	 * A function that spawn the enemies at specified intervals and decreases
	 * the interval at a linear rate (currently set at 5 seconds) 
	 */
	public void checkList(float delta) {
		// Spawn enemies are regular interval
		if(currentInterval >= interval) {
			addRandomEnemy();
			currentInterval -= interval;
		}
		
		// Decrement the interval time
		if(currentTimer >= decrementTimer) {
			interval -= 0.05*difficulty;
			if(interval < 0.2) {
				interval = (float) 0.25;
			}
			currentTimer -= decrementTimer;
		}
		
		// Increase the timer by delta
		currentInterval += delta;
		currentTimer += delta;
	}

	/**
	 * Automatically random spawn an enemy around the edge of the screen
	 */
	public void addRandomEnemy() {
			float startX = (float) 0;
			float startY = (float) 0;
			
			float widthC = (float) 740;
			float heightC = (float) 360;
					
			int direction = (int) Math.ceil(Math.random() * 4);
			
			// Determine where the enemy will start to spawn
			switch (direction) {
			case 1: // top
				startY = (float) 720;
				startX = (float) Math.ceil(Math.random() * 1000) + 100;
				break;
			case 2: // right
				startX = 1280;
				startY = (float) Math.ceil(Math.random() * 600) + 50;
				break;
			case 3: // bottom
				startY = 0;
				startX = (float) Math.ceil(Math.random() * 1000) + 100;
				break;
			case 4: // left
				startX = 0;
				startY = (float) Math.ceil(Math.random() * 600) + 50;
				break;			
			}
			
			// Randomly set the x and y velocity
			float vX = (float) (Math.ceil(Math.random() * (widthC - startX))/10 + (widthC - startX)/10)*difficulty;
			float vY = (float) (Math.ceil(Math.random() * (heightC - startY))/7 + (heightC - startY)/5)*difficulty;

			// Add some random enemies according to the set difficulty
			double test = Math.random();
			int enemyL1Hp = 200 + (60*difficulty);
			int enemy8Hp = 300 + (80*difficulty);
			int enemyBorHp = 75 + (35*difficulty);
			if(test >= (0.2*(0.5*difficulty)) && test < (0.5*(0.34*difficulty))) {
				screen.addEnemy(new Level1Enemy(enemyL1Hp, enemyTex[1], new Vector2(startX,startY), new Vector2(vX, vY), screen,
						screen.getPlayer(), enemyPoints, difficulty) );
			} else if (test < (0.2*(0.5*difficulty))) {
				screen.addEnemy(new Enemy8(enemy8Hp, enemyTex[3], new Vector2(startX,startY), new Vector2(vX, vY), screen, 
						screen.getPlayer(), enemyPoints, difficulty) );
			} else {
				screen.addEnemy(new BoringEnemy(enemyBorHp, enemyTex[0], new Vector2(startX,startY), new Vector2(vX, vY), screen,
						screen.getPlayer(), enemyPoints, difficulty) );
			}
	}
	
	/**
	 * Set the interval to spawn enemies
	 * @ensure time != null
	 */	
	public void setTimer(float time) {
		interval = time;
	}
}
