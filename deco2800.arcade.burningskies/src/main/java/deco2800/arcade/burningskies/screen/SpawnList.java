package deco2800.arcade.burningskies.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import deco2800.arcade.burningskies.entities.BoringEnemy;
import deco2800.arcade.burningskies.entities.Enemy8;
import deco2800.arcade.burningskies.entities.Level1Enemy;

public class SpawnList {
	
	private PlayScreen screen;
	
	// Spawn enemies at intervals
	private float currentInterval;
	private float interval;
	
	// Variables for linear increase in spawn rate
	private float currentTimer;
	private float decrementTimer;
	
	private int difficulty;
	
	private static final long standardEnemyPoints = 1006493;
	
	private static Texture[] enemyTex = {
		new Texture(Gdx.files.internal("images/ships/enemy1.png")),
		new Texture(Gdx.files.internal("images/ships/enemy2.png")),
		new Texture(Gdx.files.internal("images/ships/enemy3.png")),
		new Texture(Gdx.files.internal("images/ships/enemy8.png"))
	};
	
	public SpawnList(PlayScreen s, int difficulty){
		this.screen = s;
		currentInterval = 0;
		interval = 2f;	
		currentTimer = 0;
		decrementTimer = 5f;
		this.difficulty = difficulty;
	}
		
	/**
	 * Main function that spawn the enemies at specified intervals and decreases
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
			interval -= 0.05;
			if(interval < 0.2) {
				interval = (float) 0.25;
			}
			currentTimer -= decrementTimer;
		}
		
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
			float vX = (float) Math.ceil(Math.random() * (widthC - startX))/10 + (widthC - startX)/10;
			float vY = (float) Math.ceil(Math.random() * (heightC - startY))/7 + (heightC - startY)/5;

			// Add some random enemies
			double test = Math.random();
			if(test >= 0.2 && test < 0.5) {
				screen.addEnemy(new Level1Enemy(400, enemyTex[1], new Vector2(startX,startY), new Vector2(vX, vY), screen,
						screen.getPlayer(), standardEnemyPoints, difficulty) );
			} else if (test < 0.2) {
				screen.addEnemy(new Enemy8(400, enemyTex[3], new Vector2(startX,startY), new Vector2(vX, vY), screen, 
						screen.getPlayer(), standardEnemyPoints, difficulty) );
			} else {
				screen.addEnemy(new BoringEnemy(200, enemyTex[0], new Vector2(startX,startY), new Vector2(vX, vY), screen,
						screen.getPlayer(), standardEnemyPoints, difficulty) );
			}
	}
	
	/**
	 * Set the interval to spawn enemies
	 * @param time
	 */	
	public void setTimer(float time) {
		interval = time;
	}
}
