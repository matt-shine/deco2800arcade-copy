package deco2800.arcade.burningskies.screen;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import deco2800.arcade.burningskies.entities.BoringEnemy;
import deco2800.arcade.burningskies.entities.Level1Enemy;

public class SpawnList {
	
	private PlayScreen screen;
	private float currentInterval;
	private float interval;
	private List<Object> list;
	
	private static final long standardEnemyPoints = 1006493;
	
	// TODO more variable pointing to other types of enemies
	private static Texture[] enemyTex = {
		new Texture(Gdx.files.internal("images/ships/enemy1.png")),
		new Texture(Gdx.files.internal("images/ships/enemy2.png")),
		new Texture(Gdx.files.internal("images/ships/enemy3.png"))
	};
	
	public SpawnList(PlayScreen s){
		this.screen = s;
		currentInterval = 0;
		interval = 2f;		
		list = new ArrayList<Object>();
		makeList1();
	}
	
	/* Just a dummy function, but still keep
	 * TODO make each function call setup a
	 * level for enemy spawn sequence
	 */
	private void makeList1() {
		list.add((float) 2); // when to spawn on map
		list.add((float) 1); // interval
		list.add((int) 10); // number of times
		Vector2[] test = new Vector2[2];
		test[0] = new Vector2(1000, 600); // x and y position
		test[1] = new Vector2(-50,-50); // x and y velocity
		list.add(test); 
	}
	
	/* Main function that spawn the enemies at specified intervals
	 * The conditional to check for screen.map.getY() can be further
	 * improved TODO make a list in order contain the spawn sequence of 
	 * enemies
	 */
	public void checkList(float delta) {
		// TODO make an auto queue for the enemies
//		if(screen.level.getTimer() > (Float) list.get(0) && mapCounter != 0)  {
//			interval = (Float) list.get(1);
//			counter = (Integer) list.get(2);
//			mapCounter = 0;
//		}

//		if(interval == (float) 0 || counter == 0)
//			return;
		
		if(currentInterval >= interval) {
//			spawnEnemy(list.get(3));
//			--counter;
			interval -= 0.05;
			if(interval < 0.2) {
				interval = (float) 0.25;
			}
			
			addRandomEnemy();
			currentInterval -= interval;
		}
		currentInterval += delta;
	}

	/**
	 * Automatically random spawn an enemy around the edge of the screen
	 */
	private void addRandomEnemy() {
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
			if(test < 0.1) {
				screen.addEnemy(new Level1Enemy(200, enemyTex[1], new Vector2(startX,startY), new Vector2(vX, vY), screen, screen.getPlayer(), standardEnemyPoints) );
			} else {
				screen.addEnemy(new BoringEnemy(200, enemyTex[0], new Vector2(startX,startY), new Vector2(vX, vY), screen, screen.getPlayer(), standardEnemyPoints) );
			}
	}
	
	public void setTimer(float time) {
		interval = time;
	}
}
